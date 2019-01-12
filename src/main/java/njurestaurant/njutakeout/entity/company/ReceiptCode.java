package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

@Entity
@Table(name = "receipt_code")
public class ReceiptCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "teamName")
    private String teamName;
    @Column(name = "type")
    private String type;
    @Column(name = "duration")
    private int duration;
    @Column(name = "priorityLevel")
    private String priorityLevel;
    @Column(name = "accountInfo")
    private String accountInfo;
    @Column(name = "accountNumber")
    private String accountNumber;

    public ReceiptCode(String teamName, String type, int duration, String priorityLevel, String accountInfo, String accountNumber) {
        this.teamName = teamName;
        this.type = type;
        this.duration = duration;
        this.priorityLevel = priorityLevel;
        this.accountInfo = accountInfo;
        this.accountNumber = accountNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
