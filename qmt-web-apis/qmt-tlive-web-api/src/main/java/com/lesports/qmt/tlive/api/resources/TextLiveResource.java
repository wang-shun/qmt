package com.lesports.qmt.tlive.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.qmt.tlive.api.cache.TextLiveLogUrlProcessor;
import com.lesports.qmt.web.api.core.service.TextLiveService;
import com.lesports.qmt.web.api.core.vo.TextLiveMessageVo;
import com.lesports.sms.api.common.TextLiveMessageType;
import com.lesports.sms.api.common.UpDownAct;
import com.lesports.sms.api.vo.TAnchor;
import com.lesports.sms.api.vo.TTextLive;
import com.lesports.sms.client.TextLiveApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/9/17.
 */
@Path("/")
public class TextLiveResource {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveResource.class);

    @Inject
    public TextLiveService textLiveService;

    @LOG_URL
    @GET
    @LJSONP
    @Path("textLives/{textLiveId}/messageIds")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getLiveMessageIdsByTextLiveId(@PathParam("textLiveId") long textLiveId) {
        try {
            Map<String, Object> result = Maps.newHashMap();
            List<String> messageIds = TextLiveApis.getLiveMessageIdsByTextLiveId(textLiveId);
            result.put("messageIds", messageIds);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LOG_URL
    @LJSONP
    @Path("textLives/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TTextLive getTextLiveById(@PathParam("id") long id) {
        try {
            return TextLiveApis.getTextLiveById(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @LJSONP
    @Path("liveMessages/ids")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getLiveMessages(@QueryParam("textLiveId") long textLiveId,
                                               @QueryParam("section") long section,
                                               @QueryParam("messageType") @DefaultValue("0") int messageType) {
        try {
            Map<String, Object> result = Maps.newHashMap();
            List<String> messageIds = TextLiveApis.getLiveMessagesIds(textLiveId, section, TextLiveMessageType.findByValue(messageType));
            result.put("messageIds", messageIds);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取图文直播消息最新索引
     *
     * @param textLiveId
     * @param section
     * @return
     */
    @GET
    @LJSONP
    @Path("textLives/{textLiveId}/latestIndex")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getTLiveLatestIndex(@PathParam("textLiveId") long textLiveId,
                                                   @QueryParam("section") long section) {
        try {
            Map<String, Object> result = Maps.newHashMap();
            int index = TextLiveApis.getLiveMessageLatestIndex(textLiveId, section);
            result.put("latestIndex", index);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 按页获取消息列表
     *
     * @param textLiveId
     * @param section
     * @param page
     * @return
     */
    @GET
    @LJSONP
    @Path("textLives/{textLiveId}/page/{page}")
    @LOG_URL(processor = TextLiveLogUrlProcessor.class)
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TextLiveMessageVo> getLiveMessageByPage(@PathParam("textLiveId") long textLiveId,
                                                        @QueryParam("section") long section,
                                                        @PathParam("page") int page) {
        try {
            return textLiveService.getLiveMessageByPage(textLiveId, section, page);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上报在线人数
     *
     * @param eid
     * @return
     */
    @POST
    @LJSONP
    @Path("/episodes/{eid}/online")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> reportOnlineCount(@PathParam("eid") long eid) {
        try {
            Map<String, Object> result = Maps.newHashMap();
            long onlineCount = TextLiveApis.reportOnlineCount(eid);
            result.put("onlineCount", onlineCount);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取在线人数
     *
     * @param eid
     * @return
     */
    @GET
    @LJSONP
    @Path("episodes/{eid}/online")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getOnlineCount(@PathParam("eid") long eid) {
        try {
            Map<String, Object> result = Maps.newHashMap();
            long onlineCount = TextLiveApis.getOnlineCount(eid);
            result.put("onlineCount", onlineCount);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 直播员顶踩
     *
     * @param textLiveId
     * @param anchorId
     * @param act
     * @return
     */
    @POST
    @LJSONP
    @Path("textLives/{textLiveId}/anchor")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TAnchor upDownAnchor(@PathParam("textLiveId") long textLiveId,
                                @QueryParam("anchorId") long anchorId,
                                @QueryParam("act") int act) {
        try {
            Preconditions.checkArgument(anchorId != 0, "请输入anchorId");
            Preconditions.checkArgument(act != 1 || act != 2, "顶踩参数非法");
            return TextLiveApis.upDownAnchor(textLiveId, anchorId, UpDownAct.findByValue(act));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取直播员顶踩
     *
     * @param textLiveId
     * @param anchorId
     * @return
     */
    @GET
    @LJSONP
    @Path("textLives/{textLiveId}/anchor")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TAnchor upDownAnchor(@PathParam("textLiveId") long textLiveId,
                                @QueryParam("anchorId") long anchorId) {
        try {
            Preconditions.checkArgument(anchorId != 0, "请输入anchorId");
            return TextLiveApis.getAnchorUpDown(textLiveId, anchorId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
