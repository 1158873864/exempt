package njurestaurant.njutakeout.bl.account;

import net.sf.json.JSONObject;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.blservice.company.PostAndPermissionBlService;
import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.company.Post;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.publicdatas.account.Role;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.*;
import njurestaurant.njutakeout.security.jwt.JwtService;
import njurestaurant.njutakeout.security.jwt.JwtUser;
import njurestaurant.njutakeout.security.jwt.JwtUserDetailsService;
import njurestaurant.njutakeout.util.AESDecodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBlServiceImpl implements UserBlService {

    private final static long EXPIRATION = 604800;
    private final static String USER_DEFAULT_PASSWORD = "user";

    private final UserDataService userDataService;
    private final AgentDataService agentDataService;
    private final MerchantDataService merchantDataService;
    private final StaffDataService staffDataService;
    private final SupplierDataService supplierDataService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtService jwtService;
    private final PostAndPermissionBlService postAndPermissionBlService;

    @Value(value = "${wechat.url}")
    private String wechatUrl;

    @Value(value = "${wechat.id}")
    private String appId;

    @Value(value = "${wechat.secret}")
    private String appSecret;

    @Autowired
    public UserBlServiceImpl(UserDataService userDataService, AgentDataService agentDataService, MerchantDataService merchantDataService, StaffDataService staffDataService, SupplierDataService supplierDataService, JwtUserDetailsService jwtUserDetailsService, JwtService jwtService, PostAndPermissionBlService postAndPermissionBlService) {
        this.userDataService = userDataService;
        this.agentDataService = agentDataService;
        this.merchantDataService = merchantDataService;
        this.staffDataService = staffDataService;
        this.supplierDataService = supplierDataService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtService = jwtService;
        this.postAndPermissionBlService = postAndPermissionBlService;
    }

    /**
     * login
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the login info to response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    @Override
    public UserLoginResponse login(String username, String password) throws WrongUsernameOrPasswordException, CannotRegisterException {
        if (username.length() == 0) {
            throw new CannotRegisterException();
        }
        if (password.equals(USER_DEFAULT_PASSWORD)) {
            if (!userDataService.isUserExistent(username)) {
                userDataService.saveUser(new User(username, password, 1, 0));
//                userDataService.saveUser(new User("", username, password, Role.USER, new ArrayList<>(), new ArrayList<>()));
            }
            JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            String token = jwtService.generateToken(jwtUser, EXPIRATION);
            return new UserLoginResponse(token);
        } else {
            if (userDataService.confirmPassword(username, password)) {
                User user = userDataService.getUserByUsername(username);
                JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
                String token = jwtService.generateToken(jwtUser, EXPIRATION);
                String post = null;
                switch (user.getRole()) {
                    case 1:
                        post = staffDataService.findStaffById(user.getTableId()).getPost();
                        break;
                    case 2:
                        post = "代理商";
                        break;
                    case 3:
                        post = "商户";
                        break;
                    case 4:
                        post = "供码用户";
                        break;
                }

                return new UserLoginResponse(token, user.getRole(), user.getId(), postAndPermissionBlService.getPostAndPermissionsByPost(post).getPermission());
            } else {
                throw new WrongUsernameOrPasswordException();
            }
        }
    }

    /**
     * get user openid
     *
     * @param jsCode wechat js code
     * @return whether the operation is success or not
     */
    @Override
    public OpenIdAndSessionKeyResponse getOpenIdAndSessionKey(String jsCode) throws CannotGetOpenIdAndSessionKeyException {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> response = client.exchange(wechatUrl + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code", HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return new OpenIdAndSessionKeyResponse((String) JSONObject.fromObject(response.getBody()).get("openid"), (String) JSONObject.fromObject(response.getBody()).get("session_key"));
        } else {
            throw new CannotGetOpenIdAndSessionKeyException();
        }
    }

    /**
     * register
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserLoginResponse register(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDataService.saveUser(new User(username, encoder.encode(password), 0, 0));
//        userDataService.saveUser(new User("", username, encoder.encode(password), Role.RESTAURANT, new ArrayList<>(), new ArrayList<>()));
        JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
        String token = jwtService.generateToken(jwtUser, EXPIRATION);
        return new UserLoginResponse(token);
    }

    /**
     * decrypt data
     *
     * @param encryptData
     * @param sessionKey
     * @param ivCode
     * @return
     */
    @Override
    public PhoneNumberGetResponse decryptData(String encryptData, String sessionKey, String ivCode) throws PhoneNumberGetWrongException {
        try {
            return new PhoneNumberGetResponse(AESDecodeUtils.decrypt(sessionKey, ivCode, encryptData));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PhoneNumberGetWrongException();
        }
    }

    /**
     * save avatar to a user
     *
     * @param username
     * @param avatarUrl
     */
    @Override
    public AvatarSaveResponse saveAvatar(String username, String avatarUrl) throws UsernameDoesNotFoundException {
        User user = userDataService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameDoesNotFoundException();
        } else {
            user.setAvatarUrl(avatarUrl);
            userDataService.saveUser(user);
            return new AvatarSaveResponse();
        }
    }


    @Override
    public void updateUser(User user) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDataService.saveUser(user);
    }

    /**
     * check the username whether existent
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        return userDataService.isUserExistent(username);
    }

    @Override
    public Response findUserInfoById(int id) {
        User user = userDataService.getUserById(id);
        if(user == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            if(user.getTableId() == 0) {
                return new WrongResponse(10130, "Wrong id.");
            }
            if(user.getRole() == 1) {
                Staff staff = staffDataService.findStaffById(user.getTableId());
                staff.setUser(null);
                userInfoResponse.setInfo(staff);
                userInfoResponse.setRole(1);
            } else if (user.getRole() == 2) {
                Agent agent = agentDataService.findAgentById(user.getTableId());
                agent.setUser(null);
                userInfoResponse.setInfo(agent);
                userInfoResponse.setRole(2);
            } else if(user.getRole() == 3) {
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                merchant.setUser(null);
                userInfoResponse.setInfo(merchant);
                userInfoResponse.setRole(3);
            } else if(user.getRole() == 4) {
                Supplier supplier = supplierDataService.findSupplierById(user.getTableId());
                supplier.setUser(null);
                userInfoResponse.setInfo(supplier);
                userInfoResponse.setRole(4);
            } else {
                return new WrongResponse(10150, "Wrong role.");
            }
            return userInfoResponse;
        }
    }

    @Override
    public Response deleteUserById(int id) {
        User user = userDataService.getUserById(id);
        if(user == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            if(user.getTableId() == 0) {
                return new WrongResponse(10130, "Wrong id.");
            }
            UserDeleteResponse userDeleteResponse = new UserDeleteResponse(user.getId(), user.getTableId());
            if(user.getRole() == 1) {
                userDeleteResponse.setTableId(user.getTableId());
                staffDataService.deleteStaffById(user.getTableId());
            } else if (user.getRole() == 2) {
                agentDataService.deleteAgentById(user.getTableId());
            } else if(user.getRole() == 3) {
                merchantDataService.deleteMerchantById(user.getTableId());
            } else if(user.getRole() == 4) {
                supplierDataService.deleteSupplierById(user.getTableId());
            } else {
                return new WrongResponse(10150, "Wrong role.");
            }
            return userDeleteResponse;
        }
    }
}
