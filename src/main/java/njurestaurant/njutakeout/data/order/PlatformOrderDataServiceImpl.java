package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.PlatformOrderDao;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformOrderDataServiceImpl implements PlatformOrderDataService {
    private final PlatformOrderDao platformOrderDao;

    @Autowired
    public PlatformOrderDataServiceImpl(PlatformOrderDao platformOrderDao) {
        this.platformOrderDao = platformOrderDao;
    }

    @Override
    public PlatformOrder findById(int id) {
        Optional<PlatformOrder> platformOrderOption = platformOrderDao.findById(id);
        if(platformOrderOption.isPresent()) return platformOrderOption.get();
        else return null;
    }

    @Override
    public List<PlatformOrder> findByUid(int uid) {
        return platformOrderDao.findPlatformOrderByUid(uid);
    }

    @Override
    public PlatformOrder savePlatformOrder(PlatformOrder platformOrder) {
        return platformOrderDao.save(platformOrder);
    }

    @Override
    public void deleteById(int id) {
        platformOrderDao.deleteById(id);
    }

    @Override
    public List<PlatformOrder> findByState(OrderState orderState) {
        return platformOrderDao.findPlatformsByState(orderState);
    }

    @Override
    public List<PlatformOrder> findAll() {
        return platformOrderDao.findAll();
    }

    @Override
    public List<PlatformOrder> findByUidAndState(int uid, OrderState orderState) {
        return platformOrderDao.findPlatformOrderByUidAndState(uid, orderState);
    }

    @Override
    public PlatformOrder findByNumber(String number) {
        return platformOrderDao.findPlatformOrderByNumber(number);
    }

    @Override
    public PlatformOrder findByImeiAndState(String imei, OrderState orderState) {
        return platformOrderDao.findPlatformOrdersByImeiAndState(imei, orderState);
    }

    @Override
    public void savePlatformOrders(List<PlatformOrder> platformOrders) {
        platformOrderDao.saveAll(platformOrders);
    }
}
