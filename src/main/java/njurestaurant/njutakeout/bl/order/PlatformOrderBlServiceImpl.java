package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformOrderBlServiceImpl implements PlatformOrderBlService {
    private final PlatformOrderDataService platformOrderDataService;

    @Autowired
    public PlatformOrderBlServiceImpl(PlatformOrderDataService platformOrderDataService) {
        this.platformOrderDataService = platformOrderDataService;
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
}
