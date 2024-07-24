package com.system.repair_person.dao;

import lombok.Data;

@Data
public class EquipmentAndFaultItem {
    Integer equipmentId;
    String  equipmentTypeName;
    String  producerName;
    String faultName;
    String suggestion;
}
