package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.company.ReceiptCodeBlService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptCodeController {
    private final ReceiptCodeBlService receiptCodeBlService;

    @Autowired
    public ReceiptCodeController(ReceiptCodeBlService receiptCodeBlService) {
        this.receiptCodeBlService = receiptCodeBlService;
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
    @RequestMapping(value = "receiptCode/add", method = RequestMethod.GET)
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
}
