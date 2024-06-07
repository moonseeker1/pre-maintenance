package com.system.repair_person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.repair_person.mapper.EquipmentMapper;
import com.system.repair_person.mapper.PreserveEquipmentRelationMapper;
import com.system.repair_person.model.Equipment;
import com.system.repair_person.model.PreserveEquipmentRelation;
import com.system.repair_person.model.PreserveOrder;
import com.system.repair_person.mapper.PreserveOrderMapper;
import com.system.repair_person.service.IPreserveOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
@Service
public class PreserveOrderServiceImpl extends ServiceImpl<PreserveOrderMapper, PreserveOrder> implements IPreserveOrderService {

    @Autowired
    PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @Autowired
    EquipmentMapper equipmentMapper;
    @Override
    public boolean setEquipment(Integer id) {
        QueryWrapper<PreserveEquipmentRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id);
        List<PreserveEquipmentRelation> list = preserveEquipmentRelationMapper.selectList(queryWrapper);
        for (PreserveEquipmentRelation preserveEquipmentRelation : list){
            Integer equipmentId = preserveEquipmentRelation.getEquipmentId();
            Equipment equipment = new Equipment();
            equipment.setState(0);
            equipment.setId(equipmentId);
            equipmentMapper.updateById(equipment);
        }

        return true;
    }
}
