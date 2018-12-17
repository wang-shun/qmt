package com.lesports.qmt.ipad.api;

import com.lesports.jersey.support.filter.LogCacheUrlResponseFilter;
import com.lesports.jersey.support.spring.LeSpringApplication;

/**
 * User: ellios
 * Time: 14-6-11 : 下午10:49
 */
public class SmsIpadApiApplication extends LeSpringApplication {

    /**
     * Register JAX-RS application components.
     */
    public SmsIpadApiApplication() {
        register(LogCacheUrlResponseFilter.class);
        packages("com.lesports.qmt.ipad.api.resources");
    }

}
