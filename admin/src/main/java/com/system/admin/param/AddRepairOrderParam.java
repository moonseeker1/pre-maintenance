package com.system.admin.param;

import com.system.admin.dao.EquipmentAndFaultDao;
import lombok.Data;

import java.util.List;

@Data
public class AddRepairOrderParam {
    List<EquipmentAndFaultDao> list;
    Integer repairPersonId;

}
