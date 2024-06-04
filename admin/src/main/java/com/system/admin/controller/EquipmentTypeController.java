package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.EquipmentType;
import com.system.admin.param.AddEquipmentTypeParam;
import com.system.admin.param.ModifyEquipmentTypeParam;
import com.system.admin.service.IEquipmentTypeService;
import com.system.admin.vo.EquipmentTypePageVO;
import com.system.common.api.CommonResult;
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
@RequestMapping("/system/equipmentType")
public class EquipmentTypeController {

    @Autowired
    private IEquipmentTypeService equipmentTypeService;

    @GetMapping("/{id}")
    public CommonResult<EquipmentType> getById(@PathVariable Integer id){
        EquipmentType equipmentType = equipmentTypeService.getById(id);
        return CommonResult.success(equipmentType);
    }

    @PostMapping
    public CommonResult insert(@RequestBody AddEquipmentTypeParam param){
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setProducerId(param.getProducerId()).getProducerId();
        equipmentType.setName(param.getName());
        equipmentTypeService.save(equipmentType);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
        equipmentTypeService.removeById(id);
        return CommonResult.success(null);
    }

    @GetMapping("/list")
    public CommonResult<EquipmentTypePageVO> list(@RequestParam Integer pageSize,@RequestParam Integer pageNum,@RequestParam(required = false) Integer producerId){
        QueryWrapper<EquipmentType> wrapper=null;
        if (producerId!=null) {
            wrapper=new QueryWrapper<EquipmentType>()
                    .eq("producer_id",producerId);
        }
        Page<EquipmentType> page = equipmentTypeService.page(new Page<>(pageNum, pageSize), wrapper);
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
        equipmentType.setCount(param.getCount());
        equipmentType.setName(param.getName());
        equipmentType.setProducerId(param.getProducerId());
        equipmentTypeService.updateById(equipmentType);
        return CommonResult.success(null);
    }
}
