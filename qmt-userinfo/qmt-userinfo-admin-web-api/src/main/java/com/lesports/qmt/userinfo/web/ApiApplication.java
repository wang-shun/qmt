package com.lesports.qmt.userinfo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * User: ellios
 * Time: 16-10-16 : 下午8:30
 */
@SpringBootApplication(scanBasePackages = {"com.lesports"}, exclude = {DataSourceAutoConfiguration.class,
        RabbitAutoConfiguration.class})
@ImportResource("classpath:META-INF/spring/*.xml")
public class ApiApplication  {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiApplication.class, args);
    }
}
