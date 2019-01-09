package njurestaurant.njutakeout.parameters.user;

public class AgentAddParameters {
    private String username;
    private String flow;
    private String status;
    private String code;
    private double brokerage;

    public AgentAddParameters() {
    }

    public AgentAddParameters(String username, String flow, String status, String code, double brokerage) {
        this.username = username;
        this.flow = flow;
        this.status = status;
        this.code = code;
        this.brokerage = brokerage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(double brokerage) {
        this.brokerage = brokerage;
    }
}
