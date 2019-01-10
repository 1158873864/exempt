package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.company.*;
import njurestaurant.njutakeout.entity.company.AllocationRecord;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CompanyController {
    private final StaffBlService staffBlService;
    private final TeamBlService teamBlService;
    private final ReceiptCodeBlService receiptCodeBlService;
    private final CompanyCardBlService companyCardBlService;
    private final PostAndPermissionBlService postAndPermissionBlService;
    private final AllocationRecordBlService allocationRecordBlService;

    @Autowired
    public CompanyController(StaffBlService staffBlService, TeamBlService teamBlService, ReceiptCodeBlService receiptCodeBlService, CompanyCardBlService companyCardBlService, PostAndPermissionBlService postAndPermissionBlService, AllocationRecordBlService allocationRecordBlService) {
        this.staffBlService = staffBlService;
        this.teamBlService = teamBlService;
        this.receiptCodeBlService = receiptCodeBlService;
        this.companyCardBlService = companyCardBlService;
        this.postAndPermissionBlService = postAndPermissionBlService;
        this.allocationRecordBlService = allocationRecordBlService;
    }



    @ApiOperation(value = "管理员登录", notes = "验证用户登录并返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "admin/login", method = RequestMethod.GET)
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

    @ApiOperation(value = "新增管理员", notes = "管理员新增管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "team", value = "所属团队", required = true, dataType = "String"),
            @ApiImplicitParam(name = "post", value = "职务", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "addTime", value = "添加时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "operator", value = "操作上级", required = true, dataType = "String")
    })
    @RequestMapping(value = "admin/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = StaffLoginReponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addStaff(
            @RequestParam("username") String username, @RequestParam("team") String team,
            @RequestParam("status") String status, @RequestParam("post") String post,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("addTime") Date addTime,
            @RequestParam("operator") String operator) {
        StaffLoginReponse staffLoginReponse = staffBlService.add(username, team, post, status, verifyCode, addTime, operator);
        return new ResponseEntity<>(staffLoginReponse, HttpStatus.OK);
    }

    @ApiOperation(value = "新增团队", notes = "公司管理员新增团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supervisor", value = "主管账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "area", value = "地区分布", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "operator", value = "添加人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "addTime", value = "添加时间", required = true, dataType = "Date"),
    })
    @RequestMapping(value = "company/team/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addTeam(@RequestBody TeamAddParameters teamAddParameters) {
        TeamAddResponse teamAddResponse = teamBlService.addTeam(teamAddParameters);
        return new ResponseEntity<>(teamAddResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "新增收款码", notes = "管理员新增收款码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "team", value = "归属团队", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "所属类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "priority", value = "优先等级", required = true, dataType = "String"),
            @ApiImplicitParam(name = "duration", value = "持续时间", required = true, dataType = "int"),
            @ApiImplicitParam(name = "info", value = "账号信息", required = true, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "提现卡号", required = true, dataType = "String"),
    })
    @RequestMapping(value = "company/code/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addReceiptCode(
            @RequestParam("type") String type, @RequestParam("team") String team,
            @RequestParam("priority") String priority, @RequestParam("duration") int duration,
            @RequestParam("number") String number, @RequestParam("info") String info) {
        ReceiptCodeAddResponse receiptCodeAddResponse = receiptCodeBlService.addReceiptCode(new ReceiptCode(team, type, duration, priority, info, number));
        return new ResponseEntity<>(receiptCodeAddResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "新增银行卡", notes = "公司管理员新增银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bank", value = "银行", required = true, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "卡号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "balance", value = "余额", required = true, dataType = "double"),
            @ApiImplicitParam(name = "attribution", value = "归属", required = true, dataType = "String"),
            @ApiImplicitParam(name = "relation", value = "关联", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String"),
    })
    @RequestMapping(value = "company/card/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addCompanyCard(@RequestBody CompanyCardAddParameters companyCardAddParameters) {
        CompanyCard companyCard = new CompanyCard(companyCardAddParameters.getName(), companyCardAddParameters.getBank(), companyCardAddParameters.getNumber(), companyCardAddParameters.getBalance(), companyCardAddParameters.getAttribution(), companyCardAddParameters.getRelation(), companyCardAddParameters.getStatus());
        CompanyCardAddResponse companyCardAddResponse = companyCardBlService.addCompanyCard(companyCard);
        return new ResponseEntity<>(companyCardAddResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "权限新增", notes = "管理员新增权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "post", value = "岗位", required = true, dataType = "String"),
            @ApiImplicitParam(name = "permission", value = "权限", required = true, dataType = "String"),
    })
    @RequestMapping(value = "company/permission/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPermissionOfPost(@RequestParam(name = "post") String post, @RequestParam(name = "permission") String permission) {
        PostAndPermissionAddResponse postAndPermissionAddResponse= postAndPermissionBlService.addPostAndPermission(new PostAndPermission(post, permission));
        if(postAndPermissionAddResponse.getId() != 0) {
            allocationRecordBlService.addAllocationRecordBlService(new AllocationRecord(post, permission, new Date()));
        }
        return new ResponseEntity<>(postAndPermissionAddResponse, HttpStatus.OK);
    }

}
