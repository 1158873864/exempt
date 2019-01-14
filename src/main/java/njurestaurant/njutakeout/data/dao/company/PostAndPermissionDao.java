package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.PostAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAndPermissionDao extends JpaRepository<PostAndPermission, String> {
    void deletePostAndPermissionsByPermission(String permission);

    void deletePostAndPermissionsByPost(String post);

    List<PostAndPermission> findPostAndPermissionsByPost(String post);

}
