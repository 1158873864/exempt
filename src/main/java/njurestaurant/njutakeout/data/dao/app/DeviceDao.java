package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.app.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDao extends JpaRepository<Device, Integer> {
    Device findDeviceByImei(String imei);
    Device findDeviceByAlipayId(int id);
}
