package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.blservice.account.SupplierBlService;
import njurestaurant.njutakeout.blservice.company.*;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.company.*;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.parameters.company.PermissionsAllocationParameters;
import njurestaurant.njutakeout.parameters.company.ReceiptCodeAddParameters;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    private final TeamBlService teamBlService;
    private final ReceiptCodeBlService receiptCodeBlService;
    private final CompanyCardBlService companyCardBlService;
    private final PostAndPermissionBlService postAndPermissionBlService;
    private final AllocationRecordBlService allocationRecordBlService;
    private final MerchantBlService merchantBlService;
    private final SupplierBlService supplierBlService;

    @Autowired
    public CompanyController(TeamBlService teamBlService, ReceiptCodeBlService receiptCodeBlService, CompanyCardBlService companyCardBlService, PostAndPermissionBlService postAndPermissionBlService, AllocationRecordBlService allocationRecordBlService, MerchantBlService merchantBlService, SupplierBlService supplierBlService) {
        this.teamBlService = teamBlService;
        this.receiptCodeBlService = receiptCodeBlService;
        this.companyCardBlService = companyCardBlService;
        this.postAndPermissionBlService = postAndPermissionBlService;
        this.allocationRecordBlService = allocationRecordBlService;
        this.merchantBlService = merchantBlService;
        this.supplierBlService = supplierBlService;
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
    public ResponseEntity<Response> addReceiptCode(@RequestBody ReceiptCodeAddParameters receiptCodeAddParameters) {
        ReceiptCodeAddResponse receiptCodeAddResponse = receiptCodeBlService.addReceiptCode(new ReceiptCode(receiptCodeAddParameters.getTeam(), receiptCodeAddParameters.getType(), receiptCodeAddParameters.getDuration(), receiptCodeAddParameters.getPriority(), receiptCodeAddParameters.getInfo(), receiptCodeAddParameters.getNumber()));
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
        if(StringUtils.isBlank(companyCardAddParameters.getNumber())) {
            return new ResponseEntity<>(new JSONResponse(10110, new WrongResponse(10110, "the card number is null.")), HttpStatus.OK);
        }
        CompanyCard companyCard = new CompanyCard(companyCardAddParameters.getName(), companyCardAddParameters.getBank(), companyCardAddParameters.getNumber(), companyCardAddParameters.getBalance(), companyCardAddParameters.getAttribution(), companyCardAddParameters.getRelation(), companyCardAddParameters.getStatus());
        try {
            CompanyCardAddResponse companyCardAddResponse = companyCardBlService.addCompanyCard(companyCard);
            return new ResponseEntity<>(new JSONResponse(200, companyCardAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, "the card number is existent.", e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "权限分配", notes = "管理员分配权限")
    @RequestMapping(value = "company/permission/allocate", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPermissionOfPost(@RequestBody PermissionsAllocationParameters permissionsAllocationParameters) {
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.addPostAndPermissions(permissionsAllocationParameters.getPost(), permissionsAllocationParameters.getPermissions())), HttpStatus.OK);
    }

    @ApiOperation(value = "查看权限", notes = "管理员查看岗位的权限")
    @RequestMapping(value = "company/permission", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showPermissionOfPost(@RequestParam("post") String post) {
        if(StringUtils.isBlank(post)) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "post is null.")), HttpStatus.OK);
        }
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.getPostAndPermissionsByPost(post)), HttpStatus.OK);
    }

    @ApiOperation(value = "查看全部岗位权限", notes = "管理员查看全部岗位的权限")
    @RequestMapping(value = "company/permissions", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showPermissions() {
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.getAll()), HttpStatus.OK);
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
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showTeamsNumber() {
//        List<Integer> teamsNumber = teamBlService.loadAllTeam().stream().map(x -> x.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(new JSONResponse(200,  teamBlService.loadAllTeam()), HttpStatus.OK);
    }

    @ApiOperation(value = "审批商户账号开通", notes = "管理员审批待审批的商户账号")
    @RequestMapping(value = "company/approval/merchant/{mid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantApproval(@PathVariable("mid") int mid, @RequestParam("state")String state) {

        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.ApprovalMerchant(mid, state)), HttpStatus.OK);
    }

    @ApiOperation(value = "待审批商户账号", notes = "管理员查看待审批的商户账号列表")
    @RequestMapping(value = "company/approval/merchants", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Merchant.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> approvalMerchants() {

        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.findMerchantsByState(MerchantState.WAITING)), HttpStatus.OK);
    }

    @ApiOperation(value = "审批供码账号", notes = "管理员审批待审批供码用户账号")
    @RequestMapping(value = "company/approval/supplier/{sid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> supplierApproval(@PathVariable("sid") int sid, @RequestParam("state")String state) {
        return new ResponseEntity<>(new JSONResponse(200, supplierBlService.approvalSupplier(sid, state)), HttpStatus.OK);
    }

    @ApiOperation(value = "待审批供码账号", notes = "管理员查看待审批的供码用户列表")
    @RequestMapping(value = "company/approval/suppliers", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Supplier.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> approvalSuppliers() {

        return new ResponseEntity<>(new JSONResponse(200, supplierBlService.findSupplierByState(SupplierState.CHECKING)), HttpStatus.OK);
    }

    @ApiOperation(value = "删除银行卡", notes = "删除银行卡")
    @RequestMapping(value = "company/card/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteBankCard(@PathVariable("id") int id) {
        companyCardBlService.delCompanyCardById(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }

//    @ApiOperation(value = "公司银行卡信息", notes = "查看银行卡详细信息")
//    @RequestMapping(value = "company/card/info/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showBankCard(@PathVariable("id") int id) {
//        CompanyCard companyCard = companyCardBlService.loadCompanyCardById(id);
//        if(companyCard == null || companyCard.getId() == 0) {
//            return new ResponseEntity<>(new JSONResponse(10150, new WrongResponse(10130, "Id isn't existent.")), HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(new JSONResponse(200, companyCard), HttpStatus.OK);
//    }

    @ApiOperation(value = "删除收款码", notes = "删除收款码")
    @RequestMapping(value = "company/code/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteReceiptCode(@PathVariable("id") int id) {
        receiptCodeBlService.delReceiptCode(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }

//    @ApiOperation(value = "收款码信息", notes = "查看收款码详细信息")
//    @RequestMapping(value = "company/code/info/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showReceiptCode(@PathVariable("id") int id) {
//        ReceiptCode receiptCode = receiptCodeBlService.findReceiptCodeById(id);
//        if(receiptCode == null || receiptCode.getId() == 0) {
//            return new ResponseEntity<>(new JSONResponse(10150, new WrongResponse(10130, "Id isn't existent.")), HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(new JSONResponse(200, receiptCode), HttpStatus.OK);
//    }
}
