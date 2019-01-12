package njurestaurant.njutakeout.springcontroller.app;

import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AppController {
    @ApiOperation(value = "websocket测试", notes = "websocket测试")
    @MessageMapping(value = "ws/test")
    @SendTo("ws/client")
    public String greeting(String message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
//        Thread.sleep(1000);
        return "Hello, ";
    }

    @ApiOperation(value = "重定向测试", notes = "支付宝跳转重定向测试")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public void redirect(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws IOException {
        response.sendRedirect("alipayqr://platformapi/startapp?saId=20000123");
    }
}
