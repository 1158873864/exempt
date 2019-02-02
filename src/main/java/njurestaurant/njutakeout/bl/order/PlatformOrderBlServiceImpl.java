package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.data.dao.app.DeviceDao;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.OrderWrongInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static njurestaurant.njutakeout.publicdatas.order.OrderState.PAID;

@Service
public class PlatformOrderBlServiceImpl implements PlatformOrderBlService {

    private final PlatformOrderDataService platformOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final AlipayDataService alipayDataService;
    private final UserDao userDao;
    private final DeviceDao deviceDao ;
    private final SupplierDataService supplierDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final AgentDataService agentDataService;
    @Autowired
    public PlatformOrderBlServiceImpl(PlatformOrderDataService platformOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, AlipayDataService alipayDataService, UserDao userDao, DeviceDao deviceDao, SupplierDataService supplierDataService, AlipayOrderDataService alipayOrderDataService, AgentDataService agentDataService) {
        this.platformOrderDataService = platformOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.alipayDataService = alipayDataService;
        this.userDao = userDao;
        this.deviceDao = deviceDao;
        this.supplierDataService = supplierDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.agentDataService = agentDataService;
    }

    @Override
    public PlatformOrder findPlatformOrderById(int id) throws WrongIdException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if (platformOrder == null) {
            throw new WrongIdException();
        } else {

            return platformOrder;
        }
    }

    /**
     * 查看全部订单明细
     *
     * @return the order information
     */
    @Override
    public List<OrderListResponse> findAllPlatformOrders() {
        // 找出全部商家的信息
        List<User> merchantUser = userDataService.getUserByRole(3);
        Map<Integer, String> usernameMap = new HashMap<>();
        for (User user : merchantUser) {
            usernameMap.put(user.getId(), user.getUsername());
        }
        // 将错误的商家id信息过滤
        return platformOrderDataService.findAll().stream().map(p -> {
            String type = null;
            if (usernameMap.containsKey(p.getUid())) {
                if (p.getType().equals("alipay")) {  // 支付宝的收款方式
                    Alipay alipay = alipayDataService.findById(p.getTableId());
                    type = "支付宝";
                    User user = userDao.findUserById(p.getUid());
                    Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                    if (userDao.findUserById(merchant.getApplyId()).getRole() == 1)
                        return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(),
                                p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), p.getSupplierid(), 0, usernameMap.get(p.getUid()),
                                type, alipay.getId(), alipay.getName());
                    else if (userDao.findUserById(merchant.getApplyId()).getRole() == 2)
                        return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(),
                                p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(),p.getSupplierid() , merchant.getApplyId(), usernameMap.get(p.getUid()),
                                type, alipay.getId(), alipay.getName());
                } else return null; // 可能有微信的收款方式
            } else return null;
            return null;
        }).filter(pf -> pf != null).collect(Collectors.toList());
    }


    @Override
    public PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws  BlankInputException ,OrderWrongInputException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if (platformOrder == null) {
            throw new OrderWrongInputException(new WrongResponse(9999,"订单不存在"));
        } else {

            OrderState orderState = OrderState.valueOf(platformUpdateParameters.getState());
            switch (orderState){
                case WAITING_FOR_PAYING:
//                    if (platformOrder.getState() == OrderState.PAID || platformOrder.getState() == OrderState.EXPIRED)
                        throw new OrderWrongInputException(new WrongResponse(9998,"所有订单不允许修改成未支付订单"));
                case PAID:
                    if ( platformOrder.getState() == OrderState.EXPIRED)
                        throw new OrderWrongInputException(new WrongResponse(9997,"已失效订单不允许修改成已支付订单"));
                    else if (platformOrder.getState() == PAID) {
                        AlipayOrder alipayOrder = alipayOrderDataService.getAlipayOrderByOrderId(platformUpdateParameters.getOrderId());
                        alipayOrder.setMoney(platformUpdateParameters.getRealPay());
                        alipayOrder.setTime(platformUpdateParameters.getPayTime());
                        alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    }else if (platformOrder.getState() == OrderState.WAITING_FOR_PAYING){
                        AlipayOrder alipayOrder = new AlipayOrder(platformOrder.getImei(), platformUpdateParameters.getOrderId(),
                                platformUpdateParameters.getRealPay(), "补单", platformUpdateParameters.getPayTime());
                        alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    }
                    platformOrder.setMoney(platformUpdateParameters.getMoney());
                    platformOrder.setPayMoney(platformUpdateParameters.getRealPay());
                    platformOrder.setPayTime(platformUpdateParameters.getPayTime());
                    platformOrder.setState(PAID);
                 //   platformOrderDataService.savePlatformOrder(platformOrder);
                    User user = userDataService.getUserById(platformOrder.getUid());
                    if (user != null) {
                        Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                        User suser = userDataService.getUserById(merchant.getApplyId());
                        if (merchant != null) {
                            merchant.setBalance(merchant.getBalance() + platformUpdateParameters.getRealPay() * (1 - merchant.getAlipay() / 100));
                            merchantDataService.saveMerchant(merchant);
                        }
                        // 操作上级,商户的代理商
                        if (suser != null) {
                            if (suser.getRole() == 2) {
                                Agent agent = agentDataService.findAgentById(suser.getTableId());
                                agent.setBalance(agent.getBalance() + platformUpdateParameters.getRealPay() * agent.getAlipay() / 100);
                                agentDataService.saveAgent(agent);
                                if (!DateUtils.isSameDay(AgentDailyFlow.date, new Date())) {
                                    AgentDailyFlow.commission.clear();
                                    AgentDailyFlow.flow.clear();
                                    AgentDailyFlow.date = new Date();
                                }
                                // 计算代理的每日流量
                                if (AgentDailyFlow.flow.containsKey(agent.getId())) {
                                    AgentDailyFlow.flow.put(agent.getId(), AgentDailyFlow.flow.get(agent.getId()) + platformUpdateParameters.getRealPay() * (1 - merchant.getAlipay() / 100));
                                } else {
                                    AgentDailyFlow.flow.put(agent.getId(), platformUpdateParameters.getRealPay() * (1 - merchant.getAlipay() / 100));
                                }
                                // 计算代理的每日佣金
                                if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                    AgentDailyFlow.commission.put(agent.getId(), AgentDailyFlow.commission.get(agent.getId()) + platformUpdateParameters.getRealPay() * agent.getAlipay() / 100);
                                } else {
                                    AgentDailyFlow.commission.put(agent.getId(), platformUpdateParameters.getRealPay() * agent.getAlipay() / 100);
                                }
                            }
                        }
                    }
                    break;
                case EXPIRED:
                    throw new OrderWrongInputException(new WrongResponse(9996,"所有订单不允许修改成已失效订单"));
                default:
                    throw new BlankInputException();
            }
            return platformOrderDataService.savePlatformOrder(platformOrder);
        }
    }

