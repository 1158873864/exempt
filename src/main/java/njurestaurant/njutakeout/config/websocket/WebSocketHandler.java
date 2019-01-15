package njurestaurant.njutakeout.config.websocket;

import com.amazonaws.util.json.JSONObject;
import njurestaurant.njutakeout.bl.order.TransactionBlServiceImpl;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.app.DeviceUpdateResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebSocketHandler extends TextWebSocketHandler {
    @Value("${CROWD_CAST_USER_TOKEN_KEY}")
    private String CROWD_CAST_USER_TOKEN_KEY;

    private final DeviceDataService deviceDataService;
    private final AlipayDataService alipayDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final PlatformOrderDataService platformOrderDataService;
    private final MerchantDataService merchantDataService;
    private final UserDataService userDataService;

    @Autowired
    public WebSocketHandler(DeviceDataService deviceDataService, AlipayDataService alipayDataService, AlipayOrderDataService alipayOrderDataService, PlatformOrderDataService platformOrderDataService, MerchantDataService merchantDataService, UserDataService userDataService) {
        this.deviceDataService = deviceDataService;
        this.alipayDataService = alipayDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.platformOrderDataService = platformOrderDataService;
        this.merchantDataService = merchantDataService;
        this.userDataService = userDataService;
    }

    public static Map<String, Thread> mapThread = new HashMap<>();

    public static Map<String, TextMessage> msgMap = new HashMap<>();


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
    	System.out.println("123");
    	session.sendMessage(new TextMessage(message.getPayload()));
		JSONObject jsonObject = new JSONObject(message.getPayload());
        String cmd = jsonObject.getString("cmd");
        String type = jsonObject.getString("type");
        String imei = jsonObject.getString("imei");

        //收到设备更新信息
        // 客户端消息:{"cmd":"validation","type":"alipay","userid":"支付宝userid","loginid":"支付宝loginid","imei":"设备唯一标识","name":"支付宝账号昵称/姓名(暂时未定)"}
        if(cmd.equals("validation") && type.equals("alipay")){

            String userid = jsonObject.getString("userid");
            String loginid = jsonObject.getString("loginid");
            String name = jsonObject.getString("name");
            Device device = deviceDataService.findByImei(imei);
            if(device != null) {
                Alipay alipay = alipayDataService.findById(device.getAlipayId());
                if(alipay == null || !alipay.getUserId().equals(userid)) {  // 没有支付宝信息/支付宝信息对不上
                    alipay = new Alipay(loginid, userid, null, null, null, imei, name);
                    Alipay a = alipayDataService.saveAlipay(alipay);
                    device.setAlipayId(a.getId());
                }
                device.setOnline(1);    // 支付宝已登录
                deviceDataService.saveDevice(device);
                // 入库成功
                session.sendMessage(new TextMessage(new DeviceUpdateResponse("", imei).toString()));
            }
        }

        // 收到订单信息
        // 客户端消息(订单信息):{"cmd":"order","type":"alipay","imei":"设备唯一标识","orderId":"订单号","money":"订单金额","memo":"备注","time":"订单时间"}
        if(cmd.equals("order") && type.equals("alipay")){

            String orderId = jsonObject.getString("orderId");
            double money = jsonObject.getDouble("money");
            String memo = jsonObject.getString("memo");
            String time = jsonObject.getString("time");

            Device device = deviceDataService.findByImei(imei);
            Supplier supplier = device.getSupplier();
            System.out.println(jsonObject);
            if(supplier.getCodeType() == CodeType.RPASSQR || supplier.getCodeType() == CodeType.RPASSOFF) { //供码用户提供收款码
            	System.out.println("1:" + supplier.getCodeType() + supplier.getId() + " " + imei);
                // 提取imei，根据imei查询未付款的订单号，根据订单号把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
                PlatformOrder platformOrders = platformOrderDataService.findByImeiAndState(imei, OrderState.WAITING_FOR_PAYING);
                if(platformOrders != null) {
                    platformOrders.setState(OrderState.PAID);
                    platformOrders.setPayMoney(money);
                    platformOrderDataService.savePlatformOrder(platformOrders);
                    AlipayOrder alipayOrder = new AlipayOrder(imei, orderId, money, memo, FormatDateTime.ThirdTimestampToDate(Long.parseLong(time)));
                    alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    User user = userDataService.getUserById(platformOrders.getUid());
                   
                    if(user != null) {
                    	Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                    	if(merchant != null) {
                    		merchant.setBalance(merchant.getBalance() + money);
                            merchantDataService.saveMerchant(merchant);
                    	}
                    	
                    }
                
                }
            } else if(supplier.getCodeType() == CodeType.TPASS || supplier.getCodeType() == CodeType.TSOLID) {  // 供码用户提供转账码
                // 提取memo备注里的值（99.9%是订单号）。查询订单表中是否存在一个与memo的值匹配的订单号，如果存在，则把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
            	System.out.println("2:" + supplier.getCodeType());
                PlatformOrder platformOrder = platformOrderDataService.findByNumber(memo);
                if(platformOrder != null) {
                    platformOrder.setState(OrderState.PAID);
                    platformOrder.setPayMoney(money);
                    platformOrderDataService.savePlatformOrder(platformOrder);
                    AlipayOrder alipayOrder = new AlipayOrder(imei, orderId, money, memo, FormatDateTime.TenTimestampToDate(Integer.valueOf(time)));
                    alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    User user = userDataService.getUserById(platformOrder.getUid());
                    
                    if(user != null) {
                    	Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                    	if(merchant != null) {
                    		merchant.setBalance(merchant.getBalance() + money);
                            merchantDataService.saveMerchant(merchant);
                    	}
                    	
                    }
                }
            } else {
            	System.out.println("77:");
            }

//        	String imei = jsonObject.getString("imei");
//		    String orderId = jsonObject.getString("orderId");
//		    String money = jsonObject.getString("money");
//		    String memo = jsonObject.getString("memo");
//		    String time = jsonObject.getString("time");
//		    System.out.println("收到用户 " + imei + "的消息：" + message.getPayload().toString());

		 // 如果订单成功入库，则回复一条信息
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
         }

        //收到通码链接
        if(cmd.equals("passcode") && type.equals("alipay")){
            msgMap.put(imei, message);
            Thread thread = mapThread.get(imei);
            thread.interrupt();
         }

        //收到固码链接
        if(cmd.equals("solidcode") && type.equals("alipay")){
         }
    }

    /**
     * 当新连接建立的时候，被调用连接成功时候，会触发页面上onOpen方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       // socketSessionMap.put(session.getAttributes().get("imei").toString(), session);
    	String imei=(String) session.getAttributes().get("imei");
    	System.out.println(imei);
    	socketSessionMap.put(imei, session);
        System.out.println("用户 " + imei+ " 已建立连接");
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
        String imei = (String)session.getAttributes().get("imei");
//        for (Map.Entry<String, WebSocketSession> m : socketSessionMap.entrySet()) {
//            if(m.getValue().toString().equals(session.toString())) {
//                imei = m.getKey();
//                break;
//            }
//        }
        
        Device device = deviceDataService.findByImei(imei);
        if(device != null) {
            device.setOnline(0);
            deviceDataService.saveDevice(device);
        }
        System.out.println("用户 " + imei + " 已关闭连接。 当前状态：" + status);

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
        System.out.println("用户 " + session.getAttributes().get("imei")+ " 已关闭连接");

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
