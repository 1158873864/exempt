package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.ReceiptCodeDao;
import njurestaurant.njutakeout.dataservice.company.ReceiptCodeDataService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptCodeDataServiceImpl implements ReceiptCodeDataService {
    private final ReceiptCodeDao receiptCodeDao;

    @Autowired
    public ReceiptCodeDataServiceImpl (ReceiptCodeDao receiptCodeDao) {
        this.receiptCodeDao = receiptCodeDao;
    }

    /**
     * save the receipt code
     * @param receiptCode the code to be saved
     */
    @Override
    public ReceiptCode saveReceiptCode(ReceiptCode receiptCode) {
        return receiptCodeDao.save(receiptCode);
    }
}
