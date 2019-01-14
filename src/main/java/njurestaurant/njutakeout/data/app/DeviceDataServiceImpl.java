package njurestaurant.njutakeout.data.app;

import njurestaurant.njutakeout.data.dao.app.DeviceDao;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.entity.app.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {
    private final DeviceDao deviceDao;

    @Autowired
    public DeviceDataServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceDao.save(device);
    }

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }

    @Override
    public Device findById(int id) {
        Optional<Device> deviceOptional = deviceDao.findById(id);
        if(deviceOptional.isPresent())  return deviceOptional.get();
        else return null;
    }

    @Override
    public void deleteById(int id) {
        deviceDao.deleteById(id);
    }
}
