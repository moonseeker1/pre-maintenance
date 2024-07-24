package com.system.repair_person.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.api.CommonResult;
import com.system.repair_person.bo.RepairPersonUserDetails;
import com.system.repair_person.model.PreserveOrder;
import com.system.repair_person.param.RepairPersonPreserveOrderPageParam;
import com.system.repair_person.service.IPreserveOrderService;
import com.system.repair_person.vo.PreserveOrderDetailsVO;
import com.system.repair_person.vo.PreserveOrderPageVO;
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
@RequestMapping("/system/r/preserveOrder")
public class PreserveOrderController {

    @Autowired
    private IPreserveOrderService preserveOrderService;

    @GetMapping("/list")
    public CommonResult<PreserveOrderPageVO> list(RepairPersonPreserveOrderPageParam param) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        RepairPersonUserDetails repairPersonUserDetails = (RepairPersonUserDetails) auth.getPrincipal();
        QueryWrapper<PreserveOrder> wrapper = new QueryWrapper<PreserveOrder>()
                .eq("person_id", repairPersonUserDetails.getRepairPerson().getId());
        Page<PreserveOrder> page = preserveOrderService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        PreserveOrderPageVO pageVO = new PreserveOrderPageVO();
        pageVO.setTotalNum(page.getTotal());
        pageVO.setTotalPage(page.getPages());
        pageVO.setList(page.getRecords());
        return CommonResult.success(pageVO);
    }

    @PutMapping("/{id}")
    @Transactional
    public CommonResult finish(@PathVariable Integer id){
        PreserveOrder order = preserveOrderService.getById(id);
        boolean flag1 = preserveOrderService.setEquipment(id);
        order.setState(2);
        boolean flag2 = preserveOrderService.updateById(order);
        if (flag1&&flag2) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @GetMapping("/details/{preserveOrderId}")
    public CommonResult<PreserveOrderDetailsVO> getPreserveOrderDetails(@PathVariable Integer preserveOrderId){
        PreserveOrderDetailsVO preserveOrderDetailsVO =preserveOrderService.getPreserveOrderDetails(preserveOrderId);
        return CommonResult.success(preserveOrderDetailsVO);
    }
}
