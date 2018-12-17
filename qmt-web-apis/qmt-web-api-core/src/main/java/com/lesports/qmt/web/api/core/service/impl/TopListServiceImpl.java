package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.web.api.core.creater.TopListVoCreater;
import com.lesports.qmt.web.api.core.service.TopListService;
import com.lesports.qmt.web.api.core.vo.AppCompetitionSeasonTopListVo;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.client.SbdTopListApis;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/29.
 */
@Service("topListService")
public class TopListServiceImpl implements TopListService {

    @Resource
    TopListVoCreater toplistVoCreater;

    //东部榜单
    private static final long NBA_EAST_TOPLIST_TYPE = 100205000l;
    //西部榜单
    private static final long NBA_WEST_TOPLIST_TYPE = 100204000l;
    //东部分区
    private static final List<Long> NBA_EAST_REGION_TOPLIST_TYPE = Lists.newArrayList(104648000l, 104649000l, 104650000l);
    //西部分区
    private static final List<Long> NBA_WEST_REGION_TOPLIST_TYPE = Lists.newArrayList(104651000l, 104652000l, 104653000l);

    /**
     *  获取榜单数据
     * @param p
     * @param pageParam
     * @param callerParam
     * @return
     */
    @Override
    public TopListVo getSeasonTopList(GetSeasonTopListsParam p,PageParam pageParam,CallerParam callerParam) {

        List<TTopList> tTopLists = SbdTopListApis.getSeasonTopLists(p, pageParam, callerParam);
        if(CollectionUtils.isNotEmpty(tTopLists)){
            TTopList topList = tTopLists.get(0);
            return toplistVoCreater.createTopListVo(topList,callerParam);
        }
        return null;

    }

    @Override
    public TopListVo getSeasonTopList4TV(GetSeasonTopListsParam p,PageParam pageParam,CallerParam callerParam) {

        List<TTopList> tTopLists = SbdTopListApis.getSeasonTopLists(p,pageParam,callerParam);
        TopListVo topListVo = new TopListVo();
        List<TopListVo.TopListVoItem> topListVoItems = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(tTopLists)){
            for (TTopList tTopList : tTopLists) {
                topListVo = toplistVoCreater.createTVTopListVo(tTopList,callerParam);
                topListVoItems.addAll(topListVo.getLists());
            }
            topListVo.setGroup(null);
            topListVo.getLists().clear();
            topListVo.getLists().addAll(topListVoItems);
            return topListVo;
        }
        return null;

    }

    @Override
    public Map<String, Map<String, List<TopListVo>>> getNbaTopList(long csid, CallerParam callerParam) {
        GetSeasonTopListsParam p = new GetSeasonTopListsParam();
        p.setCid(44001l);
        p.setCsid(csid);
        List<Long> types = Lists.newArrayList();
        types.addAll(NBA_EAST_REGION_TOPLIST_TYPE);
        types.addAll(NBA_WEST_REGION_TOPLIST_TYPE);
        types.add(NBA_EAST_TOPLIST_TYPE);
        types.add(NBA_WEST_TOPLIST_TYPE);
        p.setTypes(types);
        List<TopListVo> topLists = getSeasonTopLists(p, null, callerParam);
        if (CollectionUtils.isEmpty(topLists)) {
            return Collections.EMPTY_MAP;
        }
        //NBA东部联盟
        List<TopListVo> eastLeague = Lists.newArrayList();
        //NBA西部联盟
        List<TopListVo> westLeague = Lists.newArrayList();
        //NBA东部分区
        List<TopListVo> eastregion = Lists.newArrayList();
        //NBA西部分区
        List<TopListVo> westregion = Lists.newArrayList();
        for(TopListVo topListVo : topLists) {
            if (topListVo.getTypeId() == NBA_EAST_TOPLIST_TYPE) {
                eastLeague.add(topListVo);
            }
            if (topListVo.getTypeId() == NBA_WEST_TOPLIST_TYPE) {
                westLeague.add(topListVo);
            }
            if (NBA_EAST_REGION_TOPLIST_TYPE.contains(topListVo.getTypeId())) {
                eastregion.add(topListVo);
            }
            if (NBA_WEST_REGION_TOPLIST_TYPE.contains(topListVo.getTypeId())) {
                westregion.add(topListVo);
            }
        }
        Map<String, List<TopListVo>> eastNbaListMap = Maps.newHashMap();
        eastNbaListMap.put("league", eastLeague);
        eastNbaListMap.put("region", eastregion);
        Map<String, List<TopListVo>> westNbaListMap = Maps.newHashMap();
        westNbaListMap.put("league", westLeague);
        westNbaListMap.put("region", westregion);
        Map<String, Map<String, List<TopListVo>>> map = Maps.newHashMap();
        map.put("east", eastNbaListMap);
        map.put("west", westNbaListMap);
        return map;
    }

    /**
     * 榜单数据
     * @param id 榜单id
     * @return
     */
    @Override
    public TopListVo getTopListById(long id,CallerParam callerParam) {
        TTopList topList = SbdTopListApis.getTTopListById(id,callerParam);
        if (topList == null) {
            return null;
        }
        return toplistVoCreater.createTopListVo(topList,callerParam);
    }

    @Override
    public List<TopListVo> getSeasonTopLists(GetSeasonTopListsParam p,PageParam pageParam,CallerParam callerParam) {
        List<TopListVo> results = Lists.newArrayList();
        List<TTopList> seasonTopLists = SbdTopListApis.getSeasonTopLists(p,pageParam,callerParam);
        if(CollectionUtils.isEmpty(seasonTopLists)){
            return Collections.EMPTY_LIST;
        }
        for (TTopList tTopList : seasonTopLists) {
            TopListVo topListVo = toplistVoCreater.createTopListVo(tTopList,callerParam);
            results.add(topListVo);
        }
        return results;
    }

    @Override
    public AppCompetitionSeasonTopListVo getSeasonTopLists4App(GetSeasonTopListsParam p,CallerParam callerParam) {
        List<TTopList> seasonTopLists = SbdTopListApis.getSeasonTopLists(p, null, callerParam);
        return toplistVoCreater.createAppCompetitionSeasonTopListVo(p.getCid(), p.getCsid(), callerParam, seasonTopLists);
    }
}
