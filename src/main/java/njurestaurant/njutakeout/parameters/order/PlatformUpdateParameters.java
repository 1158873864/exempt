package njurestaurant.njutakeout.parameters.order;

public class PlatformUpdateParameters {
    private String state;
    private double money;
    private double realPay;

    public PlatformUpdateParameters(String state, double money, double realPay) {
        this.state = state;
        this.money = money;
        this.realPay = realPay;
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
}
