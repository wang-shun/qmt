package com.lesports.qmt.op.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.sbc.api.service.GetCurrentEpisodesParam;
import com.lesports.qmt.sbc.api.service.GetSomeDayEpisodesParam;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2015/8/13.
 */
public interface EpisodeService {

    /**
     * 百度影音节目列表
     *
     * @param startTime
     * @param span
     * @param page
     * @param caller
     * @return
     */
    Map<String, Object> getBaiduLiveEpisodes(String startTime, int span, PageParam page, CallerParam caller);


    /**
     * 比赛大厅,按照天查询
     * @param p
     * @param page
     * @param caller
     */
    List<HallEpisodeVo> getSomeDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 中超推广
     * @param p
     * @param page
     * @param caller
     */
    List<HallEpisodeVo> getCslSomeDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);


    /**
     * 获取赛程详情
     *
     * @param id
     * @return
     */
    DetailEpisodeVo getEpisodeById(Long id, CallerParam caller);


    /**
     * 获取比赛大厅的数据 APP
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo> getHallEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);


}
