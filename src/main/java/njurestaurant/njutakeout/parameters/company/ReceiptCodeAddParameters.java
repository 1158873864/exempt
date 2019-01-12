package njurestaurant.njutakeout.parameters.company;

import com.sun.org.apache.xpath.internal.operations.String;

public class ReceiptCodeAddParameters {
    /*所属团队*/
    private String team;
    /*类型*/
    private String type;
    /*优先等级*/
    private String priority;
    /*持续时间*/
    private String duration;
    /*提现卡号*/
    private String number;
    /*账号信息*/
    private String info;

    public ReceiptCodeAddParameters(String team, String type, String priority, String duration, String number, String info) {
        this.team = team;
        this.type = type;
        this.priority = priority;
        this.duration = duration;
        this.number = number;
        this.info = info;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
