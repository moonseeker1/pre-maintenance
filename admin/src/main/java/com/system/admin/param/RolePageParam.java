package com.system.admin.param;

import lombok.Data;

@Data
public class RolePageParam {

    private Integer pageSize=5;

    private Integer pageNum=1;

    private String name;

    private Integer adminCount;
}