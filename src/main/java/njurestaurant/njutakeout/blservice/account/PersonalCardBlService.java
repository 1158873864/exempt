package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.stereotype.Service;

@Service
public interface PersonalCardBlService {
    /**
     * add a new personal bank card
     * @param personalCard the info of the new bank card
     * @return
     */
    PersonalCardAddResponse addPersonalCard(PersonalCard personalCard);
}
