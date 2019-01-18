package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;

@Entity
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "agentName")
    private String agentName;
    @Column(name = "status")
    private String status;
    @Column(name = "verifyCode")
    private String verifyCode;
    /*代理商手续费*/
    @Column(name = "percent")
    private double percent;
    /*代理商余额*/
    @Column(name = "balance")
    private double balance;
    @Column(name = "withdrewMoney")
    private double withdrewMoney;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userInfo;

    public Agent() {
    }

    public Agent(String agentName, String status, double percent, double balance, User userInfo) {
        this.agentName = agentName;
        this.status = status;
        this.percent = percent;
        this.balance = balance;
        this.userInfo = userInfo;
    }

    public double getWithdrewMoney() {
        return withdrewMoney;
    }

    public void setWithdrewMoney(double withdrewMoney) {
        this.withdrewMoney = withdrewMoney;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return userInfo;
    }

    public void setUser(User user) {
        this.userInfo = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
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

}
