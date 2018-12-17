package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.*;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.ChatRoomInfo;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.common.TicketType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.cache.TComboEpisodeCache;
import com.lesports.qmt.sbc.cache.TSimpleEpisodeCache;
import com.lesports.qmt.sbc.converter.TComboEpisodeConverter;
import com.lesports.qmt.sbc.converter.TSimpleEpisodeVoConverter;
import com.lesports.qmt.sbc.helper.CallerHelper;
import com.lesports.qmt.sbc.helper.EpisodeHelper;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.repository.EpisodeRepository;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.qmt.sbc.utils.KVCacheUtils;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.SbdCompetitionSeasonInternalApis;
import com.lesports.qmt.sbd.SbdMatchInternalApis;
import com.lesports.qmt.sbd.api.common.MatchSystem;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

//import com.lesports.qmt.sbc.config.Auth;
//import com.letv.urus.liveroom.exception.UrusLiveRoomException;

/**
 * Created by lufei1 on 2016/11/4.
 */
@Service("episodeService")
public class EpisodeServiceImpl extends AbstractSbcService<Episode, Long> implements EpisodeService {

    protected static final Logger LOG = LoggerFactory.getLogger(EpisodeServiceImpl.class);

    public static final Utf8KeyCreater<String> RELATED_EPISODES_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_RELATED_EPISODES_TF_");

    //缓存一天
    protected static final int RELATED_EPISODE_CACHE_EXPIRE_TIME = 3600 * 24;
//    @Resource
//    LiveShieldConfigAPI liveShieldConfigAPI;

    @Resource
    protected EpisodeRepository episodeRepository;

    @Resource
    protected EpisodeHelper episodeHelper;

    @Resource
    protected CallerHelper callerHelper;

    @Resource
    protected TComboEpisodeCache comboEpisodeCache;

    @Resource
    protected TSimpleEpisodeCache simpleEpisodeCache;

    @Resource
    protected TComboEpisodeConverter tComboEpisodeConverter;

    @Resource
    protected TSimpleEpisodeVoConverter simpleEpisodeVoConverter;

    @Override
    protected MongoCrudRepository<Episode, Long> getInnerRepository() {
        return episodeRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Episode entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0 || findOne(entity.getId()) == null) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    public boolean updateLive(Long episodeId, Live live) {
        long id = LeNumberUtils.toLong(live.getId());
        if (id <= 0) {
            return false;
        }
        Episode episode = episodeRepository.findOne(episodeId);
        if (null == episode) {
            return false;
        }
        for (Episode.LiveStream liveStream : episode.getLiveStreams()) {
            //更新直播流
            if (LeNumberUtils.toLong(liveStream.getId()) == id) {
                liveStream.setPlayPlatforms(live.getPlayPlatforms());
                liveStream.setPayPlatforms(live.getPayPlatforms());
                liveStream.setIsPay(live.getIsPay());
                return doUpdate(episode);
            }
        }
        //新建的直播流
        Episode.LiveStream liveStream = new Episode.LiveStream();
        liveStream.setId(live.getId());
        liveStream.setIsPay(live.getIsPay());
        liveStream.setPlayPlatforms(live.getPlayPlatforms());
        liveStream.setPayPlatforms(live.getPayPlatforms());
        episode.getLiveStreams().add(liveStream);
        liveStream.setOrder(episode.getLiveStreams().size());
        return doUpdate(episode);
    }

    @Override
    public boolean deleteLive(Long episodeId, Long liveId) {
        long id = LeNumberUtils.toLong(liveId);
        if (id <= 0) {
            return false;
        }
        Episode episode = episodeRepository.findOne(episodeId);
        if (null == episode) {
            return false;
        }
        Iterator<Episode.LiveStream> liveStreamIterator = episode.getLiveStreams().iterator();
        while (liveStreamIterator.hasNext()) {
            Episode.LiveStream liveStream = liveStreamIterator.next();
            if (LeNumberUtils.toLong(liveStream.getId()) == id) {
                liveStreamIterator.remove();
                return doUpdate(episode);
            }
        }
        return true;
    }

    @Override
    protected boolean doCreate(Episode entity) {
        entity.setId(LeIdApis.nextId(IdType.EPISODE));
        entity.setDeleted(false);
        //直播唯一标识
        entity.setLiveUniqueId(createLiveUniqueId(entity));
        //同步聊天室信息
        syncChatRoom(entity, null);
        //根据一些情况重置一些属性
        doResetEpisodeWhenSave(entity);
        return episodeRepository.save(entity);
    }

    /**
     * 创建大乐API所需的聊天室信息
     *
     * @param entity
     * @param existsEntity
     * @return
     */
    private ChatRoomInfo createChatRoomInfo(Episode entity, Episode existsEntity) {
        /**
         * 1、新增节目 以节目开始时间创建聊天室
         * 2、修改节目
         *  2.1）修改聊天室时间 以修改的时间为准
         *  2.2）节目时间变更   以节目开始时间变更聊天室时间
         *  2.3）先关闭聊天室，又开启聊天室。如果有时间以此创建聊天室，若没有以节目开始时间创建聊天室
         */
        ChatRoomInfo info = new ChatRoomInfo();
        info.setEid(entity.getId());
        info.setTitle(entity.getName());
        //新增节目
        if (existsEntity == null) {
            String episodeStartTime = entity.getStartTime();
            if (StringUtils.isEmpty(episodeStartTime)) {
                episodeStartTime = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
            }
            info.setStartTime(episodeStartTime, -3600 * 2);
            info.setEndTime(episodeStartTime, 3600 * 24);
            //修改节目
        } else {
            Episode.ChatRoom chatRoom = entity.getChatRoom();
            if (chatRoom == null || StringUtils.isBlank(chatRoom.getStartTime()) || StringUtils.isBlank(chatRoom.getEndTime())) {
                LOG.warn("episode :{} chatRoom startTime or endTime is null.", entity);
                return null;
            }
            //先关闭又开启
            Episode.ChatRoom exitsChatRoom = existsEntity.getChatRoom();
            if (exitsChatRoom == null) {
                info.setStartTime(chatRoom.getStartTime(), 0);
                info.setEndTime(chatRoom.getEndTime(), 0);
            } else {
                info.setRoomId(exitsChatRoom.getChatRoomId());
                //修改聊天室时间 以修改的时间为准
                if (!exitsChatRoom.getStartTime().equals(chatRoom.getStartTime()) ||
                        !exitsChatRoom.getEndTime().equals(chatRoom.getEndTime())) {
                    info.setStartTime(chatRoom.getStartTime(), 0);
                    info.setEndTime(chatRoom.getEndTime(), 0);
                }
                //节目时间变更   以节目开始时间变更聊天室时间
                if (StringUtils.isNotEmpty(entity.getStartTime()) && StringUtils.isNotEmpty(existsEntity.getStartTime())
                        && entity.getStartTime().compareTo(existsEntity.getStartTime()) < 0) {
                    String episodeStartTime = entity.getStartTime();
                    if (StringUtils.isEmpty(episodeStartTime)) {
                        episodeStartTime = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
                    }
                    info.setStartTime(episodeStartTime, -3600 * 2);
                    info.setEndTime(episodeStartTime, 3600 * 24);
                }
            }
        }
        if (info.getStartTime() == null || info.getEndTime() == null) {
            return null;
        }
        return info;
    }

