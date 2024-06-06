package com.system.admin.param;

import lombok.Data;

import java.util.HashMap;

@Data
public class AddRepairOrderParam {
    HashMap<Integer,Integer> map;
    Integer repairPersonId;

}
