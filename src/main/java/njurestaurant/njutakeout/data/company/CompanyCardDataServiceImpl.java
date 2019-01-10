package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.CompanyCardDao;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCardDataServiceImpl implements CompanyCardDataService {
    private CompanyCardDao companyCardDao;

    @Autowired
    public CompanyCardDataServiceImpl(CompanyCardDao companyCardDao) {
        this.companyCardDao = companyCardDao;
    }

    /**
     * save the new bank card of the company
     *
     * @param companyCard the information of bank card
     * @return
     */
    @Override
    public CompanyCard saveCompanyCard(CompanyCard companyCard) {
        return companyCardDao.save(companyCard);
    }

    /**
     * load all bank cards
     * @return
     */
    @Override
    public List<CompanyCard> findAllCompanyCards() {
        return companyCardDao.findAll();
    }
}
