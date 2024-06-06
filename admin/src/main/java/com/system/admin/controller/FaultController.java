package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Fault;
import com.system.admin.param.AddFaultParam;
import com.system.admin.param.FaultPageParam;
import com.system.admin.param.ModifyFaultParam;
import com.system.admin.service.IFaultService;
import com.system.admin.vo.FaultPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/fault")
public class FaultController {

    @Autowired
    private IFaultService faultService;
    @GetMapping("/listAll")
    public CommonResult<List<Fault>> listAll(){
        return CommonResult.success(faultService.list());
    }

    @GetMapping("/{id}")
    public CommonResult<Fault> getById(@PathVariable Integer id){
        Fault fault = faultService.getById(id);
        return CommonResult.success(fault);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyFaultParam param){
        Fault fault = new Fault();
        fault.setId(id);
        fault.setName(param.getName());
        fault.setSuggestion(param.getSuggestion());
        boolean flag = faultService.updateById(fault);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddFaultParam param){
        Fault fault = new Fault();
        fault.setName(param.getName());
        fault.setSuggestion(param.getSuggestion());
        boolean flag = faultService.save(fault);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        boolean flag = faultService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping
    public CommonResult<FaultPageVO> list(FaultPageParam param){
        QueryWrapper<Fault> wrapper = new QueryWrapper<Fault>()
                .like(param.getName() != null, "name", param.getName());
        Page<Fault> page = faultService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        FaultPageVO vo = new FaultPageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }
}
