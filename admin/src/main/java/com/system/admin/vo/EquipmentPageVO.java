package com.system.admin.vo;

import com.system.admin.model.Equipment;
import lombok.Data;

import java.util.List;

@Data
public class EquipmentPageVO {
    private Long totalNum;
    private Long totalPage;
    private List<Equipment> list;
    String producerName;
    String typeName;
}
