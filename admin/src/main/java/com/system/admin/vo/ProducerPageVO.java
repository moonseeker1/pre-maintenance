package com.system.admin.vo;

import com.system.admin.model.Producer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProducerPageVO {
    private Long totalNum;
    private Long totalPage;
    private List<Producer> list;
}
