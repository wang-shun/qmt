package com.lesports.qmt.play.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.filter.CallerCheckFilter;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.model.LiveVideoReq;
import com.lesports.model.LiveVideoRes;
import com.lesports.model.VodReq;
import com.lesports.model.VodRes;
//import com.lesports.sms.api.web.core.service.VideoPlayer2Service;
//import com.lesports.sms.api.web.core.service.ZhangyuTvPlayerService;
//import com.lesports.sms.api.web.core.util.CheckSplatidUtils;
import com.lesports.qmt.web.api.core.service.VideoPlayer2Service;
import com.lesports.qmt.web.api.core.service.ZhangyuTvPlayerService;
import com.lesports.qmt.web.api.core.util.CheckSplatidUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import static com.lesports.LeConstants.LESPORTS_TV_CIBN_CALLER_CODE;

/**
 * User: ellios
 * Time: 15-7-9 : 下午9:29
 */
@Path("/sms/app/v1/play2")
public class AppPlay2Resource {

    private static final Logger LOG = LoggerFactory.getLogger(AppPlay2Resource.class);

    @Inject
    private VideoPlayer2Service videoPlayer2Service;
    @Inject
    private ZhangyuTvPlayerService zhangyuTvPlayerService;

    private static final String VTYPE = "58,21,13,22";

    private static final boolean CONTAIN_1080P = LeProperties.getBoolean("vod.contain.1080p", false);

