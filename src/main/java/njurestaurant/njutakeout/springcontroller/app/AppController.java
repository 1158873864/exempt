package njurestaurant.njutakeout.springcontroller.app;

//import njurestaurant.njutakeout.config.websocket.WebSocketHandshakeInterceptor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import io.swagger.annotations.ApiOperation;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.exception.CannotRegisterException;
import njurestaurant.njutakeout.exception.WrongUsernameOrPasswordException;
import njurestaurant.njutakeout.parameters.app.AppLoginParameters;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.user.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    private final UserBlService userBlService;

    @Autowired
    public AppController(UserBlService userBlService) {
        this.userBlService = userBlService;
    }

    //    @ApiOperation(value = "websocket测试", notes = "websocket测试")
//    @MessageMapping(value = "ws/test")
//    @SendTo("ws/client")
//    public String greeting(String message) throws Exception {
//        // 模拟延时，以便测试客户端是否在异步工作
////        Thread.sleep(1000);
//        return "Hello, ";
//    }


    @ApiOperation(value = "app用户登录", notes = "供码用户在app上登录")
    @RequestMapping(value = "app/login", method = RequestMethod.POST)
    public ResponseEntity<Response> appLogin(@RequestBody AppLoginParameters appLoginParameters) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, userBlService.appLogin(appLoginParameters.getUsername(), appLoginParameters.getPassword(), appLoginParameters.getImei())), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            return new ResponseEntity<>(new JSONResponse(10003, e.getResponse()), HttpStatus.OK);
        } catch (CannotRegisterException e) {
            return new ResponseEntity<>(new JSONResponse(10006, e.getResponse()), HttpStatus.OK);
        }
    }

}
