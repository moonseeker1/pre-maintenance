package com.system.admin.controller;


import com.system.admin.model.Producer;
import com.system.admin.service.impl.ProducerServiceImpl;
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
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    ProducerServiceImpl producerService;
    @PostMapping("/add")
    public CommonResult save(@RequestBody Producer producer){
        boolean is = producerService.save(producer);
        if(is){
            return CommonResult.success("add success");
        }else{
            return CommonResult.failed("add failed");
        }
    }

}
