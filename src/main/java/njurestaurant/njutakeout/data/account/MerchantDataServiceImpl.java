package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.MerchantDao;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantDataServiceImpl implements MerchantDataService {
    private final MerchantDao merchantDao;

    @Autowired
    public MerchantDataServiceImpl(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    /**
     * save the merchant
     *
     * @param merchant the info of the merchant
     * @return
     */
    @Override
    public Merchant saveMerchant(Merchant merchant) {
        return merchantDao.save(merchant);
    }
}
