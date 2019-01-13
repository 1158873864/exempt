package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import njurestaurant.njutakeout.response.user.UserAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MerchantBlServiceImpl implements MerchantBlService {
    private final MerchantDataService merchantDataService;
    private final UserDataService userDataService;

    @Autowired
    public MerchantBlServiceImpl(MerchantDataService merchantDataService, UserDataService userDataService) {
        this.merchantDataService = merchantDataService;
        this.userDataService = userDataService;
    }

    /**
     * add a new merchant
     *
     * @param merchant the info of merchant
     * @return
     */
    @Override
    public MerchantAddResponse addMerchant(Merchant merchant){
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

    @Override
    public List<Merchant> findAllMerchants() {
        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        JSONFilter(merchantList);
        return merchantList;
    }



    @Override
    public List<Merchant> findMerchantsBySuperior(int id) throws WrongIdException {
        User user = userDataService.getUserById(id);
        if(user == null || user.getId() == 0) {
            throw new WrongIdException();
        } else {
            List<Merchant> result = JSONFilter(merchantDataService.getMerchantsBySuperior(Integer.toString(id)));
            return result;  //最好传id
        }
    }

    @Override
    public List<Merchant> findMerchantsByState(MerchantState merchantState) {
        List<Merchant> merchantList = merchantDataService.getMerchantsByState(merchantState);
        JSONFilter(merchantList);
        return merchantList;
    }

    private List<Merchant> JSONFilter(List<Merchant> merchantList) {
        if(merchantList.size() != 0) {
            for(Merchant merchant : merchantList) {
                List<PersonalCard> cardList = merchant.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
            }
        }
        return merchantList;
    }
}
