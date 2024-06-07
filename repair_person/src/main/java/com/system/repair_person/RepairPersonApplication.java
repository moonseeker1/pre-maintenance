package com.system.repair_person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.system.repair_person", "com.system.common", "com.system.security"})
public class RepairPersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairPersonApplication.class, args);
    }

}
