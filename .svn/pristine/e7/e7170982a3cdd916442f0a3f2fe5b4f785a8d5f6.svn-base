package com.lesports.qmt.play.api.resources;

import com.lesports.api.common.CountryCode;
import com.lesports.exception.SsoTkErrorException;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.filter.CallerCheckFilter;
import com.lesports.model.*;
import com.lesports.qmt.web.api.core.service.ValidateService;
import com.lesports.qmt.web.api.core.util.PlayerConstants;
import com.lesports.utils.BossApi;
import com.lesports.utils.SSOApi;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

//import com.lesports.sms.api.web.core.service.ValidateService;
//import com.lesports.sms.api.web.core.util.PlayerConstants;

/**
 * 鉴权
 * Created by lufei1 on 2015/7/7.
 */
@Path("/sms/v1/play")
public class ValidateResource {

    private static final Logger LOG = LoggerFactory.getLogger(ValidateResource.class);

    @Inject
    private ValidateService validateService;

    @LJSONP
    @GET
    @Path("/liveValidate")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveValidateResponse liveSportValidate(ContainerRequestContext containerRequestContext) {
        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (StringUtils.isBlank(paramMap.getFirst("liveId")) || StringUtils.isBlank(paramMap.getFirst("from"))
                    || StringUtils.isBlank(paramMap.getFirst("streamId")) || StringUtils.isBlank(paramMap.getFirst("splatId"))
                    || StringUtils.isBlank(paramMap.getFirst("liveBeginTime"))) {
                throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
            }
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveValidateParam(liveVideoReq, containerRequestContext);
            Map<String, Cookie> cookieMap = containerRequestContext.getCookies();
            Cookie userCookie = cookieMap.get("ssouid");
            String uid = "";
            if (userCookie != null) {
                uid = userCookie.getValue();
            }
            LiveRoom liveRoom = new LiveRoom();
            liveRoom.setBeginTime(paramMap.getFirst("liveBeginTime"));
            return BossApi.authLiveValidate(liveVideoReq, liveRoom, paramMap.getFirst("streamId"), uid);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @LJSONP
    @GET
    @Path("/validate/drmVod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object validateVod4Drm(ContainerRequestContext containerRequestContext) {
        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (StringUtils.isBlank(paramMap.getFirst("country")) || StringUtils.isBlank(paramMap.getFirst("from"))
                    || StringUtils.isBlank(paramMap.getFirst("storepath"))
                    || StringUtils.isBlank(paramMap.getFirst("vid")) || StringUtils.isBlank(paramMap.getFirst("isDrm"))
                    || ("1".equals(paramMap.getFirst("isDrm")) && StringUtils.isBlank(paramMap.getFirst("encodeId")))) {
                throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
            }
            ValidateReq validateReq = new ValidateReq();
            buildValidateReq(validateReq, containerRequestContext);
            return validateService.authVodForDrm(validateReq);
        } catch (SsoTkErrorException e) {
            return new ValidateRes(0, PlayerErrorCode.BUSI_USER_TOKEN_ERROR.getCode());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @LJSONP
    @GET
    @Path("/validate/drmLive")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object validateLive4Drm(ContainerRequestContext containerRequestContext) {
        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (StringUtils.isBlank(paramMap.getFirst("country")) || StringUtils.isBlank(paramMap.getFirst("from"))
                    || StringUtils.isBlank(paramMap.getFirst("streamName")) || StringUtils.isBlank(paramMap.getFirst("splatId"))
                    || StringUtils.isBlank(paramMap.getFirst("liveId")) || StringUtils.isBlank(paramMap.getFirst("isDrm"))
                    || ("1".equals(paramMap.getFirst("isDrm")) && StringUtils.isBlank(paramMap.getFirst("encodeId")))) {
                throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
            }
            ValidateReq validateReq = new ValidateReq();
            buildValidateReq(validateReq, containerRequestContext);
            return validateService.authLiveForDrm(validateReq);
        } catch (SsoTkErrorException e) {
            return new ValidateRes(0, PlayerErrorCode.BUSI_USER_TOKEN_ERROR.getCode());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @LJSONP
    @GET
    @Path("/validate/carousel")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Object validateCarousel(ContainerRequestContext containerRequestContext) {
        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (StringUtils.isBlank(paramMap.getFirst("country")) || StringUtils.isBlank(paramMap.getFirst("from"))
                    || StringUtils.isBlank(paramMap.getFirst("channelId"))
                    || StringUtils.isBlank(paramMap.getFirst("streamName")) || StringUtils.isBlank(paramMap.getFirst("splatId"))
                    || StringUtils.isBlank(paramMap.getFirst("isDrm"))
                    || ("1".equals(paramMap.getFirst("isDrm")) && StringUtils.isBlank(paramMap.getFirst("encodeId")))) {
                throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
            }
            ValidateReq validateReq = new ValidateReq();
            buildValidateReq(validateReq, containerRequestContext);
            return validateService.authCarouselForDrm(validateReq);
        } catch (SsoTkErrorException e) {
            return new ValidateRes(0, PlayerErrorCode.BUSI_USER_TOKEN_ERROR.getCode());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void buildLiveValidateParam(LiveVideoReq liveVideoReq, ContainerRequestContext containerRequestContext) {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
        liveVideoReq.setLiveId(paramMap.getFirst("liveId").toString());
        liveVideoReq.setTerminal(paramMap.getFirst("from").toString());
        liveVideoReq.setSplatId(paramMap.getFirst("splatId").toString());
        if (null != paramMap.get("uniqueFlag")) {
            liveVideoReq.setUniqueFlag(paramMap.getFirst("uniqueFlag").toString());
        }
    }


    private void buildValidateReq(ValidateReq validateReq, ContainerRequestContext containerRequestContext) {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
        if (null != paramMap.getFirst("activate")) {
            validateReq.setActivate(Integer.valueOf(paramMap.getFirst("activate").toString()));
        }
        validateReq.setCountry(getCountry(paramMap));
        validateReq.setCallerId(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));

        if (null != paramMap.getFirst("encodeId")) {
            validateReq.setEncodeId(paramMap.getFirst("encodeId").toString());
        }
        //兼容老的terminal参数
        if (PlayerConstants.LIVE_TERMINAL_MAP.containsKey(paramMap.getFirst("from").toString())) {
            validateReq.setFrom(PlayerConstants.LIVE_TERMINAL_MAP.get(paramMap.getFirst("from").toString()));
        } else {
            validateReq.setFrom(paramMap.getFirst("from").toString());
        }

        String uid = "";
        if (StringUtils.isNotBlank(paramMap.getFirst("ssotk"))) {
            try {
                uid = SSOApi.checkToken(paramMap.getFirst("ssotk").toString());
            } catch (Exception e) {
                LOG.error("get user info error. token : {}", validateReq.getToken(), e);
            }
            if (validateReq.getCallerId() != 1003 && validateReq.getCallerId() != 1014 && validateReq.getCallerId() != 1016
                    && validateReq.getCallerId() != 1051 && StringUtils.isEmpty(uid)) {
                throw new SsoTkErrorException("Fail to get user info by ssotk " + paramMap.getFirst("ssotk").toString());
            }
        } else {
            Map<String, Cookie> cookieMap = containerRequestContext.getCookies();
            Cookie userCookie = cookieMap.get("ssouid");
            if (userCookie != null) {
                uid = userCookie.getValue();
            } else {
                LOG.warn("cannot get uid from cookie for ssouid ");
            }
        }
        if ((validateReq.getCallerId() != 1003 && validateReq.getCallerId() != 1014 && validateReq.getCallerId() != 1016 && validateReq.getCallerId() != 1051)
                && StringUtils.isBlank(uid)) {
            throw new LeWebApplicationException("User fail login", LeStatus.PARAM_INVALID);
        }

        validateReq.setIsDrm(Integer.valueOf(paramMap.getFirst("isDrm").toString()));
        validateReq.setUserId(uid);
        if (StringUtils.isNotBlank(paramMap.getFirst("storepath"))) {
            validateReq.setStorepath(paramMap.getFirst("storepath").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("screenings"))) {
            validateReq.setScreenings(paramMap.getFirst("screenings").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("liveId"))) {
            validateReq.setLiveId(paramMap.getFirst("liveId").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("streamName"))) {
            validateReq.setStreamId(paramMap.getFirst("streamName").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("splatId"))) {
            validateReq.setSplatId(paramMap.getFirst("splatId").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("vid"))) {
            validateReq.setVid(paramMap.getFirst("vid").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("channelId"))) {
            validateReq.setChannelId(paramMap.getFirst("channelId").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("deviceId"))) {
            validateReq.setDeviceId(paramMap.getFirst("deviceId").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("mac"))) {
            validateReq.setMac(paramMap.getFirst("mac").toString());
        }
        if (StringUtils.isNotBlank(paramMap.getFirst("deviceKey"))) {
            validateReq.setDeviceKey(paramMap.getFirst("deviceKey").toString());
        }
        String ip = CallerCheckFilter.getIpAddr(containerRequestContext);
        validateReq.setClientIp(ip);
    }

    private String getCountry(MultivaluedMap<String, String> paramMap) {
        try {
            return String.valueOf(PlayerConstants.countryMap.get(
                    CountryCode.valueOf(paramMap.getFirst("country").toString())));
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }


}
