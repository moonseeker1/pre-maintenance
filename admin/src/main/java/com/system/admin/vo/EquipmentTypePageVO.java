package com.system.admin.vo;

import com.system.admin.model.EquipmentType;
import lombok.Data;

import java.util.List;

@Data
public class EquipmentTypePageVO {
    private Long totalNum;
    private Long totalPage;
    private List<EquipmentType> list;
}
