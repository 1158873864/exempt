package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class UserInfoResponse extends Response {
    private int role;
    private Object info;

    public UserInfoResponse() {
    }

    public UserInfoResponse(int role, Object info) {
        this.role = role;
        this.info = info;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
