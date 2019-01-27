package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;

import java.util.List;

public interface PlatformOrderBlService {
    PlatformOrder findPlatformOrderById(int id) throws WrongIdException;

    /**
     * 查看全部订单明细
     *
     * @return the order information
     */
    List<OrderListResponse> findAllPlatformOrders();

    PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws WrongIdException, BlankInputException;

   // List<OrderListResponse> merchantOrderReportByUid(int uid);

    List<MerchantReportResponse> merchantsOrderReport();

}
