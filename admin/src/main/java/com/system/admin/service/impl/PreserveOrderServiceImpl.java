package com.system.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.dao.EquipmentDetails;
import com.system.admin.mapper.*;
import com.system.admin.model.Equipment;
import com.system.admin.model.EquipmentType;
import com.system.admin.model.PreserveEquipmentRelation;
import com.system.admin.model.PreserveOrder;
import com.system.admin.service.IPreserveOrderService;
import com.system.admin.vo.PreserveOrderDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-06-07
 */
@Service
public class PreserveOrderServiceImpl extends ServiceImpl<PreserveOrderMapper, PreserveOrder> implements IPreserveOrderService {
    @Autowired
    PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @Autowired
    PreserveOrderMapper preserveOrderMapper;
    @Autowired
    EquipmentMapper equipmentMapper;
    @Autowired
    EquipmentTypeMapper equipmentTypeMapper;
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
        preserveOrderDetailsVO.setList(list1);
        return preserveOrderDetailsVO;
    }
}
