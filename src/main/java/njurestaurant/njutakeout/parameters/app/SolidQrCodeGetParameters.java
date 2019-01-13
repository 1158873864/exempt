package njurestaurant.njutakeout.parameters.app;

import njurestaurant.njutakeout.response.Response;

public class SolidQrCodeGetParameters extends Response {
    private int id;
    private double money;
    private String memo;

    public SolidQrCodeGetParameters(int id, double money, String memo) {
        this.id = id;
        this.money = money;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
