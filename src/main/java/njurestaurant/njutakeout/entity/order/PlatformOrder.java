package njurestaurant.njutakeout.entity.order;

import njurestaurant.njutakeout.publicdatas.order.OrderState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "platform_order")
public class PlatformOrder {
//    平台订单表 订单号 state(是否支付:未支付，已支付，已失效) time curCode
//    IP:"ip地址",id:"充值方编号",money:"待付款金额",商户id:"merchantid",time="10位的时间戳", 平台订单
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /*订单号*/
    @Column(name = "number")
    private String number;
    @Column(name = "state")
    private OrderState state;
    @Column(name = "time")
    private Date time;
    /*该订单对应的支付码*/
    @Column(name = "payCode")
    private String payCode;
    @Column(name = "ip")
    private String ip;
    /*充值方编号*/
    @Column(name = "rechargeId")
    private String rechargeId;
    @Column(name = "money")
    private double money;
    @Column(name = "payMoney")
    private double payMoney;
    /*商户的userid*/
    @Column(name = "uid")
    private int uid;
    @Column(name = "imei")
    private String imei;

    public PlatformOrder() {
    }

    public PlatformOrder(String number, OrderState state, Date time, String ip, String rechargeId, double money, int uid) {
        this.number = number;
        this.state = state;
        this.time = time;
        this.ip = ip;
        this.rechargeId = rechargeId;
        this.money = money;
        this.uid = uid;
    }

    public PlatformOrder(String number, OrderState state, Date time, String payCode, String ip, String rechargeId, double money, int uid, String imei) {
        this.number = number;
        this.state = state;
        this.time = time;
        this.payCode = payCode;
        this.ip = ip;
        this.rechargeId = rechargeId;
        this.money = money;
        this.uid = uid;
        this.imei = imei;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
