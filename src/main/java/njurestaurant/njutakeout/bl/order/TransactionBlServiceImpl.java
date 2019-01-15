package njurestaurant.njutakeout.bl.order;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.config.WebSocketConfig;
import njurestaurant.njutakeout.config.websocket.WebSocketHandler;
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
import njurestaurant.njutakeout.parameters.app.CheckOnlineParameters;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.app.GetReceiptCodeResponse;
import njurestaurant.njutakeout.response.order.BizData;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import njurestaurant.njutakeout.util.StringParseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    private final String TRANSFERSOLIDURL = "alipays://platformapi/startapp?appId=20000123%26actionType=scan%26biz_data={\"s\": \"money\",\"u\":\"";
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
     * <p>
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
        if (StringParseUtil.StringToInt(getQrCodeParameters.getTime()) == null || StringParseUtil.StringToDouble(getQrCodeParameters.getMoney()) == null) {
            throw new BlankInputException();
        }
        double money = StringParseUtil.StringToDouble(getQrCodeParameters.getMoney());
        Date date = FormatDateTime.TenTimestampToDate(StringParseUtil.StringToInt(getQrCodeParameters.getTime()));
        if (id == null) {
            throw new WrongIdException();
        } else {
            User user = userDataService.getUserById(id);
            if (user == null || user.getRole() != 3) {
                throw new WrongIdException();
            } else {
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                // 数据库中没有该商户信息
                if (merchant == null) {
                    throw new WrongIdException();
                }

                // id正确，开始寻找供码用户
                Random random = new Random();
                List<Supplier> supplierList = supplierDataService.findSuppliersByLevel(merchant.getPriority());
                int len = supplierList.size();
                int randomNumber;
                Supplier chosenSupplier = null;
                Device chosenDevice = null;
                
                chosenSupplier = supplierDataService.findSupplierById(4);
                List<Device> devices = chosenSupplier.getDevices();
                if (devices == null) return null;
                else {
                    for (Device d : devices) {
                        if (d.getImei().equals("304517300097652")) {
                            chosenDevice = d;
                            break;
                        }
                    }
                }
                if (chosenDevice != null) {
                    Alipay alipay = alipayDataService.findById(chosenDevice.getAlipayId());
                    // 该设备没有支付宝的信息
                    if (alipay == null) {
                        return null;
                    }
                    GetReceiptCodeResponse getReceiptCodeResponse = checkAlipayOnline(chosenDevice.getImei(), alipay.getUserId());
                    if (getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("success")) {
                        if (StringUtils.isBlank(alipay.getPassQrCode())) {
                            alipay.setPassQrCode(getReceiptCodeResponse.getQrcode());
                            if (StringUtils.isBlank(alipay.getPassOffCode())) {
                                alipay.setSolidCode(getReceiptCodeResponse.getOffcode());
                            }
                            alipayDataService.saveAlipay(alipay);
                        }
                    } else if (getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("failed")) {
                        chosenDevice.setOnline(0);
                        deviceDataService.saveDevice(chosenDevice);
                    }
                }

/*
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
                        if (chosenDevice.getOnline() == 0) {
                            dLen--;
                            continue;    // 平台账号在该设备上离线
                        }

                        // 没有离线时和手机沟通，再次验证支付宝是否在线,并且判断有没有支付宝账号
                        Alipay alipay = alipayDataService.findById(chosenDevice.getAlipayId());

                        // 该设备没有支付宝的信息
                        if (alipay == null) {
                            dLen--;
                            continue;
                        }
                        GetReceiptCodeResponse getReceiptCodeResponse = checkAlipayOnline(chosenDevice.getImei(), alipay.getUserId());
                        if (getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("success")) {
                            if (StringUtils.isBlank(alipay.getPassQrCode())) {
                                alipay.setPassQrCode(getReceiptCodeResponse.getQrcode());
                                if (StringUtils.isBlank(alipay.getPassOffCode())) {
                                    alipay.setSolidCode(getReceiptCodeResponse.getOffcode());
                                }
                                alipayDataService.saveAlipay(alipay);
                            }
                            break; // 支付宝在线
                        } else if(getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("failed")) {
                            chosenDevice.setOnline(0);
                            deviceDataService.saveDevice(chosenDevice);
                            dLen--;
                        } else dLen--;
                    }

                    if (dLen == 0) continue; // 没有任何一个合适的设备

                    // 找到一个设备支付宝账号在线的供码者
                    // 检查供码者设定的供码类型
                    if (chosenSupplier.getCodeType() == CodeType.NONE) {
                        len--;
                        continue;
                    } else break;
                }
                */
                // 没有一个供码者符合条件, 返回失败
                if (len == 0) return null;
                // 订单号生成规则： 1+10位的时间戳（从web端接收的）+两位随机数+用户的userid后四位不足补0 （支付宝通道则第一位为1）
                String orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                // 订单号查重
                while (platformOrderDataService.findByNumber(orderId) != null) {
                    orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                }

                // 检查供码者设定的供码类型, 获取收款码
                String qrCode = null;
                switch (chosenSupplier.getCodeType()) {
                    case RPASSOFF: // 收款通码离线码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getPassOffCode();
                        break;
                    case RPASSQR:   //收款通码在线码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getPassQrCode();
                        break;
                    case RSOLID:    // 收款固码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getSolidCode();
                        break;
                    case TPASS: //转账通码
                        qrCode = TRANSFERPASSURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "%26money=" + getQrCodeParameters.getMoney() + "%26amount=%26memo=" + orderId;
                        break;
                    case TSOLID:    //转账固码
                        qrCode = TRANSFERSOLIDURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "\",\"a\":\"" + money + "\",\"m\":\"" + orderId + "\"}";
                        break;
                }
                PlatformOrder platformOrder = new PlatformOrder(orderId, OrderState.WAITING_FOR_PAYING, date, qrCode, getQrCodeParameters.getIp(), getQrCodeParameters.getId(), money, user.getId(), null);
                platformOrderDataService.savePlatformOrder(platformOrder);
                return new GetQrCodeResponse("/redirect", "success", orderId);
            }
        }
    }

    @Override
    public String findQrCodeByOrderId(String orderid) throws WrongIdException {
        PlatformOrder platformOrder = platformOrderDataService.findByNumber(orderid);
        if (platformOrder == null) { // 订单号错误
            throw new WrongIdException();
        } else if (platformOrder.getState() == OrderState.EXPIRED || checkOrderIsExpired(platformOrder.getTime())) {  //已失效
            platformOrder.setState(OrderState.EXPIRED);
            platformOrderDataService.savePlatformOrder(platformOrder);
            return "expired";
        } else if (platformOrder.getState() == OrderState.PAID) {
            return "payid";
        } else {
            return platformOrder.getPayCode();
        }
    }

    /**
     * 检查订单是否已失效
     *
     * @return 失效返回true，没失效返回false
     */
    private boolean checkOrderIsExpired(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        int minutes = (int) (diff / (1000 * 60));
        // 预设失效时间为2分钟
        if (minutes > 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查该设备上的支付宝账号是否在线
     * 服务端发送消息:{"cmd":"passcode","type":"alipay","imei":"设备唯一标识","userid":"支付宝userid"}
     *
     * @param imei   设备的唯一标识
     * @param userId 支付宝的账号
     * @return
     */
    private GetReceiptCodeResponse checkAlipayOnline(String imei, String userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketHandler.sendMessageToUser(imei, new TextMessage(String.valueOf(new CheckOnlineParameters(imei, userId))));
                try {
                    //线程休眠10s
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        WebSocketHandler.mapThread.put(imei, thread);
        thread.start();
        TextMessage textMessage = WebSocketHandler.msgMap.get(imei);
        if(textMessage == null || StringUtils.isBlank(textMessage.getPayload()))	return null;
            try {
                JSONObject jsonObject = new JSONObject(textMessage.getPayload());
                String cmd = jsonObject.getString("cmd");
                String type = jsonObject.getString("type");
                String im = jsonObject.getString("imei");
                String msg = jsonObject.getString("msg");
                String userid = jsonObject.getString("userid");
                String qrCode = jsonObject.getString("qrcode");
                String offQrCode = jsonObject.getString("offQrCode");
                String status = jsonObject.getString("status");

                return new GetReceiptCodeResponse(cmd, type, im, status, msg, userid, qrCode, offQrCode);
            } catch (JSONException e) {
                return null;
            }
        }

    @Override
    public PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState) {
        return platformOrderDataService.findByImeiAndState(imei, orderState);
    }
}
