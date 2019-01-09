package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.StaffDao;
import njurestaurant.njutakeout.dataservice.company.StaffDataService;
import njurestaurant.njutakeout.entity.company.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffDataServiceImpl implements StaffDataService {
    private final StaffDao staffDao;

    @Autowired
    public StaffDataServiceImpl(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public boolean isStaffExistent(String staffName) {
        return staffDao.findByStaffName(staffName) != null;
    }

    /**
     * save the staff
     *
     * @param staff the staff to be saved
     */
    @Override
    public void saveStaff(Staff staff) {
        staffDao.save(staff);
    }

    /**
     * confirm the password of the staff
     *
     * @param staffName the username of staff
     * @param password the password of staff
     * @return true if password is correct else false
     */
    @Override
    public boolean confirmPassword(String staffName, String password) {
        Staff staff = staffDao.findByStaffName(staffName);
        if(staff != null) {
            if(staff.getPassword().equals(password)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
