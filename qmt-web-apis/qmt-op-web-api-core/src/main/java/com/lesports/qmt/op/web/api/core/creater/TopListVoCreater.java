package com.lesports.qmt.op.web.api.core.creater;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.op.web.api.core.vo.TopListVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.AssistVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.ScoreVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.ShootVo;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.dto.TTopListItem;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/23.
 */
@Component("topListVoCreater")
public class TopListVoCreater {

    public void createScoreVo(List<ScoreVo> scoreVos, List<TTopListItem> tTopListItems) {
        if (CollectionUtils.isNotEmpty(tTopListItems)) {
            Collections.sort(tTopListItems, new Comparator<TTopListItem>() {
                @Override
                public int compare(TTopListItem o1, TTopListItem o2) {
                    return o1.getShowOrder() - o2.getShowOrder();
                }
            });
            for (TTopListItem item : tTopListItems) {
                ScoreVo scoreVo = new ScoreVo();
                scoreVo.setTeam(item.getCompetitorName());
                Map<String, String> statsMap = item.getStats();
                scoreVo.setWin(statsMap.get("winMatch"));
                scoreVo.setTie(statsMap.get("flatMatch"));
                scoreVo.setLose(statsMap.get("lossMatch"));
                scoreVo.setPoint(statsMap.get("teamScore"));
                scoreVos.add(scoreVo);
            }
        }
    }

    public void createShootVo(List<ShootVo> shootVos, List<TTopListItem> tTopListItems) {
        if (CollectionUtils.isNotEmpty(tTopListItems)) {
            Collections.sort(tTopListItems, new Comparator<TTopListItem>() {
                @Override
                public int compare(TTopListItem o1, TTopListItem o2) {
                    return o1.getShowOrder() - o2.getShowOrder();
                }
            });
            int i = 0;
            for (TTopListItem item : tTopListItems) {
                if (i == 10) {
                    break;
                }
                ShootVo shootVo = new ShootVo();
                shootVo.setPlayer(item.getCompetitorName());
                shootVo.setTeam(item.getTeamName());
                Map<String, String> statsMap = item.getStats();
                shootVo.setShoot(statsMap.get("goals"));
                shootVos.add(shootVo);
                i++;
            }
        }
    }


    public void createAssistVo(List<AssistVo> assistVos, List<TTopListItem> tTopListItems) {
        if (CollectionUtils.isNotEmpty(tTopListItems)) {
            Collections.sort(tTopListItems, new Comparator<TTopListItem>() {
                @Override
                public int compare(TTopListItem o1, TTopListItem o2) {
                    return o1.getShowOrder() - o2.getShowOrder();
                }
            });
            int i = 0;
            for (TTopListItem item : tTopListItems) {
                if (i == 10) {
                    break;
                }
                AssistVo assistVo = new AssistVo();
                assistVo.setPlayer(item.getCompetitorName());
                assistVo.setTeam(item.getTeamName());
                Map<String, String> statsMap = item.getStats();
                assistVo.setAssist(statsMap.get("assists"));
                assistVos.add(assistVo);
                i++;
            }
        }
    }


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
            TDictEntry typeDictEntry = QmtConfigApis.getTDictEntryById(tTopList.getType(), caller);
            topListVo.setListType(typeDictEntry == null ? "" : typeDictEntry.getName());
        } else {
            topListVo.setListType(StringUtils.EMPTY);
        }
        topListVo.setCsid(tTopList.getCsid());
        topListVo.setSeason(tTopList.getSeason());
        topListVo.setGroup(tTopList.getGroup());
        if (tTopList.getCid() > 0) {
            TCompetition competition = SbdCompetitionApis.getTCompetitionById(tTopList.getCid(),caller);
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
                data.setCompetitorTeamId(topList.getTeamId());
                data.setCompetitorOrder(topList.getRank());
                data.setShowOrder(topList.getShowOrder());
                data.setDataMap(topList.getStats());
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
                data.setCompetitorOrder(topList.getRank());
                data.setShowOrder(topList.getShowOrder());
                data.setGroup(tTopList.getGroup());
                data.setDataMap(topList.getStats());
                topListVo.getLists().add(data);
            }
        }
        return topListVo;
    }

}
