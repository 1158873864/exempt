package njurestaurant.njutakeout.parameters.order;

import java.util.Date;

public class PlatformUpdateParameters {
    private String state;
    private double money;
    private double realPay;
    private String orderId;
    private Date payTime;

    public PlatformUpdateParameters(String state, double money, double realPay, String orderId, Date payTime) {
        this.state = state;
        this.money = money;
        this.realPay = realPay;
        this.orderId = orderId;
        this.payTime = payTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRealPay() {
        return realPay;
    }

    public void setRealPay(double realPay) {
        this.realPay = realPay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
