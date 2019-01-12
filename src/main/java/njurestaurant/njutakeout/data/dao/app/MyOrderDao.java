package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.order.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyOrderDao extends JpaRepository<MyOrder, Integer> {
}
