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
    @Column(name = "qrCodeId")
    private String qrCodeId;
    @Column(name = "loginOrNot")
    private int loginOrNot;

    public Alipay(String loginId, String userId, String qrCodeId, int loginOrNot) {
        this.loginId = loginId;
        this.userId = userId;
        this.qrCodeId = qrCodeId;
        this.loginOrNot = loginOrNot;
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

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public int getLoginOrNot() {
        return loginOrNot;
    }

    public void setLoginOrNot(int loginOrNot) {
        this.loginOrNot = loginOrNot;
    }
}
