package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class PostAndPermissionResponse extends Response {
    private String post;
    private List<String> permission;

    public PostAndPermissionResponse(String post, List<String> permission) {
        this.post = post;
        this.permission = permission;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
}
