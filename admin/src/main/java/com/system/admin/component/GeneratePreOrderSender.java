package com.system.admin.component;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GeneratePreOrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendPreOrder(List<Integer> list) {
        // 队列名称
        String queueName = "preOrder.queue";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, list);
    }
}
