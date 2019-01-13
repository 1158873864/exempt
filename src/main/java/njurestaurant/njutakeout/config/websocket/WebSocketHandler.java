package njurestaurant.njutakeout.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebSocketHandler extends TextWebSocketHandler {
    @Value("${CROWD_CAST_USER_TOKEN_KEY}")
    private String CROWD_CAST_USER_TOKEN_KEY;
    /**
     * 用Map存储已建立连接的用户
     */
    private static final Map<String, WebSocketSession> socketSessionMap = new HashMap<>();

    /**
     * 处理前端发送的文本信息 js调用websocket.send时候，会调用该方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

//		JSONObject jsonObject = new JSONObject(message.getPayload());
//        String cmd = jsonObject.getString("cmd");
//        String type = jsonObject.getString("type");
//        //收到设备更新信息
//        if(cmd.equals("validation") && type.equals("alipay")){
//		    String userid = jsonObject.getString("userid");
//		    String loginid = jsonObject.getString("loginid");
//		    String imei = jsonObject.getString("imei");
//		    String name = jsonObject.getString("name");
//		    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//
//		 // 如果设备更新成功入库，则回复一条信息
//			JSONObject jsonObject2 = new JSONObject();
//	        jsonObject2.put("cmd", cmd);
//	        jsonObject2.put("status", "success");
//	        jsonObject2.put("err", "msg");
//	        jsonObject2.put("imei", imei);
//	        session.sendMessage(new TextMessage(jsonObject2.toString()));
//        }
//
//        //收到订单信息
//        if(cmd.equals("order") && type.equals("alipay")){
//        	String imei = jsonObject.getString("imei");
//		    String orderId = jsonObject.getString("orderId");
//		    String money = jsonObject.getString("money");
//		    String memo = jsonObject.getString("memo");
//		    String time = jsonObject.getString("time");
//		    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//
//		 // 如果订单成功入库，则回复一条信息
//		    if(true){
//				JSONObject jsonObject2 = new JSONObject();
//		        jsonObject2.put("cmd", cmd);
//		        jsonObject2.put("status", "success");
//		        jsonObject2.put("orderId", orderId);
//		        jsonObject2.put("imei", imei);
//		        session.sendMessage(new TextMessage(jsonObject2.toString()));
//	        }
//		    //如果订单入库失败，则回复一条信息
//		    else {
//	        	JSONObject jsonObject3 = new JSONObject();
//		        jsonObject3.put("cmd", cmd);
//		        jsonObject3.put("status", "failed");
//		        jsonObject3.put("err", "错误信息");
//		        jsonObject3.put("orderId", orderId);
//		        jsonObject3.put("imei", imei);
//		        session.sendMessage(new TextMessage(jsonObject3.toString()));
//	        }
//         }
//
//        //收到通码链接
//        if(cmd.equals("passcode") && type.equals("alipay")){
//        	String status = jsonObject.getString("status");
//        	if(status.equals("success")) {
//        		//如果支付宝在线
//			    String imei = jsonObject.getString("imei");
//		    	String userid = jsonObject.getString("userid");
//			    String qrcode = jsonObject.getString("qrcode");
//			    String offqrcode = jsonObject.getString("offqrcode");
//			    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//        	}
//        	if(status.equals("failed")) {
//        		//如果支付宝离线
//        		String imei = jsonObject.getString("imei");
// 		    	String err = jsonObject.getString("支付宝掉线");
//			    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//        	}
//         }
//
//        //收到固码链接
//        if(cmd.equals("solidcode") && type.equals("alipay")){
//        	String status = jsonObject.getString("status");
//        	if(status.equals("success")) {
//        		//如果支付宝在线
//			    String imei = jsonObject.getString("imei");
//		    	String userid = jsonObject.getString("userid");
//			    String qrcode = jsonObject.getString("qrcode");
//			    String money = jsonObject.getString("money");
//			    String memo = jsonObject.getString("memo");
//			    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//        	}
//        	if(status.equals("failed")) {
//        		//如果支付宝离线
//        		String imei = jsonObject.getString("imei");
// 		    	String err = jsonObject.getString("支付宝掉线");
//			    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());
//        	}
//         }

    }

    /**
     * 当新连接建立的时候，被调用连接成功时候，会触发页面上onOpen方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("用户 " + session.getId() + " 已建立连接");
        // session.sendMessage(new TextMessage("@"+Settings.SUCCESS_CODE + ""));
    }

    /**
     * 当连接关闭时被调用
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("用户 " + session.getId() + " 已关闭连接。 当前状态：" + status);

    }

    /**
     * 传输错误时调用
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        if (session.isOpen()) {
            session.close();
        }
        System.out.println("用户 " + session.getId() + " 已关闭连接");

    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public static void sendMessageToUsers(TextMessage message) {

        for (WebSocketSession socketSession : socketSessionMap.values()) {
            try {
                if (socketSession.isOpen()) {
                    socketSession.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 给某个用户发送消息
     *
     * @param token
     * @param message
     */
    public static void sendMessageToUser(String token, TextMessage message) {

        Set<String> keySet = socketSessionMap.keySet();
        for (String key : keySet) {
            System.out.println("sendMessageToUser:" + key);
        }
        WebSocketSession socketSession = socketSessionMap.get(token);
        try {
            if (socketSession != null && socketSession.isOpen()) {
                socketSession.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
