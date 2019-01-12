package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PostAndPermissionBlService;
import njurestaurant.njutakeout.dataservice.account.PostAndPermissionDataService;
import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.response.company.PostAndPermissionAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostAndPermissionBlServiceImpl implements PostAndPermissionBlService {
    private final PostAndPermissionDataService postAndPermissionDataService;

    @Autowired
    public PostAndPermissionBlServiceImpl(PostAndPermissionDataService permissionDataService) {
        this.postAndPermissionDataService = permissionDataService;
    }

    /**
     *
     * @param postAndPermission
     * @return
     */
    @Override
    public PostAndPermissionAddResponse addPostAndPermission(PostAndPermission postAndPermission) {
        return new PostAndPermissionAddResponse(postAndPermissionDataService.savePostAndPermission(postAndPermission).getId());
    }
}
