package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantDao extends JpaRepository<Merchant, Integer> {
    List<Merchant> findMerchantByStatus(MerchantState merchantState);

    List<Merchant> findMerchantsByApplyId(int id);
}
