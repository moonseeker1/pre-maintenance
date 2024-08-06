package com.system.admin.component;

import com.system.admin.mapper.EquipmentMapper;
import com.system.admin.model.Equipment;
import com.system.admin.model.PreserveEquipmentRelation;
import com.system.admin.model.PreserveOrder;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IPreserveEquipmentRelationService;
import com.system.admin.service.IPreserveOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Component
public class GeneratePreOrderReceiver {
    @Autowired
    EquipmentMapper equipmentMapper;
    @Autowired
    IEquipmentService equipmentService;
    @Autowired
    IPreserveOrderService preserveOrderService;
    @Autowired
    IPreserveEquipmentRelationService preserveEquipmentRelationService;
    @RabbitListener(queues = "preOrder.queue", containerFactory = "rabbitListenerContainerFactory")
    @Transactional
    public void receiveMessage(List<Integer> list) {
        // 处理消息的逻辑
        if(!list.isEmpty()){
            List<Equipment> equipmentList = new ArrayList<>();
            List<PreserveEquipmentRelation> preserveEquipmentRelations = new ArrayList<>();
            Integer orderId = 0;
            int count = 0;
            for(Integer id :list){
                Equipment equipment1 = equipmentService.getById(id);
                if(equipment1!=null&&equipment1.getState()==0){
                    Equipment equipment = new Equipment();
                    equipment.setState(2);
                    equipment.setId(id);
                    equipmentList.add(equipment);
                    if(count%3==0){
                        PreserveOrder preserveOrder = new PreserveOrder();
                        preserveOrderService.save(preserveOrder);
                        orderId = preserveOrder.getId();
                    }
                    PreserveEquipmentRelation preserveEquipmentRelation = new PreserveEquipmentRelation();
                    preserveEquipmentRelation.setEquipmentId(id);
                    preserveEquipmentRelation.setOrderId(orderId);
                    preserveEquipmentRelations.add(preserveEquipmentRelation);
                }
                count++;
            }
            equipmentService.updateBatchById(equipmentList);
            preserveEquipmentRelationService.saveBatch(preserveEquipmentRelations);
        }
    }
}
