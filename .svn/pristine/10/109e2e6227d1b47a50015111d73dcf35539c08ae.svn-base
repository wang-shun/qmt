package com.lesports.qmt.web.api.core.util;

//import client.SopsApis;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lesports.api.common.CallerParam;
import com.lesports.model.ChainUrlResponse;
import com.lesports.model.LiveRoomResponse;
import com.lesports.model.LiveStreamResponse;
import com.lesports.model.Video;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.vo.GetLiveRoomCacheParam;
import com.lesports.qmt.web.api.core.vo.GetLiveStreamCacheParam;
import com.lesports.qmt.web.api.core.vo.GetTVideoCacheParam;
import com.lesports.qmt.web.api.core.vo.GetVrsChainCacheParam;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LiveApis;
import com.lesports.utils.MmsApis;
import com.lesports.utils.math.LeNumberUtils;

import java.util.concurrent.TimeUnit;

//import com.lesports.sms.api.vo.TVideo;
//import com.lesports.sms.api.web.core.vo.GetLiveRoomCacheParam;
//import com.lesports.sms.api.web.core.vo.GetLiveStreamCacheParam;
//import com.lesports.sms.api.web.core.vo.GetTVideoCacheParam;
//import com.lesports.sms.api.web.core.vo.GetVrsChainCacheParam;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/8/5
 */
public class CacheContainer {
    public static final LoadingCache<GetTVideoCacheParam, TVideo> TVIDEO_CACHE = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<GetTVideoCacheParam, TVideo>() {
                @Override
                public TVideo load(GetTVideoCacheParam key) throws Exception {
                    CallerParam callerParam = CallerUtils.createCallerParam(key.getCaller(), null, null);
                    return QmtSbcApis.getTVideoById(LeNumberUtils.toLong(key.getVid()), callerParam);
                }
            });

    public final static LoadingCache<String, Video> VIDEO_LOADING_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Video>() {
                @Override
                public Video load(String key) throws Exception {
                    return MmsApis.getVideo(key);
                }
            });

    public final static LoadingCache<GetLiveRoomCacheParam, LiveRoomResponse> LIVE_ROOM_LOADING_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetLiveRoomCacheParam, LiveRoomResponse>() {
                @Override
                public LiveRoomResponse load(GetLiveRoomCacheParam key) throws Exception {
                    return LiveApis.getLiveRoom(key.getLiveId(), key.getSplatId());
                }
            });

    public final static LoadingCache<GetLiveStreamCacheParam, LiveStreamResponse> LIVE_STREAM_LOADING_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetLiveStreamCacheParam, LiveStreamResponse>() {
                @Override
                public LiveStreamResponse load(GetLiveStreamCacheParam key) throws Exception {
                    return LiveApis.getLiveStream(key.getSelectId(), key.getSplatId(), key.getWithCibnStreams(), key.getLiveId());
                }
            });

    public final static LoadingCache<GetVrsChainCacheParam, ChainUrlResponse> VIDEO_CHAIN_LOADING_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetVrsChainCacheParam, ChainUrlResponse>() {
                @Override
                public ChainUrlResponse load(GetVrsChainCacheParam key) throws Exception {
                    return MmsApis.getChainUrl(key.getTss(), key.getvType(), key.getPlatId(),
                            key.getSplatId(), key.getClientIp(), key.getBc(), key.getMmsid());
                }
            });
}
