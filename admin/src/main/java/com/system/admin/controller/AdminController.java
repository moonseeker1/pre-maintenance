package com.system.admin.controller;


import com.system.admin.model.Admin;
import com.system.admin.service.impl.AdminServiceImpl;
import com.system.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/admin")
//
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;
    @PostMapping("/register")
    public CommonResult register(@RequestBody Admin admin) {
        log.info("register");
        adminService.insertAdmin(admin);
        return CommonResult.success("register success");
    }
}
