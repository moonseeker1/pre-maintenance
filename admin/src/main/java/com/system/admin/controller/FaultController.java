package com.system.admin.controller;


import com.system.admin.model.Fault;
import com.system.admin.service.IFaultService;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
}
