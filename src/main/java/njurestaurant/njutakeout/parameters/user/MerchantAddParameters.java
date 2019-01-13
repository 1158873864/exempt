package njurestaurant.njutakeout.parameters.user;

import java.util.Date;

public class MerchantAddParameters {
    private String username;
    private String password;
    private String alipay;
    private String wechat;
    private double balance;
    private String code;
    private String superior;
    private int level;

    public MerchantAddParameters(String username, String password, String alipay, String wechat, double balance, String code, String superior, int level) {
        this.username = username;
        this.password = password;
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.code = code;
        this.superior = superior;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
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

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}