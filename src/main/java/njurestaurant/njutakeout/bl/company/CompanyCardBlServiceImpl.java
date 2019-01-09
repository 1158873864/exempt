package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.CompanyCardBlService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.response.company.CompanyCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyCardBlServiceImpl implements CompanyCardBlService {
    private final CompanyCardDataService companyCardDataService;

    @Autowired
    public CompanyCardBlServiceImpl(CompanyCardDataService companyCardDataService) {
        this.companyCardDataService = companyCardDataService;
    }

    /**
     * add a new bank card of company
     *
     * @param companyCard the information of the bank card
     * @return
     */
    @Override
    public CompanyCardAddResponse addCompanyCard(CompanyCard companyCard) {
        CompanyCard cc = companyCardDataService.saveCompanyCard(companyCard);
        return new CompanyCardAddResponse(cc.getId());
    }
}