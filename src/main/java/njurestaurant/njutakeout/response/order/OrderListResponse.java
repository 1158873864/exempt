package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;

public class OrderListResponse {
    private int orderId;
    private String orderNumber;
    private double money;
    private double paymoney;
    private String rechargeId;
    private String code;
    private OrderState orderState;
    private Date time;
    private Date payTime;
    private int merchantId;
    private String merchantName;
    private String type;
    private int tableId;
    private String nickname;

    public OrderListResponse(int orderId, String orderNumber, double money, double paymoney, String rechargeId, String code, OrderState orderState, Date time, Date payTime, int merchantId, String merchantName) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.money = money;
        this.paymoney = paymoney;
        this.rechargeId = rechargeId;
        this.code = code;
        this.orderState = orderState;
        this.time = time;
        this.payTime = payTime;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
    }

    public OrderListResponse(int orderId, String orderNumber, double money, double paymoney, String rechargeId, String code, OrderState orderState, Date time, Date payTime, int merchantId, String merchantName, String type, int tableId, String nickname) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.money = money;
        this.paymoney = paymoney;
        this.rechargeId = rechargeId;
        this.code = code;
        this.orderState = orderState;
        this.time = time;
        this.payTime = payTime;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.type = type;
        this.tableId = tableId;
        this.nickname = nickname;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(double paymoney) {
        this.paymoney = paymoney;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
