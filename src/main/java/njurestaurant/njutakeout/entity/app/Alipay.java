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
//    /*固码*/
//    @Column(name = "solidCode")
//    private String solidCode;
//    /*通码*/
//    @Column(name = "passCode")
//    private String passCode;
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
