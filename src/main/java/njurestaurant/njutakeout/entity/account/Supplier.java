package njurestaurant.njutakeout.entity.account;

import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 供码用户
 */
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    /*支付宝用户id*/
    @Column(name = "alipayUserId")
    private String alipayUserId;
    /*支付宝账号*/
    @Column(name = "loginId")
    private String loginId;
    @Column(name = "time")
    private Date time;
    /*审批状态*/
    @Column(name = "status")
    private SupplierState status;
    @OneToMany(mappedBy = "supplier", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Device> devices;
    @Column(name = "alipayName")
    private String alipayName;
    @Column(name = "priority")
    private int priority;

    public Supplier() {
    }

    public Supplier(User user, String alipayUserId, String loginId, Date time, SupplierState status, List<Device> devices, String alipayName) {
        this.user = user;
        this.alipayUserId = alipayUserId;
        this.loginId = loginId;
        this.time = time;
        this.status = status;
        this.devices = devices;
        this.alipayName = alipayName;
    }

    public Supplier(User user, String loginId, Date time, SupplierState status, List<Device> devices, int priority) {
        this.user = user;
        this.loginId = loginId;
        this.time = time;
        this.status = status;
        this.devices = devices;
        this.priority = priority;
    }

    public Supplier(User user, String loginId, Date time, SupplierState status) {
        this.user = user;
        this.loginId = loginId;
        this.time = time;
        this.status = status;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public Supplier(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public SupplierState getStatus() {
        return status;
    }

    public void setStatus(SupplierState status) {
        this.status = status;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
