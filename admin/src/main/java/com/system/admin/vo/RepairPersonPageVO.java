package com.system.admin.vo;

import com.system.admin.model.RepairPerson;
import lombok.Data;

import java.util.List;
@Data
public class RepairPersonPageVO {
    private Long totalNum;
    private Long totalPage;
    private List<RepairPerson> list;
}
