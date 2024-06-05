package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Producer;
import com.system.admin.param.AddProducerParam;
import com.system.admin.param.ModifyProducerParam;
import com.system.admin.param.ProducerPageParam;
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
@RequestMapping("/system/producer")
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
    public CommonResult<ProducerPageVO> list(ProducerPageParam param){
        QueryWrapper<Producer> wrapper = new QueryWrapper<Producer>()
                .like(param.getName() != null, "name", param.getName())
                .like(param.getPhone() != null, "phone", param.getPhone());
        Page<Producer> page = producerService.page(new Page<>(param.getPageNum(), param.getPageSize()),wrapper);
        ProducerPageVO vo = new ProducerPageVO(page.getTotal(), page.getPages(), page.getRecords());
        return CommonResult.success(vo);
    }

    @PutMapping("/{id}")
    public CommonResult modify(@PathVariable Integer id, @RequestBody ModifyProducerParam param){
        Producer producer = new Producer();
        producer.setId(id);
        producer.setName(param.getName());
        producer.setPhone(param.getPhone());
        boolean flag = producerService.updateById(producer);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddProducerParam param){
        Producer producer = new Producer();
        producer.setName(param.getName());
        producer.setPhone(param.getPhone());
        boolean flag = producerService.save(producer);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        boolean flag = producerService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
