package njurestaurant.njutakeout.parameters.user;

import java.util.Date;

public class MerchantAddParameters {
    private String username;
    private String alipay;
    private String wechat;
    private double balance;
    private String status;
    private String code;
    private Date time;

    public MerchantAddParameters() {
    }

    public MerchantAddParameters(String username, String alipay, String wechat, double balance, String status, String code, Date time) {
        this.username = username;
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.status = status;
        this.code = code;
        this.time = time;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}