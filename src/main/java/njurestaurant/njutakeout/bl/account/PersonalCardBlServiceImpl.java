package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.PersonalCardBlService;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalCardBlServiceImpl implements PersonalCardBlService {
    private final PersonalCardDataService personalCardDataService;

    @Autowired
    public PersonalCardBlServiceImpl(PersonalCardDataService personalCardDataService) {
        this.personalCardDataService = personalCardDataService;
    }

    @Override
    public PersonalCardAddResponse addPersonalCard(PersonalCard personalCard) {
        PersonalCard tmp = personalCardDataService.savePersonalCard(personalCard);
        return new PersonalCardAddResponse(tmp.getId());
    }
}
