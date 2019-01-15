package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.response.report.MerchantReport;

import java.util.List;

public interface PlatformOrderBlService {
    PlatformOrder findPlatformOrderById(int id) throws WrongIdException;

    List<PlatformOrder> findAllPlatformOrders();

    PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws WrongIdException, BlankInputException;

    List<MerchantReport> merchantOrderReportByUid(int uid);

    List<MerchantReport> merchantsOrderReport();

}
