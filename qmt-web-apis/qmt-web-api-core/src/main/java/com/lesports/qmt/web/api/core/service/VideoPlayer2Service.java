package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.exception.VrsAccessException;
import com.lesports.model.*;
import com.lesports.qmt.web.api.core.vo.PcPlayVo;
//import com.lesports.sms.api.web.core.vo.PcPlayVo;

/**
 * Created by lufei1 on 2015/7/7.
 */
public interface VideoPlayer2Service {
    /**
     * 处理点播流程
     *
     * @param vodReq
     * @return
     */
    public VodRes dealVod(VodReq vodReq) throws VrsAccessException;


    /**
     * 拥有服务降级的点播流程
     *
     * @param videoOnDemandReq
     * @return
     */
    public VodRes dealVodWithFallback(VodReq videoOnDemandReq);


    /**
     * drm 播放
     *
     * @param videoOnDemandReq
     * @return
     */
    public VodRes dealVod4Drm(VodReq videoOnDemandReq, CallerParam callerParam) throws VrsAccessException;

    /**
     * pc drm 播放
     *
     * @param
     * @return
     */
    public PcPlayVo dealVod4PcDrm(VodReq vodReq, CallerParam callerParam)throws VrsAccessException;

    /**
     * pc直播代理
     * @param liveId
     * @param splatId
     * @return
     */
    public LiveVideoResPC dealLive4PC(String liveId, String splatId, CallerParam callerParam);


    /**
     * 处理直播流程
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideo(LiveVideoReq liveVideoReq);

    /**
     * drm 直播
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLive4Drm(LiveVideoReq liveVideoReq, CallerParam callerParam);


    /**
     * 拥有服务降级的直播流程
     *
     * @param liveVideoReq
     * @return
     */
    public LiveVideoRes dealLiveVideoWithFallback(LiveVideoReq liveVideoReq);

}
