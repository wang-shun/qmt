package com.lesports.qmt.cms.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * 配置启动入口
 *
 * @author jiangbo
 */
@SpringBootApplication(scanBasePackages = {"com.lesports"}, exclude = {MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        RabbitAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class CmsAdminWebApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CmsAdminWebApiApplication.class, args);
    }
}
