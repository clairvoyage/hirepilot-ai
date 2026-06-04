package com.hirepilot.employer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hirepilot.employer", "com.hirepilot.shared"})
public class EmployerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployerApplication.class, args);
    }
}
