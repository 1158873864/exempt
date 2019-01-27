package njurestaurant.njutakeout.parameters.user;

public class MerchantUpdateParameters {

    private String name;
    private String password;
    private double alipay;
    private double wechat;
    private String status;
    private int level;

    public MerchantUpdateParameters(String password, String name, double alipay, double wechat, String status, int level) {
        this.password = password;
        this.name = name;
        this.alipay = alipay;
        this.wechat = wechat;
        this.status = status;
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlipay() {
        return alipay;
    }

    public void setAlipay(double alipay) {
        this.alipay = alipay;
    }

    public double getWechat() {
        return wechat;
    }

    public void setWechat(double wechat) {
        this.wechat = wechat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
