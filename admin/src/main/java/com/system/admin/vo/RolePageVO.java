package com.system.admin.vo;

import com.system.admin.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class RolePageVO {
    private Long totalNum;
    private Long totalPage;
    private List<Role> list;
}
