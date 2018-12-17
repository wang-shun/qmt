package com.lesports.qmt.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * User: ellios
 * Time: 16-10-16 : 下午8:30
 */
@SpringBootApplication(scanBasePackages = {"com.lesports.qmt"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource("classpath:xml/applicationContext-mq.xml")
public class MsgCenterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MsgCenterApplication.class, args);
    }
}
