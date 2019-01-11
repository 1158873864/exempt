package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.SupplierDao;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.entity.account.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierDataServiceImpl implements SupplierDataService {
    private final SupplierDao supplierDao;

    @Autowired
    public SupplierDataServiceImpl(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    /**
     *
     * @param supplier the supplier to be saved
     * @return
     */
    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierDao.save(supplier);
    }

}
