package com.system.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("pre");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/pre");
        // 设置连接超时时间（毫秒）
        connectionFactory.setConnectionTimeout(30000);
        // 设置心跳时间（秒）
        connectionFactory.setRequestedHeartBeat(60);
        // 设置最大连接数
        connectionFactory.setConnectionLimit(4);
        // 设置每个连接最大频道数
        connectionFactory.setChannelCacheSize(10);
        return connectionFactory;
    }

    @Bean
    public Queue queue() {
        return new Queue("preOrder.queue", true);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory,
            Executor taskExecutor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setTaskExecutor(taskExecutor); // 使用自定义的线程池
        factory.setConcurrentConsumers(6); // 设置并发消费者数量
        factory.setMaxConcurrentConsumers(10); // 设置最大并发消费者数量
        factory.setMessageConverter(new Jackson2JsonMessageConverter()); // 配置消息转换器
        return factory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter()); // 配置消息转换器
        return template;
    }
}
