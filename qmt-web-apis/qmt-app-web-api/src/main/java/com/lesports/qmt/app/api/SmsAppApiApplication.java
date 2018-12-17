package com.lesports.qmt.app.api;

import com.lesports.jersey.support.filter.LogCacheUrlResponseFilter;
import com.lesports.jersey.support.spring.LeSpringApplication;

/**
 * User: ellios
 * Time: 14-6-11 : 下午10:49
 */
public class SmsAppApiApplication extends LeSpringApplication {

    /**
     * Register JAX-RS application components.
     */
    public SmsAppApiApplication() {
        register(LogCacheUrlResponseFilter.class);
        packages("com.lesports.qmt.app.api.resources");
    }

}
