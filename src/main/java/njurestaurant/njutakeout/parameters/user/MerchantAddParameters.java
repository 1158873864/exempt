package njurestaurant.njutakeout.parameters.user;

public class MerchantAddParameters {
    private String username;
    private String password;
    private double alipay;
    private double wechat;
    private int applyId;
    private int level;
    private String status;

    public MerchantAddParameters(String username, String password, double alipay, double wechat, int applyId, int level, String status) {
        this.username = username;
        this.password = password;
        this.alipay = alipay;
        this.wechat = wechat;
        this.applyId = applyId;
        this.level = level;
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public MerchantAddParameters() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}