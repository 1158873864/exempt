package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;

public interface TransactionBlService {
    GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException;

    void addDevice();

    String findQrCodeByOrderId(String orderid) throws WrongIdException;
    
    PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState);
}
