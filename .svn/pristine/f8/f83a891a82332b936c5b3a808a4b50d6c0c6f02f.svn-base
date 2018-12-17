package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.common.CareerScopeType;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.model.PlayerCareerStat;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * 球员生涯统计
 * Created by lufei1 on 2016/8/16.
 */
public interface PlayerCareerStatService extends QmtService<PlayerCareerStat, Long> {

    public List<TPlayerCareerStat> getTPlayerCareerStats(long playerId, long scopeId, CareerScopeType scopeType, CallerParam caller);

    public TPlayerCareerStat getTPlayerCareerStatById(long id, CallerParam caller);

    public List<TPlayerCareerStat> getTPlayerCareerStatByIds(List<Long> ids, CallerParam caller);
}
