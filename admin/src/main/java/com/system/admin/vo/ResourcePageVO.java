package com.system.admin.vo;

import com.system.admin.model.Resource;
import lombok.Data;

import java.util.List;

@Data
public class ResourcePageVO {
    private Long totalNum;
    private Long totalPage;
    private List<Resource> list;
}
