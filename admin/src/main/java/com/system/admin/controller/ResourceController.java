package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Resource;
import com.system.admin.param.AddResourceParam;
import com.system.admin.param.ModifyResourceParam;
import com.system.admin.param.ResourcePageParam;
import com.system.admin.service.IResourceService;
import com.system.admin.vo.ResourcePageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@RestController
@RequestMapping("/system/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @GetMapping("/{id}")
    public CommonResult<Resource> getById(@PathVariable Integer id){
        Resource resource = resourceService.getById(id);
        return CommonResult.success(resource);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id,@RequestBody ModifyResourceParam param){

        Resource resource = new Resource();
        resource.setId(id);
        resource.setName(param.getName());
        resource.setUrl(param.getUrl());
        boolean flag = resourceService.updateById(resource);
        if (flag) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddResourceParam param){
        Resource resource = new Resource();
        resource.setUrl(param.getUrl());
        resource.setName(param.getName());
        boolean flag = resourceService.save(resource);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        boolean flag = resourceService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping("/list")
    public CommonResult<ResourcePageVO> list(ResourcePageParam param){
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>()
                .like(param.getName() != null, "name", param.getName())
                .like(param.getUrl() != null, "url", param.getUrl());
        Page<Resource> page = resourceService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        ResourcePageVO vo = new ResourcePageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }

    @GetMapping("/listAll")
    public CommonResult<List<Resource>> listAll(){
        return CommonResult.success(resourceService.list());
    }
}
