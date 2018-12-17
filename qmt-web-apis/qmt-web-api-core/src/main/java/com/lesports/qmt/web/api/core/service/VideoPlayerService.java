package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.exception.VrsAccessException;
import com.lesports.model.LiveVideoReq;
import com.lesports.model.LiveVideoRes;
import com.lesports.model.VodReq;
import com.lesports.model.VodRes;

/**
 * Created by lufei1 on 2015/7/7.
 */
public interface VideoPlayerService {
    /**
     * 处理点播流程
     *
     * @param vodReq
     * @return
     */
    public VodRes dealVod(VodReq vodReq)throws VrsAccessException;


    /**
     * 拥有服务降级的点播流程
     *
     * @param videoOnDemandReq
     * @return
     */
    public VodRes dealVodWithFallback(VodReq videoOnDemandReq);

    /**
     * 处理直播流程
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideo(LiveVideoReq liveVideoReq);


    /**
     * 拥有服务降级的直播流程
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideoWithFallback(LiveVideoReq liveVideoReq);


    public Boolean eachPlayLanding(String token, String flag, String devicekey, String terminal);


    /**
     * 大陆tv直播
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideo4TV(LiveVideoReq liveVideoReq);

    /**
     * 香港tv直播
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideo4TVHk(LiveVideoReq liveVideoReq, CallerParam callerParam);


}
