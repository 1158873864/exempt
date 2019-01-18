package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.transaction.FailedToLoadCodeResponse;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TransactionController {

    private final TransactionBlService transactionBlService;

    @Autowired
    public TransactionController(TransactionBlService transactionBlService) {
        this.transactionBlService = transactionBlService;
    }

    @ApiOperation(value = "获取二维码", notes = "商家发起获取二维码请求")
    @RequestMapping(value = "qrCode/get", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GetQrCodeResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getQrCode(@RequestBody GetQrCodeParameters getQrCodeParameters) {
        try {
            GetQrCodeResponse getQrCodeResponse = transactionBlService.getQrCode(getQrCodeParameters);
            if(getQrCodeResponse == null) {
                return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "未获取到供码设备")), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new JSONResponse(200, getQrCodeResponse), HttpStatus.OK);
            }
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "用户id出错。")), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "参数错误。")), HttpStatus.OK);
        }
    }

//    @ApiOperation(value = "增加设备测试", notes = "测试")
//    @RequestMapping(value = "device/add/test", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = GetQrCodeResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> test() {
//        transactionBlService.addDevice();
//        return null;
//    }

    @ApiOperation(value = "重定向", notes = "支付宝跳转重定向")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public void redirect(@RequestParam("orderId") String orderId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String code = transactionBlService.findQrCodeByOrderId(orderId);
            if(code.equals("expired")) {
            } else if(code.equals("paid")) {
            }
            else {
            	String result = "";
                int index = 0;
                for(int i = 0; i < code.length(); i++) {
                	if(code.charAt(i) == '\"') {
                		result = result + code.substring(index, i) + "%22";
                		index = i + 1;
                	}
                }
                if(index == 0) result = code;
                else result += code.substring(index, code.length());
            	response.sendRedirect(result);
            }
        } catch (WrongIdException e) {
        }
    }

    @ApiOperation(value = "根据imei号查找测试", notes = "支付宝跳转重定向")
    @RequestMapping(value = "/test/order", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> platformTest(@RequestParam("imei")String imei) {

        return new ResponseEntity<>(new JSONResponse(200, transactionBlService.findPlatformOrderByImeiAndState(imei, OrderState.WAITING_FOR_PAYING)), HttpStatus.OK);
    }
}
