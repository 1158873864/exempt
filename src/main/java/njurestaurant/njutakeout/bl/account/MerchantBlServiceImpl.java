package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantBlServiceImpl implements MerchantBlService {
    private final MerchantDataService merchantDataService;

    @Autowired
    public MerchantBlServiceImpl(MerchantDataService merchantDataService) {
        this.merchantDataService = merchantDataService;
    }

    /**
     * add a new merchant
     *
     * @param merchant the info of merchant
     * @return
     */
    @Override
    public MerchantAddResponse addMerchant(Merchant merchant) {
        return new MerchantAddResponse(merchantDataService.saveMerchant(merchant).getId());
    }
}
