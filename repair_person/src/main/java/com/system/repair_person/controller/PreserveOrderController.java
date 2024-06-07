package com.system.repair_person.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.api.CommonResult;
import com.system.repair_person.model.RepairPerson;
import com.system.repair_person.service.IRepairPersonService;
import com.system.repair_person.vo.PreserveOrderPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/system/r/preserveOrder")
public class PreserveOrderController {

    @Autowired
    private IRepairPersonService repairPersonService;

    @GetMapping("/list")








    public CommonResult<PreserveOrderPageVO> list(@AuthenticationPrincipal RepairPerson repairPerson) {

        QueryWrapper<RepairPerson> wrapper = new QueryWrapper<RepairPerson>()
                .eq("person_id", repairPerson.getId());

    }
}
