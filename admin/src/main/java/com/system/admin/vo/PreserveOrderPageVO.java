package com.system.admin.vo;

import com.system.admin.model.PreserveOrder;
import lombok.Data;

import java.util.List;

@Data
public class PreserveOrderPageVO {

    private Long totalNum;
    private Long totalPage;
    private List<PreserveOrder> list;
}
