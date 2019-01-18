package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final PlatformOrderBlService platformOrderBlService;

    @Autowired
    public OrderController(PlatformOrderBlService platformOrderBlService) {
        this.platformOrderBlService = platformOrderBlService;
    }

    @ApiOperation(value = "订单明细", notes = "查看全部订单明细")
    @RequestMapping(value = "order/list", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderListResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getOrders() {
        return new ResponseEntity<>(new JSONResponse(200,  platformOrderBlService.findAllPlatformOrders()), HttpStatus.OK);
    }

    @ApiOperation(value = "修改订单", notes = "修改某张订单")
    @RequestMapping(value = "order/list/update/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getOrder(@PathVariable("id")int id, @RequestBody PlatformUpdateParameters platformUpdateParameters) {
        try {
            return new ResponseEntity<>(new JSONResponse(200,  platformOrderBlService.updatePlatformOrder(id, platformUpdateParameters)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160,  e.getResponse()), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10170,  new WrongResponse(10170, "输入格式错误")), HttpStatus.OK);
        }
    }
}
