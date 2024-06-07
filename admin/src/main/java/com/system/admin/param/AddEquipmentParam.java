package com.system.admin.param;

import lombok.Data;

@Data
public class AddEquipmentParam {

    private Integer equipmentTypeId;

    private Integer preserveCycle;
    private Integer serviceLife;
}
