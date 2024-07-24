package com.system.admin.param;

import lombok.Data;

@Data
public class ModifyEquipmentParam {

    private Integer equipmentTypeId;
    private Integer preserveCycle;
    private Integer serviceLife;
    private Integer output;
}
