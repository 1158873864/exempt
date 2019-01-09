package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<Staff, String> {
    Staff findByStaffName(String staffName);
}
