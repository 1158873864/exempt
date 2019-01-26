package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.app.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDao extends JpaRepository<Device, Integer> {
    Device findDeviceByImei(String imei);
    Device findDeviceByAlipayId(int id);
    List<Device> findDevicesBySupplierId(int id);
}
