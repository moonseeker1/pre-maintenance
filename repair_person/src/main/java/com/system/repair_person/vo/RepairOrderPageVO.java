package com.system.repair_person.vo;

import com.system.repair_person.model.RepairOrder;
import lombok.Data;

import java.util.List;

@Data
public class RepairOrderPageVO {
    private Long totalNum;
    private Long totalPage;
    private List<RepairOrder> list;
}
