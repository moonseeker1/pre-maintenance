package com.system.admin.vo;

import com.system.admin.dao.EquipmentAndFaultItem;
import lombok.Data;

import java.util.List;
@Data
public class RepairOrderDetailsVO {


    private Integer repairOrderId;

    private String personName;

    private Integer state;

    List<EquipmentAndFaultItem> list;
}
