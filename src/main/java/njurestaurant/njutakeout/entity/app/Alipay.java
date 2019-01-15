package njurestaurant.njutakeout.entity.app;

import javax.persistence.*;

/**
 * 存储用户的支付宝信息
 */
@Entity
@Table(name = "alipay")
public class Alipay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "loginId")
    private String loginId;
    @Column(name = "userId")
    private String userId;
    /*固码*/
    @Column(name = "solidCode")
    private String solidCode;
    /*收款码通码在线码*/
    @Column(name = "passQrCode")
    private String passQrCode;
    /*收款码通码离线码*/
    @Column(name = "passOffCode")
    private String passOffCode;
    @Column(name = "imei")
    private String imei;
    @Column(name = "name")
    private String name;

    public Alipay() {
    }

    public Alipay(String loginId) {
        this.loginId = loginId;
    }

    public Alipay(String loginId, String userId, String imei) {
        this.loginId = loginId;
        this.userId = userId;
        this.imei = imei;
    }

    public Alipay(String loginId, String userId, String solidCode, String passQrCode, String passOffCode, String imei, String name) {
        this.loginId = loginId;
        this.userId = userId;
        this.solidCode = solidCode;
        this.passQrCode = passQrCode;
        this.passOffCode = passOffCode;
        this.imei = imei;
        this.name = name;
    }

    public String getSolidCode() {
        return solidCode;
    }

    public void setSolidCode(String solidCode) {
        this.solidCode = solidCode;
    }

    public String getPassQrCode() {
        return passQrCode;
    }

    public void setPassQrCode(String passQrCode) {
        this.passQrCode = passQrCode;
    }

    public String getPassOffCode() {
        return passOffCode;
    }

    public void setPassOffCode(String passOffCode) {
        this.passOffCode = passOffCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
