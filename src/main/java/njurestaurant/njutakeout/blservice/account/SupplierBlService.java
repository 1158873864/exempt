package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.user.SupplierUpdateParameters;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.user.UserAddResponse;

import java.util.List;

public interface SupplierBlService {
    /**
     * add the supplier
     *
     * @param supplier the info of supplier
     * @return
     */
    UserAddResponse addSupplier(Supplier supplier) throws UsernameIsExistentException;


    Response approvalSupplier(int mid, String state);

    void delSupplierById(int id);

    List<Supplier> findAllSuppliers();

    List<Supplier> findSupplierByState(SupplierState supplierState);

    Supplier updateSupplier(int id, SupplierUpdateParameters supplierUpdateParameters) throws WrongIdException, BlankInputException;
}
