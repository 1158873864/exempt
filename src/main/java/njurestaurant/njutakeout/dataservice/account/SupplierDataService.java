package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Supplier;

public interface SupplierDataService {

    /**
     * save the supplier
     * @param supplier the supplier to be saved
     * @return
     */
    Supplier saveSupplier(Supplier supplier);

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    boolean confirmPassword(String username, String password);

    /**
     * get supplier by username
     *
     * @param username the username
     * @return
     */
    Supplier getSupplierByUsername(String username);
}
