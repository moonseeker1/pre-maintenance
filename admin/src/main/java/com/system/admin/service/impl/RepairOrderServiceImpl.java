package com.system.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.dao.EquipmentAndFaultDao;
import com.system.admin.mapper.EquipmentMapper;
import com.system.admin.mapper.OrderEquipmentFaultRelationMapper;
import com.system.admin.mapper.RepairOrderMapper;
import com.system.admin.mapper.RepairPersonMapper;
import com.system.admin.model.Equipment;
import com.system.admin.model.OrderEquipmentFaultRelation;
import com.system.admin.model.RepairOrder;
import com.system.admin.param.AddRepairOrderParam;
import com.system.admin.param.RepairOrderPageParam;
import com.system.admin.service.IRepairOrderService;
import com.system.admin.vo.RepairOrderPageVO;
import com.system.common.exception.Asserts;
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
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private RepairPersonMapper repairPersonMapper;
    @Override
    /**
     * 生成维修单
     */
    public boolean generateRepairOrder(AddRepairOrderParam addRepairOrderParam) {
        //TODO:没有处理错误情况
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setPersonId(addRepairOrderParam.getRepairPersonId());
        repairOrder.setPersonName(repairPersonMapper.selectById(addRepairOrderParam.getRepairPersonId()).getName());
        repairOrderMapper.insert(repairOrder);
        List<EquipmentAndFaultDao> list = addRepairOrderParam.getList();
        Integer orderId = repairOrder.getId();
        for(EquipmentAndFaultDao equipmentAndFaultDao: list){
            OrderEquipmentFaultRelation orderEquipmentFaultRelation = new OrderEquipmentFaultRelation();
            QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
            equipmentQueryWrapper.eq("id",equipmentAndFaultDao.getEquipmentId());
            Equipment equipment = equipmentMapper.selectOne(equipmentQueryWrapper);
            if(equipment.getState()!=0){
                Asserts.fail("设备状态不是正常状态");
            }
            equipment.setState(1);
            equipmentMapper.updateById(equipment);
            orderEquipmentFaultRelation.setEquipmentId(equipmentAndFaultDao.getEquipmentId());
            orderEquipmentFaultRelation.setFaultId(equipmentAndFaultDao.getFaultId());
            orderEquipmentFaultRelation.setOrderId(orderId);
            orderEquipmentFaultRelationMapper.insert(orderEquipmentFaultRelation);
        }
        return true;
    }

}
