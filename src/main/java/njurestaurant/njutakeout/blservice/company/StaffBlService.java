package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.response.company.StaffLoginReponse;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * add a staff
     *
     * @param staffName the username of the staff
     * @param team the team of staff
     * @param role the post of staff
     * @param status
     * @param verifyCode
     * @param addTime operation time of add a new staff
     * @param operator 操作上级
     * @return
     */
    StaffLoginReponse add(String staffName, String team, String role, String status, String verifyCode, Date addTime, String operator);
}
