package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QRcodeChangeOrderDao extends JpaRepository<QRcodeChangeOrder, Integer> {

    List<QRcodeChangeOrder> findAll(Specification<QRcodeChangeOrder> dateBetweenOfQrCode);
}
