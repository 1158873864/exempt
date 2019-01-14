package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;

public interface TransactionBlService {
    GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException;

    void addDevice();
}
