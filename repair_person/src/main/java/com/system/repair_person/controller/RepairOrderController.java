package com.system.repair_person.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.api.CommonResult;
import com.system.repair_person.bo.RepairPersonUserDetails;
import com.system.repair_person.model.RepairOrder;
import com.system.repair_person.param.RepairPersonRepairOrderPageParam;
import com.system.repair_person.service.IRepairOrderService;
import com.system.repair_person.vo.RepairOrderDetailsVO;
import com.system.repair_person.vo.RepairOrderPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<RepairOrderPageVO> list(RepairPersonRepairOrderPageParam param) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        RepairPersonUserDetails repairPersonUserDetails = (RepairPersonUserDetails) auth.getPrincipal();
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<RepairOrder>()
                .eq("person_id", repairPersonUserDetails.getRepairPerson().getId());
        Page<RepairOrder> page = repairOrderService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        RepairOrderPageVO pageVO = new RepairOrderPageVO();
        pageVO.setTotalNum(page.getTotal());
        pageVO.setTotalPage(page.getPages());
        pageVO.setList(page.getRecords());
        return CommonResult.success(pageVO);
    }
    @GetMapping("/details/{repairOrderId}")
    public CommonResult<RepairOrderDetailsVO> getRepairOrderDetails(@PathVariable Integer repairOrderId){
        RepairOrderDetailsVO repairOrderDetailsVO=repairOrderService.getRepairOrderDetails(repairOrderId);
        return CommonResult.success(repairOrderDetailsVO);
    }
    @PutMapping("/{id}")
    @Transactional
    public CommonResult finish(@PathVariable Integer id){
        RepairOrder order = repairOrderService.getById(id);
        order.setState(1);
        boolean flag1 = repairOrderService.setEquipment(id);
        boolean flag2 = repairOrderService.updateById(order);
        if (flag1&&flag2) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
