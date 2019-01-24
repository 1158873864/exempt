package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRcodeChangeOrderDao extends JpaRepository<QRcodeChangeOrder, Integer> {

}
