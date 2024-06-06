package com.system.admin.param;

import lombok.Data;

@Data
public class EquipmentPageParam {

    private Integer state;

    private Integer preserveNum;

    private Integer outputMin;

    private Integer outputMax;

    private Integer serviceLife;

    private Integer pageSize=5;

    private Integer pageNum=1;

    private Integer typeId;

    private Integer producerId;


}
