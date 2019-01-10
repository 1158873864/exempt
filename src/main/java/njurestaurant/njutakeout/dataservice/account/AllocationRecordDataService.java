package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.company.AllocationRecord;

public interface AllocationRecordDataService {
    /**
     * save the allocation record of permission
     *
     * @param allocationRecord the record of allocation
     * @return
     */
    AllocationRecord saveAllocationRecord(AllocationRecord allocationRecord);
}
