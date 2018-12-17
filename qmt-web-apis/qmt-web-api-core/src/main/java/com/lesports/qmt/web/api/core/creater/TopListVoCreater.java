package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.util.UrlParamUtils;
import com.lesports.qmt.web.api.core.vo.AppCompetitionSeasonTopListVo;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.dto.TTopListItem;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/29.
 */
@Component("TopListVoCreater")
public class TopListVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(TopListVoCreater.class);

    /**
     * 获取榜单信息
     * @param tTopList
     * @return
     */
    public TopListVo createTopListVo(TTopList tTopList,CallerParam caller) {
        TopListVo topListVo = new TopListVo();
        topListVo.setId(tTopList.getId());
        topListVo.setCid(tTopList.getCid());
        topListVo.setCname(tTopList.getCname());
        if (tTopList.getType() > 0) {
            topListVo.setTypeId(tTopList.getType());
            TDictEntry typeDictEntry = QmtConfigApis.getTDictEntryById(tTopList.getType(), caller);
            topListVo.setListType(typeDictEntry == null ? "" : typeDictEntry.getName());
        } else {
            topListVo.setListType(StringUtils.EMPTY);
        }
        topListVo.setCsid(tTopList.getCsid());
        topListVo.setSeason(tTopList.getSeason());
        topListVo.setGroup(tTopList.getGroup());
        if (tTopList.getCid() > 0) {
            TCompetition competition = SbdCompetitionApis.getTCompetitionById(tTopList.getCid(), caller);
            if(competition != null){
                topListVo.setLogo(competition.getLogoUrl());
            }
        }
        if(CollectionUtils.isEmpty(tTopList.getItems())){
            return topListVo;
        }
        List<TTopListItem> dataList = Lists.newArrayList(tTopList.getItems());

        //根据showOrder排序
        Collections.sort(dataList, new Comparator<TTopListItem>() {
            @Override
            public int compare(TTopListItem o1, TTopListItem o2) {
                return o1.getShowOrder() - o2.getShowOrder();
            }
        });
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (TTopListItem topList : dataList) {
                TopListVo.TopListVoItem data = topListVo.new TopListVoItem();
                data.setCompetitorId(topList.getCompetitorId());
                data.setCompetitorName(topList.getCompetitorName());
                data.setLogo(topList.getLogoUrl());
                data.setTeamLogo(topList.getTeamLogo());
                data.setCompetitorType(topList.getCompetitorType());
                data.setCompetitorTeamName(topList.getTeamName());
                data.setOfficialName(topList.getTeamOfficialName());
                data.setCompetitorTeamId(topList.getTeamId());
                data.setCompetitorOrder(topList.getRank());
                data.setShowOrder(topList.getShowOrder());
                data.setDataMap(topList.getStats());
                if (topList.getPositionId() > 0) {
                    data.setPosition(topList.getPosition());
                    data.setPositionId(topList.getPositionId());
                }
                data.setNumber(topList.getNumber());
                topListVo.getLists().add(data);
            }
        }
        return topListVo;
    }

    /**
     * 获取榜单信息
     * @param tTopList
     * @return
     */
    public TopListVo createTVTopListVo(TTopList tTopList,CallerParam caller) {
        TopListVo topListVo = new TopListVo();
        topListVo.setId(tTopList.getId());
        topListVo.setCid(tTopList.getCid());
        topListVo.setCname(tTopList.getCname());
        if (tTopList.getType() > 0) {
            TDictEntry typeDictEntry = QmtConfigApis.getTDictEntryById(tTopList.getType(),caller);
            topListVo.setListType(typeDictEntry == null ? "" : typeDictEntry.getName());
        } else {
            topListVo.setListType(StringUtils.EMPTY);
        }
        topListVo.setCsid(tTopList.getCsid());
        topListVo.setSeason(tTopList.getSeason());
        topListVo.setGroup(tTopList.getGroup());
        if (tTopList.getCid() > 0) {
            TCompetition competition = SbdCompetitionApis.getTCompetitionById(tTopList.getCid(), caller);
            if(competition != null){
                topListVo.setLogo(competition.getLogoUrl());
            }
        }
        if(CollectionUtils.isEmpty(tTopList.getItems())){
            return topListVo;
        }
        List<TTopListItem> dataList = Lists.newArrayList(tTopList.getItems());

        //根据showOrder排序
        Collections.sort(dataList, new Comparator<TTopListItem>() {
            @Override
            public int compare(TTopListItem o1, TTopListItem o2) {
                return o1.getShowOrder() - o2.getShowOrder();
            }
        });

        if (CollectionUtils.isNotEmpty(dataList)) {
            int size = dataList.size() < 20?dataList.size():20;
            for (int i = 0;i< size;i++) {
                TTopListItem topList = dataList.get(i);
                TopListVo.TopListVoItem data = topListVo.new TopListVoItem();
                data.setCompetitorId(topList.getCompetitorId());
                data.setCompetitorName(topList.getCompetitorName());
                data.setLogo(topList.getLogoUrl());
                data.setCompetitorType(topList.getCompetitorType());
                data.setCompetitorTeamId(topList.getTeamId());
                data.setCompetitorTeamName(topList.getTeamName());
                data.setOfficialName(topList.getTeamOfficialName());
                data.setCompetitorOrder(topList.getRank());
                data.setShowOrder(topList.getShowOrder());
                data.setGroup(tTopList.getGroup());
                data.setDataMap(topList.getStats());
                topListVo.getLists().add(data);
            }
        }
        return topListVo;
    }

    /**
     * 创建适配app的榜单信息
     * @return
     */
    public AppCompetitionSeasonTopListVo createAppCompetitionSeasonTopListVo(long cid, long csid,CallerParam caller, List<TTopList> toplists) {
        if(cid <= 0){
            LOG.warn("fail to createAppCompetitionSeasonTopListVo. cid below zero.");
            return null;
        }
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(cid,caller);
        if(competition == null){
            LOG.warn("fail to createAppCompetitionSeasonTopListVo. competition : {} is null.");
            return null;
        }
        AppCompetitionSeasonTopListVo csTopListVo = new AppCompetitionSeasonTopListVo();
        csTopListVo.setCid(cid);
        csTopListVo.setCname(competition.getName());
        csTopListVo.setClogo(competition.getLogoUrl());
        if(csid > 0){
            TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(csid, caller);
            if(competitionSeason != null){
                csTopListVo.setCsid(csid);
                csTopListVo.setSeason(competitionSeason.getSeason());
            }
        }
        if(CollectionUtils.isNotEmpty(toplists)){
            for(TTopList topList : toplists){
                if(LeNumberUtils.toLong(csTopListVo.getCsid()) <= 0){
                    csTopListVo.setCsid(topList.getCsid());
                    csTopListVo.setSeason(topList.getSeason());
                }
                //h5URL为空不需要返回给app
                AppCompetitionSeasonTopListVo.AppTopListVo appTopListVo = createAppTopListVo(topList,caller.getCallerId());
                if (StringUtils.isNotBlank(appTopListVo.getH5Url())) {
                    csTopListVo.addTopList(appTopListVo);
                }

            }
        }
        return csTopListVo;
    }


    private AppCompetitionSeasonTopListVo.AppTopListVo createAppTopListVo(TTopList topList, long callerId){
        AppCompetitionSeasonTopListVo.AppTopListVo appTopListVo = new AppCompetitionSeasonTopListVo.AppTopListVo();
        //如果自定义榜单填写了name的话就已后台name为准
        if (StringUtils.isNotBlank(topList.getName())) {
            appTopListVo.setType(topList.getName());
        } else {
            appTopListVo.setType(topList.getTypeName());
        }
        appTopListVo.setTypeId(topList.getType());
        appTopListVo.setToplistId(topList.getId());
        appTopListVo.setDesc(topList.getDesc());
        String url = UrlParamUtils.addCallerToUrl(topList.getUrl(), callerId);
        if (callerId == 3003l) {
            if (url.contains("column")) {
                url = url.replaceAll("column/", "");
            }
        }
        appTopListVo.setH5Url(url);
        return appTopListVo;
    }

}
