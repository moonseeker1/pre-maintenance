package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.RepairOrder;
import com.system.admin.param.RepairOrderPageParam;
import com.system.admin.service.IRepairOrderService;
import com.system.admin.vo.RepairOrderDetailsVO;
import com.system.admin.vo.RepairOrderPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/system/preserveOrder")
public class PreserveOrderController {
    @Autowired
    private IRepairOrderService repairOrderService;
    /**
     * 查看订单详情
     */
    @GetMapping("/details/{repairOrderId}")
    public CommonResult<RepairOrderDetailsVO> getRepairOrderDetails(@PathVariable Integer preserveOrderId){
        RepairOrderDetailsVO repairOrderDetailsVO=repairOrderService.getRepairOrderDetails(preserveOrderId);
        return CommonResult.success(repairOrderDetailsVO);
    }

    @GetMapping
    public CommonResult<RepairOrderPageVO> list(RepairOrderPageParam param){
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<RepairOrder>()
                .like(param.getPersonName() != null, "person_name", param.getPersonName());
        Page<RepairOrder> page = repairOrderService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        RepairOrderPageVO vo = new RepairOrderPageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }
}
