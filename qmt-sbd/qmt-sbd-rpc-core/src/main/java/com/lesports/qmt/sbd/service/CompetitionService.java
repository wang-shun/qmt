package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.service.GetCompetitionsParam;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: ellios
 * Time: 15-6-4 : 上午10:18
 */
public interface CompetitionService extends QmtService<Competition, Long> {


    /**
     * 分页查找所有的赛事
     *
     * @param pageable
     * @return
     */
    Page<Competition> findAll(Pageable pageable);


    /**
     * 根据名称查询赛事
     *
     * @param name
     * @return
     */
    Competition findByName(String name);

    /**
     * 根据缩略名称查询赛事
     *
     * @param name
     * @return
     */
    Competition findByAbbreviation(String name);


    TCompetition getTCompetitionById(long id, CallerParam callerParam);

    List<TCompetition> getTCompetitionByIds(List<Long> ids, CallerParam callerParam);

    /**
     * 根据赛事参数查询赛事信息
     *
     * @param p
     * @param pageable
     * @return
     */
    List<Long> getTCompetitonIds(GetCompetitionsParam p, Pageable pageable, CallerParam caller);


    /**
     * 根据code获取赛事
     * @param code
     * @param callerParam
     * @return
     */
    TCompetition getTCompetitionByCode(String code, CallerParam callerParam);

}
