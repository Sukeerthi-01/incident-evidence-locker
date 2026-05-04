
package com.internship.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "com.internship.tool.controller"   // ONLY controllers
    }
)
public class IncidentEvidenceLockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentEvidenceLockerApplication.class, args);
    }
}