package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.company.PostAndPermission;

public interface PostAndPermissionDataService {
    /**
     * save the permission of the post
     * @param postAndPermission the permission of the post
     */
    void savePostAndPermission(PostAndPermission postAndPermission);
}
