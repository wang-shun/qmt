package com.lesports.qmt.transcode.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * User: ellios
 * Time: 16-10-16 : 下午8:30
 */
@SpringBootApplication(scanBasePackages = {"com.lesports"},
        exclude = {MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                RabbitAutoConfiguration.class})
@ImportResource("classpath:META-INF/spring/application*.xml")
public class ApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiApplication.class, args);
    }
}
