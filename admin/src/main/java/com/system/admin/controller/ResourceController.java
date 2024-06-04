package com.system.admin.controller;


import com.system.admin.model.Resource;
import com.system.admin.service.impl.ResourceServiceImpl;
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
 * @since 2024-06-04
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    ResourceServiceImpl resourceService;
    @PostMapping("/save")
    public CommonResult save(@RequestBody Resource resource) {
        resourceService.save(resource);
        return CommonResult.success("save success");
    }

}
