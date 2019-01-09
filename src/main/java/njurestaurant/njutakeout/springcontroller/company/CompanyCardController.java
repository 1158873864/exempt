package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.company.CompanyCardBlService;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.CompanyCardAddResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyCardController {
    private final CompanyCardBlService companyCardBlService;

    @Autowired
    public CompanyCardController(CompanyCardBlService companyCardBlService) {
        this.companyCardBlService = companyCardBlService;
    }

    @ApiOperation(value = "新增银行卡", notes = "公司管理员新增公司")
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

}
