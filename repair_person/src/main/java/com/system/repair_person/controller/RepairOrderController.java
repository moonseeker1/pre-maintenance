package com.system.repair_person.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.api.CommonResult;
import com.system.repair_person.model.RepairOrder;
import com.system.repair_person.model.RepairPerson;
import com.system.repair_person.param.RepairOrderPageParam;
import com.system.repair_person.service.IRepairOrderService;
import com.system.repair_person.vo.RepairOrderPageVO;
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
@RequestMapping("/system/r/repairOrder")
public class RepairOrderController {
    @Autowired
    private IRepairOrderService repairOrderService;

    @GetMapping("/list")
    public CommonResult<RepairOrderPageVO> list(@AuthenticationPrincipal RepairPerson repairPerson
            , RepairOrderPageParam param) {

        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<RepairOrder>()
                .eq("person_id", repairPerson.getId());
        Page<RepairOrder> page = repairOrderService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        RepairOrderPageVO pageVO = new RepairOrderPageVO();
        pageVO.setTotalNum(page.getTotal());
        pageVO.setTotalPage(page.getPages());
        pageVO.setList(page.getRecords());
        return CommonResult.success(pageVO);
    }
}
