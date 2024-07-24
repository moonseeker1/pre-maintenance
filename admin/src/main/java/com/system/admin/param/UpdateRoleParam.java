package com.system.admin.param;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleParam {

    private Integer adminId;

    private List<Integer> roleIds;
}
