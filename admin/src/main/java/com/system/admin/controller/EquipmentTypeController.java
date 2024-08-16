package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.Equipment;
import com.system.admin.model.EquipmentType;
import com.system.admin.model.Producer;
import com.system.admin.param.AddEquipmentTypeParam;
import com.system.admin.param.EquipmentTypePageParam;
import com.system.admin.param.ModifyEquipmentTypeParam;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IEquipmentTypeService;
import com.system.admin.service.IProducerService;
import com.system.admin.vo.EquipmentTypePageVO;
import com.system.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyu
 * @since 2024-06-04
 */
@RestController
@RequestMapping("/system/equipmentType")
public class EquipmentTypeController {

    @Autowired
    private IEquipmentTypeService equipmentTypeService;

    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IProducerService producerService;
    @GetMapping("/{id}")
    public CommonResult<EquipmentType> getById(@PathVariable Integer id){
        EquipmentType equipmentType = equipmentTypeService.getById(id);
        return CommonResult.success(equipmentType);
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddEquipmentTypeParam param){
        EquipmentType equipmentType = new EquipmentType();
        Integer producerId = param.getProducerId();
        Producer producer = producerService.getById(producerId);
        equipmentType.setProducerId(producerId);
        equipmentType.setName(param.getName());
        equipmentType.setProducerName(producer.getName());
        boolean flag = equipmentTypeService.save(equipmentType);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public CommonResult deleteById(@PathVariable Integer id){

        QueryWrapper<Equipment> wrapper = new QueryWrapper<Equipment>()
                .eq("equipment_type_id", id);
        List<Equipment> equipmentList = equipmentService.list(wrapper);
        if (equipmentList.size()!=0) {
            CommonResult.failed("该设备种类id下还存在设备，不能删除");
        }
        boolean flag = equipmentTypeService.removeById(id);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping("/list")
    public CommonResult<EquipmentTypePageVO> list(EquipmentTypePageParam param){
        QueryWrapper<EquipmentType> wrapper=new QueryWrapper<EquipmentType>()
                .eq(param.getProducerId()!=null,"producer_id",param.getProducerId());
        Page<EquipmentType> page = equipmentTypeService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        EquipmentTypePageVO vo = new EquipmentTypePageVO();
        vo.setTotalNum(page.getTotal());
        vo.setTotalPage(page.getPages());
        vo.setList(page.getRecords());
        return CommonResult.success(vo);
    }

    @PutMapping("/{id}")
    public CommonResult modifyById(@PathVariable Integer id, @RequestBody ModifyEquipmentTypeParam param){
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setId(id);
        equipmentType.setName(param.getName());
        equipmentType.setProducerId(param.getProducerId());
        equipmentType.setProducerName(producerService.getById(param.getProducerId()).getName());
        boolean flag = equipmentTypeService.updateById(equipmentType);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @GetMapping("/listAll")
    public CommonResult<List<EquipmentType>> listAll(){
        return CommonResult.success(equipmentTypeService.list());
    }
}
