package com.lesports.qmt.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import com.lesports.qmt.sbd.client.SbdTopListApis;
import com.lesports.qmt.web.model.TeamTopListVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/29.
 */
@Service("topListService")
public class TopListService {
    private static final Logger LOG = LoggerFactory.getLogger(TopListService.class);

    //积分榜
    public static final Long TOPLIST_TYPE_TABLE = 100162000L;
    //射手榜、助攻榜
    public static final List<Long> TOPLIST_TYPE_OTHER = Lists.newArrayList(100160000L, 100161000L);
    //NBA西部排行榜
    public static final Long TOPLIST_TYPE_WEST_LIST = 100204000L;
    //NBA东部排行榜
    public static final Long TOPLIST_TYPE_EAST_LIST = 100205000L;
    //NBA赛事id
    public static final Long NBA_COMPETITION_ID = 44001L;
    //区域和榜单的映射map
    public final static Map<Long, Long> NBA_REGION_TOPLIST_MAP = Maps.newHashMap();
    //12强赛
    public static final Long TOP12_COMPETITION_ID = 101425001L;

    static {
        //东部
        NBA_REGION_TOPLIST_MAP.put(100130000L, 100205000L);
        //西部
        NBA_REGION_TOPLIST_MAP.put(100131000L, 100204000L);
        //东南区
        NBA_REGION_TOPLIST_MAP.put(100132000L, 104650000L);
        //大西洋
        NBA_REGION_TOPLIST_MAP.put(100133000L, 104648000L);
        //中部区
        NBA_REGION_TOPLIST_MAP.put(100134000L, 104649000L);
        //西南区
        NBA_REGION_TOPLIST_MAP.put(100135000L, 104652000L);
        //西北区
        NBA_REGION_TOPLIST_MAP.put(100136000L, 104653000L);
        //太平洋区
        NBA_REGION_TOPLIST_MAP.put(100137000L, 104651000L);
    }

    /**
     * 获取赛事积分榜单数据
     *
     * @param cid 赛事id
     * @return
     */
    public List<TTopList> getSeasonTopLists(long cid, long csid, CallerParam caller) {
        List<TTopList> results = Lists.newArrayList();
        if (cid == NBA_COMPETITION_ID) {
            //NBA榜单特殊处理下
            TTopList eastTopList = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, TOPLIST_TYPE_EAST_LIST, caller);
            if (eastTopList != null) {
                results.add(eastTopList);
            }
            TTopList westTopList = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, TOPLIST_TYPE_WEST_LIST, caller);
            if (westTopList != null) {
                results.add(westTopList);
            }
        } else {
            if(cid == TOP12_COMPETITION_ID){
                return results;
            }
            TTopList topList = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, TOPLIST_TYPE_TABLE, caller);
            if (topList != null) {
                results.add(topList);
            }
        }
        return results;
    }

    /**
     * 获取射手、助攻等其他数据榜单
     *
     * @param cid
     * @return
     */
    public Map<Long, TTopList> getOtherTopLists(long cid, long csid, CallerParam caller) {
        Map<Long, TTopList> topListMap = Maps.newHashMap();
        for (Long type : TOPLIST_TYPE_OTHER) {
            TTopList topList = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, type, caller);
            if (topList != null) {
                topListMap.put(type, topList);
            }
        }
        return topListMap;
    }

    /**
     * 获取参赛队伍的榜单统计信息
     *
     * @param match
     * @return
     */
    public Map<Long, TeamTopListVo> getTeamTopListVos(TDetailMatch match, CallerParam caller) {
        Map<Long, TeamTopListVo> teamTopListVoMap = Maps.newHashMap();
        List<TCompetitor> tCompetitors = match.getCompetitors();
        if (CollectionUtils.isEmpty(tCompetitors)) {
            return Collections.EMPTY_MAP;
        }
        for (TCompetitor competitor : tCompetitors) {
            if (competitor.getCompetitorType() == CompetitorType.PLAYER) {
                return Collections.EMPTY_MAP;
            }
            TeamTopListVo teamTopListVo = new TeamTopListVo();
            TTeam team = SbdTeamApis.getTTeamById(competitor.getId(), caller);
            if (team == null) {
                LOG.warn("fail to getTeamTopListVos. team : {} not found. mid : {}", competitor.getId(), match.getId());
                return Collections.EMPTY_MAP;
            }
            teamTopListVo.setTid(team.getId());
            teamTopListVo.setName(team.getName());
            teamTopListVo.setConference(team.getConference());
            teamTopListVo.setRegion(team.getRegion());
            //获取联盟统计信息
            Long standingType = NBA_REGION_TOPLIST_MAP.get(team.getConferenceId());
            if (standingType != null) {
                TTopList topList = SbdTopListApis.getSomeTypeTopListOfSeason(match.getCid(), match.getCsid(), standingType, caller);
                teamTopListVo.setConferenceItem(getTopListItemOfSomeTeam(topList, team.getId()));
            }
            //获取分区统计信息
            standingType = NBA_REGION_TOPLIST_MAP.get(team.getRegionId());
            if (standingType != null) {
                TTopList topList = SbdTopListApis.getSomeTypeTopListOfSeason(match.getCid(), match.getCsid(), standingType, caller);
                teamTopListVo.setRegionItem(getTopListItemOfSomeTeam(topList, team.getId()));
            }
            teamTopListVoMap.put(team.getId(), teamTopListVo);
        }
        return teamTopListVoMap;
    }


    /**
     * 获取某支球队在榜单里的统计信息
     *
     * @param topList
     * @param teamId
     * @return
     */
    public TTopListItem getTopListItemOfSomeTeam(TTopList topList, long teamId) {
        if (topList == null || CollectionUtils.isEmpty(topList.getItems())) {
            return null;
        }
        for (TTopListItem item : topList.getItems()) {
            if (teamId == item.getCompetitorId()) {
                return item;
            }
        }
        return null;
    }
}
