package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.PersonalCardDao;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalCardDataServiceImpl implements PersonalCardDataService {
    private final PersonalCardDao personalCardDao;

    @Autowired
    public PersonalCardDataServiceImpl(PersonalCardDao personalCardDao) {
        this.personalCardDao = personalCardDao;
    }

    /**
     * save the personal bank card
     * @param personalCard the info of the bank card
     * @return
     */
    @Override
    public PersonalCard savePersonalCard(PersonalCard personalCard) {
        return personalCardDao.saveAndFlush(personalCard);
    }
}
