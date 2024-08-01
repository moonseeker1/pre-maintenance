package com.system.admin.task;

import com.google.common.util.concurrent.RateLimiter;
import com.system.admin.component.GeneratePreOrderSender;
import com.system.admin.mapper.EquipmentMapper;
import com.system.admin.mapper.PreserveEquipmentRelationMapper;
import com.system.admin.service.IEquipmentService;
import com.system.admin.service.IPreserveOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AutoPreserveTask {
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IPreserveOrderService preserveOrderService;
    @Autowired
    private PreserveEquipmentRelationMapper preserveEquipmentRelationMapper;
    @Autowired
    private GeneratePreOrderSender generatePreOrderSender;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Scheduled(cron = "0 */10 * * * ?")
    public void executeTask(){
        while (true) {
            Instant start = Instant.now();
            LocalDateTime now = LocalDateTime.now();
            List<Integer> list = equipmentMapper.selectIdByStatus(now);
            int count = 0;
            RateLimiter rateLimiter = RateLimiter.create(150);
            List<Integer> batchList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (count < 3) {
                    batchList.add(list.get(i));
                    count++;
                } else {
                    // 每秒500个请求

                    rateLimiter.acquire(); // 这将阻塞，直到可以发送消息
                    // 发送消息逻辑
                    generatePreOrderSender.sendPreOrder(batchList);
                    batchList = new ArrayList<>();
                    batchList.add(list.get(i));
                    count++;

                }
            }
            generatePreOrderSender.sendPreOrder(batchList);


//        List<Equipment> equipmentList = new ArrayList<>();
//        for (Equipment equipment : list) {

//            if(count==3){
//                PreserveOrder preserveOrder = new PreserveOrder();
//                preserveOrderService.save(preserveOrder);
//                Integer orderId = preserveOrder.getId();
//                for(Equipment equipment1:equipmentList){
//                    equipment1.setState(2);
//                    equipmentService.updateById(equipment1);
//                    PreserveEquipmentRelation preserveEquipmentRelation = new PreserveEquipmentRelation();
//                    preserveEquipmentRelation.setEquipmentId(equipment1.getId());
//                    preserveEquipmentRelation.setOrderId(orderId);
//                    preserveEquipmentRelationMapper.insert(preserveEquipmentRelation);
//                }
//                count=0;
//                equipmentList.clear();
//            }
//            Integer preserveCycle = equipment.getPreserveCycle();
//            LocalDateTime lastPreserveTime = equipment.getLastPreserveTime();
//            LocalDateTime now = LocalDateTime.now();
//            if (now.isAfter(lastPreserveTime.plusMinutes(preserveCycle))) {
//                equipmentList.add(equipment);
//                count++;
//            }
//        }
//
//
        }
    }
}
