package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptCodeBlService {

    /**
     * add a new receipt code
     *
     * @param receiptCode
     * @return
     */
    ReceiptCodeAddResponse addReceiptCode(ReceiptCode receiptCode);
}
