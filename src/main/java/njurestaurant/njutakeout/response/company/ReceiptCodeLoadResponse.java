package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class ReceiptCodeLoadResponse extends Response {
    private List<ReceiptCode> receiptCodeList;

    public ReceiptCodeLoadResponse(List<ReceiptCode> receiptCodeList) {
        this.receiptCodeList = receiptCodeList;
    }

    public List<ReceiptCode> getReceiptCodeList() {
        return receiptCodeList;
    }

    public void setReceiptCodeList(List<ReceiptCode> receiptCodeList) {
        this.receiptCodeList = receiptCodeList;
    }
}
