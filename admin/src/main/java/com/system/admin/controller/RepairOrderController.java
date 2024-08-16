package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.OrderEquipmentFaultRelation;
import com.system.admin.model.RepairOrder;
import com.system.admin.param.AddRepairOrderParam;
import com.system.admin.param.RepairOrderPageParam;
import com.system.admin.service.IOrderEquipmentFaultRelationService;
import com.system.admin.service.IRepairOrderService;
import com.system.admin.vo.RepairOrderDetailsVO;
import com.system.admin.vo.RepairOrderPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/system/repairOrder")
public class RepairOrderController {
    @Autowired
    private IRepairOrderService repairOrderService;
    @Autowired
    private IOrderEquipmentFaultRelationService orderEquipmentFaultRelationService;
    @PostMapping
    public CommonResult generateRepairOrder(@RequestBody AddRepairOrderParam addRepairOrderParam){
        boolean flag=repairOrderService.generateRepairOrder(addRepairOrderParam);
        if(flag){
            return CommonResult.success(null);
        }else{
            return CommonResult.failed();
        }

    }
    /**
     * 查看订单详情
     */
    @GetMapping("/details/{repairOrderId}")
    public CommonResult<RepairOrderDetailsVO> getRepairOrderDetails(@PathVariable Integer repairOrderId){
        RepairOrderDetailsVO repairOrderDetailsVO=repairOrderService.getRepairOrderDetails(repairOrderId);
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
    @Transactional
    @DeleteMapping("/{repairOrderId}")
    public CommonResult deleteRepairOrder(@PathVariable Integer repairOrderId){
        if(repairOrderService.getById(repairOrderId).getState()!=1){
            return CommonResult.failed("订单未完成，无法删除");
        }
        boolean flag2=orderEquipmentFaultRelationService.remove(new QueryWrapper<OrderEquipmentFaultRelation>().eq("order_id",repairOrderId));
        boolean flag1=repairOrderService.removeById(repairOrderId);

        if(flag1&&flag2){
            return CommonResult.success(null);
        }else{
            return CommonResult.failed();
        }
    }
}
