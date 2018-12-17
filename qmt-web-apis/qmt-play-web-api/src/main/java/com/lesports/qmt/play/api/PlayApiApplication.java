package com.lesports.qmt.play.api;

import com.lesports.jersey.support.spring.LeSpringApplication;
import com.lesports.utils.IpLocationCheckUtils;

/**
 * User: ellios
 * Time: 14-6-11 : 下午10:49
 */
public class PlayApiApplication extends LeSpringApplication {

    /**
     * Register JAX-RS application components.
     */
    public PlayApiApplication() {
        packages("com.lesports.qmt.play.api.resources");
        IpLocationCheckUtils.initialize();
    }

}
