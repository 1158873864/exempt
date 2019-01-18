package njurestaurant.njutakeout.parameters.user;

public class AgentAddParameters {
    private String username;
    private String password;
    private String status;
    private double percent;

    public AgentAddParameters(String username, String password, String status, double percent) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.percent = percent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
