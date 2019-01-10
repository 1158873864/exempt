package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class PostAndPermissionAddResponse extends Response {
    private int id;

    public PostAndPermissionAddResponse(int id) {
        this.id = id;
    }

    public PostAndPermissionAddResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
