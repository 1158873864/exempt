package njurestaurant.njutakeout.parameters.order;

public class PlatformUpdateParameters {
    private String state;
    private double money;

    public PlatformUpdateParameters(String state, double money) {
        this.state = state;
        this.money = money;
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
}
