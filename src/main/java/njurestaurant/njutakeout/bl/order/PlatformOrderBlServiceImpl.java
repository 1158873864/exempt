package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.report.MerchantReport;
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

    @Autowired
    public PlatformOrderBlServiceImpl(PlatformOrderDataService platformOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService) {
        this.platformOrderDataService = platformOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
    }

    @Override
    public PlatformOrder findPlatformOrderById(int id) throws WrongIdException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if(platformOrder == null) {
            throw new WrongIdException();
        } else {

            return platformOrder;
        }
    }

    @Override
    public List<PlatformOrder> findAllPlatformOrders() {
        return platformOrderDataService.findAll();
    }


    @Override
    public PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws WrongIdException, BlankInputException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if(platformOrder == null) {
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
    public List<MerchantReport> merchantOrderReportByUid(int uid) {
        List<PlatformOrder> platformOrders = platformOrderDataService.findByUid(uid);
        return platformOrders.stream().map(p -> new MerchantReport(p.getMoney(),p.getPayMoney(), p.getTime(), p.getState())).collect(Collectors.toList());
    }

    @Override
    public List<MerchantReport> merchantsOrderReport() {
        List<PlatformOrder> platformOrders = platformOrderDataService.findAll();
        List<User> merchantUser = userDataService.getUserByRole(3);
        Map<Integer, String> usernameMap = new HashMap<>();
        for(User user : merchantUser) {
            usernameMap.put(user.getId(), user.getUsername());
        }
        return platformOrders.stream().map(p -> {
            if(usernameMap.containsKey(p.getUid())) {
                MerchantReport merchantReport = new MerchantReport(usernameMap.get(p.getUid()), p.getMoney(), p.getPayMoney(), p.getTime(), p.getState());
                return merchantReport;
            } else {
                return null;
            }
        }).filter(p -> p != null).collect(Collectors.toList());
    }
}
