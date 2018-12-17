package com.lesports.qmt.play.api.resources;

import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.annotation.NoEnvelopeResponse;
import com.lesports.jersey.support.filter.CallerCheckFilter;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.model.LiveVideoResPC;
import com.lesports.model.VodReq;
//import com.lesports.sms.api.web.core.service.VideoPlayer2Service;
//import com.lesports.sms.api.web.core.vo.PcPlayVo;
import com.lesports.qmt.web.api.core.service.VideoPlayer2Service;
import com.lesports.qmt.web.api.core.vo.PcPlayVo;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * Created by lufei1 on 2016/5/26.
 */
@Path("/sms/v1/play")
public class PcPlayResource {

    private static final Logger LOG = LoggerFactory.getLogger(PcPlayResource.class);

    @Inject
    private VideoPlayer2Service videoPlayer2Service;

    @LJSONP
    @GET
    @Path("/live")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoResPC playerLiveVideo(@QueryParam("liveid") String liveId,
                                          @QueryParam("splatid") String splatId,
                                          @BeanParam CallerBean callerBean) {
        try {
            return videoPlayer2Service.dealLive4PC(liveId, splatId, callerBean.getCallerParam());
        } catch (Exception e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/vod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public PcPlayVo playerVod(@BeanParam CallerBean callerBean, ContainerRequestContext containerRequestContext) {
        return pcPlayVo(callerBean, containerRequestContext);
    }

    @LJSONP
    @GET
    @NoEnvelopeResponse
    @Path("/time")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map time() {
        Map<String, Long> result = Maps.newHashMap();
        result.put("stime", System.currentTimeMillis() / 1000);
        return result;
    }

    @LJSONP
    @GET
    @Path("/hkVod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public PcPlayVo playerHkVod(@BeanParam CallerBean callerBean, ContainerRequestContext containerRequestContext) {
        return pcPlayVo(callerBean, containerRequestContext);
    }

    private PcPlayVo pcPlayVo(CallerBean callerBean, ContainerRequestContext containerRequestContext) {
        try {
            VodReq vodReq = new VodReq();
            PcPlayVo pcPlayVo = new PcPlayVo();
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (paramMap.getFirst("vid") == null || paramMap.getFirst("platid") == null || paramMap.getFirst("splatid") == null
                    || paramMap.getFirst("dvtype") == null) {
                pcPlayVo.setStatuscode(PlayerErrorCode.PARAM_REQUIRED.getCode());
                return pcPlayVo;
            }
            buildVodParam4Pc(vodReq, paramMap, containerRequestContext);
            return videoPlayer2Service.dealVod4PcDrm(vodReq, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void buildVodParam4Pc(VodReq vodReq, MultivaluedMap<String, String> paramMap, ContainerRequestContext containerRequestContext) {
        vodReq.setPlatid(Integer.parseInt(paramMap.getFirst("platid").toString()));
        vodReq.setSplatid(Integer.parseInt(paramMap.getFirst("splatid").toString()));
        vodReq.setVid(paramMap.getFirst("vid").toString());
        vodReq.setDvtype(paramMap.getFirst("dvtype").toString());
        vodReq.setCaller(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));
        if (paramMap.getFirst("activate") != null) {
            vodReq.setActivate(Integer.parseInt(paramMap.getFirst("activate").toString()));
        }

        LOG.info("headers : {}", containerRequestContext.getHeaders());
        String ip = CallerCheckFilter.getIpAddr(containerRequestContext);
        if (StringUtils.isNotBlank(ip)) {
            vodReq.setClientip(ip);
        }
        Map<String, Cookie> cookieMap = containerRequestContext.getCookies();
        Cookie userCookie = cookieMap.get("ssouid");
        String uid = "";
        if (userCookie != null) {
            uid = userCookie.getValue();
        } else {
            if (paramMap.getFirst("uid") != null) {
                uid = paramMap.getFirst("uid").toString();
            }
        }
//        if (StringUtils.isBlank(uid)) {
//            throw new LeWebApplicationException("User fail login", LeStatus.PARAM_INVALID);
//        }

        vodReq.setUid(uid);
        vodReq.setTss("ios");
        vodReq.setTerminal("141001");
        //默认drm码流
        vodReq.setVtype("13,21,22,51,188,189,190,191,192,193,194");
    }


}
