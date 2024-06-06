package com.system.admin.controller;


import com.system.admin.param.AddRepairOrderParam;
import com.system.admin.service.IRepairOrderService;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    public CommonResult generateRepairOrder(@RequestBody AddRepairOrderParam addRepairOrderParam){
        boolean flag=repairOrderService.generateRepairOrder(addRepairOrderParam);
        if(flag){
            return CommonResult.success(null);
        }else{
            return CommonResult.failed();
        }

    }

}
