package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.Staff;

public interface StaffDataService {
    /**
     * find whether the staff is exists
     *
     * @param staffName the username of staff
     * @return whether the staff is exists
     */
    boolean isStaffExistent(String staffName);

    /**
     * save the staff
     *
     * @param staff the staff to be saved
     */
    void saveStaff(Staff staff);

    /**
     * confirm the password
     *
     * @param staffName the username of staff
     * @param password the password of staff
     * @return true if password is correct else false
     */
    boolean confirmPassword(String staffName, String password);
}
