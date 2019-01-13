package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class UserLoginResponse extends Response{
    private String token;
    private int role;
    private int uid;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String token) {
        this.token = token;
    }

    public UserLoginResponse(String token, int role) {
        this.token = token;
        this.role = role;
    }

    public UserLoginResponse(String token, int role, int uid) {
        this.token = token;
        this.role = role;
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
