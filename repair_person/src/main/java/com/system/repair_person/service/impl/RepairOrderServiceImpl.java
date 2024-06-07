package com.system.repair_person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.repair_person.mapper.EquipmentMapper;
import com.system.repair_person.mapper.OrderEquipmentFaultRelationMapper;
import com.system.repair_person.mapper.RepairOrderMapper;
import com.system.repair_person.model.Equipment;
import com.system.repair_person.model.OrderEquipmentFaultRelation;
import com.system.repair_person.model.RepairOrder;
import com.system.repair_person.service.IRepairOrderService;
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
public class RepairOrderServiceImpl extends ServiceImpl<RepairOrderMapper, RepairOrder> implements IRepairOrderService {
    @Autowired
    OrderEquipmentFaultRelationMapper orderEquipmentFaultRelationMapper;
    @Autowired
    EquipmentMapper equipmentMapper;
    @Override
    public boolean setEquipment(Integer id) {
        QueryWrapper<OrderEquipmentFaultRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id);
        List<OrderEquipmentFaultRelation> list = orderEquipmentFaultRelationMapper.selectList(queryWrapper);
        for (OrderEquipmentFaultRelation orderEquipmentFaultRelation : list){
            Integer equipmentId = orderEquipmentFaultRelation.getEquipmentId();
            Equipment equipment = new Equipment();
            equipment.setState(0);
            equipment.setId(equipmentId);
            equipmentMapper.updateById(equipment);
        }

        return true;
    }
}
