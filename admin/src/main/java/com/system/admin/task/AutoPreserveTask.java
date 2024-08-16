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

    private final RateLimiter rateLimiter = RateLimiter.create(300);
    private static boolean running = true;

    @Scheduled(cron = "0 */1 * * * ?")

    public void executeTask() {
        if(running) {
            running = false;
            Instant start = Instant.now();
            LocalDateTime now = LocalDateTime.now();
            List<Integer> list = equipmentMapper.selectIdByStatus(now);
            List<Integer> batchList = new ArrayList<>();
            int count = 0;

            for (int id : list) {
                if (count < 12) {
                    batchList.add(id);
                    count++;
                } else {
                    rateLimiter.acquire(); // 限流
                    generatePreOrderSender.sendPreOrder(batchList);
                    count = 0;
                    batchList = new ArrayList<>();
                    batchList.add(id);
                    count++;
                }
            }

            if (!batchList.isEmpty()) {
                rateLimiter.acquire(); // 限流
                generatePreOrderSender.sendPreOrder(batchList);
            }

            log.info("Task executed in {} ms", Instant.now().toEpochMilli() - start.toEpochMilli());
        }
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
