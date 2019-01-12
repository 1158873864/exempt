package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.blservice.company.*;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.company.*;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CompanyController {

    private final TeamBlService teamBlService;
    private final ReceiptCodeBlService receiptCodeBlService;
    private final CompanyCardBlService companyCardBlService;
    private final PostAndPermissionBlService postAndPermissionBlService;
    private final AllocationRecordBlService allocationRecordBlService;
    private final MerchantBlService merchantBlService;

    @Autowired
    public CompanyController(TeamBlService teamBlService, ReceiptCodeBlService receiptCodeBlService, CompanyCardBlService companyCardBlService, PostAndPermissionBlService postAndPermissionBlService, AllocationRecordBlService allocationRecordBlService, MerchantBlService merchantBlService) {
        this.teamBlService = teamBlService;
        this.receiptCodeBlService = receiptCodeBlService;
        this.companyCardBlService = companyCardBlService;
        this.postAndPermissionBlService = postAndPermissionBlService;
        this.allocationRecordBlService = allocationRecordBlService;
        this.merchantBlService = merchantBlService;
    }



    @ApiOperation(value = "新增团队", notes = "公司管理员新增团队")
    @RequestMapping(value = "company/team/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addTeam(@RequestBody TeamAddParameters teamAddParameters) {
        try {
            TeamAddResponse teamAddResponse = teamBlService.addTeam(teamAddParameters);
            return new ResponseEntity<>(new JSONResponse(200, teamAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, "The team name is existent!", e.getResponse()), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10120, "The team name is null or size 0!", e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "新增收款码", notes = "管理员新增收款码")
    @RequestMapping(value = "company/code/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addReceiptCode(@RequestBody ReceiptCode receiptCode) {
        ReceiptCodeAddResponse receiptCodeAddResponse = receiptCodeBlService.addReceiptCode(new ReceiptCode(receiptCode.getTeamName(), receiptCode.getType(), receiptCode.getDuration(), receiptCode.getPriorityLevel(), receiptCode.getAccountInfo(), receiptCode.getAccountNumber()));
        return new ResponseEntity<>(new JSONResponse(200, receiptCodeAddResponse), HttpStatus.OK);
    }

    @ApiOperation(value = "新增银行卡", notes = "公司管理员新增银行卡")
    @RequestMapping(value = "company/card/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addCompanyCard(@RequestBody CompanyCardAddParameters companyCardAddParameters) {
        CompanyCard companyCard = new CompanyCard(companyCardAddParameters.getName(), companyCardAddParameters.getBank(), companyCardAddParameters.getNumber(), companyCardAddParameters.getBalance(), companyCardAddParameters.getAttribution(), companyCardAddParameters.getRelation(), companyCardAddParameters.getStatus());
        try {
            CompanyCardAddResponse companyCardAddResponse = companyCardBlService.addCompanyCard(companyCard);
            return new ResponseEntity<>(new JSONResponse(200, companyCardAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, "the card number is existent.", e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "权限新增", notes = "管理员新增权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "post", value = "岗位", required = true, dataType = "String"),
            @ApiImplicitParam(name = "permission", value = "权限", required = true, dataType = "String"),
    })
    @RequestMapping(value = "company/permission/allocate", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPermissionOfPost(@RequestParam(name = "post") String post, @RequestParam(name = "permission") String permission) {
        PostAndPermissionAddResponse postAndPermissionAddResponse= postAndPermissionBlService.addPostAndPermission(new PostAndPermission(post, permission));
        if(postAndPermissionAddResponse.getId() != 0) {
            allocationRecordBlService.addAllocationRecordBlService(new AllocationRecord(post, permission, new Date()));
        }
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionAddResponse), HttpStatus.OK);
    }

    @ApiOperation(value = "收款码列表", notes = "财务管理收款码列表")
    @RequestMapping(value = "company/codes", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showReceiptCode() {
        return new ResponseEntity<>(new JSONResponse(200, receiptCodeBlService.loadReceiptCodes()), HttpStatus.OK);
    }

    @ApiOperation(value = "银行卡列表", notes = "财务管理银行卡列表")
    @RequestMapping(value = "company/cards", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showBankCards() {
        return new ResponseEntity<>(new JSONResponse(200, companyCardBlService.loadAllCompanyCards()), HttpStatus.OK);
    }

    @ApiOperation(value = "团队列表", notes = "查看全部团队列表")
    @RequestMapping(value = "company/teams", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showTeamsNumber() {
//        List<Integer> teamsNumber = teamBlService.loadAllTeam().stream().map(x -> x.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(new JSONResponse(200,  teamBlService.loadAllTeam()), HttpStatus.OK);
    }

    @ApiOperation(value = "审批商户账号开通", notes = "管理员审批待审批账号列表")
    @RequestMapping(value = "company/approval/{mid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantApproval(@PathVariable("mid") int mid, @RequestParam("state")String state) {

        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.ApprovalMerchant(mid, state)), HttpStatus.OK);
//        List<Integer> teamsNumber = teamBlService.loadAllTeam().stream().map(x -> x.getId()).collect(Collectors.toList());
//        return new ResponseEntity<>(new JSONResponse(200,  teamBlService.loadAllTeam()), HttpStatus.OK);
    }
}
