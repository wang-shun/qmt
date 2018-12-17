package com.lesports.qmt.op.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.creater.EpisodeVoCreater;
import com.lesports.qmt.op.web.api.core.service.EpisodeService;
import com.lesports.qmt.op.web.api.core.util.MPLayLinkUtil;
import com.lesports.qmt.op.web.api.core.util.TimeUtil;
import com.lesports.qmt.op.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.BaiduEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.SiteInfo;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetCurrentEpisodesParam;
import com.lesports.qmt.sbc.api.service.GetSomeDayEpisodesParam;
import com.lesports.qmt.sbc.api.service.LiveTypeParam;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service("episodeService")
public class EpisodeServiceImpl implements EpisodeService {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeServiceImpl.class);

    @Resource
    private EpisodeVoCreater episodeVoCreater;


    @Override
    public Map<String, Object> getBaiduLiveEpisodes(String startTime, int span,PageParam page,CallerParam caller) {
        Map<String, Object> result = Maps.newHashMap();
        for (int i = 0; i < span; i++) {
            String day = TimeUtil.addDay(startTime, i);
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.ONLY_LIVE);
            p.setDate(startTime);
            List<BaiduEpisodeVo> dayEpisodes = getDayEpisodesForBaidu(p,page,caller);
            result.put(day, dayEpisodes);
        }
        result.put("siteInfo", SiteInfo.createSiteInfo());
        return result;
    }


    @Override
    public DetailEpisodeVo getEpisodeById(Long id,CallerParam caller) {
        try {
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(id, caller);
            return episodeVoCreater.createDetailEpisodeVo(episode,caller);
        } catch (Exception e) {
            LOG.error("getEpisodeById fail, e={}", e);
        }
        return null;
    }


    /**
     * 比赛大厅,按照天查询
     * <p/>
     * date 起始时间,精确到天(yyyyMMdd:20150528)
     * game_type 比赛分类\
     * callerId 调用者id
     * episode_type 节目类型 0:match 1:program 2:all
     * contain_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @Override
    public List<HallEpisodeVo> getSomeDayEpisodes(GetSomeDayEpisodesParam p,PageParam page,CallerParam caller) {
        try {
            List<HallEpisodeVo> returnList = Lists.newArrayList();


            List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p,page,caller);
            if (CollectionUtils.isNotEmpty(episodes)) {
                for (TComboEpisode comboEpisode : episodes) {
                    HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(comboEpisode,caller);
                    returnList.add(hallEpisodeVo);
                }
                return returnList;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            LOG.error("getSomeDayEpisodes fail, e={}", e);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 中超推广
     * <p/>
     * date 起始时间,精确到天(yyyyMMdd:20150528)
     * game_type 比赛分类\
     * callerId 调用者id
     * episode_type 节目类型 0:match 1:program 2:all
     * contain_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @Override
    public List<HallEpisodeVo> getCslSomeDayEpisodes(GetSomeDayEpisodesParam p,PageParam page,CallerParam caller) {
        try {
            List<HallEpisodeVo> returnList = Lists.newArrayList();
            List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p,page,caller);
            if (CollectionUtils.isNotEmpty(episodes)) {
                for (TComboEpisode comboEpisode : episodes) {
                    HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(comboEpisode,caller);
                    hallEpisodeVo.setMPlayLink(MPLayLinkUtil.getEpisodeMOpCslPlayLink(comboEpisode, caller));
//                    hallEpisodeVo.setPlayLink(hallEpisodeVo.getMPlayLink());
                    returnList.add(hallEpisodeVo);
                }
                return returnList;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            LOG.error("getSomeDayEpisodes fail, e={}", e);
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    public List<HallEpisodeVo> getHallEpisodes(GetCurrentEpisodesParam p,PageParam page ,CallerParam caller) {
        try {
            List<HallEpisodeVo> returnList = Lists.newArrayList();
            List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, page, caller);
            if (CollectionUtils.isNotEmpty(episodes)) {
                for (TComboEpisode comboEpisode : episodes) {
                    HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(comboEpisode,caller);
                    returnList.add(hallEpisodeVo);
                }
                return returnList;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            LOG.error("getHallEpisodes fail, e={}", e);
        }
        return Collections.EMPTY_LIST;
    }


    public List<BaiduEpisodeVo> getDayEpisodesForBaidu(GetSomeDayEpisodesParam p,PageParam page ,CallerParam caller) {
        List<BaiduEpisodeVo> baiduEpisodeVos = Lists.newArrayList();

        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, page, caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                //对外接口过滤中超和NBA数据
                if(comboEpisode.getCid()==47001 || comboEpisode.getCid()==44001){
                    continue;
                }
                baiduEpisodeVos.add(episodeVoCreater.createBaiduEpisodeVo(comboEpisode));
            }
        }
        return baiduEpisodeVos;
    }


}
