package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "alipay")
    private String alipay;
    @Column(name = "wechat")
    private String wechat;
    @Column(name = "balance")
    private double banlance;
    @Column(name = "status")
    private String status;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "addTime")
    private Date addTime;

    public Merchant(String username, String password, String alipay, String wechat, double banlance, String status, String verifyCode, Date addTime) {
        this.username = username;
        this.password = password;
        this.alipay = alipay;
        this.wechat = wechat;
        this.banlance = banlance;
        this.status = status;
        this.verifyCode = verifyCode;
        this.addTime = addTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getBanlance() {
        return banlance;
    }

    public void setBanlance(double banlance) {
        this.banlance = banlance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
