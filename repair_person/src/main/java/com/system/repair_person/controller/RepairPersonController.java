package com.system.repair_person.controller;


import com.system.common.api.CommonResult;
import com.system.repair_person.model.RepairPerson;
import com.system.repair_person.service.IRepairPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/system/r/repairPerson")
public class RepairPersonController {

    @Autowired
    IRepairPersonService repairPersonService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @PostMapping("/login")
    public CommonResult login(@RequestBody RepairPerson person) {
        String token = repairPersonService.login(person.getUsername(), person.getPasswd());
        if (token != null) {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return CommonResult.success(tokenMap);
        } else {
            return CommonResult.failed("login failed");
        }
    }


}
