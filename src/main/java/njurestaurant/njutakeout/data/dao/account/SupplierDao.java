package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {
}
