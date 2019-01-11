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
    @Column(name = "flow")
    private String flow;
    @Column(name = "status")
    private String status;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "dailyCommission")
    private double dailyCommission;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Agent() {
    }

    public Agent(String agentName, String flow, String status, String verifyCode, double dailyCommission, User user) {
        this.agentName = agentName;
        this.flow = flow;
        this.status = status;
        this.verifyCode = verifyCode;
        this.dailyCommission = dailyCommission;
        this.user = user;
    }

    public Agent(String agentName, String flow, String status, String verifyCode, double dailyCommission) {
        this.agentName = agentName;
        this.flow = flow;
        this.status = status;
        this.verifyCode = verifyCode;
        this.dailyCommission = dailyCommission;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
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

    public double getDailyCommission() {
        return dailyCommission;
    }

    public void setDailyCommission(double dailyCommission) {
        this.dailyCommission = dailyCommission;
    }
}
