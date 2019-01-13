package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.PersonalCardBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.user.PersonalCardAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalCardBlServiceImpl implements PersonalCardBlService {
    private final PersonalCardDataService personalCardDataService;
    private final UserDataService userDataService;

    @Autowired
    public PersonalCardBlServiceImpl(PersonalCardDataService personalCardDataService, UserDataService userDataService) {
        this.personalCardDataService = personalCardDataService;
        this.userDataService = userDataService;
    }

    @Override
    public PersonalCardAddResponse addPersonalCard(PersonalCardAddParameters personalCardAddParameters) throws WrongIdException, IsExistentException {
        User user = userDataService.getUserById(personalCardAddParameters.getUid());
        if(user == null || user.getId() == 0) {
            throw new WrongIdException();
        }
        if(personalCardDataService.findPersonalCardByCardNumber(personalCardAddParameters.getNumber()) != null) {
            throw new IsExistentException();
        }
        PersonalCard tmp = personalCardDataService.savePersonalCard(new PersonalCard(personalCardAddParameters.getNumber(), personalCardAddParameters.getName(), personalCardAddParameters.getBank(),personalCardAddParameters.getAccountOfBank(), personalCardAddParameters.getBin(), personalCardAddParameters.getStatus(), user));
        return new PersonalCardAddResponse(tmp.getId());
    }

    @Override
    public PersonalCard getPersonalCardById(int id) {
        return personalCardDataService.findPersonalCardById(id);
    }

    @Override
    public List<PersonalCard> getMyPersonalCardByUserId(int uid) throws WrongIdException {
        User user = userDataService.getUserById(uid);
        if(user == null || user.getId() == 0) {
            throw new WrongIdException();
        }
        return personalCardDataService.findPersonalCarsByUid(uid);
    }

    @Override
    public List<PersonalCard> getAllCards() {
        return personalCardDataService.findAllCards();
    }
}
