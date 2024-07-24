package com.system.admin.param;

import lombok.Data;

import java.util.List;

@Data
public class SetResourceParam {

    private Integer roleId;

    private List<Integer> resourceIds;
}
