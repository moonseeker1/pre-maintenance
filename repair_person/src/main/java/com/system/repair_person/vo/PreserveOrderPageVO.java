package com.system.repair_person.vo;

import com.system.repair_person.model.PreserveOrder;
import lombok.Data;

import java.util.List;

@Data
public class PreserveOrderPageVO {

    private Long totalNum;
    private Long totalPage;
    private List<PreserveOrder> list;
}
