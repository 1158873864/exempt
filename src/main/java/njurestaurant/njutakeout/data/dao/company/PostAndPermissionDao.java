package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.PostAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAndPermissionDao extends JpaRepository<PostAndPermission, String> {
}
