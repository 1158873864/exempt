package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlatformOrderBlServiceImpl implements PlatformOrderBlService {

    private final PlatformOrderDataService platformOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final AlipayDataService alipayDataService;

    @Autowired
    public PlatformOrderBlServiceImpl(PlatformOrderDataService platformOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, AlipayDataService alipayDataService) {
        this.platformOrderDataService = platformOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.alipayDataService = alipayDataService;
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
                    return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), usernameMap.get(p.getUid()), type, alipay.getId(), alipay.getName());
                } else return null; // 可能有微信的收款方式
            } else return null;
        }).filter(pf -> pf != null).collect(Collectors.toList());
    }


    @Override
    public PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws WrongIdException, BlankInputException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if (platformOrder == null) {
            throw new WrongIdException();
        } else {
            platformOrder.setMoney(platformUpdateParameters.getMoney());
            platformOrder.setPayMoney(platformUpdateParameters.getRealPay());
            switch (platformUpdateParameters.getState()) {
                case "WAITING_FOR_PAYING":
                    break;
                case "PAID":
                    break;
                case "EXPIRED":
                    break;
                default:
                    throw new BlankInputException();
            }
            return platformOrderDataService.savePlatformOrder(platformOrder);
        }
    }

    @Override
    public List<OrderListResponse> merchantOrderReportByUid(int uid) {
        // 找出全部商家的信息
        User user = userDataService.getUserById(uid);
        // 如果改用户不是商家无法看到商家的个人订单报表信息
        if (user.getRole() != 3) return null;
        List<PlatformOrder> platformOrders = platformOrderDataService.findByUid(uid);
        return platformOrders.stream().map(p -> {
            String type = null;
            if (p.getType().equals("alipay")) {  // 支付宝的收款方式
                Alipay alipay = alipayDataService.findById(p.getTableId());
                type = "支付宝";
                return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), user.getUsername(), type, alipay.getId(), alipay.getName());
            } else return null; // 可能有微信的收款方式
        }).filter(pf -> pf != null).collect(Collectors.toList());
    }

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
