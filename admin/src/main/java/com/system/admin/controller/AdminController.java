package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.bo.AdminUserDetails;
import com.system.admin.model.Admin;
import com.system.admin.model.Role;
import com.system.admin.param.AdminPageParam;
import com.system.admin.param.ModifyAdminParam;
import com.system.admin.param.UpdateRoleParam;
import com.system.admin.service.impl.AdminServiceImpl;
import com.system.admin.vo.AdminPageVO;
import com.system.common.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@Slf4j
@RestController
@RequestMapping("/system/admin")
//
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //注册
    @PostMapping("/register")
    @ResponseBody
    public CommonResult register(@RequestBody Admin admin) {
        log.info("register");
        boolean is = adminService.register(admin);
        if(is){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }
    //登录
    @PostMapping("/login")
    public CommonResult login(@RequestBody Admin admin){
        log.info("login");
        String token = adminService.login(admin);
        if(token != null){
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return CommonResult.success(tokenMap);
        }else{
            return CommonResult.failed("login failed");
        }
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update")
    @ResponseBody
    public CommonResult updateRole(@RequestBody UpdateRoleParam param) {
        boolean flag = adminService.updateRole(param.getAdminId(), param.getRoleIds());
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> getRoleList(@PathVariable Integer adminId) {
        List<Role> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyAdminParam param){
        Boolean flag=adminService.modifyById(id,param);
        if(flag){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        Boolean flag=adminService.removeById(id);
        if(flag){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping
    public CommonResult<AdminPageVO> list(AdminPageParam param){
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .like(param.getName() != null, "name", param.getName())
                .like(param.getEmail() != null, "email", param.getEmail());
        Page<Admin> page = adminService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        AdminPageVO vo = new AdminPageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }
    @GetMapping("/info")
    public CommonResult<Admin> getAdminInfo(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) auth.getPrincipal();
        Admin admin = adminService.getById(adminUserDetails.getAdmin().getId());
        return CommonResult.success(admin);
    }

}
