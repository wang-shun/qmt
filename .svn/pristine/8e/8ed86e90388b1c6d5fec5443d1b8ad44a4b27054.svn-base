package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.converter.CompetitionVoConverter;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
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
 * Created by lufei1 on 2016/10/25.
 */
@Component("competitionWebService")
public class CompetitionWebServiceImpl extends AbstractSbdWebService<CompetitionVo, Long> implements CompetitionWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionWebServiceImpl.class);

    @Resource
    private CompetitionVoConverter competitionVoConverter;

    @Override
    public CompetitionVo findOne(Long id, CallerParam caller) {
        Competition competition = SbdCompetitionInternalApis.getCompetitionById(id);
        if (competition == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return competitionVoConverter.toTVo(competition, caller);
    }


    @Override
    public boolean save(CompetitionVo entity, CallerParam caller) {
        boolean isCreate = isCreateOp(entity);
        Competition old = null;
        if (entity.getId() != null) {
            old = SbdCompetitionInternalApis.getCompetitionById(entity.getId());
        }
        if (old != null) {
            entity.setMultiLangNames(old.getMultiLangNames());
            entity.setMultiLangAbbrs(old.getMultiLangAbbrs());
            entity.setMultiLangConts(old.getMultiLangConts());
            entity.setMultiLangIntros(old.getMultiLangIntros());
            entity.setMultiLangNations(old.getMultiLangNations());
        }
        // set value into lang string list
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), null));
        entity.setMultiLangNames(setValueOfLanguage(entity.getMultiLangNames(), entity.getName(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangAbbrs(setValueOfLanguage(entity.getMultiLangAbbrs(), entity.getAbbreviation(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangConts(setValueOfLanguage(entity.getMultiLangConts(), entity.getContinent(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangIntros(setValueOfLanguage(entity.getMultiLangIntros(), entity.getIntroduction(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangNations(setValueOfLanguage(entity.getMultiLangNations(), entity.getNation(), isCreate ? null : caller.getLanguage()));
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        entity.setAbbreviation(getDefaultValueOfMultiLang(entity.getMultiLangAbbrs()));
        entity.setContinent(getDefaultValueOfMultiLang(entity.getMultiLangConts()));
        entity.setIntroduction(getDefaultValueOfMultiLang(entity.getMultiLangIntros()));
        entity.setNation(getDefaultValueOfMultiLang(entity.getMultiLangNations()));
        long id = SbdCompetitionInternalApis.saveCompetition(entity.toModel());
        return setEntityId(entity, id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdCompetitionInternalApis.countCompetitionsByQuery(query);
    }

    @Override
    public List<CompetitionVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<Competition> competitions = SbdCompetitionInternalApis.getCompetitionsByQuery(query);
        return Lists.transform(competitions, new Function<Competition, CompetitionVo>() {
            @Nullable
            @Override
            public CompetitionVo apply(@Nullable Competition input) {
                return competitionVoConverter.toTVo(input, caller);
            }
        });
    }

    @Override
    public QmtPage<CompetitionVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        InternalQuery criteria = buildCriteria(params, pageParam);
        return list(criteria, pageParam, caller);
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        return false;
    }

    @Override
    public boolean save(CompetitionVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(CompetitionVo entity) {
        return null;
    }

    @Override
    public CompetitionVo findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return SbdCompetitionInternalApis.deleteCompetition(id);
    }

    protected InternalQuery buildCriteria(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery q = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            long id = MapUtils.getLongValue(params, "id");
            if (id > 0) {
                q.addCriteria(InternalCriteria.where("id").is(id));
            }
            String name = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(name)) {
                Pattern pattern = getNamePattern(name);
                q.addCriteria(
                        new InternalCriteria().orOperator(InternalCriteria.where("multi_lang_names.value").regex(pattern), InternalCriteria.where("multi_lang_abbrs.value").regex(pattern)));
            }
            long gameFType = MapUtils.getLongValue(params, "gameFType");
            if (gameFType > 0) {
                q.addCriteria(InternalCriteria.where("game_f_type").is(gameFType));
            }
            long channelId = MapUtils.getLongValue(params, "channelId");
            if (channelId > 0) {
                q.addCriteria(InternalCriteria.where("channel_id").is(channelId));
            }
            long subChannelId = MapUtils.getLongValue(params, "subChannelId");
            if (subChannelId > 0) {
                q.addCriteria(InternalCriteria.where("sub_channel_id").is(subChannelId));
            }
        }
        q.addCriteria(InternalCriteria.where("deleted").is(false));
        q.with(getValidPageable(pageParam));
        return q;
    }


    @Override
    public CompetitionVo findByName(String name, CallerParam caller) {
        InternalQuery query = new InternalQuery();
        Pattern pattern = getNamePattern(name);
        query.addCriteria(InternalCriteria.where("multi_lang_names.value").regex(pattern));
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        return findOneByQuery(query, caller);
    }

    @Override
    public CompetitionVo checkName(String name, CallerParam caller) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("multi_lang_names.value").is(name));
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        return findOneByQuery(query, caller);
    }

    @Override
    public List<CompetitionVo> findByFType(Long fType, CallerParam callerParam) {
        InternalQuery query = new InternalQuery();
        if (fType != null) {
            query.addCriteria(InternalCriteria.where("game_f_type").is(fType));
        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        return findByQuery(query, callerParam);
    }

    @Override
    public boolean publish(Long id, boolean publish, CallerParam caller) {
        return false;
    }

    @Override
    public List<CompetitionVo> getCompetitionsByIds(String ids, CallerParam caller) {
        List<Competition> competitions = SbdCompetitionInternalApis.getCompetitionsByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(competitions, new Function<Competition, CompetitionVo>() {
            @Nullable
            @Override
            public CompetitionVo apply(@Nullable Competition input) {
                return competitionVoConverter.toTVo(input, caller);
            }
        });
    }


    @Override
    protected boolean isCreateOp(CompetitionVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }


}
