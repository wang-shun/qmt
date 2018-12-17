package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.service.support.SbdWebService;
import com.lesports.qmt.sbd.vo.CompetitionVo;

import java.util.List;

/**
 * Created by lufei1 on 2016/10/25.
 */
public interface CompetitionWebService extends SbdWebService<CompetitionVo, Long> {

    CompetitionVo findByName(String param, CallerParam callerParam);

    CompetitionVo checkName(String name, CallerParam caller);

    List<CompetitionVo> findByFType(Long fType, CallerParam callerParam);

    boolean publish(Long id, boolean publish, CallerParam caller);

    List<CompetitionVo> getCompetitionsByIds(String ids, CallerParam caller);
}
