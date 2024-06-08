package com.system.repair_person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.repair_person.dao.EquipmentAndFaultItem;
import com.system.repair_person.mapper.*;
import com.system.repair_person.model.*;
import com.system.repair_person.service.IRepairOrderService;
import com.system.repair_person.vo.RepairOrderDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    RepairOrderMapper repairOrderMapper;
    @Autowired
    FaultMapper faultMapper;
    @Autowired
    EquipmentTypeMapper equipmentTypeMapper;
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

    @Override
    public RepairOrderDetailsVO getRepairOrderDetails(Integer repairOrderId) {
        RepairOrderDetailsVO repairOrderDetailsVO = new RepairOrderDetailsVO();
        RepairOrder repairOrder = repairOrderMapper.selectById(repairOrderId);
        repairOrderDetailsVO.setPersonName(repairOrder.getPersonName());
        repairOrderDetailsVO.setRepairOrderId(repairOrder.getId());
        repairOrderDetailsVO.setState(repairOrder.getState());
        QueryWrapper<OrderEquipmentFaultRelation> wrapper = new QueryWrapper<OrderEquipmentFaultRelation>()
                .eq("order_id", repairOrderId);
        List<OrderEquipmentFaultRelation> list = orderEquipmentFaultRelationMapper.selectList(wrapper);
        List<EquipmentAndFaultItem> list1 = new ArrayList<>();
        for(OrderEquipmentFaultRelation orderEquipmentFaultRelation: list){
            Equipment equipment = equipmentMapper.selectById(orderEquipmentFaultRelation.getEquipmentId());
            EquipmentType equipmentType = equipmentTypeMapper.selectById(equipment.getEquipmentTypeId());
            Fault fault = faultMapper.selectById(orderEquipmentFaultRelation.getFaultId());
            EquipmentAndFaultItem equipmentAndFaultItem = new EquipmentAndFaultItem();
            equipmentAndFaultItem.setProducerName(equipmentType.getProducerName());
            equipmentAndFaultItem.setEquipmentTypeName(equipmentType.getName());
            equipmentAndFaultItem.setEquipmentId(equipment.getId());
            equipmentAndFaultItem.setSuggestion(fault.getSuggestion());
            equipmentAndFaultItem.setFaultName(fault.getName());
            list1.add(equipmentAndFaultItem);
        }
        repairOrderDetailsVO.setList(list1);
        return repairOrderDetailsVO;
    }
}
