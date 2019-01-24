package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardChangeOrderDao extends JpaRepository<CardChangeOrder, Integer> {

}
