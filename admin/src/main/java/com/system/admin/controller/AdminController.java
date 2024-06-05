package com.system.admin.controller;


import com.system.admin.model.Admin;
import com.system.admin.service.impl.AdminServiceImpl;
import com.system.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RequestMapping("system/admin")
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
            return CommonResult.failed("register failed");
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
}
