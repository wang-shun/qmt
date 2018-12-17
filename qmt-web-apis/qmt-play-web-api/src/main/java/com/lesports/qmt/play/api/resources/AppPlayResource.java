package com.lesports.qmt.play.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.filter.CallerCheckFilter;
import com.lesports.model.LiveVideoReq;
import com.lesports.model.LiveVideoRes;
import com.lesports.model.VodReq;
import com.lesports.model.VodRes;
//import com.lesports.sms.api.web.core.service.VideoPlayerService;
//import com.lesports.sms.api.web.core.util.CheckSplatidUtils;
import com.lesports.qmt.web.api.core.service.VideoPlayerService;
import com.lesports.qmt.web.api.core.util.CheckSplatidUtils;
import com.lesports.utils.LeProperties;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * User: ellios
 * Time: 15-7-9 : 下午9:29
 */
@Path("/sms/app/v1/play")
public class AppPlayResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppPlayResource.class);

    @Inject
    private VideoPlayerService videoPlayerService;

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
            vodRes = videoPlayerService.dealVodWithFallback(vodReq);
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
    @Path("/live")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoRes playerLiveVideo(ContainerRequestContext containerRequestContext) {

        try {
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveVideoReq(liveVideoReq, uriInfo.getQueryParameters());
			//增加对platId 和 splatId 的校验
			LiveVideoRes liveVideoRes = new LiveVideoRes();
			Boolean checkLive = CheckSplatidUtils.checkLive(liveVideoReq.getSplatId(), liveVideoReq.getPlatId());
			if (!checkLive) {
				liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
				return liveVideoRes;
			}
            liveVideoRes = videoPlayerService.dealLiveVideoWithFallback(liveVideoReq);
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
//        if (StringUtils.isBlank(paramMap.getFirst("vid")) || StringUtils.isBlank(paramMap.getFirst("tss")) || StringUtils.isBlank(paramMap.getFirst("platid"))
//                || StringUtils.isBlank(paramMap.getFirst("splatid")) || StringUtils.isBlank(paramMap.getFirst("vtype"))) {
//            throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
//        }
        String deviceInfo = containerRequestContext.getHeaderString("DEVICE_INFO");
        String ip = CallerCheckFilter.getIpAddr(containerRequestContext);
        LOG.info("vodReq:{},deviceInfo:{},ip:{}", videoOnDemandReq, deviceInfo, ip);
        videoOnDemandReq.setClientip(ip);
        if (null != paramMap.get("uid")) {
            videoOnDemandReq.setUid(paramMap.getFirst("uid").toString());
        }
        if (null != paramMap.get("uname")) {
            videoOnDemandReq.setUname(paramMap.getFirst("uname").toString());
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


    private LiveVideoReq buildLiveVideoReq(LiveVideoReq liveVideoReq, MultivaluedMap<String, String> paramMap) {
//        if (StringUtils.isBlank(paramMap.getFirst("liveid")) || StringUtils.isBlank(paramMap.getFirst("splatid")) || StringUtils.isBlank(paramMap.getFirst("flag"))
//                || StringUtils.isBlank(paramMap.getFirst("platid")) || StringUtils.isBlank(paramMap.getFirst("hwtype")) || StringUtils.isBlank(paramMap.getFirst("ostype"))
//                || StringUtils.isBlank(paramMap.getFirst("termid"))) {
//            throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
//        }
        liveVideoReq.setFlag(paramMap.getFirst("flag").toString());
        liveVideoReq.setHwtype(paramMap.getFirst("hwtype").toString());
        liveVideoReq.setLiveId(paramMap.getFirst("liveid").toString());
        liveVideoReq.setOstype(paramMap.getFirst("ostype").toString());
        liveVideoReq.setPlatId(paramMap.getFirst("platid").toString());
        liveVideoReq.setSplatId(paramMap.getFirst("splatid").toString());
        liveVideoReq.setTermid(paramMap.getFirst("termid").toString());
        liveVideoReq.setFrom(paramMap.getFirst("from") != null ? paramMap.getFirst("from").toString() : null);
        Object uid = paramMap.get("uid");
        if (null != uid) {
            liveVideoReq.setUserId(uid.toString());
        }
        liveVideoReq.setWithCibnStreams("");
        return liveVideoReq;
    }
}
