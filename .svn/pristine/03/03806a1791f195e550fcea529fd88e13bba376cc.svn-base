package com.lesports.qmt.api;

import com.lesports.jersey.support.filter.LogCacheUrlResponseFilter;
import com.lesports.jersey.support.spring.LeSpringApplication;
import com.lesports.jersey.velocity.VelocityMvcFeature;

/**
 * User: ellios
 * Time: 14-6-11 : 下午10:49
 */
public class SmsApiApplication extends LeSpringApplication {

    /**
     * Register JAX-RS application components.
     */
    public SmsApiApplication() {
        packages("com.lesports.qmt.api.resources");

        register(LogCacheUrlResponseFilter.class);
        register(VelocityMvcFeature.class);
        property(VelocityMvcFeature.TEMPLATES_BASE_PATH, "pages");
    }

}
