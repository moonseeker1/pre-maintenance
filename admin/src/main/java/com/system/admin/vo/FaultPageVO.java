package com.system.admin.vo;

import com.system.admin.model.Fault;
import lombok.Data;

import java.util.List;

@Data
public class FaultPageVO {

    private Long totalNum;
    private Long totalPage;
    private List<Fault> list;
}
