package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.RepairPerson;
import com.system.admin.param.AddRepairPersonParam;
import com.system.admin.param.ModifyRepairPersonParam;
import com.system.admin.param.RepairPersonPageParam;
import com.system.admin.service.IRepairPersonService;
import com.system.admin.vo.RepairPersonPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/repair-person")
public class RepairPersonController {

    @Autowired
    private IRepairPersonService repairPersonService;

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Integer id){
        return CommonResult.success(repairPersonService.getById(id));
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyRepairPersonParam param){
        RepairPerson repairPerson = new RepairPerson();
        repairPerson.setId(id);
        repairPerson.setEmail(param.getEmail());
        repairPerson.setUsername(param.getUsername());
        repairPerson.setPasswd(param.getPasswd());
        repairPerson.setNickname(param.getNickname());
        boolean flag = repairPersonService.updateById(repairPerson);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddRepairPersonParam param){
        RepairPerson repairPerson = new RepairPerson();
        repairPerson.setEmail(param.getEmail());
        repairPerson.setUsername(param.getUsername());
        repairPerson.setPasswd(param.getPasswd());
        repairPerson.setNickname(param.getNickname());
        boolean flag = repairPersonService.save(repairPerson);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        boolean flag = repairPersonService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping
    public CommonResult<RepairPersonPageVO> list(RepairPersonPageParam param){
        QueryWrapper<RepairPerson> wrapper = new QueryWrapper<RepairPerson>()
                .like(param.getEmail() != null, "email", param.getEmail())
                .like(param.getNickname() != null, "nickname", param.getNickname());
        Page<RepairPerson> page = repairPersonService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        RepairPersonPageVO vo = new RepairPersonPageVO();
        vo.setList(page.getRecords());
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        return CommonResult.success(vo);
    }
}
