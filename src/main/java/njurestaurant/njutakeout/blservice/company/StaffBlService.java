package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.response.company.StaffLoginReponse;
import org.springframework.stereotype.Service;

@Service
public interface StaffBlService {
    /**
     * login
     *
     * @param staffName the username of staff
     * @param password the password of staff
     * @return the login info to  response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    StaffLoginReponse login(String staffName, String password) throws WrongUsernameOrPasswordException, CannotRegisterException;
}
