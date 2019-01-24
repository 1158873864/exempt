package njurestaurant.njutakeout.springcontroller.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.blservice.report.ReportBlService;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.AgentReportResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ReportController {

    private final ReportBlService reportBlService;

    @Autowired
    public ReportController(ReportBlService reportBlService) {
        this.reportBlService = reportBlService;
    }

    @ApiOperation(value = "商户报表", notes = "管理员查看商户全部报表")
    @RequestMapping(value = "report/merchant", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfMerchant(date)), HttpStatus.OK);
    }

    @ApiOperation(value = "代理报表", notes = "管理员查看代理商全部报表")
    @RequestMapping(value = "report/agent", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AgentReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> agentReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfAgent(startDate, endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

}
