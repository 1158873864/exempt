package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.order.BizData;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import njurestaurant.njutakeout.util.StringParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TransactionBlServiceImpl implements TransactionBlService {
    private final SupplierDataService supplierDataService;
    private final PlatformOrderDataService platformOrderDataService;
    private final AlipayDataService alipayDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final DeviceDataService deviceDataService;

    private final String TRANSFERSOLIDURL = "alipays://platformapi/startapp?appId=20000123%26actionType=scan%26biz_data={\"s\": \"money\",\"u\":";
    private final String TRANSFERPASSURL = "alipays://platformapi/startapp?appId=09999988%26actionType=toAccount%26sourceId=contactAmount%26chatLoginId=%26chatUserId=";


    @Autowired
    public TransactionBlServiceImpl(SupplierDataService supplierDataService, PlatformOrderDataService platformOrderDataService, AlipayDataService alipayDataService, AlipayOrderDataService alipayOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, DeviceDataService deviceDataService) {
        this.supplierDataService = supplierDataService;
        this.platformOrderDataService = platformOrderDataService;
        this.alipayDataService = alipayDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.deviceDataService = deviceDataService;
    }

    @Override
    public void addDevice() {
        Supplier supplier = supplierDataService.findSupplierById(10);
        Device device = new Device(supplier);
        deviceDataService.saveDevice(device);
    }

    /**
     * web客户端发起获取二维码请求
     *
     * 成功，则服务器返回：
     * {url:"我们平台的网址(在二维码未失效的情况下可重定向到支付宝付款地址) 重定向的接口",status:"success",orderid:"订单号"}
     * 失败，则服务器返回：
     * {status:"failed",reason:"失败原因（目前有网络异常、未获取到供码设备）"}
     *
     * @param getQrCodeParameters web端发来的消息{IP:"ip地址",id:"充值方编号",money:"待付款金额",商户id:"merchantid",time="10位的时间戳",sign="签名算法(http://kfb.im/index/doc/sign.do)"}
     * @return
     * @throws WrongIdException 供码用户id错误/用户id对应的用户身份不为商户 抛出异常
     */
    @Override
    public GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException {
        Integer id = StringParseUtil.StringToInt(getQrCodeParameters.getMerchantId());
        if(StringParseUtil.StringToInt(getQrCodeParameters.getTime()) == null || StringParseUtil.StringToDouble(getQrCodeParameters.getMoney()) == null) {
            throw new BlankInputException();
        }
        double money = StringParseUtil.StringToDouble(getQrCodeParameters.getMoney());
        Date date = FormatDateTime.TenTimestampToDate(StringParseUtil.StringToInt(getQrCodeParameters.getTime()));
        if(id == null) {
            throw new WrongIdException();
        } else {
            User user = userDataService.getUserById(id);
            if(user == null || user.getRole() != 3) {
                throw new WrongIdException();
            } else {
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                // 数据库中没有该商户信息
                if(merchant == null) {
                    throw new WrongIdException();
                }

                // id正确，开始寻找供码用户
                Random random = new Random();
                List<Supplier> supplierList = supplierDataService.findSuppliersByLevel(merchant.getPriority());
                int len = supplierList.size();
                int randomNumber;
                Supplier chosenSupplier = null;
                Device chosenDevice = null;
                while (len > 0) {
                    // 随机挑选一个供码者
                    randomNumber = random.nextInt(len);
                    chosenSupplier = supplierList.get(randomNumber);
                    supplierList.remove(randomNumber);

                    // 查找该用户设备
                    List<Device> devices = chosenSupplier.getDevices();
                    int dLen = devices.size();
                    while (dLen > 0) {
                        randomNumber = random.nextInt(dLen);
                        chosenDevice = devices.get(randomNumber);
                        devices.remove(randomNumber);
                        if(chosenDevice.getOnline() == 0) {
                            dLen--;
                            continue;    // 平台账号在该设备上离线
                        }

                        // TODO 没有离线时和手机沟通，双重验证支付宝。在这里同时验证支付宝是否在线，并且判断有没有支付宝账号

                        else break; // 支付宝在线
                    }
                    System.out.println("hello");
                    if(dLen == 0)  continue; // 没有任何一个合适的设备

                    // 找到一个设备支付宝账号在线的供码者
                    // 检查供码者设定的供码类型
                    if(chosenSupplier.getCodeType() == CodeType.NONE) {
                        len--;
                        continue;
                    }
                    else break;
                }


                // 没有一个供码者符合条件, 返回失败
                if(len == 0 || chosenDevice == null) return null;
                // 订单号生成规则： 1+10位的时间戳（从web端接收的）+两位随机数+用户的userid后四位不足补0 （支付宝通道则第一位为1）
                String orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                // 订单号查重
                while(platformOrderDataService.findByNumber(orderId) != null) {
                    orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                }

                // 检查供码者设定的供码类型, 获取收款码
                String qrCode = null;
                switch (chosenSupplier.getCodeType()) {
                    case RPASS: // 收款通码
                        break;
                    case RSOLID:    // 收款固码
                        break;
                    case TPASS: //转账通码
                        qrCode = TRANSFERPASSURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "%26money=" + getQrCodeParameters.getMoney() + "%26amount=%26memo=";
//                        o = new BizData("money", alipayDataService.findById(chosenDevice.getAlipayId()).getUserId(), getQrCodeParameters.getMoney(), orderId, TRANSFERPASSURL);
                        break;
                    case TSOLID:    //转账固码
                        qrCode = TRANSFERSOLIDURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "%26money=" + getQrCodeParameters.getMoney() + "%26amount=%26memo=";
//                        o = new BizData("money", alipayDataService.findById(chosenDevice.getAlipayId()).getUserId(), getQrCodeParameters.getMoney(), orderId, TRANSFERSOLIDURL);
                        break;
                }
                PlatformOrder platformOrder = new PlatformOrder(orderId, OrderState.WAITING_FOR_PAYING, date, qrCode + orderId, getQrCodeParameters.getIp(), getQrCodeParameters.getId(), money, user.getId(), null);
                platformOrderDataService.savePlatformOrder(platformOrder);
                return new GetQrCodeResponse("/redirect", "success", orderId);
            }
        }
    }
}
