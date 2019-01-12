package njurestaurant.njutakeout.entity.account;

import njurestaurant.njutakeout.publicdatas.account.MerchantState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "alipay")
    private String alipay;
    @Column(name = "wechat")
    private String wechat;
    @Column(name = "balance")
    private double balance;
    @Column(name = "status")
    private MerchantState status;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "addTime")
    private Date addTime;
    @Column(name = "name")
    private String name;
    @Column(name = "superior")
    private String superior;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Merchant() {
    }

    public Merchant(String alipay, String wechat, double balance, MerchantState status, String verifyCode, Date addTime, String name, String superior, User user) {
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.status = status;
        this.verifyCode = verifyCode;
        this.addTime = addTime;
        this.name = name;
        this.superior = superior;
        this.user = user;
    }

    public Merchant(String alipay, String wechat, double balance, MerchantState status, String verifyCode, Date addTime, String name, String superior) {
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.status = status;
        this.verifyCode = verifyCode;
        this.addTime = addTime;
        this.name = name;
        this.superior = superior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public MerchantState getStatus() {
        return status;
    }

    public void setStatus(MerchantState status) {
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

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
