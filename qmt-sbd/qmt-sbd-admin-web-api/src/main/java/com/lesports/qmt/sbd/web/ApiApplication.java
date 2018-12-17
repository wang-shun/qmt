package com.lesports.qmt.sbd.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: ellios
 * Time: 16-10-16 : 下午8:30
 */
@SpringBootApplication(scanBasePackages = {"com.lesports"})
public class ApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiApplication.class, args);
    }
}
