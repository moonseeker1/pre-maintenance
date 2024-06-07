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
import com.system.common.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/system/repairPerson")
public class RepairPersonController {

    @Autowired
    private IRepairPersonService repairPersonService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String password = passwordEncoder.encode(param.getPasswd());
        repairPerson.setPasswd(password);
        repairPerson.setName(param.getName());
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
        QueryWrapper<RepairPerson> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",param.getUsername());
        if(!repairPersonService.list(queryWrapper).isEmpty()){
            Asserts.fail("用户名已存在");
        }
        String passwd = param.getPasswd();
        passwd=passwordEncoder.encode(passwd); // encode password
        repairPerson.setPasswd(passwd);
        repairPerson.setName(param.getName());
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
                .like(param.getName() != null, "nickname", param.getName());
        Page<RepairPerson> page = repairPersonService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        RepairPersonPageVO vo = new RepairPersonPageVO();
        vo.setList(page.getRecords());
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        return CommonResult.success(vo);
    }
}
