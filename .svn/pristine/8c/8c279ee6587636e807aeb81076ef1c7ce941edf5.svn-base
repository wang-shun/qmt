package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.model.Online;
import com.lesports.utils.CibnAuthApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.Arrays;

/**
 * Created by ruiyuansheng on 2016/6/15.
 */
@Path("/api")
public class AuthResource {

    private static final Logger LOG = LoggerFactory.getLogger(AuthResource.class);
    /**
     * 获取自制节目专辑
     *
     * @return
     */
    @LJSONP
    @GET
    @Path("/auth")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Online getAuth (@QueryParam("type") @DefaultValue("1") int type,
                                        @QueryParam("recordmode") @DefaultValue("1") int recordmode,
                                        @QueryParam("addr") String addr,
                                        @BeanParam CallerBean callerBean){

        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(addr), "设备MAC地址或唯一设备号不能为空");
            Online online =  CibnAuthApis.getAuth(addr, type, recordmode);
            if(null != online) {
                LOG.info("deviceId:{},code:{}", online.getDeviceId(),online.getResultCode());
            }

            return online;
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
