package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.model.LiveRoom;
import com.lesports.model.LiveRoomResponse;
import com.lesports.model.LiveStreamResponse;
import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.utils.LiveApis;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2015/7/6.
 */
@Component
public class LiveCache extends AbstractCache {
    private static final Logger logger = LoggerFactory.getLogger(VrsCache.class);
    private static final Utf8KeyCreater<String> LIVEROOM_CREATE = new Utf8KeyCreater<>("V2_LIVE_LIVEROOM_");
    private static final Utf8KeyCreater<String> LIVE_STREAM_CREATE = new Utf8KeyCreater<>("V2_LIVE_LIVESTREAM_");
    private static final int EXPIRE_TIME = 30;

    public LiveRoom getLiveRoom(String liveId) {
        try {
            return findByKey(LIVEROOM_CREATE.textKey(liveId), LiveRoom.class);
        } catch (Exception e) {
            logger.error("get liveRoom from cache liveId: " + liveId, e);
            LiveRoomResponse liveRoomResponse = LiveApis.getLiveRoom(liveId, "1031");
            return liveRoomResponse.getRows().get(0);
        }
    }

    public LiveRoom getLiveRoom(String liveId, String splatId) {
        try {
            return findByKey(LIVEROOM_CREATE.textKey(liveId + "_" + splatId), LiveRoom.class);
        } catch (Exception e) {
            logger.error("get liveRoom from cache liveId: " + liveId, e);
            LiveRoomResponse liveRoomResponse = LiveApis.getLiveRoom(liveId, splatId);
            return liveRoomResponse.getRows().get(0);
        }
    }

    public void saveLiveRoom(String liveId, LiveRoom liveRoom) {
        save(LIVEROOM_CREATE.textKey(liveId), liveRoom, EXPIRE_TIME);
    }

    public void saveLiveRoom(String liveId, String splatId, LiveRoom liveRoom) {
        save(LIVEROOM_CREATE.textKey(liveId + "_" + splatId), liveRoom, EXPIRE_TIME);
    }

    public LiveStreamResponse getLiveStream(String channelId, String splatId, String guoguang, String liveId) {
        try {
            return findByKey(LIVE_STREAM_CREATE.textKey(channelId + "_" + splatId + "_" + guoguang + "_" + liveId), LiveStreamResponse.class);
        } catch (Exception e) {
            logger.error("get liveStream from cache channelId: " + channelId, e);
            return LiveApis.getLiveStream(channelId, splatId, guoguang, liveId);
        }
    }

    public void saveLiveStream(String channelId, String splatId, String guoguang, String liveId, LiveStreamResponse liveStreamResponse) {
        save(LIVE_STREAM_CREATE.textKey(channelId + "_" + splatId + "_" + guoguang + "_" + liveId), liveStreamResponse, EXPIRE_TIME);
    }

}
