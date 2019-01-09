package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.company.StaffBlService;
import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.StaffLoginReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class StaffController {
    private final StaffBlService staffBlService;

    @Autowired
    public StaffController(StaffBlService staffBlService) {
        this.staffBlService = staffBlService;
    }

    @ApiOperation(value = "用户登录", notes = "验证用户登录并返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "staff/login", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = StaffLoginReponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> login(
            @RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            StaffLoginReponse staffLoginReponse = staffBlService.login(username, password);
            return new ResponseEntity<>(staffLoginReponse, HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
        } catch (CannotRegisterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ApiOperation(value = "新增用户", notes = "管理员新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "team", value = "所属团队", required = true, dataType = "String"),
            @ApiImplicitParam(name = "post", value = "职务", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "addTime", value = "添加时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "operator", value = "操作上级", required = true, dataType = "String")
    })
    @RequestMapping(value = "staff/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = StaffLoginReponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> register(
            @RequestParam("username") String username, @RequestParam("team") String team,
            @RequestParam("status") String status, @RequestParam("post") String post,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("addTime") Date addTime,
            @RequestParam("operator") String operator) {
        StaffLoginReponse staffLoginReponse = staffBlService.add(username, team, post, status, verifyCode, addTime, operator);
        return new ResponseEntity<>(staffLoginReponse, HttpStatus.OK);
    }
}
