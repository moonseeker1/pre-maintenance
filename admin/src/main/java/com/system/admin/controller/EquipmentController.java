package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Equipment;
import com.system.admin.model.EquipmentType;
import com.system.admin.param.AddEquipmentParam;
import com.system.admin.param.EquipmentPageParam;
import com.system.admin.param.ModifyEquipmentParam;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IEquipmentTypeService;
import com.system.admin.vo.EquipmentPageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/system/equipment")
public class EquipmentController {

    @Autowired
    private IEquipmentService equipmentService;

    @Autowired
    private IEquipmentTypeService equipmentTypeService;

    @PostMapping
    @Transactional
    public CommonResult insert(@RequestBody AddEquipmentParam param){

        EquipmentType type = equipmentTypeService.getById(param.getEquipmentTypeId());
        if (type==null) {
            return CommonResult.failed("不存在输入的设备种类id"+param.getEquipmentTypeId());
        }
        Equipment equipment = new Equipment();
        equipment.setEquipmentTypeId(param.getEquipmentTypeId());
        equipment.setServiceLife(param.getServiceLife());
        boolean flag = equipmentService.save(equipment);
        if (flag) {
            EquipmentType equipmentType = equipmentTypeService.getById(param.getEquipmentTypeId());
            equipmentType.setCount(equipmentType.getCount()+1);
            equipmentTypeService.updateById(equipmentType);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping("/{id}")
    public CommonResult<Equipment> getById(@PathVariable Integer id){
        Equipment equipment = equipmentService.getById(id);
        return CommonResult.success(equipment);
    }

    @GetMapping("/list")
    public CommonResult<EquipmentPageVO> list(EquipmentPageParam param){
        QueryWrapper<Equipment> wrapper=new QueryWrapper<Equipment>()
                .eq(param.getState()!=null,"state",param.getState())
                .eq(param.getPreserveNum()!=null,"preserve_num",param.getPreserveNum())
                .gt(param.getOutputMin()!=null,"output",param.getOutputMin())
                .lt(param.getOutputMax()!=null,"output",param.getOutputMax())
                .eq(param.getServiceLife()!=null,"service_life",param.getServiceLife());
        Page<Equipment> page = equipmentService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        EquipmentPageVO pageVO = new EquipmentPageVO();
        pageVO.setTotalNum(page.getTotal());
        pageVO.setTotalPage(page.getPages());
        pageVO.setList(page.getRecords());
        return CommonResult.success(pageVO);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyEquipmentParam param){
        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setPreserveNum(param.getPreserveNum());
        equipment.setOutput(param.getOutput());
        equipment.setState(param.getState());
        equipment.setServiceLife(param.getServiceLife());
        equipment.setEquipmentTypeId(param.getEquipmentTypeId());
        boolean flag = equipmentService.updateById(equipment);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public CommonResult deleteById(@PathVariable Integer id){
        Integer equipmentTypeId = equipmentService.getById(id).getEquipmentTypeId();
        boolean flag = equipmentService.removeById(id);
        if (flag) {
            EquipmentType equipmentType = equipmentTypeService.getById(equipmentTypeId);
            equipmentType.setCount(equipmentType.getCount()-1);
            equipmentTypeService.updateById(equipmentType);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
