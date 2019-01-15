package njurestaurant.njutakeout.springcontroller.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.report.MerchantReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

    private final PlatformOrderBlService platformOrderBlService;

    @Autowired
    public ReportController(PlatformOrderBlService platformOrderBlService) {
        this.platformOrderBlService = platformOrderBlService;
    }

    @ApiOperation(value = "商户个人报表", notes = "商户查看个人的报表")
    @RequestMapping(value = "report/merchant/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantReport.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantReport(@PathVariable("id") int id) {
        return new ResponseEntity<>(new JSONResponse(200, platformOrderBlService.merchantOrderReportByUid(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "全部商户报表", notes = "管理员查看商户全部报表")
    @RequestMapping(value = "report/merchants", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantReport.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantsReport() {
        return null;
    }
}
