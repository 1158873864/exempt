package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;

public interface ChangeOrderDataService {
    QRcodeChangeOrder saveQRcodeChangeOrder(QRcodeChangeOrder QRcodeChangeOrder);

    CardChangeOrder saveCardChangeOrder(CardChangeOrder CardChangeOrder);

    List<QRcodeChangeOrder> findAllQrCodeChangeOrder();

    List<QRcodeChangeOrder> findAllQrCodeChangeOrderByDate(Date startDate, Date endDate);

    List<CardChangeOrder> findAllCardChangeOrderByDate(Date startDate, Date endDate);

}
