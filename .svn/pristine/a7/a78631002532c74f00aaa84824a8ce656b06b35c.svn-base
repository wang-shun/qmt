package com.lesports.qmt.op.web.api.resources;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ENCRYPT;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.annotation.NoEnvelopeResponse;
import com.lesports.qmt.op.web.api.core.service.PinZhuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Created by zhonglin on 2016/7/13.
 */
@Path("/")
public class PinZhuanResource {

    private static final Logger LOG = LoggerFactory.getLogger(PinZhuanResource.class);

    @Inject
    private PinZhuanService pinZhuanService;

    /**
     * 百度中超品专焦点
     */
    @NoEnvelopeResponse
    @GET
    @LJSONP
    @ENCRYPT
    @Path("pinzhuan/focus")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object  getFocus(@QueryParam("caller") long callerId) {
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            return pinZhuanService.getPinZhuanFocus(callerParam) ;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 百度中超品专热点
     */
    @NoEnvelopeResponse
    @GET
    @LJSONP
    @ENCRYPT
    @Path("pinzhuan/hot")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object  getCslPic(@QueryParam("caller") long callerId) {
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            return pinZhuanService.getPinZhuanHot(callerParam) ;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 百度中超品专自制节目
     */
    @NoEnvelopeResponse
    @GET
    @LJSONP
    @ENCRYPT
    @Path("pinzhuan/program")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object  getCslAdv(@QueryParam("caller") long callerId) {
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            return pinZhuanService.getPinZhuanProgram(callerParam) ;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
