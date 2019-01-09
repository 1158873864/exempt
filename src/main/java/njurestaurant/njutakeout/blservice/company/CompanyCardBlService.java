package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.response.company.CompanyCardAddResponse;
import org.springframework.stereotype.Service;

@Service
public interface CompanyCardBlService {
    /**
     * add a new bank card of company
     *
     * @param companyCard the information of the bank card
     * @return
     */
    CompanyCardAddResponse addCompanyCard(CompanyCard companyCard);

}