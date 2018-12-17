package com.lesports.qmt.sbc.web.controller;

import com.lesports.api.common.LiveStreamChannelType;
import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.api.dto.StreamSourceType;
import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.model.LiveErrorCode;
import com.lesports.qmt.sbc.service.LiveWebService;
import com.lesports.qmt.sbc.vo.LiveVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.dto.LiveBranchDTO;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsQueryAPI;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import com.letv.urus.liveroom.exception.UrusLiveRoomException;
import me.ellios.hedwig.common.exceptions.HedwigException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjichuan on 16-10-25.
 * <p/>
 * 直播相关功能控制器
 */
@RestController
@RequestMapping(value = "/lives")
@WebLogAnnotation(ID_TYPE = IdType.LETV_LIVE)
public class LiveController extends AbstractQmtController<LiveVo, Long> {
    private static final Logger LOG = LoggerFactory.getLogger(LiveController.class);
    @Autowired
    private LiveRoomSportsQueryAPI liveRoomSportsQueryAPI;
    @Autowired
    private LiveRoomSportsWriterAPI liveRoomSportsWriterAPI;
    @Resource
    private LiveWebService liveWebService;

    @RequestMapping(value = "/getOccupied", method = RequestMethod.GET)
    public Map<String, Object> getOccupied(@RequestParam(name = "liveId", required = true) Long liveId,
                                           @RequestParam(name = "channelId", required = true) Integer channelId,
                                           @RequestParam(name = "startTime", required = true) String startTime,
                                           @RequestParam(name = "endTime", required = true) String endTime) {
        UrusAuth urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
        try {
            Map<String, Object> map = new HashMap<>();
            ChannelIdNameEnameDTO liveChannel = liveRoomSportsQueryAPI.getLiveChannelById(urusAuth, channelId.longValue());
            List<LiveBranchDTO> result = liveRoomSportsQueryAPI.selectIdOccupiedBranchList(urusAuth, channelId, liveChannel.getDrmFlag() > 0 ? String.valueOf(liveChannel.getDrmFlag()) : "",
                    LeDateUtils.parseYYYYMMDDHHMMSS(startTime), LeDateUtils.parseYYYYMMDDHHMMSS(endTime));
            if (CollectionUtils.isNotEmpty(result)) {
                result.removeIf(entity -> LeNumberUtils.toLong(entity.getLiveid()) == LeNumberUtils.toLong(liveId));
            }
            if (CollectionUtils.isNotEmpty(result)) {
                map.put("occupied", true);
            } else {
                map.put("occupied", false);
            }
            map.put("lives", result);
            return map;
        } catch (UrusLiveRoomException e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Live> batchGet(@RequestParam(name = "ids", required = false) String liveIds) {
        List<Live> lives = new ArrayList<>();
        if (StringUtils.isBlank(liveIds)) {
            return lives;
        }
        try {
            lives = liveWebService.batchGet(LeStringUtils.commaString2LongList(liveIds));
        } catch (Exception e) {
            LOG.error("[live][batchGet error][liveIds={}]", liveIds, e);
            throw new LeWebApplicationException("fail batch get", LeStatus.SERVER_EXCEPTION);
        }
        return lives;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public LiveVo save(LiveVo live) {
        SaveResult result;
        try {
            checkParam(live);
            result = liveWebService.saveWithResult(live);
            if (!result.isSuccess()) {
                throw new LeWebApplicationException(result.getMsg(), LeStatus.PARAM_INVALID);
            }
            return liveWebService.findOne(live.getId());
        } catch (HedwigException e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.BAD_REQUEST);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("[live][save error]", e);
            throw new LeWebApplicationException(LiveErrorCode.BACKEND_ERROR.name(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public LiveVo getOneById(@PathVariable Long id) {
        LiveVo live = null;
        try {
            live = liveWebService.findOne(id);
        } catch (Exception e) {
            LOG.error("[live][get error][id={}]", id, e);
            throw e;
        }
        return live;
    }

    @RequestMapping(value = "/livechannel", method = RequestMethod.GET)
    public List<ChannelIdNameEnameDTO> getLiveChannelList(@RequestParam(name = "type", required = false) int type) {
        UrusAuth urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
        LiveStreamChannelType channelType = LiveStreamChannelType.findByValue(type);
        List<ChannelIdNameEnameDTO> channelIdNameEnameDTOs = new ArrayList<ChannelIdNameEnameDTO>();
        try {
            switch (channelType) {
                case CIBN:
                    channelIdNameEnameDTOs = liveRoomSportsQueryAPI.getIdNameEnameOfCibnChannel(urusAuth);
                    break;
                case NORMAL:
                    channelIdNameEnameDTOs = liveRoomSportsQueryAPI.getIdNameEnameOfNormalChannel(urusAuth);
                    break;
                default:
                    channelIdNameEnameDTOs = liveRoomSportsQueryAPI.getIdNameEnameOfUsedChannel(urusAuth);
            }
        } catch (Exception e) {
            LOG.error("[live][getLiveChannelList][type={}]", type, e);
        }
        return channelIdNameEnameDTOs;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'DELETE')")
    public boolean batchDelete(@RequestParam("ids") String liveIds) {
        try {
            if (StringUtils.isBlank(liveIds)) {
                throw new LeWebApplicationException("param error", LeStatus.PARAM_INVALID);
            }
            return liveWebService.batchDelete(LeStringUtils.commaString2LongList(liveIds));
        } catch (Exception e) {
            LOG.error("[live][batchDelete error][liveIds={}]", liveIds, e);
            throw e;
        }
    }

    private void checkParam(LiveVo live) {
        if (live == null) {
            throw new LeWebApplicationException("param error", LeStatus.PARAM_INVALID);
        }
        if (StringUtils.isEmpty(live.getStartTime())) {
            throw new LeWebApplicationException("param error live start or end time must be not empty", LeStatus.PARAM_INVALID);
        }
        if (StringUtils.isEmpty(live.getCommentaryLanguage())) {
            throw new LeWebApplicationException("param error live commentary language must be not empty", LeStatus.PARAM_INVALID);
        }
        if (live.getStreamSourceTypeParam() == StreamSourceType.LETV.getValue()) {
            if (LeNumberUtils.toInt(live.getChannelId()) <= 0 && LeNumberUtils.toInt(live.getCibnChannelId()) <= 0) {
                throw new LeWebApplicationException("param error live channel or cibn channel is empty", LeStatus.PARAM_INVALID);
            }
        } else if (live.getStreamSourceTypeParam() == StreamSourceType.THRID_PARTY.getValue()) {
            if (StringUtils.isBlank(live.getThirdLivePageUrl())) {
                throw new LeWebApplicationException("param error third live page is empty", LeStatus.PARAM_INVALID);
            }
        }
    }

}
