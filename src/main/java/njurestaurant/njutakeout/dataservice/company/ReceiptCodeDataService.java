package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;

public interface ReceiptCodeDataService {
    /**
     * save the receipt code
     *
     * @param receiptCode the code to be saved
     */
    ReceiptCode saveReceiptCode(ReceiptCode receiptCode);
}