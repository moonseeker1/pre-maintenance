package com.system.admin.vo;

import com.system.admin.model.Admin;
import lombok.Data;

import java.util.List;

@Data
public class AdminPageVO {

    private Long totalNum;
    private Long totalPage;
    private List<Admin> list;
}
