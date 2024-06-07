package com.system.admin.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.admin.mapper.PreserveEquipmentRelationMapper;
import com.system.admin.model.Equipment;
import com.system.admin.model.PreserveEquipmentRelation;
import com.system.admin.model.PreserveOrder;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IPreserveOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AutoPreserveTask {
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IPreserveOrderService preserveOrderService;
    @Autowired
    private PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @Scheduled(cron = "0 */1 * * * * ?")
    @Transactional
    public void executeTask(){
        QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
        equipmentQueryWrapper.eq("status", 0);
        List<Equipment> list = equipmentService.list(equipmentQueryWrapper);
        int count = 0;
        List<Equipment> equipmentList = new ArrayList<>();
        for (Equipment equipment : list) {
            if(count==3){
                PreserveOrder preserveOrder = new PreserveOrder();
                preserveOrderService.save(preserveOrder);
                Integer orderId = preserveOrder.getId();
                for(Equipment equipment1:equipmentList){
                    equipment1.setState(2);
                    equipmentService.updateById(equipment1);
                    PreserveEquipmentRelation preserveEquipmentRelation = new PreserveEquipmentRelation();
                    preserveEquipmentRelation.setEquipmentId(equipment1.getId());
                    preserveEquipmentRelation.setOrderId(orderId);
                    preserveEquipmentRelationMapper.insert(preserveEquipmentRelation);
                }
                count=0;
                equipmentList.clear();
            }
            Integer preserveCycle = equipment.getPreserveCycle();
            LocalDateTime lastPreserveTime = equipment.getLastPreserveTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(lastPreserveTime.plusDays(preserveCycle))) {
                equipmentList.add(equipment);
                count++;
            }
        }
        if(count!=0){
            PreserveOrder preserveOrder = new PreserveOrder();
            preserveOrderService.save(preserveOrder);
            Integer orderId = preserveOrder.getId();
            for(Equipment equipment1:equipmentList){
                equipment1.setState(2);
                equipmentService.updateById(equipment1);
                PreserveEquipmentRelation preserveEquipmentRelation = new PreserveEquipmentRelation();
                preserveEquipmentRelation.setEquipmentId(equipment1.getId());
                preserveEquipmentRelation.setOrderId(orderId);
                preserveEquipmentRelationMapper.insert(preserveEquipmentRelation);
            }
        }

    }
}
