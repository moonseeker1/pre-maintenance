package com.system.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Producer;
import com.system.admin.param.AddProducerParam;
import com.system.admin.param.ModifyProducerParam;
import com.system.admin.service.IProducerService;
import com.system.admin.vo.ProducerPageVO;
import com.system.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-04
 */
@RestController
@RequestMapping("system/producer")
@Slf4j
public class ProducerController {

    @Autowired
    private IProducerService producerService;

    @GetMapping("/{id}")
    public CommonResult<Producer> getById(@PathVariable Integer id){
        Producer producer = producerService.getById(id);
        return CommonResult.success(producer);
    }

    @GetMapping("/list")
    public CommonResult<ProducerPageVO> list(@RequestParam(required = false,defaultValue = "5") Integer pageSize,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<Producer> page = producerService.page(new Page<>(pageNum, pageSize));
        ProducerPageVO vo = new ProducerPageVO(page.getTotal(), page.getPages(), page.getRecords());
        return CommonResult.success(vo);
    }

    @PutMapping("/{id}")
    public CommonResult modify(@PathVariable Integer id, @RequestBody ModifyProducerParam param){
        Producer producer = new Producer();
        producer.setId(id);
        producer.setName(param.getName());
        producer.setPhone(param.getPhone());
        producerService.updateById(producer);
        return CommonResult.success(null);
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddProducerParam param){
        Producer producer = new Producer();
        producer.setName(param.getName());
        producer.setPhone(param.getPhone());
        producerService.save(producer);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        producerService.removeById(id);
        return CommonResult.success(null);
    }
}
