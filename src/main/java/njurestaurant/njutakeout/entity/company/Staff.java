package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "staffName")
    private String staffName;
    @Column(name = "password")
    private String password;
    @Column(name = "team")
    private String team;
    @Column(name = "addTime")
    private Date addTime;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "operator")
    private String operator;
    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;

    public Staff() {
    }

    public Staff(String staffName, String password, String team, Date addTime, String verifyCode, String operator, String status, String role) {
        this.staffName = staffName;
        this.password = password;
        this.team = team;
        this.addTime = addTime;
        this.verifyCode = verifyCode;
        this.operator = operator;
        this.status = status;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
