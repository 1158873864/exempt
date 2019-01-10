package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalCardDao extends JpaRepository<PersonalCard, String> {
}
