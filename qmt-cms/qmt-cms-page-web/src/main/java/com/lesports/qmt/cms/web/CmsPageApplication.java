package com.lesports.qmt.cms.web;

import com.baidu.fis.servlet.MapListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * 配置启动入口
 *
 * @author jiangbo
 */
@SpringBootApplication(scanBasePackages = {"com.lesports"},exclude = {VelocityAutoConfiguration.class,MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource("classpath:template-servlet.xml")
public class CmsPageApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CmsPageApplication.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean<MapListener> fisConfigListener(){
        ServletListenerRegistrationBean<MapListener> fisConfigListener = new ServletListenerRegistrationBean<>(new MapListener());
        return fisConfigListener;
    }
}
