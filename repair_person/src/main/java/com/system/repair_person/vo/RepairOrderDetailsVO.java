package com.system.repair_person.vo;


import com.system.repair_person.dao.EquipmentAndFaultItem;
import lombok.Data;

import java.util.List;
@Data
public class RepairOrderDetailsVO {


    private Integer repairOrderId;

    private String personName;

    private Integer state;

    List<EquipmentAndFaultItem> list;
}
