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
import njurestaurant.njutakeout.security.jwt.JwtService;
import njurestaurant.njutakeout.security.jwt.JwtStaffDetailService;
import njurestaurant.njutakeout.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StaffBlServiceImpl implements StaffBlService {

    private final static long EXPIRATION = 604800;

    private final StaffDataService staffDataService;
    private final JwtStaffDetailService jwtStaffDetailService;
    private final JwtService jwtService;

    @Autowired
    public StaffBlServiceImpl(StaffDataService staffDataService, JwtService jwtService, JwtStaffDetailService jwtStaffDetailService) {
        this.staffDataService = staffDataService;
        this.jwtService = jwtService;
        this.jwtStaffDetailService = jwtStaffDetailService;
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
            JwtUser jwtUser = (JwtUser) jwtStaffDetailService.loadUserByUsername(staffName);
            String token = jwtService.generateToken(jwtUser, EXPIRATION);
            return new StaffLoginReponse(token);
        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }

    /**
     * add a new staff
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
    @Override
    public StaffLoginReponse add(String staffName, String team, String role, String status, String verifyCode, Date addTime, String operator) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        staffDataService.saveStaff(new Staff(staffName, encoder.encode("123"), team, addTime, verifyCode, operator, status, role));
        JwtUser jwtUser = (JwtUser) jwtStaffDetailService.loadUserByUsername(staffName);
        String token = jwtService.generateToken(jwtUser, EXPIRATION);
        return new StaffLoginReponse(token);
    }
}
