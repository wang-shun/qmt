package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdMatchInternalApis;
import com.lesports.qmt.sbd.converter.MatchVoConverter;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.service.MatchWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.utils.QmtSearchApis;
import com.lesports.qmt.sbd.utils.QmtSearchData;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import com.lesports.qmt.sbd.vo.MatchVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lufei1 on 2016/11/3.
 */
@Component("matchWebService")
public class MatchWebServiceImpl extends AbstractSbdWebService<MatchVo, Long> implements MatchWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchWebServiceImpl.class);

    @Resource
    private MatchVoConverter matchVoConverter;
    @Resource
    private CompetitionWebService competitionWebService;

    @Override
    protected boolean isCreateOp(MatchVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public MatchVo findOne(Long id, CallerParam caller) {
        Match match = SbdMatchInternalApis.getMatchById(id);
        if (match == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return matchVoConverter.toTVo(match, caller);
    }

    @Override
    public boolean save(MatchVo entity, CallerParam caller) {
        boolean isCreate = isCreateOp(entity);
        Match old = null;
        if (entity.getId() != null) {
            old = SbdMatchInternalApis.getMatchById(entity.getId());
        }
        if (old != null) {
            entity.setMultiLangNames(old.getMultiLangNames());
            entity.setMultiLangCitys(old.getMultiLangCitys());
            entity.setMultiLangJudges(old.getMultiLangJudges());
            entity.setMultiLangVenues(old.getMultiLangVenues());
            entity.setMultiLangWeathers(old.getMultiLangWeathers());
        }
        // entity.setCreateCountry(caller.getCountry());
        // entity.setCreateLanguages(Lists.newArrayList(caller.getLanguage()));
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), entity.getCid()));
        // set value into lang string list
        entity.setMultiLangNames(setValueOfLanguage(entity.getMultiLangNames(), entity.getName(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangCitys(setValueOfLanguage(entity.getMultiLangCitys(), entity.getCity(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangJudges(setValueOfLanguage(entity.getMultiLangJudges(), entity.getJudge(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangVenues(setValueOfLanguage(entity.getMultiLangVenues(), entity.getVenue(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangWeathers(setValueOfLanguage(entity.getMultiLangWeathers(), entity.getWeather(), isCreate ? null : caller.getLanguage()));
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        entity.setCity(getDefaultValueOfMultiLang(entity.getMultiLangCitys()));
        entity.setJudge(getDefaultValueOfMultiLang(entity.getMultiLangJudges()));
        entity.setVenue(getDefaultValueOfMultiLang(entity.getMultiLangVenues()));
        entity.setWeather(getDefaultValueOfMultiLang(entity.getMultiLangWeathers()));
        long id = SbdMatchInternalApis.saveMatch(entity.toModel());
        return setEntityId(entity, id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdMatchInternalApis.countMatchesByQuery(query);
    }

    @Override
    public List<MatchVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<Match> matches = SbdMatchInternalApis.getMatchesByQuery(query);
        return Lists.transform(matches, new Function<Match, MatchVo>() {
            @Nullable
            @Override
            public MatchVo apply(@Nullable Match input) {
                try {
                    return matchVoConverter.toTVo(input, caller);
                } catch (Exception e) {
                    LOGGER.error("match : {} converter error", input, e);
                }
                return null;
            }
        });
    }

    @Override
    public QmtPage<MatchVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        params.put("sortBy", "startTime");
        params.put("desc", true);
        QmtSearchData qmtSearchData = QmtSearchApis.searchData(params, Match.class);
        List<Match> matches = qmtSearchData.getRows();
        List<MatchVo> matchVos = Lists.transform(matches, new Function<Match, MatchVo>() {
            @Nullable
            @Override
            public MatchVo apply(@Nullable Match input) {
                MatchVo matchVo = new MatchVo(input);
                try {
                    CompetitionVo competitionVo = competitionWebService.findOne(input.getCid(), caller);
                    if (competitionVo != null) {
                        matchVo.setCname(competitionVo.getName());
                        matchVo.setGameFName(competitionVo.getGameFName());
                    }
                    if (CollectionUtils.isNotEmpty(input.getEids())) {
                        matchVo.setEid(CallerUtils.getIdofCountryAndLanguage(input.getEids(), caller.getCountry(), caller.getLanguage()));
                    }
                    matchVo.setOnline(CallerUtils.isOnline(input.getOnlineLanguages(), caller.getLanguage()));
                } catch (Exception e) {
                    LOGGER.error("match converter vo error. match : {}", input, e);
                }
                return matchVo;
            }
        });
        return new QmtPage<MatchVo>(matchVos, pageParam, qmtSearchData.getTotal());
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        Match match = SbdMatchInternalApis.getMatchById(id);
        if (match != null) {
            match.setOnlineLanguages(setOnlineLanguages(match.getOnlineLanguages(), online, caller.getLanguage()));
            return SbdMatchInternalApis.saveMatch(match, true) > 0;
        }
        return false;
    }

    protected InternalQuery buildCriteria(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            long id = MapUtils.getLongValue(params, "id");
            if (id > 0) {
                query.addCriteria(InternalCriteria.where("id").is(id));
            }
            String name = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(name)) {
                Pattern pattern = getNamePattern(name);
                query.addCriteria(InternalCriteria.where("multi_lang_names.value").regex(pattern));
            }
            long gameFType = MapUtils.getLongValue(params, "gameFType");
            if (gameFType > 0) {
                query.addCriteria(InternalCriteria.where("game_f_type").is(gameFType));
            }

            long cid = MapUtils.getLongValue(params, "cid");
            if (cid > 0) {
                query.addCriteria(InternalCriteria.where("cids").is(cid));
            }

            String startTime = params.get("startTime") != null ? LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(params.get("startTime").toString()) : null;
            String endTime = params.get("endTime") != null ? LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(params.get("endTime").toString()) : null;

            if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                query.addCriteria(InternalCriteria.where("start_time").gte(startTime).lte(endTime));
            } else if (StringUtils.isNotBlank(startTime)) {
                query.addCriteria(InternalCriteria.where("start_time").gte(startTime));
            } else if (StringUtils.isNotBlank(endTime)) {
                query.addCriteria(InternalCriteria.where("start_time").lte(endTime));
            }
        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.with(getValidPageable(pageParam));
        return query;
    }

    @Override
    public boolean save(MatchVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(MatchVo entity) {
        return null;
    }

    @Override
    public MatchVo findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return SbdMatchInternalApis.deleteMatch(id);
    }

    @Override
    public List<MatchVo> getMatchesByIds(String ids, CallerParam caller) {
        List<Match> matches = SbdMatchInternalApis.getMatchesByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(matches, new Function<Match, MatchVo>() {
            @Nullable
            @Override
            public MatchVo apply(@Nullable Match input) {
                return matchVoConverter.toTVo(input, caller);
            }
        });
    }
}
