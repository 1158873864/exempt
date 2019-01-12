package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.response.company.PostAndPermissionAddResponse;

public interface PostAndPermissionBlService {
    /**
     * add the permission of post
     * @param postAndPermission
     * @return
     */
    PostAndPermissionAddResponse addPostAndPermission(PostAndPermission postAndPermission);
}
