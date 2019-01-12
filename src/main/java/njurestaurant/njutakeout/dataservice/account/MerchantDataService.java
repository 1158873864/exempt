package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Merchant;

public interface MerchantDataService {
    /**
     * save the merchant
     * @param merchant the info of the merchant
     * @return
     */
    Merchant saveMerchant(Merchant merchant);

    /**
     * find the merchant by id
     *
     * @param id merchant id
     * @return
     */
    Merchant findMerchantById(int id);

    void deleteMerchantById(int id);

}
