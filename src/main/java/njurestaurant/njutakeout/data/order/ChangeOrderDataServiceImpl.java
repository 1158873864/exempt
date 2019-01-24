package njurestaurant.njutakeout.data.order;


import njurestaurant.njutakeout.data.dao.order.CardChangeOrderDao;
import njurestaurant.njutakeout.data.dao.order.QRcodeChangeOrderDao;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeOrderDataServiceImpl implements ChangeOrderDataService {
    private final QRcodeChangeOrderDao QRcodeChangeOrderDao;
    private final CardChangeOrderDao CardChangeOrderDao;

    @Autowired
    public ChangeOrderDataServiceImpl(QRcodeChangeOrderDao QRcodeChangeOrderDao, CardChangeOrderDao CardChangeOrderDao) {
        this.QRcodeChangeOrderDao = QRcodeChangeOrderDao;
        this.CardChangeOrderDao = CardChangeOrderDao;
    }

    @Override
    public QRcodeChangeOrder saveQRcodeChangeOrder(QRcodeChangeOrder QRcodeChangeOrder) {
        return QRcodeChangeOrderDao.save(QRcodeChangeOrder);
    }

    @Override
    public CardChangeOrder saveCardChangeOrder(CardChangeOrder CardChangeOrder) {
        return CardChangeOrderDao.save(CardChangeOrder);
    }
}
