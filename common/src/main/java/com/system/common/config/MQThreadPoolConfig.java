package com.system.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class MQThreadPoolConfig {

    @Bean
    public Executor taskExecutor() {
        // 核心线程数
        int corePoolSize = 3;
        // 最大线程数
        int maximumPoolSize = 4;
        // 线程空闲时间（秒）
        long keepAliveTime = 60;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 工作队列容量
        int queueCapacity = 100;

        // 创建工作队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);

        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                new CustomThreadFactory(), // 自定义线程工厂（可选）
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

        return threadPoolExecutor;
    }

    // 自定义线程工厂（可选）
    private static class CustomThreadFactory implements ThreadFactory {
        private int counter = 0;
        private String prefix = "mq-thread-";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, prefix + counter++);
        }
    }
}
