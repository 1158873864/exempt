package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.account.AgentBlService;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.blservice.account.StaffBlService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.account.Staff;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.company.StaffAddParameters;
import njurestaurant.njutakeout.parameters.user.*;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.StaffAddResponse;
import njurestaurant.njutakeout.response.user.AdminAddResponse;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import njurestaurant.njutakeout.response.user.UserLoginResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {
    private final UserBlService userBlService;
    private final StaffBlService staffBlService;
    private final AgentBlService agentBlService;
    private final MerchantBlService merchantBlService;

    @Autowired
    public UserController(UserBlService userBlService, StaffBlService staffBlService, AgentBlService agentBlService, MerchantBlService merchantBlService) {
        this.userBlService = userBlService;
        this.staffBlService = staffBlService;
        this.agentBlService = agentBlService;
        this.merchantBlService = merchantBlService;
    }

    @ApiOperation(value = "用户登录", notes = "验证用户登录并返回token")
    @RequestMapping(value = "account/login", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> login(@RequestBody UserLoginParameters userLoginParameters) {
        try {
            UserLoginResponse userLoginResponse = userBlService.login(userLoginParameters.getUsername(), userLoginParameters.getPassword());
            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
        } catch (CannotRegisterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

//    @ApiOperation(value = "用户注册", notes = "管理员注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
//    })
//    @RequestMapping(value = "account/register", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> register(
//            @RequestParam("username") String username, @RequestParam("password") String password) {
//        UserLoginResponse userLoginResponse = userBlService.register(username, password);
//        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "获得微信openid", notes = "获得微信openid")
//    @RequestMapping(method = RequestMethod.POST, path = "/getOpenId", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> getOpenId(@RequestParam("jsCode") String jsCode) {
//        try {
//            return new ResponseEntity<>(userBlService.getOpenIdAndSessionKey(jsCode), HttpStatus.OK);
//        } catch (CannotGetOpenIdAndSessionKeyException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @ApiOperation(value = "获得手机号码", notes = "获得手机号码")
//    @RequestMapping(method = RequestMethod.POST, path = "/getPhoneNumber", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> getPhoneNumber(@RequestBody PhoneNumberGetParameters phoneNumberGetParameters) {
//        try {
//            return new ResponseEntity<>(userBlService.decryptData(phoneNumberGetParameters.getEncryptData(), phoneNumberGetParameters.getSessionKey(), phoneNumberGetParameters.getIvCode()), HttpStatus.OK);
//        } catch (PhoneNumberGetWrongException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @ApiOperation(value = "保存用户头像", notes = "提交订单工程中保存用户头像")
//    @RequestMapping(method = RequestMethod.POST, path = "/saveAvatar", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> saveAvatar(@RequestBody AvatarSaveParameters avatarSaveParameters) {
//        try {
//            return new ResponseEntity<>(userBlService.saveAvatar(UserInfoUtil.getUsername(), avatarSaveParameters.getAvatarUrl()), HttpStatus.OK);
//        } catch (UsernameDoesNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }

    @ApiOperation(value = "新增管理员", notes = "管理员新增管理员")
    @RequestMapping(value = "admin/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = StaffAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addStaff(@RequestBody StaffAddParameters staffAddParameters) throws UsernameIsExistentException {
        if(!userBlService.checkUsername(staffAddParameters.getUsername())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(staffAddParameters.getUsername(), encoder.encode(staffAddParameters.getPassword()), 1);
            Staff staff = new Staff(staffAddParameters.getUsername(), staffAddParameters.getTeam(), new Date(), staffAddParameters.getCode(), staffAddParameters.getOperator(), staffAddParameters.getStatus(), staffAddParameters.getPost(), user);
            Staff result =  staffBlService.addStaff(staff);
            user.setTableId(result.getId());
            userBlService.updateUser(user);
            AdminAddResponse adminAddResponse = new AdminAddResponse(staff.getId(), 1);
            return new ResponseEntity<>(new JSONResponse(200, adminAddResponse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "新增代理商", notes = "管理员新增代理")
    @RequestMapping(value = "agent/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AgentAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addAgent(@RequestBody AgentAddParameters agentAddParameters){
        if(userBlService.checkUsername(agentAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(agentAddParameters.getUsername(), encoder.encode(agentAddParameters.getPassword()), 2);
            Agent agent = new Agent(agentAddParameters.getUsername(), agentAddParameters.getFlow(), agentAddParameters.getStatus(), agentAddParameters.getCode(), agentAddParameters.getBrokerage(), user);
            AgentAddResponse agentAddResponse = agentBlService.addAgent(agent);
            user.setTableId(agentAddResponse.getAgentId());
            userBlService.updateUser(user);
            return new ResponseEntity<>(new JSONResponse(200, agentAddResponse), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "新增商家", notes = "代理/管理员新增商家")
    @RequestMapping(value = "merchant/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addAgent(@RequestBody MerchantAddParameters merchantAddParameters) {
        if(userBlService.checkUsername(merchantAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        } else if(StringUtils.isBlank(merchantAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10110, new BlankInputException().getResponse()), HttpStatus.OK);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(merchantAddParameters.getUsername(), encoder.encode(merchantAddParameters.getPassword()), 3);
            Merchant merchant = new Merchant(merchantAddParameters.getAlipay(), merchantAddParameters.getWechat(), merchantAddParameters.getBalance(), MerchantState.WAITING, merchantAddParameters.getCode(), new Date(), merchantAddParameters.getUsername(), merchantAddParameters.getSuperior(), user);
            MerchantAddResponse merchantAddResponse = merchantBlService.addMerchant(merchant);
            userBlService.updateUser(user);
            return new ResponseEntity<>(new JSONResponse(200, merchantAddResponse), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "删除代理商", notes = "管理员删除代理商")
    @RequestMapping(value = "agent/delete/{aid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteAgent(@PathVariable("aid")int aid) {
        agentBlService.delAgentById(aid);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
    }

    @ApiOperation(value = "删除商家", notes = "代理商/管理员删除商家")
    @RequestMapping(value = "merchant/delete/{mid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteMerchant(@PathVariable("mid")int mid) {
        merchantBlService.delMerchantById(mid);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
    }

    @ApiOperation(value = "删除管理员", notes = "主管理员删除管理员")
    @RequestMapping(value = "admin/delete/{aid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteAdmin(@PathVariable("aid")int aid) {
        staffBlService.delStaffById(aid);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
    }

}
