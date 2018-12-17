package com.lesports.qmt.op.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.creater.TopListVoCreater;
import com.lesports.qmt.op.web.api.core.service.TopListService;
import com.lesports.qmt.op.web.api.core.vo.TopListVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.AssistVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.ScoreVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.ShootVo;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.client.SbdTopListApis;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/23.
 */
@Service("topListService")
public class TopListServiceImpl implements TopListService {

    private static final Logger LOG = LoggerFactory.getLogger(TopListServiceImpl.class);

    private static final long SCORE_DIC = 100162000L;

    private static final long SHOOT_DIC = 100160000L;

    private static final long ASSIST_DIC = 100161000L;

    @Resource
    private TopListVoCreater topListVoCreater;

    @Override
    public Map<String, Object> getTopListForBaidu(GetSeasonTopListsParam p,PageParam page,CallerParam caller) {
        Map<String, Object> resultMap = Maps.newHashMap();

        List<TTopList> tTopLists = SbdTopListApis.getSeasonTopLists(p, page, caller);
        List<ScoreVo> scoreVos = Lists.newArrayList();
        List<ShootVo> shootVos = Lists.newArrayList();
        List<AssistVo> assistVos = Lists.newArrayList();
        for (TTopList tTopList : tTopLists) {
            if (tTopList.getType() == SCORE_DIC) {
                topListVoCreater.createScoreVo(scoreVos, tTopList.getItems());
            }
            if (tTopList.getType() == SHOOT_DIC) {
                topListVoCreater.createShootVo(shootVos, tTopList.getItems());
            }
            if (tTopList.getType() == ASSIST_DIC) {
                topListVoCreater.createAssistVo(assistVos, tTopList.getItems());
            }
        }
        resultMap.put("score", scoreVos);
        resultMap.put("shoot", shootVos);
        resultMap.put("assist", assistVos);
        return resultMap;
    }

    /**
     *  获取榜单数据
     * @param p
     * @param pageParam
     * @param callerParam
     * @return
     */
    @Override
    public TopListVo getSeasonTopList(GetSeasonTopListsParam p,PageParam pageParam,CallerParam callerParam) {

        List<TTopList> tTopLists = SbdTopListApis.getSeasonTopLists(p,pageParam,callerParam);
        if(CollectionUtils.isNotEmpty(tTopLists)){
            TTopList topList = tTopLists.get(0);
            return topListVoCreater.createTopListVo(topList,callerParam);
        }
        return null;

    }
}
