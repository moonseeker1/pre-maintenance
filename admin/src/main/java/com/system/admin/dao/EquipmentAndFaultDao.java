package com.system.admin.dao;

import com.system.admin.model.Equipment;
import com.system.admin.model.Fault;
import com.system.admin.model.RepairOrder;
import lombok.Data;

@Data
public class EquipmentAndFaultDao extends RepairOrder {
    private Equipment equipment;
    private Fault fault;
}
