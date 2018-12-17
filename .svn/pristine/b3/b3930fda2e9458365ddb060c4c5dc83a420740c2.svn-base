package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.MatchStatus;
import com.lesports.api.common.PageParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.service.GetPlayerMatchesParam;
import com.lesports.qmt.sbd.cache.TDetailMatchCache;
import com.lesports.qmt.sbd.cache.TMatchCache;
import com.lesports.qmt.sbd.converter.TDetailMatchVoConverter;
import com.lesports.qmt.sbd.converter.TMatchVoConverter;
import com.lesports.qmt.sbd.helper.CompetitionSeasonHelper;
import com.lesports.qmt.sbd.helper.DictHelper;
import com.lesports.qmt.sbd.model.*;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import com.lesports.qmt.sbd.repository.MatchRepository;
import com.lesports.qmt.sbd.service.MatchService;
import com.lesports.qmt.sbd.service.TeamService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.qmt.util.TimeOutComputer;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: lufei
 * Time: 2016-10-24 : 下午12:04
 */
@Service("matchService")
public class MatchServiceImpl extends AbstractSbdService<Match, Long> implements MatchService {

    private static final Logger LOG = LoggerFactory.getLogger(MatchServiceImpl.class);

    @Resource
    private MatchRepository matchRepository;
    @Resource
    private TeamService teamService;
    @Resource
    private DictHelper dictHelper;
    @Resource
    private TDetailMatchCache detailMatchCache;
    @Resource
    private TMatchCache matchCache;
    @Resource
    private CompetitionSeasonHelper competitionSeasonHelper;
    @Resource
    private CompetitionSeasonRepository competitionSeasonRepository;
    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private TMatchVoConverter tMatchVoConverter;
    @Resource
    private TDetailMatchVoConverter tDetailMatchVoConverter;


    protected static final int CACHE_EXPIRE_SECONDS = 60 * 60 * 24;


