package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdCompetitionSeasonInternalApis;
import com.lesports.qmt.sbd.converter.CompetitionSeasonVoConverter;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.service.CompetitionSeasonWebService;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.service.TeamSeasonWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.vo.CompetitionSeasonVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2016/10/27
 */
@Component("competitionSeasonWebService")
public class CompetitionSeasonWebServiceImpl extends AbstractSbdWebService<CompetitionSeasonVo, Long> implements CompetitionSeasonWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionWebServiceImpl.class);

    @Resource
    private TeamSeasonWebService teamSeasonWebService;
    @Resource
    private CompetitionWebService competitionWebService;
    @Resource
    private CompetitionSeasonVoConverter competitionSeasonVoConverter;

    @Override
    public CompetitionSeasonVo findOne(Long id, CallerParam caller) {
        CompetitionSeason competitionSeason = SbdCompetitionSeasonInternalApis.getCompetitionSeasonById(id);
        if (competitionSeason == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return competitionSeasonVoConverter.toTVo(competitionSeason, caller);
    }

    @Override
    public boolean save(CompetitionSeasonVo entity, CallerParam caller) {
        //保存competitionSeason
        boolean isCreate = isCreateOp(entity);
        CompetitionSeason old = null;
        if (entity.getId() != null) {
            old = SbdCompetitionSeasonInternalApis.getCompetitionSeasonById(entity.getId());
        }
        if (old != null) {
            entity.setMultiLangCitys(old.getMultiLangCitys());
        }
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), entity.getCid()));
        // set value into lang string list
        entity.setMultiLangCitys(setValueOfLanguage(entity.getMultiLangCitys(), entity.getCity(), isCreate ? null : caller.getLanguage()));
        // set default value
        entity.setCity(getDefaultValueOfMultiLang(entity.getMultiLangCitys()));
        long id = SbdCompetitionSeasonInternalApis.saveCompetitionSeason(entity.toModel());
        boolean result1 = setEntityId(entity, id);
        //更新teamSeason
        boolean result2 = teamSeasonWebService.updateTeamSeason(entity.getId(), entity.getTids(), caller);
        return result1 && result2;
    }

    @Override

    public boolean save(CompetitionSeasonVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(CompetitionSeasonVo entity) {
        return null;
    }

    @Override
    public CompetitionSeasonVo findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return SbdCompetitionSeasonInternalApis.deleteCompetitionSeason(id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdCompetitionSeasonInternalApis.countCompetitionSeasonsByQuery(query);
    }

    @Override
    public List<CompetitionSeasonVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<CompetitionSeason> competitionSeasons = SbdCompetitionSeasonInternalApis.getCompetitionSeasonsByQuery(query);
        return Lists.transform(competitionSeasons, new Function<CompetitionSeason, CompetitionSeasonVo>() {
            @Nullable
            @Override
            public CompetitionSeasonVo apply(@Nullable CompetitionSeason input) {
                CompetitionSeasonVo competitionSeasonVo = new CompetitionSeasonVo(input);
                Competition competition = competitionWebService.findOne(input.getCid(), caller);
                if (competition != null) {
                    competitionSeasonVo.setcName(competition.getName());
                }
                return competitionSeasonVo;
            }
        });
    }

    @Override
    public QmtPage<CompetitionSeasonVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        return null;
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        return false;
    }

    @Override
    public List<CompetitionSeasonVo> findByCid(long cid, CallerParam caller) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("cid").is(cid));
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.setSort(new Sort(Sort.Direction.DESC, "season"));
        return findByQuery(query, caller);
    }


    @Override
    protected boolean isCreateOp(CompetitionSeasonVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }
}
