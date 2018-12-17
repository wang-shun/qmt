package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.service.GetCompetitionsParam;
import com.lesports.qmt.sbd.cache.TCompetitionCache;
import com.lesports.qmt.sbd.converter.TCompetitionVoConverter;
import com.lesports.qmt.sbd.helper.DictHelper;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.service.CompetitionService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import com.letv.urus.liveroom.bo.MatchType;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: lufei
 * Time: 2016-10-24 : 下午12:04
 */
@Service("competitionService")
public class CompetitionServiceImpl extends AbstractSbdService<Competition, Long> implements CompetitionService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private TCompetitionCache competitionCache;
    @Resource
    private TCompetitionVoConverter tCompetitionVoConverter;
    @Resource
    private DictHelper dictHelper;
    //缓存一天
    protected static final int CACHE_EXPIRE_SECONDS = 60 * 60 * 24;

    @Resource
    private LiveRoomSportsWriterAPI liveRoomSportsWriterAPI;

    private static final UrusAuth urusAuth;

    static {
        urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
    }


    @Override
    public Competition findOne(Long id) {
        return competitionRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Competition entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Competition entity) {
        entity.setId(LeIdApis.nextId(IdType.COMPETITION));
        entity.setDeleted(false);
        entity.setCode(Competition.createCode(entity.getId()));
        return competitionRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Competition entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.COMPETITION.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);

        //同步给直播
        MatchType matchType = new MatchType();
        matchType.setName(entity.getName());
        matchType.setMatchtypeid(String.valueOf(entity.getId()));
        try {
            liveRoomSportsWriterAPI.addMatchType(urusAuth, matchType);
            LOG.info("sync competition add to live success, entity: {}", entity);
        } catch (Exception e) {
            LOG.error("failed to call liveRoomSportsWriterAPI when create competition, entity:{}", entity, e);
        }
        return true;
    }

    @Override
    protected boolean doUpdate(Competition entity) {
        return competitionRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Competition entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.COMPETITION.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);

        //同步给直播
        MatchType matchType = new MatchType();
        matchType.setName(entity.getName());
        matchType.setMatchtypeid(String.valueOf(entity.getId()));
        try {
            liveRoomSportsWriterAPI.updateMatchType(urusAuth, matchType);
            LOG.info("sync competition add to live success, entity: {}", entity);
        } catch (Exception e) {
            LOG.error("failed to call liveRoomSportsWriterAPI when update competition, entity:{}", entity, e);
        }
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return competitionRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Competition entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.COMPETITION.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);

        //同步给直播
        try {
            liveRoomSportsWriterAPI.deleteMatchType(urusAuth, String.valueOf(entity.getId()));
            LOG.info("sync competition add to live success, entity: {}", entity);
        } catch (Exception e) {
            LOG.error("failed to call liveRoomSportsWriterAPI when delete competition, entity:{}", entity, e);
        }
        return true;
    }

    @Override
    protected Competition doFindExistEntity(Competition entity) {
        return competitionRepository.findOne(entity.getId());
    }

    @Override
    public Page<Competition> findAll(Pageable pageable) {
        return competitionRepository.findAll(pageable);
    }


    @Override
    protected MongoCrudRepository getInnerRepository() {
        return competitionRepository;
    }

    @Override
    public Competition findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return competitionRepository.findOneByQuery(query);
    }

    @Override
    public Competition findByAbbreviation(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("abbreviation").is(name));
        return competitionRepository.findOneByQuery(query);
    }

    @Override
    public TCompetition getTCompetitionById(long id, CallerParam caller) {
        if (id <= 0) {
            return null;
        }
        TCompetition vo = competitionCache.findOne(id);
        if (vo == null) {
            Competition competition = competitionRepository.findOne(id);
            if (competition == null) {
                LOG.warn("fail to getTCompetitionById, competition no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            vo = tCompetitionVoConverter.toDto(competition);
            if (vo == null) {
                LOG.warn("fail to getTCompetitionById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            competitionCache.save(vo, CACHE_EXPIRE_SECONDS);
        }
        tCompetitionVoConverter.adaptDto(vo, caller);
        return vo;
    }


    @Override
    public List<Long> getTCompetitonIds(GetCompetitionsParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (p.getGameType() > 0) {
            CriteriaDefinition gameTypeCriteria = getGameTypeCriteria(p.getGameType());
            if (gameTypeCriteria == null) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return Collections.EMPTY_LIST;
            }
            q.addCriteria(gameTypeCriteria);
        }
        if (p.getChargeable() >= 0) {
            CriteriaDefinition chargeableCriteria = getChargeableCriteria(p.getChargeable(), caller);
            if (chargeableCriteria == null) {
                LOG.error("illegal chargeable : {}", p.getChargeable());
                return Collections.EMPTY_LIST;
            }
            q.addCriteria(chargeableCriteria);
        }

        addInternationalCriteriaToQuery(q, caller);

        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        List<Long> ids = competitionRepository.findIdByQuery(q);
        LOG.info("ids : {}", ids);
        if (ids == null || ids.size() <= 0) {
            return Collections.EMPTY_LIST;
        }
        return ids;
    }

    @Override
    public TCompetition getTCompetitionByCode(String code, CallerParam caller) {
        Query q = query(where("code").is(code));
        List<Long> ids = competitionRepository.findIdByQuery(q);
        if (ids == null || ids.size() <= 0) {
            return null;
        }
        return getTCompetitionById(ids.get(0), caller);
    }

    @Override
    public List<TCompetition> getTCompetitionByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TCompetition> competitions = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TCompetition competition = getTCompetitionById(id, caller);
            if (competition != null) {
                competitions.add(competition);
            }
        }
        return competitions;
    }

    protected CriteriaDefinition getGameTypeCriteria(long gameType) {
        if (gameType <= 0) {
            return null;
        }
        IdType idType = LeIdApis.checkIdTye(gameType);
        if (idType == null) {
            LOG.warn("illegal gameType : {}", gameType);
            return null;
        }
        if (idType == IdType.COMPETITION) {
            return where("cid").is(gameType);
        } else if (idType == IdType.DICT_ENTRY) {
            DictEntryTopType entryType = dictHelper.getDictEntryTopType(gameType);
            if (entryType == DictEntryTopType.GAME_FIRST_TYPE) {
                return where("game_f_type").is(gameType);
            } else {
                LOG.warn("illegal gameType : {}", gameType);
                return null;
            }
        } else if (idType == IdType.TAG) {
            return where("tag_ids").is(gameType);
        } else {
            LOG.warn("illegal gameType : {}", gameType);
            return null;
        }
    }

    private CriteriaDefinition getChargeableCriteria(int chargeable, CallerParam caller) {
        if (chargeable == 0) {
            return where("chargeable_countries").is(caller.getCountry());
        } else if (chargeable == 1) {
            return where("chargeable_countries").is(caller.getCountry());
        }
        return null;
    }

}