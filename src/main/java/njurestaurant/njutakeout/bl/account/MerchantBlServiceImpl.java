package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
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

    /**
     * update the merchant
     *
     * @param merchant the new info of merchant
     * @return
     */
    @Override
    public MerchantAddResponse updateMerchant(Merchant merchant) {
        return new MerchantAddResponse(merchantDataService.saveMerchant(merchant).getId());
    }

    /**
     *
     * @param id the merchant id
     * @return
     */
    @Override
    public Merchant selectMerchantById(int id) {
        return merchantDataService.findMerchantById(id);
    }

    @Override
    public Response ApprovalMerchant(int id, String state) {
        Merchant merchant = merchantDataService.findMerchantById(id);
        if(merchant != null) {
            if(state.equals("1")) {
                merchant.setStatus(MerchantState.PASS);
            } else if(state.equals("0")){
                merchant.setStatus((MerchantState.REJECT));
            } else {
                return new WrongResponse(10140, "Wrong state");
            }
            merchantDataService.saveMerchant(merchant);
            return new SuccessResponse("Update success.");
        } else {
            return new WrongResponse(10130, "Wrong id.");
        }
    }

    @Override
    public void delMerchantById(int id) {
        merchantDataService.deleteMerchantById(id);
    }
}
