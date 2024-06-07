package com.system.admin.vo;

import com.system.admin.dao.EquipmentDetails;
import lombok.Data;

import java.util.List;
@Data
public class PreserveOrderDetailsVO {
    List<EquipmentDetails> equipmentDetails;
    Integer preserveOrderId;
    String repairPersonName;
    Integer state;
}
