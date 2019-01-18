package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.WithdrewOrderDao;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WithdrewOrderDataServiceImpl implements WithdrewOrderDataService {
    private final WithdrewOrderDao withdrewOrderDao;

    @Autowired
    public WithdrewOrderDataServiceImpl(WithdrewOrderDao withdrewOrderDao) {
        this.withdrewOrderDao = withdrewOrderDao;
    }

    @Override
    public WithdrewOrder saveWithdrewOrder(WithdrewOrder withdrewOrder) {
        return withdrewOrderDao.save(withdrewOrder);
    }

    @Override
    public void deleteById(int id) {
        withdrewOrderDao.deleteById(id);
    }

    @Override
    public WithdrewOrder findWithdrewOrderById(int id) {
        Optional<WithdrewOrder> withdrewOrderOptional = withdrewOrderDao.findById(id);
        return withdrewOrderOptional.isPresent()? withdrewOrderOptional.get() : null;
    }

    @Override
    public List<WithdrewOrder> findAll() {
        return withdrewOrderDao.findAll();
    }

    @Override
    public List<WithdrewOrder> findByState(WithdrewState withdrewState) {
        return withdrewOrderDao.findWithdrewOrdersByState(withdrewState);
    }

    @Override
    public List<WithdrewOrder> findByOperatorId(int id) {
        return withdrewOrderDao.findWithdrewOrdersByOperateId(id);
    }
}
