package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.MerchantDao;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     *
     * @param id merchant id
     * @return
     */
    @Override
    public Merchant findMerchantById(int id) {
        Optional<Merchant> optionalMerchant = merchantDao.findById(id);
        if (optionalMerchant.isPresent()) {
            return optionalMerchant.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteMerchantById(int id) {
        merchantDao.deleteById(id);
    }
}
