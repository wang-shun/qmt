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
//import com.lesports.sms.api.web.core.service.VideoPlayerService;
//import com.lesports.sms.api.web.core.util.CheckSplatidUtils;
//import com.lesports.sms.model.Caller;
import com.lesports.qmt.web.api.core.service.VideoPlayerService;
import com.lesports.qmt.web.api.core.util.CheckSplatidUtils;
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
import static com.lesports.LeConstants.*;
/**
 * User: lufei
 * Time: 15-7-9 : 下午9:29
 */
@Path("/sms/tv/v1")
public class TvPlayResource {

    private static final Logger LOG = LoggerFactory.getLogger(TvPlayResource.class);

    @Inject
    private VideoPlayerService videoPlayerService;
    private static final String TVVTYPE = "51,53,21,13,22";


    @LJSONP
    @GET
    @Path("/play/vod")
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
            return videoPlayerService.dealVod(vodReq);
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
    @Path("/hkPlay/vod")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public VodRes playerVod4Hk(ContainerRequestContext containerRequestContext, @BeanParam CallerBean callerBean) {
        try {
            VodReq vodReq = new VodReq();
            buildVodParam(vodReq, containerRequestContext);
            VodRes vodRes = new VodRes();
            Boolean checkVod = CheckSplatidUtils.checkVod(vodReq.getSplatid() + "", vodReq.getPlatid() + "");
            if (!checkVod) {
                vodRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return vodRes;
            }
            return videoPlayerService.dealVod(vodReq);
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
    @Path("/play/live")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoRes playerLiveVideo(ContainerRequestContext containerRequestContext) {

        try {
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveVideoReq(liveVideoReq, containerRequestContext);
            //增加对platId 和 splatId 的校验
            LiveVideoRes liveVideoRes = new LiveVideoRes();
            Boolean checkLive = CheckSplatidUtils.checkLive(liveVideoReq.getSplatId(), liveVideoReq.getPlatId());
            if (!checkLive) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return liveVideoRes;
            }
            return videoPlayerService.dealLiveVideo4TV(liveVideoReq);
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
    @Path("/hkPlay/live")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveVideoRes playerLiveVideo4Hk(ContainerRequestContext containerRequestContext, @BeanParam CallerBean callerBean) {

        try {
            LiveVideoReq liveVideoReq = new LiveVideoReq();
            buildLiveVideoReq(liveVideoReq, containerRequestContext);
            //增加对platId 和 splatId 的校验
            LiveVideoRes liveVideoRes = new LiveVideoRes();
            Boolean checkLive = CheckSplatidUtils.checkLive(liveVideoReq.getSplatId(), liveVideoReq.getPlatId());
            if (!checkLive) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return liveVideoRes;
            }
            return videoPlayerService.dealLiveVideo4TVHk(liveVideoReq, callerBean.getCallerParam());
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
        videoOnDemandReq.setClientip(CallerCheckFilter.getIpAddr(containerRequestContext));
        if (null != paramMap.get("uid")) {
            videoOnDemandReq.setUid(paramMap.getFirst("uid").toString());
        }
        if (null != paramMap.get("uname")) {
            videoOnDemandReq.setUname(paramMap.getFirst("uname").toString());
        }
        if (null != paramMap.get("token")) {
            videoOnDemandReq.setToken(paramMap.getFirst("token").toString());
        }
        if (null != paramMap.get("flag")) {
            videoOnDemandReq.setFlag(paramMap.getFirst("flag").toString());
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
        //过滤得到需要的码流
        String vTypeStr = paramMap.getFirst("vtype").toString();
        String[] vTypeArr = StringUtils.split(vTypeStr, ",");
        StringBuilder sb = new StringBuilder();
        for (String vType : vTypeArr) {
            if (TVVTYPE.contains(vType)) {
                sb.append(vType).append(",");
            }
        }
        videoOnDemandReq.setVtype(sb.toString());
        videoOnDemandReq.setVid(paramMap.getFirst("vid").toString());
        videoOnDemandReq.setCaller(LeNumberUtils.toLong(paramMap.getFirst("caller").toString()));
        //国广防盗链使用
        if (videoOnDemandReq.getCaller().equals(LESPORTS_TV_CIBN_CALLER_CODE)) {
            videoOnDemandReq.setBc(2);
        }
        return videoOnDemandReq;
    }


    private LiveVideoReq buildLiveVideoReq(LiveVideoReq liveVideoReq, ContainerRequestContext containerRequestContext) {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
        liveVideoReq.setFlag(paramMap.getFirst("flag").toString());
        liveVideoReq.setHwtype(paramMap.getFirst("hwtype").toString());
        liveVideoReq.setLiveId(paramMap.getFirst("liveid").toString());
        liveVideoReq.setOstype(paramMap.getFirst("ostype").toString());
        liveVideoReq.setPlatId(paramMap.getFirst("platid").toString());
        liveVideoReq.setSplatId(paramMap.getFirst("splatid").toString());
        liveVideoReq.setTermid(paramMap.getFirst("termid").toString());
        liveVideoReq.setFrom(paramMap.getFirst("from") != null ? paramMap.getFirst("from").toString() : null);
        liveVideoReq.setUserId(paramMap.getFirst("uid") != null ? paramMap.getFirst("uid").toString() : null);
        liveVideoReq.setToken(paramMap.getFirst("token") != null ? paramMap.getFirst("token").toString() : null);
        liveVideoReq.setDevicekey(paramMap.getFirst("devicekey") != null ? paramMap.getFirst("devicekey").toString() : null);
        liveVideoReq.setTerminal(paramMap.getFirst("terminal") != null ? paramMap.getFirst("terminal").toString() : null);
        liveVideoReq.setClientip(CallerCheckFilter.getIpAddr(containerRequestContext));
        if (null != paramMap.get("uniqueFlag")) {
            liveVideoReq.setUniqueFlag(paramMap.getFirst("uniqueFlag").toString());
        }
        //国广防盗链使用
        if (LeNumberUtils.toLong(paramMap.getFirst("caller").toString()) == LESPORTS_TV_CIBN_CALLER_CODE) {
            liveVideoReq.setWithCibnStreams("1");
        } else {
            liveVideoReq.setWithCibnStreams("");
        }
        return liveVideoReq;
    }
}
