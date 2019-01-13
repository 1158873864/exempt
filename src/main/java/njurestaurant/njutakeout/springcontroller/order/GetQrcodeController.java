package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.blservice.order.OrderBlService;
import njurestaurant.njutakeout.config.WebSocketHandler;
import njurestaurant.njutakeout.config.WebSocketHandshakeInterceptor;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.OrderIdDoesNotExistException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.parameters.user.UserLoginParameters;
import njurestaurant.njutakeout.publicdatas.account.Role;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderAcceptResponse;
import njurestaurant.njutakeout.response.order.OrderConfirmArriveResponse;
import njurestaurant.njutakeout.response.order.OrderRejectResponse;
import njurestaurant.njutakeout.response.user.UserLoginResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import aj.org.objectweb.asm.Attribute;

//@PreAuthorize(value = "hasRole('" + Role.RESTAURANT_NAME + "')")
@RestController
public class GetQrcodeController {
//    //private final OrderBlService orderBlService;
//
//    @Autowired
//    public GetQrcodeController(OrderBlService orderBlService) {
//        this.orderBlService = orderBlService;
//    }
    
	//商户可选择获取通码或固码
	//商户和出码用户对应关系为多对一
	
    @ApiOperation(value = "获取通码", notes = "返回通码url等信息")
    @RequestMapping(value = "/getpasscode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GetPassCodeResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getpasscode() {
        try {
        	
        	String imei = WebSocketHandshakeInterceptor.imei;
        	User user = UserBlService.findUserByimei(imei);
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("cmd", "passcode");
            jsonObject.put("type", "alipay");
            jsonObject.put("imei", imei);
            jsonObject.put("userid", "支付宝userid");
            WebSocketHandler.sendMessageToUser(imei, new TextMessage(jsonObject.toString()));	
            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
        } catch (CannotRegisterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    
    @ApiOperation(value = "获取固码", notes = "返回固码url等信息")
    @RequestMapping(value = "/getsolidcode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GetSolidCodeResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getsolidcode(String money , String memo) {
        try {
        	
        	String imei="123";
        	User user = UserBlService.findUserByimei(imei);
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("cmd", "solidcode");
            jsonObject.put("type", "alipay");
            jsonObject.put("imei", imei);
            jsonObject.put("userid", "支付宝userid");
            jsonObject.put("money", money);
            jsonObject.put("memo", memo);
            WebSocketHandler.sendMessageToUser(imei, new TextMessage(jsonObject.toString()));	
            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
        } catch (CannotRegisterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
