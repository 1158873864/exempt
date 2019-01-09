package njurestaurant.njutakeout.parameters.company;

import java.util.Date;

public class TeamAddParameters {
    private String supervisor;
    private String area;
    private String status;
    private String verifyCode;
    private String operator;
    private Date addTime;

    public TeamAddParameters() {
    }

    public TeamAddParameters(String supervisor, String area, String status, String verifyCode, String operator, Date addTime) {
        this.supervisor = supervisor;
        this.area = area;
        this.status = status;
        this.verifyCode = verifyCode;
        this.operator = operator;
        this.addTime = addTime;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
