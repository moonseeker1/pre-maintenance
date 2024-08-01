package com.system.admin.component;

import com.system.admin.mapper.EquipmentMapper;
import com.system.admin.mapper.PreserveEquipmentRelationMapper;
import com.system.admin.model.Equipment;
import com.system.admin.model.PreserveEquipmentRelation;
import com.system.admin.model.PreserveOrder;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IPreserveOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @RabbitListener(queues = "preOrder.queue", containerFactory = "rabbitListenerContainerFactory")
    @Transactional
    public void receiveMessage(List<Integer> list) {
        // 处理消息的逻辑
        if(!list.isEmpty()){
            for(Integer id :list){
                Equipment equipment1 = equipmentService.getById(id);
                if(equipment1!=null&&equipment1.getState()==0){
                    Equipment equipment = new Equipment();
                    equipment.setState(2);
                    equipment.setId(id);
                    equipmentMapper.updateById(equipment);
                    PreserveOrder preserveOrder = new PreserveOrder();
                    preserveOrderService.save(preserveOrder);
                    Integer orderId = preserveOrder.getId();
                    PreserveEquipmentRelation preserveEquipmentRelation = new PreserveEquipmentRelation();
                    preserveEquipmentRelation.setEquipmentId(id);
                    preserveEquipmentRelation.setOrderId(orderId);
                    preserveEquipmentRelationMapper.insert(preserveEquipmentRelation);
                }
            }
        }
    }
}
