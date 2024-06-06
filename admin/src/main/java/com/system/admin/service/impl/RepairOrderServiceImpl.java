package com.system.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.mapper.OrderEquipmentFaultRelationMapper;
import com.system.admin.mapper.RepairOrderMapper;
import com.system.admin.model.OrderEquipmentFaultRelation;
import com.system.admin.model.RepairOrder;
import com.system.admin.param.AddRepairOrderParam;
import com.system.admin.service.IRepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-06-06
 */
@Service
public class RepairOrderServiceImpl extends ServiceImpl<RepairOrderMapper, RepairOrder> implements IRepairOrderService {

    @Autowired
    RepairOrderMapper repairOrderMapper;
    @Autowired
    private OrderEquipmentFaultRelationMapper orderEquipmentFaultRelationMapper;
    @Override
    public boolean generateRepairOrder(AddRepairOrderParam addRepairOrderParam) {
        //TODO:没有处理错误情况
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setPersonId(addRepairOrderParam.getRepairPersonId());
        repairOrderMapper.insert(repairOrder);
        List<Integer> equipmentIds = addRepairOrderParam.getEquipmentIds();
        List<Integer> faultIds = addRepairOrderParam.getFaultIds();
        Integer orderId = repairOrder.getId();
        for(int i=0;i<equipmentIds.size();i++){
            OrderEquipmentFaultRelation orderEquipmentFaultRelation = new OrderEquipmentFaultRelation();
            orderEquipmentFaultRelation.setOrderId(orderId);
            orderEquipmentFaultRelation.setEquipmentId(equipmentIds.get(i));
            orderEquipmentFaultRelation.setFaultId(faultIds.get(i));
            orderEquipmentFaultRelationMapper.insert(orderEquipmentFaultRelation);
        }
        return true;
    }
}