//    @Override
//    public List<OrderListResponse> merchantOrderReportByUid(int uid) {
//        // 找出全部商家的信息
//        User user = userDataService.getUserById(uid);
//        // 如果改用户不是商家无法看到商家的个人订单报表信息
//        if (user.getRole() != 3) return null;
//        List<PlatformOrder> platformOrders = platformOrderDataService.findByUid(uid);
//        return platformOrders.stream().map(p -> {
//            String type = null;
//            if (p.getType().equals("alipay")) {  // 支付宝的收款方式
//                Alipay alipay = alipayDataService.findById(p.getTableId());
//                type = "支付宝";
//                return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), user.getUsername(), type, alipay.getId(), alipay.getName());
//            } else return null; // 可能有微信的收款方式
//        }).filter(pf -> pf != null).collect(Collectors.toList());
//    }

    @Override
    public List<MerchantReportResponse> merchantsOrderReport() {
        List<PlatformOrder> platformOrders = platformOrderDataService.findAll();
        List<User> merchantUser = userDataService.getUserByRole(3);
        Map<Integer, String> usernameMap = new HashMap<>();
        for (User user : merchantUser) {
            usernameMap.put(user.getId(), user.getUsername());
        }
        return null;
//        return platformOrders.stream().map(p -> {
//            if (usernameMap.containsKey(p.getUid())) {
//                MerchantReportResponse merchantReportResponse = new MerchantReportResponse(usernameMap.get(p.getUid()), p.getMoney(), p.getPayMoney(), p.getTime(), p.getState());
//                return merchantReportResponse;
//            } else {
//                return null;
//            }
//        }).filter(p -> p != null).collect(Collectors.toList());
    }
}
