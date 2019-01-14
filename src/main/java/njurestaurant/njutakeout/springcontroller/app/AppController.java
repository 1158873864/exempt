package njurestaurant.njutakeout.springcontroller.app;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
//import njurestaurant.njutakeout.config.websocket.WebSocketHandshakeInterceptor;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.app.SolidQrCodeGetParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.app.SolidCodeGetResponse;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AppController {
//    @ApiOperation(value = "websocket测试", notes = "websocket测试")
//    @MessageMapping(value = "ws/test")
//    @SendTo("ws/client")
//    public String greeting(String message) throws Exception {
//        // 模拟延时，以便测试客户端是否在异步工作
////        Thread.sleep(1000);
//        return "Hello, ";
//    }

    @ApiOperation(value = "重定向测试", notes = "支付宝跳转重定向测试")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public void redirect(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws IOException {
        response.sendRedirect("alipayqr://platformapi/startapp?saId=20000123&actionType=scan&biz_data={\"s\": \"money\",\"u\": \"2088222774538613\",\"a\": \"0.02\",\"m\": \"66666\"}");
    }



}
