package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import org.springframework.stereotype.Service;

@Service
public interface MerchantBlService {
    /**
     * add a new merchant
     * @param merchant the info of merchant
     * @return
     */
    MerchantAddResponse addMerchant(Merchant merchant);
}
