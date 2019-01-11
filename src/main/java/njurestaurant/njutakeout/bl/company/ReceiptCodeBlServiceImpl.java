package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.ReceiptCodeBlService;
import njurestaurant.njutakeout.dataservice.company.ReceiptCodeDataService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeLoadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptCodeBlServiceImpl implements ReceiptCodeBlService {

    private final ReceiptCodeDataService receiptCodeDataService;


    @Autowired
    public ReceiptCodeBlServiceImpl(ReceiptCodeDataService receiptCodeDataService) {
        this.receiptCodeDataService = receiptCodeDataService;
    }

    /**
     * save the new receipt code
     *
     * @param receiptCode
     * @return
     */
    @Override
    public ReceiptCodeAddResponse addReceiptCode(ReceiptCode receiptCode) {
        ReceiptCode rc =  receiptCodeDataService.saveReceiptCode(receiptCode);
        return new ReceiptCodeAddResponse(rc.getId());
    }

    /**
     * load all receipt code
     *
     * @return
     */
    @Override
    public ReceiptCodeLoadResponse loadReceiptCodes() {
        return new ReceiptCodeLoadResponse(receiptCodeDataService.findAllReceipt());
    }
}
