package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.StaffBlService;
import njurestaurant.njutakeout.dataservice.company.StaffDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.company.Staff;
import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.publicdatas.account.Role;
import njurestaurant.njutakeout.response.company.StaffLoginReponse;
import njurestaurant.njutakeout.response.user.UserLoginResponse;
import njurestaurant.njutakeout.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StaffBlServiceImpl implements StaffBlService {
    private final StaffDataService staffDataService;

    @Autowired
    public StaffBlServiceImpl(StaffDataService staffDataService) {
        this.staffDataService = staffDataService;
    }

    /**
     * login
     *
     * @param staffName the username of staff
     * @param password  the password of staff
     * @return the login info to response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    @Override
    public StaffLoginReponse login(String staffName, String password) throws WrongUsernameOrPasswordException, CannotRegisterException {
        if (staffName.length() == 0) {
            throw new CannotRegisterException();
        }

        if (staffDataService.confirmPassword(staffName, password)) {
//            JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
//            String token = jwtService.generateToken(jwtUser, EXPIRATION);
//            return new UserLoginResponse(token);
            return new StaffLoginReponse("123456");
        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }
}
