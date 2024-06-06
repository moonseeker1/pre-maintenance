package com.system.admin.param;

import lombok.Data;

@Data
public class ModifyEquipmentParam {

    private Integer equipmentTypeId;
    private Integer state;
    private Integer preserveNum;
    private Integer serviceLife;
    private Integer output;
}
