package njurestaurant.njutakeout.response.report;

import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;

public class MerchantReport {
    /*商户用户名*/
    private String username;
    private double orderMoney;
    private double payMoney;
    private Date time;
    private OrderState orderState;

    public MerchantReport(double orderMoney, double payMoney, Date time, OrderState orderState) {
        this.orderMoney = orderMoney;
        this.payMoney = payMoney;
        this.time = time;
        this.orderState = orderState;
    }

    public MerchantReport(String username, double orderMoney, double payMoney, Date time, OrderState orderState) {
        this.username = username;
        this.orderMoney = orderMoney;
        this.payMoney = payMoney;
        this.time = time;
        this.orderState = orderState;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
