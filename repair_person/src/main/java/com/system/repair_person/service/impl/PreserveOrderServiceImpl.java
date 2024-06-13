package com.system.repair_person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.repair_person.dao.EquipmentDetails;
import com.system.repair_person.mapper.EquipmentMapper;
import com.system.repair_person.mapper.EquipmentTypeMapper;
import com.system.repair_person.mapper.PreserveEquipmentRelationMapper;
import com.system.repair_person.mapper.PreserveOrderMapper;
import com.system.repair_person.model.Equipment;
import com.system.repair_person.model.EquipmentType;
import com.system.repair_person.model.PreserveEquipmentRelation;
import com.system.repair_person.model.PreserveOrder;
import com.system.repair_person.service.IPreserveOrderService;
import com.system.repair_person.vo.PreserveOrderDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class PreserveOrderServiceImpl extends ServiceImpl<PreserveOrderMapper, PreserveOrder> implements IPreserveOrderService {

    @Autowired
    PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @Autowired
    EquipmentMapper equipmentMapper;
    @Autowired
    PreserveOrderMapper preserveOrderMapper;
    @Autowired
    EquipmentTypeMapper equipmentTypeMapper;
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
            equipment.setLastPreserveTime(LocalDateTime.now());
            equipmentMapper.updateById(equipment);
        }

        return true;
    }
    @Override
    public PreserveOrderDetailsVO getPreserveOrderDetails(Integer preserveOrderId) {
        PreserveOrderDetailsVO preserveOrderDetailsVO = new PreserveOrderDetailsVO();
        PreserveOrder preserveOrder = preserveOrderMapper.selectById(preserveOrderId);
        preserveOrderDetailsVO.setPreserveOrderId(preserveOrderId);
        preserveOrderDetailsVO.setRepairPersonName(preserveOrder.getPersonName());
        preserveOrderDetailsVO.setState(preserveOrder.getState());
        QueryWrapper<PreserveEquipmentRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",preserveOrderId);
        List<PreserveEquipmentRelation> list = preserveEquipmentRelationMapper.selectList(queryWrapper);
        List<EquipmentDetails> list1 = new ArrayList<>();
        for(PreserveEquipmentRelation preserveEquipmentRelation:list){
            EquipmentDetails equipmentDetails = new EquipmentDetails();
            Equipment equipment = equipmentMapper.selectById(preserveEquipmentRelation.getEquipmentId());
            equipmentDetails.setEquipmentId(equipment.getId());
            equipmentDetails.setEquipmentTypeName(equipment.getEquipmentTypeName());
            EquipmentType equipmentType = equipmentTypeMapper.selectById(equipment.getEquipmentTypeId());
            equipmentDetails.setProducerName(equipmentType.getProducerName());
            list1.add(equipmentDetails);
        }
        preserveOrderDetailsVO.setEquipmentDetails(list1);
        return preserveOrderDetailsVO;
    }
}
