package com.system.admin.controller;


import com.system.admin.model.Admin;
import com.system.admin.model.Role;
import com.system.admin.service.impl.AdminServiceImpl;
import com.system.common.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
            return CommonResult.success("register success");
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
    public CommonResult updateRole(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("roleIds") List<Integer> roleIds) {
        boolean flag = adminService.updateRole(adminId, roleIds);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> getRoleList(@PathVariable Long adminId) {
        List<Role> roleList = adminService.getRoleList();
        return CommonResult.success(roleList);
    }
}
