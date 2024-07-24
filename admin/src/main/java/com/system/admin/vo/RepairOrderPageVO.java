package com.system.admin.vo;

import com.system.admin.model.RepairOrder;
import lombok.Data;

import java.util.List;

@Data
public class RepairOrderPageVO {
    private Long totalNum;
    private Long totalPage;
    private List<RepairOrder> list;
}
