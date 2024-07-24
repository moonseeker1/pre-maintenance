package com.system.repair_person.config;

import com.system.repair_person.service.IRepairPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * mall-security模块相关配置
 */
@Configuration
public class RepairPersonSecurityConfig {

    @Autowired
    private IRepairPersonService adminService;
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

}
