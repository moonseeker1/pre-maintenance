package com.system.admin.vo;

import com.system.admin.dao.EquipmentAndFaultItem;
import lombok.Data;

import java.util.List;
@Data
public class RepairOrderDetailsVO {
    List<EquipmentAndFaultItem> list;
}
