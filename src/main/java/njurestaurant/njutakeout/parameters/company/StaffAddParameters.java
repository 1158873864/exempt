package njurestaurant.njutakeout.parameters.company;

import java.util.Date;

public class StaffAddParameters {
    private String team;
    private String post;
    private String username;
    private String status;
    private String code;
    private Date time;
    private String operator;

    public StaffAddParameters(String team, String post, String username, String status, String code, Date time, String operator) {
        this.team = team;
        this.post = post;
        this.username = username;
        this.status = status;
        this.code = code;
        this.time = time;
        this.operator = operator;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
