package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;

public interface PersonalCardDataService {
    /**
     * save the personal bank card
     *
     * @param personalCard the info of the bank card
     * @return
     */
    PersonalCard savePersonalCard(PersonalCard personalCard);
}