    /**
     * 同步聊天室信息
     *
     * @param entity
     */
    private void syncChatRoom(Episode entity, Episode existsEntity) {
        //创建时候关闭聊天室
        if (entity.getChatRoom() != null && !entity.getChatRoom().getOpen() && existsEntity == null) {
            entity.setChatRoom(null);
            //聊天室由开启转为关闭
        } else if (entity.getChatRoom() != null && !entity.getChatRoom().getOpen() && existsEntity.getChatRoom() != null) {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("roomId", entity.getChatRoom().getChatRoomId());
            Boolean result = ChatRoomApis.destroyChatRoom(map);
            if (result) {
                entity.setChatRoom(null);
            }
            //聊天室创建or更新
        } else {
            ChatRoomInfo chatRoomInfo = createChatRoomInfo(entity, existsEntity);
            if (chatRoomInfo == null) {
                return;
            }
            //创建/修改聊天室
            String roomId = ChatRoomApis.saveChatRoom(chatRoomInfo);
            Episode.ChatRoom chatRoom = entity.getChatRoom();
            if (StringUtils.isNotBlank(roomId)) {
                if (chatRoom == null) {
                    chatRoom = new Episode.ChatRoom();
                }
                chatRoom.setChatRoomId(roomId);
                chatRoom.setStartTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(chatRoomInfo.getStartTime() * 1000)));
                chatRoom.setEndTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(chatRoomInfo.getEndTime() * 1000)));
                entity.setChatRoom(chatRoom);
            }
        }
    }

    private String createLiveUniqueId(Episode entity) {
        if (entity.getCid() == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("04");
        Competition competition = SbdCompetitionInternalApis.getCompetitionById(entity.getCid());
        if (null != competition) {
            sb.append(competition.getCode());
        } else {
            sb.append(Competition.createCode(entity.getCid()));
        }
        CompetitionSeason competitionSeason = SbdCompetitionSeasonInternalApis.getCompetitionSeasonById(entity.getCsid());
        if (null != competitionSeason) {
            sb.append(competitionSeason.getSeason());
        } else {
            sb.append("0000");
        }
        sb.append("0000000000");
        if (entity.getAllowCountry() != null) {
            sb.append(substituteZero(entity.getAllowCountry().getValue() + "", 2));
        } else {
            sb.append(substituteZero(CallerUtils.getDefaultCountry().getValue() + "", 2));
        }
        return sb.append(entity.getMid()).toString();
    }

    private String substituteZero(String cid, int number) {
        for (int i = cid.length(); i < number; i++) {
            cid = "0" + cid;
        }
        return cid;
    }


    @Override
    protected boolean doAfterCreate(Episode entity) {
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.EPISODE.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.EPISODE)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Episode entity) {
        Episode existEntity = doFindExistEntity(entity);
        //直播唯一标识
        entity.setLiveUniqueId(createLiveUniqueId(existEntity));
        //同步聊天室信息
        syncChatRoom(entity, existEntity);
        //根据一些情况重置一些属性
        doResetEpisodeWhenSave(entity);
        boolean result = episodeRepository.save(entity);

        //保存时，屏蔽直播流
//        try {
//            liveShieldConfigAPI.saveLiveShield(Auth.getUrusAuth(), getLiveShieldConfigDTOFromEpisode(entity));
//        } catch (UrusLiveRoomException ulre) {
//        }

        LOG.info("success to save episode, episodeId : {}, toSaveEntity : {}, result : {}", entity.getId(), entity, result);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Episode entity) {
        comboEpisodeCache.delete(entity.getId());
        simpleEpisodeCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.EPISODE.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.EPISODE)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        return episodeRepository.delete(id);
    }

    @Override
    protected boolean doAfterDelete(Episode entity) {
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.EPISODE.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.EPISODE)
                .setBusinessTypes(ActionType.DELETE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected Episode doFindExistEntity(Episode entity) {
        return episodeRepository.findOne(entity.getId());
    }

    /**
     * @param toSaveEntity
     */
    private void doResetEpisodeWhenSave(Episode toSaveEntity) {
        episodeHelper.resetEpisodeStatus(toSaveEntity);
        //更新图文直播状态
        if (CollectionUtils.isNotEmpty(toSaveEntity.getTextLives())) {
            episodeHelper.resetTextLiveEpisodeStatus(toSaveEntity);
        }
        //设置比赛日期,方便后期查找
        if (StringUtils.isNotEmpty(toSaveEntity.getStartTime())) {
            toSaveEntity.setStartDate(toSaveEntity.getStartTime().substring(0, 8));
        }
        //是否有视频直播
        if (CollectionUtils.isNotEmpty(toSaveEntity.getLiveStreams()) && !LeNumberUtils.toBoolean(toSaveEntity.getHasLive())) {
            toSaveEntity.setHasLive(true);
        }
        //是否有图文直播
        if (CollectionUtils.isNotEmpty(toSaveEntity.getTextLives()) && !toSaveEntity.getHasTextLive()) {
            toSaveEntity.setHasTextLive(true);
        }
    }

    @Override
    public boolean updateSelfMadeId(Long episodeId, Long videoId) {
        Query query = new Query(Criteria.where("vid").is(videoId));
        query.addCriteria(Criteria.where("deleted").is(false));
        Episode episode = episodeRepository.findOneByQuery(query);
        if (null != episode) {
            if (LeNumberUtils.toLong(episodeId) == LeNumberUtils.toLong(episode.getId())) {
                return true;
            }
            episode.setRecordId(null);
            boolean result = episodeRepository.save(episode);
            LOG.info("update episode : {} self made id to : {}, result : {}.", episode.getId(), videoId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(episode.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.EPISODE).build();
            SwiftMessageApis.sendMsgSync(message);
        }

        Episode newEpisode = episodeRepository.findOne(episodeId);
        if (null != newEpisode) {
            newEpisode.setRecordId(videoId);
            episodeRepository.save(episode);
            boolean result = episodeRepository.save(episode);
            LOG.info("update episode : {} highlight id to : {}, result : {}.", episode.getId(), videoId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(episode.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.EPISODE).build();
            SwiftMessageApis.sendMsgSync(message);
        }
        return true;
    }

    @Override
    public boolean updateHighlightId(Long episodeId, Long videoId) {
        Query query = new Query(Criteria.where("highlights_id").is(videoId));
        query.addCriteria(Criteria.where("deleted").is(false));
        Episode episode = episodeRepository.findOneByQuery(query);
        if (null != episode) {
            if (LeNumberUtils.toLong(episodeId) == LeNumberUtils.toLong(episode.getId())) {
                return true;
            }
            episode.setHighlightsId(null);
            boolean result = episodeRepository.save(episode);
            LOG.info("update episode : {} highlight id to : {}, result : {}.", episode.getId(), videoId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(episode.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.EPISODE).build();
            SwiftMessageApis.sendMsgSync(message);
        }

        Episode newEpisode = episodeRepository.findOne(episodeId);
        if (null != newEpisode) {
            newEpisode.setHighlightsId(videoId);
            episodeRepository.save(episode);
            boolean result = episodeRepository.save(episode);
            LOG.info("update episode : {} highlight id to : {}, result : {}.", episode.getId(), videoId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(episode.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.EPISODE).build();
            SwiftMessageApis.sendMsgSync(message);
        }
        return true;
    }

    @Override
    public List<Long> createEpisodes(long matchId) {
        LOG.info("begin create episode for match : {}", matchId);
        Match match = SbdMatchInternalApis.getMatchById(matchId);
        if (null == match || CollectionUtils.isEmpty(match.getAllowCountries())) {
            return Collections.emptyList();
        }
        List<CountryLangId> newIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(match.getEids())) {
            newIds.addAll(match.getEids());
        }
        for (CountryCode countryCode : match.getAllowCountries()) {
            List<LanguageCode> languageCodes = getRelatedLanguage(countryCode);
            if (CollectionUtils.isEmpty(languageCodes)) {
                continue;
            }
            for (LanguageCode languageCode : languageCodes) {
                Episode episode = episodeHelper.createEpisodeWithMatch(match, languageCode, countryCode);
                boolean result = save(episode);
                LOG.info("create episode {} for match : {}, result : {}", episode.getId(), match.getId(), result);
                if (result) {
                    if (!hasId(newIds, episode.getId())) {
                        CountryLangId countryLangId = new CountryLangId();
                        countryLangId.setCountry(countryCode);
                        countryLangId.setLanguage(languageCode);
                        countryLangId.setId(episode.getId());
                        newIds.add(countryLangId);
                    }
                }
            }
        }
        List<Episode> episodes = getEpisodesByMid(matchId);
        for (Episode episode : episodes) {
            if (!match.getAllowCountries().contains(episode.getAllowCountry())) {
                delete(episode.getId());
                // 去除删除的节目id
                newIds = removeId(newIds, episode.getId());
                LOG.info("delete episode {} since countries of its match changed", episode.getId());
            }
        }
        //在赛程上保存节目id
        if (CollectionUtils.isNotEmpty(newIds)) {
            match.setEids(newIds);
            SbdMatchInternalApis.doUpdateMatch(match);
        }
        return Lists.transform(newIds, new Function<CountryLangId, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable CountryLangId input) {
                return input.getId();
            }
        });
    }

    @Override
    public Episode getLatestEpisodeByAid(long aid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(aid));
        q.addCriteria(where("record_id").exists(true));
        q.addCriteria(where("online").is(true));
        q.addCriteria(new Criteria().orOperator(where("status").is(LiveShowStatus.LIVE_END), where("status").is(LiveShowStatus.NO_LIVE)));
        q.with(new Sort(Sort.Direction.DESC, "start_time"));
        List<Episode> episodes = episodeRepository.findByQuery(q);
        return CollectionUtils.isNotEmpty(episodes) ? episodes.get(0) : null;
    }


    //outer service method

    @Override
    public long getEpisodeIdByLiveId(String liveId, CallerParam caller) {
        if (StringUtils.isEmpty(liveId)) {
            return -1;
        }
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("live_streams._id").is(Long.parseLong(liveId)));
        q.addCriteria(where("online").is(true));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        if (CollectionUtils.isNotEmpty(ids)) {
            return ids.get(0);
        }
        return 0;
    }

    @Override
    public long getEpisodeIdByTextLiveId(long textLiveId, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("text_lives.text_live_id").is(textLiveId));
        q.addCriteria(where("online").is(true));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        if (CollectionUtils.isNotEmpty(ids)) {
            return ids.get(0);
        }
        return 0;
    }

    @Override
    public long getEpisodeIdByMid(long mid, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("mid").is(mid));
        q.addCriteria(where("online").is(true));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        if (CollectionUtils.isNotEmpty(ids)) {
            return ids.get(0);
        }
        return 0;
    }

    @Override
    public TComboEpisode getTComboEpisodeById(long episodeId, CallerParam caller) {
        TComboEpisode vo = comboEpisodeCache.findOne(episodeId);
        if (vo == null) {
            Episode episode = episodeRepository.findOne(episodeId);
            if (episode == null) {
                LOG.warn("fail to getTComboEpisodeById, episode no exists. id : {}, caller : {}", episodeId, caller);
                return null;
            }
            vo = tComboEpisodeConverter.toDto(episode);
            if (vo == null) {
                LOG.warn("fail to getTComboEpisodeById, convert vo fail. id : {}, caller : {}", episodeId, caller);
                return null;
            }
            comboEpisodeCache.save(vo);
        }
        tComboEpisodeConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public List<TComboEpisode> getTComboEpisodesByIds(List<Long> episodeIds, CallerParam caller) {
        if (CollectionUtils.isEmpty(episodeIds)) {
            return Collections.EMPTY_LIST;
        }
        List<TComboEpisode> results = Lists.newArrayList();
        for (Long episodeId : episodeIds) {
            try {
                TComboEpisode episode = getTComboEpisodeById(episodeId, caller);
                //如果被删除了就不要返回了
                if (episode != null && !episode.isDeleted()) {
                    results.add(episode);
                }
            } catch (Exception e) {
                LOG.error("create comboEpisode error, episodeId : {}", episodeId, e);
                continue;
            }
        }
        return results;
    }

    @Override
    public List<Long> getCurrentEpisodeIds(GetCurrentEpisodesParam p, Pageable page, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }
        if (p.getGameType() > 0) {
            if (!addGameTypeToQuery(q, p.getGameType())) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return Collections.EMPTY_LIST;
            }
        }
        if (CollectionUtils.isNotEmpty(p.getCids())) {
            q.addCriteria(where("cid").in(p.getCids()));
        }
        if (p.getCsid() > 0) {
            q.addCriteria(where("csid").is(p.getCsid()));
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);
        if (p.isOctopus()) {
            q.addCriteria(where("is_octopus").is(true));
        }
        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }

        PageRequest pageRequest = getValidPageRequest4CurrentEpisodes(p, page);
        //对外接口过滤中超数据
        //暂时注释
        if (caller.getCallerId() > 2000 && caller.getCallerId() < 3000) {
            q.addCriteria(where("cid").ne(47001));
            pageRequest = getValidEpisodePageRequest(page);
        }
        if (pageRequest != null) {
            q.with(pageRequest);
        }
        return episodeRepository.findIdByQuery(q);
    }

    private PageRequest getValidEpisodePageRequest(Pageable page) {
        Pageable validPageable = (PageRequest) PageUtils.getValidPageable(page);
        if (page.getSort() != null) {
            return new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(), validPageable.getSort());
        }
        return new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                Sort.Direction.DESC, "start_time");
    }

    private PageRequest getValidPageRequest4CurrentEpisodes(GetCurrentEpisodesParam p, Pageable page) {
        Pageable validPageable = (PageRequest) PageUtils.getValidPageable(page);
        if (page.getSort() != null) {
            return new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(), validPageable.getSort());
        }

        if (p.getLiveShowStatusParam() == LiveShowStatusParam.LIVE_END) {
            return new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                    Sort.Direction.DESC, "start_time");
        } else {
            return new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                    Sort.Direction.ASC, "start_time");
        }
    }

    @Override
    public List<Long> getSomeDayEpisodeIds(GetSomeDayEpisodesParam p, Pageable page, CallerParam caller) {
        if (StringUtils.isEmpty(p.getDate())) {
            LOG.warn("illegal date : {}", p.getDate());
            return Collections.EMPTY_LIST;
        }

        Query q = query(where("deleted").is(false));
        if (p.isOctopus()) {
            q.addCriteria(where("is_octopus").is(true));
        }
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }

        //对外接口过滤中超数据
        //暂时注释
        if (caller.getCallerId() > 2000 && caller.getCallerId() < 3000) {
            //没有赛事id条件,直接过滤赛事id
            if (CollectionUtils.isEmpty(p.getCids())) {
                q.addCriteria(where("cid").ne(47001));
            }
        }

        if (caller.getPubChannel() == PubChannel.TCL) {//TCL过滤英超
            q.addCriteria(where("cid").ne(20001));
        }

        if (CollectionUtils.isNotEmpty(p.getCids())) {
            q.addCriteria(where("cid").in(p.getCids()));
        }

        if (StringUtils.isEmpty(p.getEndDate())) {
            q.addCriteria(where("start_date").is(p.getDate()));
        } else {
            q.addCriteria((new Criteria("start_date").gte(p.getDate()).lte(p.getEndDate())));
        }
        if (p.getGameType() > 0) {
            if (!addGameTypeToQuery(q, p.getGameType())) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return Collections.EMPTY_LIST;
            }
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }
        addCountryAndLanguageCriteria(q, caller);

        if (page == null) {
            if (null == p.getSortParam() || p.getSortParam() == SortParam.DESC) {
                q.with(new Sort(Sort.Direction.DESC, "start_time"));
            } else {
                q.with(new Sort(Sort.Direction.ASC, "start_time"));
            }
        } else {
            q.with(getValidEpisodePageRequest(page));
        }

        return episodeRepository.findIdByQuery(q);
    }

    @Override
    public List<Long> getEpisodeIdsOfSeasonByMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, Pageable page, CallerParam caller) {
        PageParam pageParam = PageUtils.createPageParam(page.getPageNumber(), page.getPageSize());
        List<Long> mids = SbdMatchApis.getMatchIdsByCidAndMetaEntryId(p.getCid(), p.getCsid(), p.getEntryId(), pageParam);
        if (CollectionUtils.isEmpty(mids)) {
            LOG.warn("fail to getEpisodeIdsOfSeasonByMetaEntryId. mids is empty. cid : {}, csid : {}, entryId : {}", p.getCid(), p.getCsid(), p.getEntryId());
            return Collections.EMPTY_LIST;
        }
        Query q = query(where("deleted").is(false));
        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }
        q.addCriteria(where("mid").in(mids));
        q.addCriteria(where("online").is(true));

        if (caller.getPubChannel() == PubChannel.TCL) {//TCL过滤英超
            q.addCriteria(where("cid").ne(20001));
        }

        addCountryAndLanguageCriteria(q, caller);
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(),
                Sort.Direction.ASC, "start_time");
        if (p.getLiveShowStatusParam() != null && p.getLiveShowStatusParam() == LiveShowStatusParam.LIVE_END) {
            pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(),
                    Sort.Direction.DESC, "start_time");
        }
        q.with(getValidEpisodePageRequest(pageRequest));

        return episodeRepository.findIdByQuery(q);
    }

    @Override
    public List<Long> getEpisodeIdsRelatedWithSomeEpisode(long episodeId, int count, CallerParam caller) {
        if (episodeId <= 0) {
            return Collections.EMPTY_LIST;
        }
        String key = RELATED_EPISODES_KEY_CREATER.textKey(String.valueOf(episodeId + LeConstants.UNDERLINE + caller.getCallerId()));
        List<Long> episodeIds = Lists.newArrayList();
        Object obj = KVCacheUtils.get(key);
        if (obj != null) {
            episodeIds = (List<Long>) obj;
        } else {
            LOG.warn("related match episode not find in cache. key : {} try to get from mongodb", key);
            episodeIds = doGetEpisodeIdsRelatedWithSomeEpisode(episodeId, caller);
            if (CollectionUtils.isEmpty(episodeIds)) {
                return Collections.EMPTY_LIST;
            }
            KVCacheUtils.set(key, episodeIds, RELATED_EPISODE_CACHE_EXPIRE_TIME);
        }

        if (count < episodeIds.size()) {
            return episodeIds.subList(0, count);
        }
        return episodeIds;
    }

    private List<Long> doGetEpisodeIdsRelatedWithSomeEpisode(long episodeId, CallerParam caller) {
        Episode episode = episodeRepository.findOne(episodeId);
        if (episode == null || episode.getType() != com.lesports.qmt.sbc.api.common.EpisodeType.MATCH) {
            LOG.warn("episode : {} no exist or not match episode.", episodeId);
            return Collections.EMPTY_LIST;
        }
        TMatch match = SbdMatchApis.getTMatchById(episode.getMid(), null);
        if (match == null) {
            LOG.warn("this episode : {} has no match", episodeId);
            return Collections.EMPTY_LIST;
        }
        Long cid = episode.getCid();
        Long csid = episode.getCsid();
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(cid, null);
        if (competition == null) {
            LOG.warn("this episode  : {} has no competition", episodeId);
            return Collections.EMPTY_LIST;
        }
        //杯赛的话按照比赛的阶段获取相关赛程
        if (competition.getMatchSystem() != null && competition.getMatchSystem().equals(MatchSystem.CUP)) {
            if (match.getStage() != null) {
                //小组赛就查询该分组的赛程 小组赛id :100076000L
                if (match.getStage().equals(100076000L)) {
                    return doGetRelatedEpisodeIdsOfSeason(cid, csid, LeNumberUtils.toLong(match.getGroup()), caller);
                } else {
                    return doGetRelatedEpisodeIdsOfSeason(cid, csid, LeNumberUtils.toLong(match.getStage()), caller);
                }
            } else {
                return doGetRelatedEpisodeIdsOfSeason(cid, csid, 0L, caller);
            }

        } else if (competition.getMatchSystem() != null && competition.getMatchSystem().equals(MatchSystem.LEAGUE)) {
            //联赛的话按照轮次获取相关赛程
            return doGetRelatedEpisodeIdsOfSeason(cid, csid, LeNumberUtils.toLong(match.getRound()), caller);
        } else {
            //其它赛事通过赛事获取相关赛程
            return doGetRelatedEpisodeIdsOfSeason(cid, csid, 0L, caller);
        }
    }

    /**
     * 根据相关节目的规则，获取赛季下的赛程列表
     *
     * @param cid     赛事id
     * @param csid    赛季id
     * @param entryId 字典项id
     * @return
     */
    private List<Long> doGetRelatedEpisodeIdsOfSeason(long cid, long csid, long entryId, CallerParam caller) {

        List<Long> episodeIds = Lists.newArrayList();
        //6个未完成的赛程
        Pageable pageable = new PageRequest(0, 6);
        GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
        p.setCid(cid);
        p.setCsid(csid);
        p.setEntryId(entryId);
        p.setLiveShowStatusParam(LiveShowStatusParam.LIVE_NOT_END);
        List<Long> notEndEpisodeIds = getEpisodeIdsOfSeasonByMetaEntryId(p, pageable, caller);
        episodeIds.addAll(notEndEpisodeIds);
        //剩余的部分用完成的赛程补齐
        int endEpisodesSize = 10 - episodeIds.size();
        Pageable endPageable = new PageRequest(0, endEpisodesSize);
        p.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
        List<Long> endEpisodeIds = getEpisodeIdsOfSeasonByMetaEntryId(p, pageable, caller);
        episodeIds.addAll(endEpisodeIds);

        if (CollectionUtils.isEmpty(episodeIds)) {
            return Collections.EMPTY_LIST;
        }
        return episodeIds;
    }

    @Override
    public List<Long> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam caller) {
        if (StringUtils.isEmpty(p.getStartDate()) || p.getDays() <= 0) {
            LOG.warn("illegal param : {}, caller : {}", p, caller);
            return Collections.EMPTY_LIST;
        }

        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());

        List<Long> counts = Lists.newArrayList();
        Date sDate = LeDateUtils.parseYYYYMMDD(p.getStartDate());
        for (int i = 0; i < p.getDays(); i++) {
            String date = LeDateUtils.formatYYYYMMDD(LeDateUtils.addDays(sDate, i));
            Query q = query(where("deleted").is(false));
            q.addCriteria(where("start_date").is(date));
            if (p.getEpisodeTypeParam() != null) {
                q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
            }
            if (liveCriteria != null) {
                q.addCriteria(liveCriteria);
            }
            if (p.getGameType() > 0) {
                if (!addGameTypeToQuery(q, p.getGameType())) {
                    LOG.warn("illegal gameType : {}", p.getGameType());
                    return Collections.EMPTY_LIST;
                }
            }
            q.addCriteria(where("online").is(true));
            addCountryAndLanguageCriteria(q, caller);
            long count = episodeRepository.countByQuery(q);
            counts.add(count);
        }
        return counts;
    }

    @Override
    public Map<String, Long> getEpisodeIdsNearbySomeEpisode(GetEpisodesNearbySomeEpisodeParam p, CallerParam caller) {
        Map<String, Long> returnMap = Maps.newHashMap();
        TSimpleEpisode simpleEpisode = getTSimpleEpisodeById(p.getEpisodeId(), caller);
        if (null == simpleEpisode) {
            LOG.error("fail to getEpisodeIdsNearbySomeEpisode. episode : {} no exist. p : {}", p.getEpisodeId(), p);
            return Collections.EMPTY_MAP;
        }
        Long prev = getPreviousEpisode(p.getEpisodeId(), simpleEpisode.getCid(), p.getLiveShowStatusParam(), p.getLiveTypeParam());
        if (prev != null) {
            returnMap.put("prev", prev);
        }
        Long next = getNextEpisode(p.getEpisodeId(), simpleEpisode.getCid(), p.getLiveShowStatusParam(), p.getLiveTypeParam());
        if (next != null) {
            returnMap.put("next", next);
        }
        return returnMap;
    }

    private Long getPreviousEpisode(long episodeId, long cid, LiveShowStatusParam statusParam,
                                    LiveTypeParam liveTypeParam) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
        CriteriaDefinition liveTypeCriteria = getLiveCriteria(liveTypeParam);
        if (liveTypeCriteria != null) {
            q.addCriteria(liveTypeCriteria);
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(statusParam);
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("_id").lt(episodeId));
        q.with(new Sort(Sort.Direction.DESC, "_id"));
        return episodeRepository.findOneIdByQuery(q);
    }

    private Long getNextEpisode(long episodeId, long cid, LiveShowStatusParam statusParam,
                                LiveTypeParam liveTypeParam) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
        CriteriaDefinition liveTypeCriteria = getLiveCriteria(liveTypeParam);
        if (liveTypeCriteria != null) {
            q.addCriteria(liveTypeCriteria);
        }
        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(statusParam);
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("_id").gt(episodeId));
        q.with(new Sort(Sort.Direction.ASC, "_id"));
        return episodeRepository.findOneIdByQuery(q);
    }


    @Override
    public List<Long> getPassedEpisodeIdsInAlbum(Long albumId, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(albumId));
        q.addCriteria(new Criteria().orOperator(where("status").is(LiveShowStatus.LIVE_END), where("status").is(LiveShowStatus.NO_LIVE)));
        q.addCriteria(where("record_id").exists(true));
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);
        q.with(getValidEpisodePageRequest(pageable));

        List<Long> ids = episodeRepository.findIdByQuery(q);
        return ids;
    }

    @Override
    public List<TComboEpisode> getEpisodeByAid(long aid, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(aid));
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);
        Pageable validPageable = (PageRequest) PageUtils.getValidPageable(pageable);
        PageRequest pageRequest = new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                Sort.Direction.DESC, "start_time");
        q.with(pageRequest);

        List<Long> ids = episodeRepository.findIdByQuery(q);
        return getTComboEpisodesByIds(ids, caller);
    }

    @Override
    public List<Long> getEpisodeIdsOfOctopus(GetZhangyuEpisodesParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("octopus_match_id").in(p.getMids()));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);
        q.with(new Sort(Sort.Direction.ASC, "start_time"));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        return ids;
    }

    @Override
    public List<Long> getCurrentEpisodeIdsByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, Pageable pageable, CallerParam caller) {
        if (!episodeHelper.isValidCompetitorId(competitorId)) {
            LOG.error("illage competitorId : {}", competitorId);
            return Collections.EMPTY_LIST;
        }
        Query q2 = query(where("deleted").is(false));
        q2.addCriteria(where("competitor_ids").is(competitorId));

        if (StringUtils.isNotBlank(p.getStartDate()) && StringUtils.isNotBlank(p.getEndDate())) {
            q2.addCriteria(where("start_date").gte(p.getStartDate()).lte(p.getEndDate()));
        }

        if (p.getCsid() > 0) {
            //获取某赛季
            q2.addCriteria(where("csid").is(p.getCsid()));
        } else if (p.getCsid() == 0) {
            Query q1 = query(where("deleted").is(false));
            q1.addCriteria(where("competitor_ids").is(competitorId));
            q1.with(new Sort(Sort.Direction.DESC, "start_time"));
//            q1.withHint("competitor_ids_1_csid_1_start_time_-1");
            //随机获取参赛者的最新的节目,来判断赛季信息
            Episode episode = episodeRepository.findOneByQuery(q1);
            if (episode == null) {
                LOG.warn("no latest episode found for competitor : {}", competitorId);
                return Collections.EMPTY_LIST;
            }
            //请求的赛季id为0，获取最新赛季
            TCompetitionSeason season = SbdCompetitonSeasonApis.getTCompetitionSeasonById(episode.getCsid(), null);
            q2.addCriteria(where("csid").is(season.getId()));
        }
        CriteriaDefinition liveCriteria = getLiveCriteria(p.getLiveTypeParam());
        if (liveCriteria != null) {
            q2.addCriteria(liveCriteria);
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q2.addCriteria(statusCriteria);
        }
        addCountryAndLanguageCriteria(q2, caller);

        Pageable validPageable = (PageRequest) PageUtils.getValidPageable(pageable);

        PageRequest pageRequest = new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                Sort.Direction.ASC, "start_time");
        if (p.getLiveShowStatusParam() == LiveShowStatusParam.LIVE_END) {
            pageRequest = new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                    Sort.Direction.DESC, "start_time");
        }
        q2.with(pageRequest);

        List<Long> ids = episodeRepository.findIdByQuery(q2);
        return ids;
    }

    @Override
    public List<TComboEpisode> getFirstAndLatestEpisodesByAid(long aid, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(aid));
        q.with(new Sort(Sort.Direction.DESC, "start_time"));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<Long> needIds = Lists.newArrayList(ids.get(0), ids.get(ids.size() - 1));
        return getTComboEpisodesByIds(needIds, caller);
    }

    @Override
    public List<TComboEpisode> getEpisodeByYearAndMonth(long aid, String yearAndMonth, CallerParam caller) {
        if (StringUtils.isBlank(yearAndMonth) || yearAndMonth.length() != 6) {
            LOG.error("fail to getEpisodeByYearAndMonth. yearAndMonth is null or length != 6. yearAndMonth : {}", yearAndMonth);
            return Collections.EMPTY_LIST;
        }
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(aid));
        q.addCriteria(where("start_time").gte(LeDateUtils.formatyyyyMM2YYYYMMDDHHMMSS(yearAndMonth)).lt(LeDateUtils.addMonth4yyyyMM2YYYYMMDDHHMMSS(yearAndMonth, 1)));
        q.with(new Sort(Sort.Direction.DESC, "start_time"));
        List<Long> ids = episodeRepository.findIdByQuery(q);
        return getTComboEpisodesByIds(ids, caller);
    }


    @Override
    public void updateEpisodeSyncToCloud(long episodeId, Boolean isSyncToCloud) {

    }

    @Override
    public void updateSyncToCloudOfEpisodesWithCid(long cid) {

    }

    @Override
    public boolean deleteRpcCache(long id) {
        return comboEpisodeCache.delete(id) & simpleEpisodeCache.delete(id);
    }


    //体育桌面根据资源位方式实现
    public List<Long> getLephoneDesktopChannelEpisodes(String date, long gameType, CallerParam caller) {
        return null;
    }

    @Override
    public List<Long> getPeriodAppRecommendEpisodes(CallerParam caller) {
        //往前N天
        String before = "1";
        //往后N天
        String after = "1";

        List<Long> allIds = Lists.newArrayList();

        Query beforeQ = query(where("deleted").is(false));
        beforeQ.addCriteria((new Criteria("start_date").gte(LeDateUtils.formatYYYYMMDD(LeDateUtils.addDay(new Date(), -LeNumberUtils.toInt(before)))).lte(LeDateUtils.formatYYYYMMDD(new Date()))));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(LiveTypeParam.ONLY_APP_HOMEPAGE_RECOMMEND, caller.getCallerId());
        if (liveCriteria != null) {
            beforeQ.addCriteria(liveCriteria);
        }
        addCountryAndLanguageCriteria(beforeQ, caller);
        List<Long> beforeIds = episodeRepository.findIdByQuery(beforeQ);

        Query afterQ = query(where("deleted").is(false));
        afterQ.addCriteria((new Criteria("start_date").gt(LeDateUtils.formatYYYYMMDD(new Date())).lte(LeDateUtils.formatYYYYMMDD(LeDateUtils.addDay(new Date(), LeNumberUtils.toInt(after))))));
        afterQ.with(new Sort(Sort.Direction.DESC, "start_time"));
        CriteriaDefinition afterLiveCriteria = getLiveCriteria5CallerFilter(LiveTypeParam.ONLY_KEY, caller.getCallerId());
        if (afterLiveCriteria != null) {
            afterQ.addCriteria(afterLiveCriteria);
        }
        addCountryAndLanguageCriteria(afterQ, caller);
        List<Long> afterIds = episodeRepository.findIdByQuery(afterQ);

        if (CollectionUtils.isNotEmpty(beforeIds)) {
            allIds.addAll(beforeIds);
        }

        if (CollectionUtils.isNotEmpty(afterIds)) {
            allIds.addAll(afterIds);
        }

        if (CollectionUtils.isEmpty(allIds)) {
            return Collections.EMPTY_LIST;
        }
        return allIds;
    }

    @Override
    public List<Long> getTheRoadOfAdvance(long cid, long csid, CallerParam caller) {
        return null;
    }

    @Override
    //TODO: 资源位
    public List<TComboEpisode> getAppRecommendEpisodes(Boolean isRecommendTag, long cid, CallerParam caller) {
        return null;
    }

    @Override
    public TSimpleEpisode getTSimpleEpisodeById(long episodeId, CallerParam caller) {
        TSimpleEpisode vo = simpleEpisodeCache.findOne(episodeId);
        if (vo == null) {
            Episode episode = episodeRepository.findOne(episodeId);
            if (episode == null) {
                LOG.warn("fail to getTSimpleEpisodeById, episode no exists. id : {}, caller : {}", episodeId, caller);
                return null;
            }

            vo = simpleEpisodeVoConverter.toDto(episode);
            if (vo == null) {
                LOG.warn("fail to getTSimpleEpisodeById, convert vo fail. id : {}, caller : {}", episodeId, caller);
                return null;
            }
            simpleEpisodeCache.save(vo);
        }
        return vo;
    }

    @Override
    public List<Long> getMemberEpisodeIds(GetCurrentEpisodesParam p, Pageable page, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }
        if (p.getGameType() > 0) {
            if (!addGameTypeToQuery(q, p.getGameType())) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return Collections.EMPTY_LIST;
            }
        }
        if (CollectionUtils.isNotEmpty(p.getCids())) {
            q.addCriteria(where("cid").in(p.getCids()));
        }
        if (p.getCsid() > 0) {
            q.addCriteria(where("csid").is(p.getCsid()));
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);

        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }

        if (caller.getPubChannel() == PubChannel.TCL) {//TCL过滤英超
            q.addCriteria(where("cid").ne(20001));
        }

        q.addCriteria(where("live_streams.isPay").is(true));
        q.addCriteria(where("live_streams.pay_platforms").is(callerHelper.getSplatIdOfCaller(caller.getCallerId())));
        PageRequest pageRequest = getValidPageRequest4CurrentEpisodes(p, page);

        if (pageRequest != null) {
            q.with(pageRequest);
        }
        LOG.info("[getMemberEpisodeIds] [query:{}]", q.toString());
        return episodeRepository.findIdByQuery(q);
    }

    @Override
    public List<String> getStartDatesBySomeDayEpisodeParam(CountEpisodesByDayParam p, CallerParam caller) {
        List<String> resultList = Lists.newArrayList();
        Date sDate = LeDateUtils.parseYYYYMMDD(p.getStartDate(), p.getTimezone());
        for (int i = -p.getDays(); i <= p.getDays(); i++) {
            String date = LeDateUtils.formatYYYYMMDD(LeDateUtils.addDays(sDate, i));
            p.setStartDate(date);
            if (countSomeDaysEpisodes(p, caller) > 0) {
                resultList.add(p.getStartDate());
            }
        }
        return resultList;
    }

    private long countSomeDaysEpisodes(CountEpisodesByDayParam p, CallerParam caller) {
        if (StringUtils.isEmpty(p.getStartDate())) {
            LOG.warn("illegal date : {}", p.getStartDate());
            return 0;
        }

        Query q = query(where("deleted").is(false));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }

        String startTime = LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYYYYMMDDHHMMSSZ(p.getStartDate() + "000000", p.getTimezone()));
        String endTime = LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYYYYMMDDHHMMSSZ(p.getStartDate() + "235959", p.getTimezone()));
        q.addCriteria(where("start_time").gte(startTime).lte(endTime));

        if (p.getGameType() > 0) {
            if (!addGameTypeToQuery(q, p.getGameType())) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return 0;
            }
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }
        addCountryAndLanguageCriteria(q, caller);

        return episodeRepository.countByQuery(q);
    }

    @Override
    public List<Long> getTimelineEpisodesByCids(GetCurrentEpisodesParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }
        if (CollectionUtils.isNotEmpty(p.getCids())) {
            q.addCriteria(where("cid").in(p.getCids()));
        }
        if (p.getCsid() > 0) {
            q.addCriteria(where("csid").is(p.getCsid()));
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);
        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }
        Sort.Order order = pageable.getSort().getOrderFor("start_time");
        Sort.Direction direction = order.getDirection();
        if (direction == Sort.Direction.DESC) {
            q.addCriteria(where("start_date").lt(p.getStartDate()));
        }
        if (direction == Sort.Direction.ASC) {
            q.addCriteria(where("start_date").gte(p.getStartDate()));
        }

        PageRequest pageRequest = getValidPageRequest4CurrentEpisodes(p, pageable);
        if (pageRequest != null) {
            q.with(pageRequest);
        }
        List<Long> ids = episodeRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        if (direction == Sort.Direction.DESC) {
            Collections.reverse(ids);
        }
        return ids;
    }

    @Override
    public List<Long> getSomeDayEpisodeIdsWithTimezone(GetSomeDayEpisodesParam p, Pageable page, CallerParam caller) {
        if (StringUtils.isEmpty(p.getDate())) {
            LOG.warn("illegal date : {}", p.getDate());
            return Collections.EMPTY_LIST;
        }

        Query q = query(where("deleted").is(false));
        if (p.isOctopus()) {
            q.addCriteria(where("is_octopus").is(true));
        }
        CriteriaDefinition liveCriteria = getLiveCriteria5CallerFilter(p.getLiveTypeParam(), caller.getCallerId());
        if (liveCriteria != null) {
            q.addCriteria(liveCriteria);
        }

        //对外接口过滤中超数据
        //暂时注释
        if (caller.getCallerId() > 2000 && caller.getCallerId() < 3000) {
            //没有赛事id条件,直接过滤赛事id
            if (CollectionUtils.isEmpty(p.getCids())) {
                q.addCriteria(where("cid").ne(47001));
            }
        }

        if (CollectionUtils.isNotEmpty(p.getCids())) {
            q.addCriteria(where("cid").in(p.getCids()));
        }
        String startTime = LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYYYYMMDDHHMMSSZ(p.getDate() + "000000", p.getTimezone()));
        String endTime = LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYYYYMMDDHHMMSSZ(p.getEndDate() + "235959", p.getTimezone()));
        q.addCriteria(where("start_time").gte(startTime).lte(endTime));
        if (p.getGameType() > 0) {
            if (!addGameTypeToQuery(q, p.getGameType())) {
                LOG.error("illegal gameType : {}", p.getGameType());
                return Collections.EMPTY_LIST;
            }
        }

        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        if (p.getEpisodeTypeParam() != null) {
            q.addCriteria(where("type").is(p.getEpisodeTypeParam()));
        }
        addCountryAndLanguageCriteria(q, caller);

        if (page == null) {
            if (null == p.getSortParam() || p.getSortParam() == SortParam.DESC) {
                q.with(new Sort(Sort.Direction.DESC, "start_time"));
            } else {
                q.with(new Sort(Sort.Direction.ASC, "start_time"));
            }
        } else {
            q.with(getValidEpisodePageRequest(page));
        }
        return episodeRepository.findIdByQuery(q);
    }

    @Override
    public List<Long> getTicketEpisodeIds(GetCurrentEpisodesParam p, Pageable pageable, CallerParam caller) {
        TicketType ticketType = p.getTicketType();
        if (ticketType == null) {
            return Collections.EMPTY_LIST;
        }
        String ticketId = p.getTicketId();
        if (StringUtils.isBlank(ticketId) || ticketId.equals("0")) {
            return Collections.EMPTY_LIST;
        }
        List<Long> episodesIds = Lists.newArrayList();
        //单场直播券节目
        if (ticketType == TicketType.SINGLE) {
            episodesIds = getEpisodeIdsBySingleLiveTikcketId(ticketId, caller);
        } else if (ticketType == TicketType.COMPETITION) {//赛事券节目
            episodesIds = getEpisodeIdsByCompetitionCodingId(p, pageable, caller);
        }
        return episodesIds;
    }

    private List<Long> getEpisodeIdsBySingleLiveTikcketId(String ticketId, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("online").is(true));
        q.addCriteria(where("live_unique_id").is(ticketId));
        addCountryAndLanguageCriteria(q, caller);
        return episodeRepository.findIdByQuery(q);
    }

    private List<Long> getEpisodeIdsByCompetitionCodingId(GetCurrentEpisodesParam p, Pageable pageable, CallerParam caller) {
        TCompetition competition = SbdCompetitionApis.getTCompetitionByCode(p.getTicketId(), caller);
        if (competition == null) {
            return Collections.EMPTY_LIST;
        }
        Query q = query(where("deleted").is(false));
        CriteriaDefinition statusCriteria = getLiveShowStatusCriteria(p.getLiveShowStatusParam());
        if (statusCriteria != null) {
            q.addCriteria(statusCriteria);
        }
        q.addCriteria(where("online").is(true));
        addCountryAndLanguageCriteria(q, caller);

        q.addCriteria(where("cid").is(competition.getId()));
        q.addCriteria(where("live_streams.isPay").is(true));
        q.addCriteria(where("live_streams.pay_platforms").is(callerHelper.getSplatIdOfCaller(caller.getCallerId())));
        PageRequest pageRequest = getValidPageRequest4CurrentEpisodes(p, pageable);

        if (pageRequest != null) {
            q.with(pageRequest);
        }
        return episodeRepository.findIdByQuery(q);
    }


    private List<Episode> getEpisodesByMid(long mid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("mid").is(mid));

        return episodeRepository.findByQuery(q);
    }

    public List<LanguageCode> getRelatedLanguage(CountryCode countryCode) {
        if (null == countryCode) {
            return null;
        }
        if (CountryCode.CN == countryCode) {
            return Lists.newArrayList(LanguageCode.ZH_CN);
        } else if (CountryCode.HK == countryCode) {
            return Lists.newArrayList(LanguageCode.ZH_HK, LanguageCode.EN_US);
        } else if (CountryCode.US == countryCode) {
            return Lists.newArrayList(LanguageCode.EN_US);
        }
        throw new RuntimeException("can not get related language by country code : " + countryCode);
    }

    private boolean hasId(List<CountryLangId> newIds, Long id) {
        for (CountryLangId countryLangId : newIds) {
            if (countryLangId.getId() == id.longValue()) {
                return true;
            }
        }
        return false;
    }

    private List<CountryLangId> removeId(List<CountryLangId> newIds, Long id) {
        Iterator<CountryLangId> iterator = newIds.iterator();
        while (iterator.hasNext()) {
            CountryLangId countryLangId = iterator.next();
            if (id != null && id.equals(countryLangId.getId())) {
                iterator.remove();
            }
        }
        return newIds;
    }
}
