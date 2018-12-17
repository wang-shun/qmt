package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.sbd.param.TopListItemParam;
import com.lesports.qmt.sbd.vo.TopListVo;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lufei1 on 2016/11/17.
 */
@Component
public class TopListItemAdapter {

    public TopListVo.TopListItem toVo(TopListItemParam param) {
        TopListVo.TopListItem topListItem = new TopListVo.TopListItem();
        topListItem.setCompetitorId(param.getCompetitorId());
        topListItem.setCompetitorType(param.getCompetitorType());
        topListItem.setRank(param.getRank());
        topListItem.setTeamId(param.getTeamId());
        Map<String, Object> stats = JSON.parseObject(param.getStats(), Map.class);
        if (MapUtils.isNotEmpty(stats)) {
            topListItem.setStats(stats);
        }
        return topListItem;
    }
}
