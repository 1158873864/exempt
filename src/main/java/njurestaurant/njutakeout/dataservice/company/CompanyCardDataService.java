package njurestaurant.njutakeout.dataservice.company;


import njurestaurant.njutakeout.entity.company.CompanyCard;

import java.util.List;

public interface CompanyCardDataService {
    /**
     * save the bank card of company
     * @param companyCard the company bank card
     * @return
     */
    CompanyCard saveCompanyCard(CompanyCard companyCard);

    /**
     * load all company card
     *
     * @return
     */
    List<CompanyCard> findAllCompanyCards();
}
