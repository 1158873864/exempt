package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.SupplierBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.SupplierApprovalParameters;
import njurestaurant.njutakeout.parameters.user.SupplierUpdateParameters;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.UserAddResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Response approvalSupplier(int sid, SupplierApprovalParameters supplierApprovalParameters) {
        Supplier supplier = supplierDataService.findSupplierById(sid);
        if(supplier == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = supplier.getUser();
            user.setUsername(supplierApprovalParameters.getUsername());
            user.setPassword(encoder.encode(supplierApprovalParameters.getPassword()));
            supplier.setUser(user);
            supplier.setPriority(supplierApprovalParameters.getLevel());
            supplier.setApprovalTime(new Date());
            supplier.setApproverId(supplierApprovalParameters.getId());
            if(supplierApprovalParameters.getState() == 1) {
                supplier.setStatus(SupplierState.PASS);
            } else if (supplierApprovalParameters.getState() == 0) {
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
                List<Device> devices = supplier.getDevices();
                devices.stream().peek(d -> d.setSupplier(null)).collect(Collectors.toList());
            }
        }
        return suppliers;
    }


    @Override
    public Supplier updateSupplier(int id, SupplierUpdateParameters supplierUpdateParameters) throws WrongIdException, BlankInputException {
        Supplier supplier = supplierDataService.findSupplierById(id);
        if(supplier == null) {
            throw new WrongIdException();
        } else if(StringUtils.isBlank(supplierUpdateParameters.getPassword()) || StringUtils.isBlank(supplierUpdateParameters.getCodeType()) || supplierUpdateParameters.getLevel() <= 0) {
            throw new BlankInputException();
        } else {
            User user = supplier.getUser();
            if(!supplierUpdateParameters.getPassword().equals(user.getPassword()))
                user.setPassword(supplierUpdateParameters.getPassword());
            supplier.setPriority(supplierUpdateParameters.getLevel());
            supplier.setUser(user);
            switch (supplierUpdateParameters.getCodeType()) {
                case "TSOLID":
                    supplier.setCodeType(CodeType.TSOLID);
                    break;
                case "TPASS":
                    supplier.setCodeType(CodeType.TPASS);
                    break;
                case "RSOLID":
                    supplier.setCodeType(CodeType.RSOLID);
                    break;
                case "RPASSOFF":
                    supplier.setCodeType(CodeType.RPASSOFF);
                    break;
                case "RPASSQR":
                    supplier.setCodeType(CodeType.RPASSQR);
                default:
                    throw new BlankInputException();
            }
            return supplierDataService.saveSupplier(supplier);
        }
    }
}
