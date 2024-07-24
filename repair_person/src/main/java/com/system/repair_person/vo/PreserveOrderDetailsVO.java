package com.system.repair_person.vo;


import com.system.repair_person.dao.EquipmentDetails;
import lombok.Data;

import java.util.List;
@Data
public class PreserveOrderDetailsVO {
    List<EquipmentDetails> equipmentDetails;
    Integer preserveOrderId;
    String repairPersonName;
    Integer state;
}
