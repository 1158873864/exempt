package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Merchant;

public interface MerchantDataService {
    /**
     * save the merchant
     * @param merchant the info of the merchant
     * @return
     */
    Merchant saveMerchant(Merchant merchant);
}
