package com.lesports.qmt.web.api.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lesports.api.common.CountryCode;
import com.lesports.model.*;
//import com.lesports.sms.api.vo.TVideo;
//import com.lesports.sms.api.web.core.service.ValidateService;
//import com.lesports.sms.api.web.core.util.CacheContainer;
//import com.lesports.sms.api.web.core.util.Constants;
//import com.lesports.sms.api.web.core.vo.GetLiveRoomCacheParam;
//import com.lesports.sms.api.web.core.vo.GetTVideoCacheParam;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.web.api.core.service.ValidateService;
import com.lesports.qmt.web.api.core.util.CacheContainer;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.GetLiveRoomCacheParam;
import com.lesports.qmt.web.api.core.vo.GetTVideoCacheParam;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.LiveChannelDetail;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lufei1 on 2016/6/21.
 */
@Service("validateService")
public class ValidateServiceImpl extends AbstractService implements ValidateService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidateServiceImpl.class);


    @Override
    public ValidateRes authVodForDrm(ValidateReq validateReq) {

        Video video;
        try {
            video = CacheContainer.VIDEO_LOADING_CACHE.get(validateReq.getVid());
        } catch (Exception e) {
            LOG.error("get video from mms error", e);
            return new ValidateRes(0, PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
        }
        if (null == video || video.getDeleted() == 1) {
            LOG.warn("[app] [videoPlayer] [dealVideoOnDemand] [video off line] [vid:{}]", validateReq.getVid());
            return new ValidateRes(0, PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
        }

        TVideo tVideo = getTVideo(new GetTVideoCacheParam(LeNumberUtils.toLong(validateReq.getVid()), validateReq.getCallerId()));
        //海外屏蔽
        if (null != tVideo && MmsApis.isDrm(video) == 1) {
            boolean validIp = IpLocationCheckUtils.validWhite(validateReq.getClientIp(), tVideo.getAllowCountries());
            if (!validIp) {
                LOG.info("Invalid ip {} access for vod : {}", validateReq.getClientIp(), validateReq.getVid());
                return new ValidateRes(0, PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            }
        }
        if (MmsApis.isPay(video, getPlatFormFromCaller(validateReq.getCallerId())) == 1) {
            return BossApi.authVideoForDrmNew(validateReq);
        } else if (validateReq.getIsDrm() == 1) {
            return TgsApis.genToken(validateReq.getUserId(), validateReq.getEncodeId(), validateReq.getActivate());
        } else {
            return new ValidateRes(1);
        }
    }

    @Override
    public ValidateRes authLiveForDrm(ValidateReq validateReq) {
        LiveRoomResponse liveRoomResponse;
        try {
            liveRoomResponse = CacheContainer.LIVE_ROOM_LOADING_CACHE.get(new GetLiveRoomCacheParam(validateReq.getLiveId(), validateReq.getSplatId()));
        } catch (Exception e) {
            LOG.error("getLiveRoom from live error", e);
            return new ValidateRes(0, PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
        }
        if (liveRoomResponse == null || CollectionUtils.isEmpty(liveRoomResponse.getRows()) || liveRoomResponse.getRows().get(0).getSelectId() <= 0 ) {//StringUtils.isBlank(liveRoomResponse.getRows().get(0).getSelectId())) {
            LOG.warn("[app] [videoPlayer] [dealLiveVideo] [get live room error ] [liveId:{}] ", validateReq.getLiveId());
            return new ValidateRes(0, PlayerErrorCode.LIVE_RES_ERROR.getCode());
        }
        LiveRoom liveRoom = liveRoomResponse.getRows().get(0);
        if (null != liveRoom && LiveApis.isDrm(liveRoom) == 1) {
            boolean validIp = IpLocationCheckUtils.validWhite(validateReq.getClientIp(), getCountryCode(liveRoom.getBelongArea()));
            if (!validIp) {
                LOG.info("Invalid ip {} access for live : {}", validateReq.getClientIp(), validateReq.getVid());
                return new ValidateRes(0, PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            }
        }
        if (LiveApis.isPay(liveRoom, validateReq.getSplatId()) == 1) {
            validateReq.setLiveStatus(liveRoom.getStatus());
            return BossApi.authLiveForDrmNew(validateReq);
        } else if (validateReq.getIsDrm() == 1) {
            return TgsApis.genToken(validateReq.getUserId(), validateReq.getEncodeId(), validateReq.getActivate());
        } else {
            return new ValidateRes(1);
        }
    }

    @Override
    public ValidateRes authCarouselForDrm(ValidateReq validateReq) {
        LiveChannelDetail liveChannelDetail = CarouselApis.getChannelDetail(LeNumberUtils.toInt(validateReq.getSplatId()),
                LeNumberUtils.toLong(validateReq.getChannelId()));
        if (null != liveChannelDetail && CarouselApis.isDrm(liveChannelDetail) == 1) {
            boolean validIp = IpLocationCheckUtils.validWhite(validateReq.getClientIp(), getCountryCode(liveChannelDetail.getBelongArea()));
            if (!validIp) {
                LOG.info("Invalid ip {} access for carousel : {}", validateReq.getClientIp(), validateReq.getVid());
                return new ValidateRes(0, PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            }
        }
        if (CarouselApis.isPay(liveChannelDetail) == 1) {
            return BossApi.authCarouselForDrmNew(validateReq);
        } else if (validateReq.getIsDrm() == 1) {
            return TgsApis.genToken(validateReq.getUserId(), validateReq.getEncodeId(), validateReq.getActivate());
        } else {
            return new ValidateRes(1);
        }
    }

    private CountryCode getCountryCode(String liveBelongArea) {
        if (StringUtils.isEmpty(liveBelongArea)) {
            return null;
        }
        try {
            return Constants.LIVE_BELONG_AREA_COUNTRY_MAP.get(liveBelongArea);
        } catch (Exception e) {
            return null;
        }
    }

    private TVideo getTVideo(GetTVideoCacheParam getTVideoCacheParam) {
        try {
            return CacheContainer.TVIDEO_CACHE.get(getTVideoCacheParam);
        } catch (Exception e) {
            LOG.error("Fail to load TVideo for : {}, {}", JSONObject.toJSONString(getTVideoCacheParam), e.getMessage(), e);
        }
        return null;

    }
}
