package com.system.admin.param;

import lombok.Data;

import java.util.List;

@Data
public class AddRepairOrderParam {
    List<Integer> equipmentIds;
    List<Integer> faultIds;
    Integer repairPersonId;

}
