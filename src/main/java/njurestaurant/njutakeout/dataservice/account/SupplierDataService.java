package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Supplier;

public interface SupplierDataService {

    /**
     * save the supplier
     * @param supplier the supplier to be saved
     * @return
     */
    Supplier saveSupplier(Supplier supplier);
}