    @LJSONP
    @GET
    @Path("/vod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public VodRes playerVod(ContainerRequestContext containerRequestContext) {
        try {
            VodReq vodReq = new VodReq();
            buildVodParam(vodReq, containerRequestContext);
            VodRes vodRes = new VodRes();
            Boolean checkVod = CheckSplatidUtils.checkVod(vodReq.getSplatid() + "", vodReq.getPlatid() + "");
            if (!checkVod) {
                vodRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return vodRes;
            }
            vodRes = videoPlayer2Service.dealVodWithFallback(vodReq);
            LOG.info("vod response:{}", vodRes);
            return vodRes;
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
    @Path("/drmVod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public VodRes playerVod4Drm(@BeanParam CallerBean callerBean, ContainerRequestContext containerRequestContext) {
        try {
            VodRes vodRes = new VodRes();
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (paramMap.getFirst("vid") == null || paramMap.getFirst("tss") == null || paramMap.getFirst("platid") == null
                    || paramMap.getFirst("splatid") == null || paramMap.getFirst("dvtype") == null || paramMap.getFirst("country") == null
                    || paramMap.getFirst("activate") == null || paramMap.getFirst("from") == null) {
                vodRes.setStatus(PlayerErrorCode.PARAM_REQUIRED.getCode());
                return vodRes;
            }
            VodReq vodReq = new VodReq();
            buildVodParam4Drm(vodReq, containerRequestContext);
            Boolean checkVod = CheckSplatidUtils.checkVod(vodReq.getSplatid() + "", vodReq.getPlatid() + "");
            if (!checkVod) {
                vodRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return vodRes;
            }
            vodRes = videoPlayer2Service.dealVod4Drm(vodReq, callerBean.getCallerParam());
            LOG.debug("vod response:{}", vodRes);
            return vodRes;
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
    @Path("/drmLive")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoRes playerDrmLive(@BeanParam CallerBean callerBean, ContainerRequestContext containerRequestContext) {

        try {
            LiveVideoRes liveVideoRes = new LiveVideoRes();
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
            if (paramMap.getFirst("liveid") == null || paramMap.getFirst("hwtype") == null || paramMap.getFirst("ostype") == null
                    || paramMap.getFirst("splatid") == null || paramMap.getFirst("platid") == null || paramMap.getFirst("dvtype") == null
                    || paramMap.getFirst("country") == null || paramMap.getFirst("termid") == null || paramMap.getFirst("activate") == null) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_REQUIRED.getCode());
                return liveVideoRes;
            }
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveVideoReq(liveVideoReq, containerRequestContext);
            Boolean checkLive = CheckSplatidUtils.checkLive(liveVideoReq.getSplatId(), liveVideoReq.getPlatId());
            if (!checkLive) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return liveVideoRes;
            }
            if (liveVideoReq.getLiveId().startsWith("zhangyutv_")) {
                liveVideoRes = zhangyuTvPlayerService.dealZhangyuTvLive(liveVideoReq.getLiveId());
            } else {
                liveVideoRes = videoPlayer2Service.dealLive4Drm(liveVideoReq, callerBean.getCallerParam());
            }
            LOG.debug("liveVideoRes response:{}", liveVideoRes);
            return liveVideoRes;
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
    @Path("/live")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoRes playerLiveVideo(ContainerRequestContext containerRequestContext) {

        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveVideoReq(liveVideoReq, containerRequestContext);
            LiveVideoRes liveVideoRes = new LiveVideoRes();
            Boolean checkLive = CheckSplatidUtils.checkLive(liveVideoReq.getSplatId(), liveVideoReq.getPlatId());
            if (!checkLive) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return liveVideoRes;
            }
            liveVideoRes = videoPlayer2Service.dealLiveVideoWithFallback(liveVideoReq);
            LOG.info("liveVideoRes response:{}", liveVideoRes);
            return liveVideoRes;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private VodReq buildVodParam(VodReq videoOnDemandReq, ContainerRequestContext containerRequestContext) {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
        String deviceInfo = containerRequestContext.getHeaderString("DEVICE_INFO");
        String ip = CallerCheckFilter.getIpAddr(containerRequestContext);
        LOG.info("vodReq:{},deviceInfo:{},ip:{}", videoOnDemandReq, deviceInfo, ip);
        if (StringUtils.isNotBlank(ip)) {
            videoOnDemandReq.setClientip(ip);
        } else {
            videoOnDemandReq.setClientip("10.154.157.31");
        }
        if (null != paramMap.get("uid")) {
            videoOnDemandReq.setUid(paramMap.getFirst("uid").toString());
        }
		if (null != paramMap.get("flag")) {
			videoOnDemandReq.setFlag(paramMap.getFirst("flag").toString());
		}
        if (null != paramMap.get("uname")) {
            videoOnDemandReq.setUname(paramMap.getFirst("uname").toString());
        }
        if (null != paramMap.get("token")) {
            videoOnDemandReq.setToken(paramMap.getFirst("token").toString());
        }
        if (null != paramMap.get("devicekey")) {
            videoOnDemandReq.setDevicekey(paramMap.getFirst("devicekey").toString());
        }
        if (null != paramMap.get("terminal")) {
            videoOnDemandReq.setTerminal(paramMap.getFirst("terminal").toString());
        }
        videoOnDemandReq.setPlatid(Integer.parseInt(paramMap.getFirst("platid").toString()));
        videoOnDemandReq.setSplatid(Integer.parseInt(paramMap.getFirst("splatid").toString()));
        videoOnDemandReq.setTss(paramMap.getFirst("tss").toString());
        //暂时过滤掉mp4_720p，mp4_1080p3m码流
        String vTypeStr = paramMap.getFirst("vtype").toString();
        String[] vTypeArr = StringUtils.split(vTypeStr, ",");
        StringBuilder sb = new StringBuilder();
        for (String vType : vTypeArr) {
            if (VTYPE.contains(vType)) {
                sb.append(vType).append(",");
            }
        }
        if (CONTAIN_1080P) {
            sb.append("52,");
        }
        videoOnDemandReq.setVtype(sb.toString());
        videoOnDemandReq.setVid(paramMap.getFirst("vid").toString());
        videoOnDemandReq.setCaller(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));
        return videoOnDemandReq;
    }


    private VodReq buildVodParam4Drm(VodReq videoOnDemandReq, ContainerRequestContext containerRequestContext) {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
        String deviceInfo = containerRequestContext.getHeaderString("DEVICE_INFO");
        String ip = CallerCheckFilter.getIpAddr(containerRequestContext);
        LOG.info("vodReq:{},deviceInfo:{},ip:{}", videoOnDemandReq, deviceInfo, ip);
        if (StringUtils.isNotBlank(ip)) {
            videoOnDemandReq.setClientip(ip);
        } else {
            videoOnDemandReq.setClientip("10.154.157.31");
        }
        if (null != paramMap.get("uid")) {
            videoOnDemandReq.setUid(paramMap.getFirst("uid").toString());
        }
        if (null != paramMap.get("uname")) {
            videoOnDemandReq.setUname(paramMap.getFirst("uname").toString());
        }
        if (null != paramMap.get("ssotk")) {
            videoOnDemandReq.setToken(paramMap.getFirst("ssotk").toString());
        }
        if (null != paramMap.get("devicekey")) {
            videoOnDemandReq.setDevicekey(paramMap.getFirst("devicekey").toString());
        }
        if (null != paramMap.get("terminal")) {
            videoOnDemandReq.setTerminal(paramMap.getFirst("terminal").toString());
        }
        if (null != paramMap.get("activate")) {
            videoOnDemandReq.setActivate(Integer.parseInt(paramMap.getFirst("activate").toString()));
        }
        if (null != paramMap.get("from")) {
            videoOnDemandReq.setFrom(paramMap.getFirst("from").toString());
        }
        if (null != paramMap.get("highVtypePay")) {
            videoOnDemandReq.setHighVtypePay(Integer.parseInt(paramMap.getFirst("highVtypePay").toString()));
        }
		if (null != paramMap.get("flag")) {
			videoOnDemandReq.setFlag(paramMap.getFirst("flag").toString());
		}
        videoOnDemandReq.setDvtype(paramMap.getFirst("dvtype").toString());
        videoOnDemandReq.setPlatid(Integer.parseInt(paramMap.getFirst("platid").toString()));
        videoOnDemandReq.setSplatid(Integer.parseInt(paramMap.getFirst("splatid").toString()));
        videoOnDemandReq.setTss(paramMap.getFirst("tss").toString());
        videoOnDemandReq.setVid(paramMap.getFirst("vid").toString());
        videoOnDemandReq.setCaller(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));
		//国广防盗链使用
		if (videoOnDemandReq.getCaller().equals(LESPORTS_TV_CIBN_CALLER_CODE)) {
			videoOnDemandReq.setBc(2);
		}
        return videoOnDemandReq;
    }


    private LiveVideoReq buildLiveVideoReq(LiveVideoReq liveVideoReq, ContainerRequestContext containerRequestContext) {
        MultivaluedMap<String, String> paramMap = containerRequestContext.getUriInfo().getQueryParameters();
        liveVideoReq.setHwtype(paramMap.getFirst("hwtype").toString());
        liveVideoReq.setLiveId(paramMap.getFirst("liveid").toString());
        liveVideoReq.setOstype(paramMap.getFirst("ostype").toString());
        liveVideoReq.setPlatId(paramMap.getFirst("platid").toString());
        liveVideoReq.setSplatId(paramMap.getFirst("splatid").toString());
        liveVideoReq.setClientip(CallerCheckFilter.getIpAddr(containerRequestContext));
        //整合app 兼容参数
        if (null != paramMap.get("dvtype")) {
            liveVideoReq.setDvtype(paramMap.getFirst("dvtype").toString());
        }
        if (null != paramMap.get("activate")) {
            liveVideoReq.setActivate(Integer.parseInt(paramMap.getFirst("activate").toString()));
        }
        if (null != paramMap.get("uniqueFlag")) {
            liveVideoReq.setUniqueFlag(paramMap.getFirst("uniqueFlag").toString());
        }
        liveVideoReq.setTermid(paramMap.getFirst("termid") != null ? paramMap.getFirst("termid").toString() : null);
        liveVideoReq.setFlag(paramMap.getFirst("flag") != null ? paramMap.getFirst("flag").toString() : null);
        liveVideoReq.setFrom(paramMap.getFirst("from") != null ? paramMap.getFirst("from").toString() : null);
        liveVideoReq.setUserId(paramMap.getFirst("uid") != null ? paramMap.getFirst("uid").toString() : null);
        //用户ssotk,兼容大陆token
        if (paramMap.getFirst("token") != null) {
            liveVideoReq.setToken(paramMap.getFirst("token").toString());
        }
        if (paramMap.getFirst("ssotk") != null) {
            liveVideoReq.setToken(paramMap.getFirst("ssotk").toString());
        }
        liveVideoReq.setDevicekey(paramMap.getFirst("devicekey") != null ? paramMap.getFirst("devicekey").toString() : null);
        liveVideoReq.setTerminal(paramMap.getFirst("terminal") != null ? paramMap.getFirst("terminal").toString() : null);
        liveVideoReq.setCaller(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));
		//国广防盗链使用
//		if (LeNumberUtils.toLong(paramMap.getFirst("caller").toString()) == LESPORTS_TV_CIBN_CALLER_CODE) {
//			liveVideoReq.setWithCibnStreams("1");
//		} else {
			liveVideoReq.setWithCibnStreams("");
//		}
        return liveVideoReq;
    }
}