    @Override
    public Match findOne(Long id) {
        return matchRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Match entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Match entity) {
        entity.setId(LeIdApis.nextId(IdType.MATCH));
        entity.setDeleted(false);
        return matchRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Match entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.MATCH.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.SYNC_EPISODE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    public boolean doUpdate(Match entity) {
        boolean result = matchRepository.save(entity);
        if (result) {
            //只更新索引、同步数据；不需要再发SYNC_EPISODE消息
            LeMessage message = LeMessageBuilder.create().
                    setEntityId(entity.getId()).
                    setIdType(IdType.MATCH).
                    setData(JSON.toJSONString(entity)).
                    setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                    build();
            SwiftMessageApis.sendMsgAsync(message);
        }
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Match entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.MATCH.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.SYNC_EPISODE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return matchRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Match entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.MATCH.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected Match doFindExistEntity(Match entity) {
        return matchRepository.findOne(entity.getId());
    }


    private Page<Match> findFootAndBasketballsByTime(String startTime, String endTime, Pageable pageable) {
        Query q = new Query();
        q.addCriteria(where("game_f_type").in(Lists.newArrayList(100015000L, 100014000L)));
        q.addCriteria(where("start_time").gte(startTime).lt(endTime));
        q.addCriteria(where("deleted").is(false));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "start_time");
        return matchRepository.findByQuery4Page(q, pageRequest);
    }


    @Override
    public boolean updateMatchResult(Long id, Set<Match.Competitor> competitors) {
        if (CollectionUtils.isEmpty(competitors)) {
            //没有比分信息，就默认成功吧
            return true;
        }
        Match match = matchRepository.findOne(id);
        if (match == null) {
            LOG.warn("match : {} no exist, fail to update result : {}", id, competitors);
            return false;
        }
        Set<Match.Competitor> newCompetitors = match.getCompetitors();

        if (CollectionUtils.isEmpty(newCompetitors)) {
            newCompetitors = competitors;
        } else {
            newCompetitors.addAll(competitors);
        }
        for (Match.Competitor newCompetitor : newCompetitors) {
            for (Match.Competitor competitor : competitors) {
                if (newCompetitor.equals(competitor)) {
//                    newCompetitor.setResult(competitor.getResult());
                    break;
                }
            }
        }
        return matchRepository.update(id, update("competitors", competitors));
    }


    @Override
    public boolean delete(Long id) {
        Update up = update("deleted", true);
        boolean result = true;
        result = result && matchRepository.update(id, up);
        //删除主节目
        Match match = matchRepository.findBasicOne(id);
        if (CollectionUtils.isNotEmpty(match.getEids())) {
            //给消息中心发送消息删除相关节目
            LeMessage message = LeMessageBuilder.create().
                    setEntityId(match.getId()).
                    setIdType(IdType.PLAYER).
                    setData(JSON.toJSONString(match)).
                    setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                    build();
        }
        //清缓存
        detailMatchCache.delete(id);
        matchCache.delete(id);
        return result;
    }



    @Override
    public  Match getMatchByPartner(Partner partner) {
        Query query = new Query();
        Criteria partnerCriteria = new Criteria("partners");
        partnerCriteria.elemMatch(where("partners._id").is(partner.getId())).andOperator(where("partners.type").is(partner.getType()));
        query.addCriteria(partnerCriteria);
        query.addCriteria(where("deleted").is(false));
        List<Match> matches = matchRepository.findByQuery(query);
        if (CollectionUtils.isNotEmpty(matches)) {
            return matches.get(0);
        }
        return null;
    }


    @Override
    public List<Match> findByGameTypeAndStatus(long gameType, MatchStatus status, Pageable pageable) {
        Query q = query(where("deleted").is(false));
        if (status != null) {
            q.addCriteria(where("status").is(status));
        }
        Boolean successAdd = addGameTypeCriteria(q, gameType);
        if (!successAdd) {
            return Collections.EMPTY_LIST;
        }
        q.with(pageable);
        return matchRepository.findByQuery(q);
    }


    private Boolean addGameTypeCriteria(Query q, long gameType) {
        if (gameType <= 0) {
            return true;
        }
        IdType idType = LeIdApis.checkIdTye(gameType);
        if (idType == null) {
            LOG.warn("illegal gameType : {}", gameType);
            return false;
        }
        if (idType == IdType.COMPETITION) {
            q.addCriteria(where("cid").is(gameType));
        } else if (idType == IdType.DICT_ENTRY) {
            DictEntryTopType entryType = dictHelper.getDictEntryTopType(gameType);
            if (entryType == DictEntryTopType.GAME_FIRST_TYPE) {
                q.addCriteria(where("game_f_type").is(gameType));
            } else {
                LOG.warn("illegal gameType : {}", gameType);
                return false;
            }
        } else {
            LOG.warn("illegal gameType : {}", gameType);
            return false;
        }
        return true;
    }


    @Override
    public List<TMatch> getTMatchesByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        if (ids.size() > QmtConstants.MAX_BATCH_SIZE) {
            ids = ids.subList(0, QmtConstants.MAX_BATCH_SIZE);
        }
        List<TMatch> matches = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TMatch match = getTMatchById(id, caller);
            if (match != null) {
                matches.add(match);
            }
        }
        return matches;
    }

    @Override
    public TMatch getTMatchById(long id, CallerParam caller) {
        TMatch vo = matchCache.findOne(id);
        if (vo == null) {
            Match match = matchRepository.findOne(id);
            if (match == null) {
                LOG.warn("fail to getTMatchById, match no exists. id : {}, caller : {}", id, caller);
                return null;
            }

            vo = tMatchVoConverter.toDto(match);
            LOG.info("toDto vo eid: {}",vo.getEids().get(0).getId());
            if (vo == null) {
                LOG.warn("fail to getTMatchById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            matchCache.save(vo, CACHE_EXPIRE_SECONDS);

            TMatch vo1 = matchCache.findOne(id);
            LOG.info("cache vo eid: {}",vo1.getEids().get(0).getId());
        }
        tMatchVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public TDetailMatch getTDetailMatchById(long id, CallerParam caller) {
        TDetailMatch vo = detailMatchCache.findOne(id);
        if (vo == null) {
            Match match = matchRepository.findOne(id);
            if (match == null) {
                LOG.warn("fail to getTMatchById, match no exists. id : {}, caller : {}", id, caller);
                return null;
            }

            vo = tDetailMatchVoConverter.toDto(match);
            if (vo == null) {
                LOG.warn("fail to getTMatchById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            detailMatchCache.save(vo, CACHE_EXPIRE_SECONDS);
        }
        tDetailMatchVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public List<TDetailMatch> queryTMatches(Match param, long countryId, Pageable page, CallerParam caller) {
        long start = TimeOutComputer.getStartTime();
        Page<Match> matches = this.findByParams4Page(param, countryId, page, caller);
        TimeOutComputer.printTimeOut("queryTMatches", start);

        if (matches != null && matches.getTotalPages() > 0) {
            return tDetailMatchVoConverter.fillTDetailMatchList(matches);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void updateMatchScore(long episodeId, int homeScore, int awayScore) {
        long matchId = LeIdApis.getMatchIdByEpisodeId(episodeId);
        Match match = findOne(matchId);
        if (match != null) {
            Set<Match.Competitor> set = match.getCompetitors();
            Iterator<Match.Competitor> citer = set.iterator();
            while (citer.hasNext()) {
                Match.Competitor c = citer.next();
                if (c.getGround() == GroundType.HOME) {
                    c.setFinalResult(String.valueOf(homeScore));
                } else if (c.getGround() == GroundType.AWAY) {
                    c.setFinalResult(String.valueOf(awayScore));
                }
            }
            match.setCompetitors(set);
            save(match);
        }
    }

    private boolean isValidCompetitorId(long competitorId) {
        IdType type = LeIdApis.checkIdTye(competitorId);
        if (type == IdType.PLAYER || type == IdType.TEAM) {
            return true;
        }
        return false;
    }

    /**
     * 增加赛事tagId至比赛上
     *
     * @param match
     */
    private void addTagIdsToMatch(Match match) {
        if (match.getCid() != null) {
            Competition competition = competitionRepository.findOne(match.getCid());
            Set<Long> tagIds = competition.getTagIds();
            match.setTagIds(tagIds);
        }
    }

    @Override
    public long findMatchIdByStartTimeAndCompetitors(String startTime, long com1, long com2) {
        Query query = new Query(where("competitors.competitor_id").all(com1, com2));
        query.addCriteria(where("start_time").is(startTime));
        Match match = matchRepository.findOneByQuery(query);
        if (null == match) {
            return 0;
        }
        return match.getId();
    }

    @Override
    public List<TMatch> getMatchesByCompetitorId(long competitorId, int csid, Pageable pageable, CallerParam caller) {
        if (!isValidCompetitorId(competitorId)) {
            LOG.error("illage competitorId : {}", competitorId);
            return Collections.EMPTY_LIST;
        }
        Query q1 = query(where("deleted").is(false));
        q1.addCriteria(where("competitors.competitor_id").is(competitorId));
        q1.with(new Sort(Sort.Direction.DESC, "start_time"));

        //随机获取参赛者的一场比赛,来判断赛季信息
        Match match = matchRepository.findBasicOneByQuery(q1);
        if (match == null) {
            LOG.warn("no latest match found for competitor : {}", competitorId);
            return Collections.EMPTY_LIST;
        }
        Query q2 = query(where("deleted").is(false));
        q2.addCriteria(where("competitors.competitor_id").is(competitorId));
        if (match.getCsid() != null) {
            if (csid > 0) {
                //获取某赛季
                q2.addCriteria(where("csid").is(csid));
            } else if (csid == 0) {
                //赛季id为0，获取最新赛季
                CompetitionSeason season = competitionSeasonRepository.findOne(match.getCsid());
                q2.addCriteria(where("csid").is(season.getId()));
            }
        }

        pageable = PageUtils.getValidPageable(pageable);
        q2.with(pageable);

        List<Long> ids = matchRepository.findIdByQuery(q2);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return getTMatchesByIds(ids, caller);
    }

    @Override
    public List<Long> getTheRoadOfAdvanceEids(long cid, long csid, CallerParam caller) {
        List<Long> ids = matchRepository.getTheRoadOfAdvance(cid, csid);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TMatch> tMatches = getTMatchesByIds(ids, caller);
        if (CollectionUtils.isEmpty(tMatches)) {
            return Collections.EMPTY_LIST;
        }
        List<Long> eids = Lists.newArrayList();
        for (TMatch tMatch : tMatches) {
            if (tMatch.getTheRoadOrder() != 0 && tMatch.getEid() != 0) {
                eids.add(tMatch.getEid());
            }
        }
        return eids;
    }

    @Override
    public int getMatchFullRound(long cid, long csid) {
        long round = 0L;
        int roundNumber = 1;
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
//        q.addCriteria(where("status").is(MatchStatus.MATCH_NOT_START));
        if (csid > 0) {
            //获取某赛季
            q.addCriteria(where("csid").is(csid));
        } else if (csid == 0) {
            //赛季id为0，获取最新赛季
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                q.addCriteria(where("csid").is(season.getId()));
            }
        }
        q.with(new Sort(Sort.Direction.DESC, "start_time"));

        Match match = matchRepository.findOneByQuery(q);
        if (null != match && null != match.getRound()) {
            round = match.getRound();
        }
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(round);
        if (null != dictEntry) {
            roundNumber = getRoundNumber(dictEntry.getName());
        }
        return roundNumber;
    }

    @Override
    public int getMatchCurrentRound(long cid, long csid) {
        long round = 0L;
        int roundNumber = 1;
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
        q.addCriteria(where("status").is(MatchStatus.MATCH_END));
        if (csid > 0) {
            //获取某赛季
            q.addCriteria(where("csid").is(csid));
        } else if (csid == 0) {
            //赛季id为0，获取最新赛季
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                q.addCriteria(where("csid").is(season.getId()));
            }
        }
        q.with(new Sort(Sort.Direction.DESC, "start_time"));

        Match match = matchRepository.findOneByQuery(q);
        if (null != match && null != match.getRound()) {
            round = match.getRound();
        }
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(round);
        if (null != dictEntry) {
            roundNumber = getRoundNumber(dictEntry.getName());
        }
        return roundNumber;
    }

    private int getRoundNumber(String dictName) {
        int roundNumber = 1;
        if (dictName.length() == 3) {
            roundNumber = Integer.parseInt(dictName.substring(1, 2));
        } else if (dictName.length() == 4) {
            roundNumber = Integer.parseInt(dictName.substring(1, 3));
        }
        return roundNumber;
    }


    /**
     * 通过comId1,comId2查找历史交锋数据
     *
     * @return
     */
    @Override
    public Page<Match> findMatchByCompetitors(String startTime, long comId1, long comId2, Pageable pageable) {
        Query query = new Query(where("competitors.competitor_id").all(comId1, comId2));
        query.addCriteria(where("start_time").lt(startTime));
        query.addCriteria(where("deleted").is(false));
        query.with(new Sort(Sort.Direction.DESC, "start_time"));
        return matchRepository.findByQuery4Page(query, pageable);
    }

    /**
     * 通过comId查找最近比赛信息
     *
     * @return
     */
    @Override
    public Page<Match> findNearMatchByCid(String startTime, long comId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(where("competitors.competitor_id").is(comId));
        query.addCriteria(where("start_time").lt(startTime));
        query.addCriteria(where("deleted").is(false));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.DESC, "start_time");
        return matchRepository.findByQuery4Page(query, pageRequest);
    }

    /**
     * 通过comId查找以后比赛信息
     *
     * @return
     */
    @Override
    public Page<Match> findAfterMatchByCid(String startTime, long comId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(where("competitors.competitor_id").is(comId));
        query.addCriteria(where("start_time").gt(startTime));
        query.addCriteria(where("deleted").is(false));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "start_time");
        return matchRepository.findByQuery4Page(query, pageRequest);
    }

    private Query buildCriteria(Match match, Long countryId) {
        Query query = new Query();

        if (match != null) {
            if (StringUtils.isNotBlank(match.getName())) {
                Pattern pattern = getNamePattern(match.getName());
                query.addCriteria(where("multi_lang_names.value").regex(pattern));
            }
            if (match.getCid() != null && match.getCid() > 0) {
                query.addCriteria(where("cid").is(match.getCid()));
            }
            if (match.getId() != null && match.getId() > 0) {
                query.addCriteria(where("id").is(match.getId()));
            }
            if (match.getCsid() != null) {
                query.addCriteria(where("csid").is(match.getCsid()));
            }
            if (StringUtils.isNotBlank(match.getStartTime()) && StringUtils.isBlank(match.getEndTime())) {
                query.addCriteria(where("start_time").gte(match.getStartTime()));
            } else if (StringUtils.isBlank(match.getStartTime()) && StringUtils.isNotBlank(match.getEndTime())) {
                query.addCriteria(where("start_time").lt(match.getEndTime()));
            } else if (StringUtils.isNotBlank(match.getStartTime()) && StringUtils.isNotBlank(match.getEndTime())) {
                query.addCriteria(where("start_time").gte(match.getStartTime()).lt(match.getEndTime()));
            }
            if (StringUtils.isNotBlank(match.getStartDate())) {
                query.addCriteria(where("start_date").is(match.getStartDate()));
            }
            if (StringUtils.isNotBlank(match.getLocalStartDate())) {
                query.addCriteria(where("local_start_date").is(match.getLocalStartDate()));
            }
            if (match.getMedalType() != null) {
                query.addCriteria(where("medal_type").is(match.getMedalType()));
            }
            if (match.getGameFType() != null && match.getGameFType() > 0) {
                query.addCriteria(where("game_f_type").is(match.getGameFType()));
            }
            if (match.getDiscipline() != null && match.getDiscipline() > 0) {
                query.addCriteria(where("discipline").is(match.getDiscipline()));
            }
            if (match.getGameSType() != null && match.getGameSType() > 0) {
                query.addCriteria(where("game_s_type").is(match.getGameSType()));
            }
        }
        if (countryId != null && countryId > 0) {
            query.addCriteria(where("competitors.competitor_country_id").is(countryId));
        }
        query.addCriteria(where("deleted").is(false));
        return query;
    }

    @Override
    public boolean deleteRpcCache(long id) {
        return detailMatchCache.delete(id) & matchCache.delete(id);
    }

    @Override
    public boolean updateMatchSyncToCloud(long matchId, Boolean syncToCloud) {
        if (!matchRepository.updateMSyncToCloud(matchId, syncToCloud)) {
            LOG.warn("fail to updateMatchSyncToCloud. matchId : {}, syncToCloud : {}", matchId, syncToCloud);
            return false;
        }
        //删除缓存记录
        detailMatchCache.delete(matchId);
        matchCache.delete(matchId);
        LOG.info("success to updateMatchSyncToCloud. matchId : {}, syncToCloud : {}", matchId, syncToCloud);
        return true;
    }

    /**
     * 对比赛程的开始时间: 只要开始时间在同一小时内则视为是同一开始时间
     *
     * @param leMatchTime
     * @param octopusMatchTime
     * @return
     */
    private Boolean checkMatchTime(String leMatchTime, String octopusMatchTime) {
        Date leDate = LeDateUtils.parseYYYYMMDDHHMMSS(leMatchTime);
        Date octopusDate = LeDateUtils.parseYMDHMS(octopusMatchTime);
        long subtract = leDate.getTime() - octopusDate.getTime();
        return -60 * 60 * 2 * 1000 < subtract && subtract < 60 * 60 * 2 * 1000;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return matchRepository;
    }

    @Override
    public List<TDetailMatch> getTDetailMatchesByPid(long pid, MatchStatus status, PageParam page, CallerParam caller) {
        if (!isValidCompetitorId(pid)) {
            LOG.error("illage pid : {}", pid);
            return Collections.EMPTY_LIST;
        }

        Query q = query(where("deleted").is(false));
        q.addCriteria(where("squads.players.pid").is(pid));
        q.addCriteria(where("status").is(status));
        q.with(new Sort(Sort.Direction.DESC, "start_time"));

        Pageable pageable = PageUtils.convertToPageable(page);
        q.with(pageable);

        List<Long> ids = matchRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }

        List<TDetailMatch> detailMatches = Lists.newArrayList();
        for (Long id : ids) {
            TDetailMatch detailMatch = getTDetailMatchById(id, caller);
            detailMatches.add(detailMatch);
        }
        return detailMatches;
    }

    @Override
    public List<TDetailMatch> getTDetailMatches(GetPlayerMatchesParam p, PageParam page, CallerParam caller) {
        if (p.getPid() <= 0 || !isValidCompetitorId(p.getPid())) {
            LOG.error("illage pid : {}", p.getPid());
            return Collections.EMPTY_LIST;
        }

        Query q = query(where("deleted").is(false));
        q.addCriteria(where("squads.players.pid").is(p.getPid()));

        if (p.getStatus() != null) {
            q.addCriteria(where("status").is(p.getStatus()));
        }

        if (p.getCid() > 0) {
            q.addCriteria(where("cid").is(p.getCid()));
        }

        if (p.getCsid() > 0) {
            q.addCriteria(where("csid").is(p.getCsid()));
        }

        if (p.getStage() > 0) {
            q.addCriteria(where("stage").is(p.getStage()));
        }

        Pageable pageable = PageUtils.convertToPageable(page);
        if (pageable.getSort() == null) {
            q.with(new Sort(Sort.Direction.DESC, "start_time"));
        }
        q.with(pageable);

        List<Long> ids = matchRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }

        List<TDetailMatch> detailMatches = Lists.newArrayList();
        for (Long id : ids) {
            TDetailMatch detailMatch = getTDetailMatchById(id, caller);
            detailMatches.add(detailMatch);
        }
        return detailMatches;
    }

    @Override
    public List<Long> getMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, PageParam page) {
        Pageable pageable = PageUtils.convertToPageable(page);
        return matchRepository.findMatchIdsByCidAndMetaEntryId(cid, csid, entryId, pageable);
    }


    @Override
    public void updateMatchsByCompetitionWhenItChange(Competition competition) {

    }

    @Override
    public List<Match> findByCompetitorIdAndCsid(Long competitorId, Long csid) {
        return matchRepository.findByCompetitorIdAndCsid(competitorId, csid);
    }

    @Override
    public List<Match> findByCIdAndCsidByStatus(Long cid, Long csid, Boolean stated, String matchTime, MatchStatus status) {
        return matchRepository.findByCIdAndCsidByStatus(cid, csid, stated, matchTime, status);
    }

    @Override
    public Page<Match> findByCompetitorId(Long competitorId, Pageable pageable) {
        return matchRepository.findByCompetitorId(competitorId, pageable);
    }

    @Override
    public Page<Match> findByCid(Long cid, Pageable pageable) {
        return matchRepository.findByCid(cid, pageable);
    }

    @Override
    public Page<Match> findByCsid(Long csid, Pageable pageable) {
        return matchRepository.findByCsid(csid, pageable);
    }

    @Override
    public List<Long> findEidsByParams4Page(Match param, long countryId, Pageable page, CallerParam caller) {
        Query query = buildCriteria(param, countryId);
        return matchRepository.findEidListByQuery(query, page);
    }

    @Override
    public Page<Match> findByParams4Page(Match param, long countryId, Pageable page, CallerParam caller) {
        Query query = buildCriteria(param, countryId);
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(),
                Sort.Direction.ASC, "start_time");
        return matchRepository.findByQuery4Page(query, pageRequest);
    }
}