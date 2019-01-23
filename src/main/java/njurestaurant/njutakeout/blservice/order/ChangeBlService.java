package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.QRcodeChangeParameters;

public interface ChangeBlService {
//    GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException, IPRiskControlException, IDRiskControlException, TooLittleMoneyException;
//
//    void addDevice();
//
//    String findQrCodeByOrderId(String orderid) throws WrongIdException;
//
//    PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState);

    QRcodeChangeOrder addQRcodeChangeOrder(QRcodeChangeParameters QRcodeChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, AlipayNotExistException;

    CardChangeOrder addCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException;
//    List<WithdrewOrder> getAllWaitingWithdrewOrder();
//
//    void grabWithdrewOrderById(int oid, int uid) throws WrongIdException, WrongInputException;
//
//    void dealWithdrewOrder(int id, WithdrewDealParameters withdrewDealParameters) throws WrongIdException, BlankInputException;
//
//    List<WithdrewOrder> getMyWithdrewOrder(int id) throws WrongIdException;
}
