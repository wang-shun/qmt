package com.lesports.qmt.tlive.api;

import com.lesports.jersey.support.filter.LogCacheUrlResponseFilter;
import com.lesports.jersey.support.filter.ParamCacheUrlResponseFilter;
import com.lesports.jersey.support.spring.LeSpringApplication;
import com.lesports.jersey.velocity.VelocityMvcFeature;

/**
 * User: ellios
 * Time: 14-6-11 : 下午10:49
 */
public class TLiveApiApplication extends LeSpringApplication {

    /**
     * Register JAX-RS application components.
     */
    public TLiveApiApplication() {
        packages("com.lesports.qmt.tlive.api.resources");

        register(LogCacheUrlResponseFilter.class);
        register(ParamCacheUrlResponseFilter.class);
        register(VelocityMvcFeature.class);
        property(VelocityMvcFeature.TEMPLATES_BASE_PATH, "pages");
    }

}
