package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantDao extends JpaRepository<Merchant, Integer> {
}
