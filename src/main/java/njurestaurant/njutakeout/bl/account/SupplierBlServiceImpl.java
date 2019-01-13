package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.SupplierBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.UserAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierBlServiceImpl implements SupplierBlService {
    private final UserDataService userDataService;
    private final SupplierDataService supplierDataService;

    @Autowired
    public SupplierBlServiceImpl(UserDataService userDataService, SupplierDataService supplierDataService) {
        this.userDataService = userDataService;
        this.supplierDataService = supplierDataService;
    }

    /**
     *
     * @param supplier the info of supplier
     * @return
     */
    @Override
    public UserAddResponse addSupplier(Supplier supplier) throws UsernameIsExistentException {
        if(userDataService.isUserExistent(supplier.getUser().getUsername())) {
            throw new UsernameIsExistentException();
        } else {
            return new UserAddResponse(supplierDataService.saveSupplier(supplier).getId());
        }
    }

    @Override
    public Response approvalSupplier(int sid, String state) {
        Supplier supplier = supplierDataService.findSupplierById(sid);
        if(supplier == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            if(state.equals("1")) {
                supplier.setStatus(SupplierState.PASS);
            } else if (state.equals("2")) {
                supplier.setStatus(SupplierState.REJECT);
            } else {
                return new WrongResponse(10140, "Wrong state");
            }
            supplierDataService.saveSupplier(supplier);
            return new SuccessResponse("Update success.");
        }
    }

    @Override
    public void delSupplierById(int id) {
        supplierDataService.deleteSupplierById(id);
    }

    @Override
    public List<Supplier> findAllSuppliers() {
        List<Supplier> supplierList = JSONFilter(supplierDataService.getAllSuppliers());
        return supplierList;
    }

    @Override
    public List<Supplier> findSupplierByState(SupplierState supplierState) {
        List<Supplier> supplierList = JSONFilter(supplierDataService.findSuppliersByState(supplierState));
        return supplierList;
    }

    private List<Supplier> JSONFilter(List<Supplier> suppliers) {
        if(suppliers.size() != 0) {
            for(Supplier supplier : suppliers) {
                List<PersonalCard> cardList = supplier.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
            }
        }
        return suppliers;
    }
}
