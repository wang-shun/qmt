package com.lesports.qmt.sbc.web.conf;

import com.letv.urus.liveroom.api.sports.LiveRoomSportsQueryAPI;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * create by wangjichuan  date:16-11-11 time:15:59
 */
@Configuration
public class LiveHessianConfiguration {
    @Value("${hessian.server}")
    private String hessianServer;
    @Bean
    public HessianProxyFactoryBean liveRoomSportsQueryAPI(){
        HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        hessianProxyFactoryBean.setServiceUrl("http://"+hessianServer+"/hessian/LiveRoomSportsQueryAPI");
        hessianProxyFactoryBean.setServiceInterface(LiveRoomSportsQueryAPI.class);
        return hessianProxyFactoryBean;
    }
    @Bean
    public HessianProxyFactoryBean liveRoomSportsWriterAPI(){
        HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        hessianProxyFactoryBean.setServiceUrl("http://"+hessianServer+"/hessian/LiveRoomSportsWriterAPI");
        hessianProxyFactoryBean.setServiceInterface(LiveRoomSportsWriterAPI.class);
        return hessianProxyFactoryBean;
    }
}
