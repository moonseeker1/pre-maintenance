package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Resource;
import com.system.admin.model.Role;
import com.system.admin.param.AddRoleParam;
import com.system.admin.param.ModifyRoleParam;
import com.system.admin.param.RolePageParam;
import com.system.admin.param.SetResourceParam;
import com.system.admin.service.IRoleService;
import com.system.admin.vo.RolePageVO;
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
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping
    public CommonResult insert(@RequestBody AddRoleParam param){
        Role role = new Role();
        role.setName(param.getName());
        boolean flag = roleService.save(role);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Integer id){
        Role role = roleService.getById(id);
        return CommonResult.success(role);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyRoleParam param){
        Role role = new Role();
        role.setName(param.getName());
        role.setId(id);
        boolean flag = roleService.updateById(role);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        boolean flag = roleService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping
    public CommonResult<RolePageVO> list(RolePageParam param){
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>()
                .like(param.getName() != null, "name", param.getName());
        Page<Role> page = roleService.page(new Page<>(param.getPageNum(),param.getPageSize()), wrapper);
        RolePageVO vo = new RolePageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }

    /**
     * 为角色分配资源
     * @return
     */
    @PostMapping("/role/resource")
    public CommonResult setResource(@RequestBody SetResourceParam param){
        boolean flag = roleService.updateResource(param.getRoleId(),param.getResourceIds());
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();

    }

    /**
     * 查询角色资源
     * @param roleId
     * @return
     */
    @GetMapping( "/resource/{roleId}")
    @ResponseBody
    public CommonResult<List<Resource>> getResourceList(@PathVariable Integer roleId) {
        List<Resource> list = roleService.listResources(roleId);
        return CommonResult.success(list);
    }
}
