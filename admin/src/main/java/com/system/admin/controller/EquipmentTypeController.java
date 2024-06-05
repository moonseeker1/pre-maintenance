package com.system.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.admin.model.EquipmentType;
import com.system.admin.param.AddEquipmentTypeParam;
import com.system.admin.param.EquipmentTypePageParam;
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
        boolean flag = equipmentTypeService.save(equipmentType);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id){
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
        equipmentType.setCount(param.getCount());
        equipmentType.setName(param.getName());
        equipmentType.setProducerId(param.getProducerId());
        boolean flag = equipmentTypeService.updateById(equipmentType);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
