package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.PreserveOrder;
import com.system.admin.param.PreserveOrderPageParam;
import com.system.admin.service.IPreserveOrderService;
import com.system.admin.service.IRepairPersonService;
import com.system.admin.vo.PreserveOrderDetailsVO;
import com.system.admin.vo.PreserveOrderPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IPreserveOrderService preserveOrderService;
    @Autowired
    private IRepairPersonService repairPersonService;
    /**
     * 查看订单详情
     */
    @GetMapping("/details/{preserveOrderId}")
    public CommonResult<PreserveOrderDetailsVO> getPreserveOrderDetails(@PathVariable Integer preserveOrderId){
        PreserveOrderDetailsVO preserveOrderDetailsVO =preserveOrderService.getPreserveOrderDetails(preserveOrderId);
        return CommonResult.success(preserveOrderDetailsVO);
    }

    @GetMapping
    public CommonResult<PreserveOrderPageVO> list(PreserveOrderPageParam param){
        QueryWrapper<PreserveOrder> wrapper = new QueryWrapper<PreserveOrder>()
                .like(param.getPersonName() != null, "person_name", param.getPersonName())
                .eq(param.getState()!=null,"state",param.getState());
        Page<PreserveOrder> page = preserveOrderService.page(new Page<>(param.getPageNum(), param.getPageSize()),wrapper);
        PreserveOrderPageVO vo = new PreserveOrderPageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }

    @PutMapping
    public CommonResult setRepairPerson(@RequestParam Integer repairPersonId,@RequestParam Integer preserveOrderId){

        PreserveOrder preserveOrder = preserveOrderService.getById(preserveOrderId);
        preserveOrder.setPersonId(repairPersonId);
        preserveOrder.setPersonName(repairPersonService.getById(repairPersonId).getName());
        preserveOrder.setState(1);
        boolean flag = preserveOrderService.updateById(preserveOrder);
        if(flag){
            return CommonResult.success();
        }
            return CommonResult.failed();


    }
}
