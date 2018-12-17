package com.lesports.qmt.sbc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.LiveStatus;
import com.lesports.api.common.Platform;
import com.lesports.id.api.IdType;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.client.QmtClientPlatformInternalApis;
import com.lesports.qmt.config.client.QmtCopyrightInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.common.ShieldType;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.model.LiveErrorCode;
import com.lesports.qmt.sbc.repository.LiveRepository;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.qmt.sbc.service.LiveService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.channel.LiveShieldConfigAPI;
import com.letv.urus.liveroom.api.dto.*;
import com.letv.urus.liveroom.api.dto.channel.LiveShieldConfigDTO;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsQueryAPI;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import com.letv.urus.liveroom.enums.BelongAreaType;
import com.letv.urus.liveroom.enums.LiveType;
import com.letv.urus.liveroom.exception.UrusLiveRoomException;
import com.letv.urus.streamchannel.bo.Stream;
import me.ellios.hedwig.common.exceptions.HedwigException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

//import com.lesports.qmt.sbd.api.common.GroundType;
//import com.lesports.qmt.sbd.dto.GroundType;

//import com.letv.urus.liveroom.api.LiveShieldConfigAPI;

/**
 * Created by wangjichuan on 2016/10/26.
 */
@Service("liveService")
public class LiveServiceImpl extends AbstractSbcService<Live, Long> implements LiveService {

    protected static final Logger LOG = LoggerFactory.getLogger(LiveServiceImpl.class);

    @Resource
    protected LiveRepository liveRepository;
    @Resource
    private EpisodeService episodeService;
    @Resource
    private LiveShieldConfigAPI liveShieldConfigAPI;
    @Resource
    private LiveRoomSportsQueryAPI liveRoomSportsQueryAPI;
    @Resource
    private LiveRoomSportsWriterAPI liveRoomSportsWriterAPI;
    private static final UrusAuth urusAuth;

    private static final Map<Platform, Integer> LIVE_PLATFORM_MAPPING = new HashMap<>();

    static {
        urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
//        PC(1,"pc"), TV(2,"tv"), HZ(1,"hz"), MOBILE(4,"mb");
        LIVE_PLATFORM_MAPPING.put(Platform.PC, 1);
        LIVE_PLATFORM_MAPPING.put(Platform.TV, 2);
//        LIVE_PLATFORM_MAPPING.put(Platform., 3);
        LIVE_PLATFORM_MAPPING.put(Platform.MOBILE, 4);
        LIVE_PLATFORM_MAPPING.put(Platform.MSITE, 4);
        LIVE_PLATFORM_MAPPING.put(Platform.PAD, 4);

    }

    private LiveShieldConfigDTO getLiveShieldConfigDTOFromEpisode(Live entity) {
        LiveShieldConfigDTO liveShieldConfigDTO = new LiveShieldConfigDTO();
        if (null == entity) return liveShieldConfigDTO;
        if (ShieldType.ALLOW == entity.getShieldType()) {
            liveShieldConfigDTO.setCountry(this.getCountryStr(entity.getShieldRegion())); // 保留国家列表
        } else if (ShieldType.DENY == entity.getShieldType()) {
            liveShieldConfigDTO.setCcdeny(this.getCountryStr(entity.getShieldRegion())); // 屏蔽国家
        }
        liveShieldConfigDTO.setOldStreamid(this.getOldStreamid(entity.getChannelId())); // 被屏蔽流名称 channelId -> streamIds
        liveShieldConfigDTO.setOldChannel(Integer.parseInt("" + entity.getChannelId())); // 被屏蔽频道ID
        liveShieldConfigDTO.setNewStreamid(""); // 指向流名称
        liveShieldConfigDTO.setNewChannel(1); // 指向频道ID
        liveShieldConfigDTO.setMustcheck(new Short("1")); // 是否验证防盗链
        liveShieldConfigDTO.setSplatids(this.getLongStr(entity.getPlayPlatforms())); // 保留平台列表 playplatform
        liveShieldConfigDTO.setBeginDate(LeDateUtils.parseYYYYMMDDHHMMSS(entity.getStartTime())); // 开始日期
        liveShieldConfigDTO.setEndDate(LeDateUtils.parseYYYYMMDDHHMMSS(entity.getEndTime())); // 结束日期
        liveShieldConfigDTO.setBeginTime(LeStringUtils.substringAfterLast(LeDateUtils.formatYMDHMS(liveShieldConfigDTO.getBeginDate()), " ")); // 开始时间
        liveShieldConfigDTO.setEndTime(LeStringUtils.substringAfterLast(LeDateUtils.formatYMDHMS(liveShieldConfigDTO.getEndDate()), " ")); // 结束时间
        liveShieldConfigDTO.setTimeType(new Short("1")); // 屏蔽时间类型, 1：单次屏蔽; 2：每天屏蔽; 3：按周屏蔽
        liveShieldConfigDTO.setModtime(new Date()); // 修改时间
        liveShieldConfigDTO.setFlag(new Short("1")); // 是否启用
        liveShieldConfigDTO.setWeekdays(""); // 每周屏蔽时间
        liveShieldConfigDTO.setRowid(entity.getShieldRowId());  //liveShieldId  直播流屏蔽ID

        liveShieldConfigDTO.setFirstupdateuser(entity.getCreator()); // 创建者
        liveShieldConfigDTO.setLastupdateuser(entity.getUpdater()); // 最后修改者
//        liveShieldConfigDTO.setUrlkey(""); // 不知道干什么的
//        liveShieldConfigDTO.setProvince(""); // 保留省份列表
//        liveShieldConfigDTO.setNgxcheck(new Short("1")); // 不知道干什么的，应该和nginx相关。
        return liveShieldConfigDTO;
    }

    private String getCountryStr(List<Long> shieldRegion) {
        List<Country> countries = QmtCountryInternalApis.getCountryByIds(shieldRegion);
        if (true == CollectionUtils.isEmpty(countries)) return "";
        StringBuilder result = new StringBuilder();
        for (Country country : countries) {
            if (null == country) continue;
            result.append(country.getCode());
            result.append(",");
        }
        return result.toString();
    }

    private String getOldStreamid(Long channelId) {
        if (null == channelId) return "";
        try {
            List<StreamDTO> streams = liveRoomSportsQueryAPI.getStreamListByChannelId(urusAuth, channelId);
            StringBuilder result = new StringBuilder();
            for (Stream stream : streams) {
                if (null == stream) continue;
                result.append(stream.getStreamName());
                result.append(",");
            }
            return result.toString();
        } catch (UrusLiveRoomException e) {
        }
        return "";
    }

    private String getLongStr(Set<Long> playPlatforms) {
        if (CollectionUtils.isEmpty(playPlatforms)) return "";
        StringBuilder result = new StringBuilder();
        for (Long playPlatformId : playPlatforms) {
            result.append(playPlatformId);
            result.append(",");
        }
        return result.toString();
    }

    @Override
    public Live findOne(Long id) {
        return liveRepository.findOne(id);
    }


    @Override
    protected boolean doDelete(Long aLong) {
        Live live = liveRepository.findOne(aLong);
        if (null == live) {
            return false;
        }
        live.setDeleted(true);
        return liveRepository.save(live);
    }

    @Override
    public long countByQuery(InternalQuery internalQuery) {
        return super.countByQuery(internalQuery);
    }

    @Override
    protected boolean afterOperation(Live entity, QmtOperationType opType, boolean opResult) {
        return super.afterOperation(entity, opType, opResult);
    }

    @Override
    protected QmtOperationType getOpreationType(Live entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0 || findOne(entity.getId()) == null) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    private void updateLiveStatusToStarting() {
        // no start -> starting
        InternalQuery query = new InternalQuery();
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date()).substring(0, 12) + "59";//yyyyMMddHHmmss
        query.addCriteria(new InternalCriteria("start_time").lte(now));
        query.addCriteria(new InternalCriteria("status").is(LiveStatus.LIVE_NOT_START));
        List<Live> lives = super.findByQuery(query);
        if (false == CollectionUtils.isEmpty(lives)) {
            lives.forEach(live -> {
                if (null != live) {
                    live.setStatus(LiveStatus.LIVING);
                    liveRepository.save(live);
                }
            });
            LOG.info("[LiveService] [updateLiveStatus from LIVE_NOT_START to LIVING] [updated:{}]", lives.size());
        }
    }

    private void updateLiveStatusToEnd() {
        // starting -> end
        InternalQuery query = new InternalQuery();
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date()).substring(0, 12) + "00";//yyyyMMddHHmmss
        query.addCriteria(new InternalCriteria("start_time").lte(now));
        query.addCriteria(new InternalCriteria("status").is(LiveStatus.LIVING));
        List<Live> lives = super.findByQuery(query);
        if (false == CollectionUtils.isEmpty(lives)) {
            lives.forEach(live -> {
                if (null != live) {
                    live.setStatus(LiveStatus.LIVE_END);
                    liveRepository.save(live);
                }
            });
            LOG.info("[LiveService] [updateLiveStatus from LIVING to LIVE_END] [updated:{}]", lives.size());
        }
    }

    @Override
    public boolean updateLivePlaybackId(Long liveId, Long playbackId) {
        Query query = new Query(Criteria.where("record_id").is(playbackId));
        query.addCriteria(Criteria.where("deleted").is(false));
        Live live = liveRepository.findOneByQuery(query);
        if (null != live) {
            if (LeNumberUtils.toLong(liveId) == LeNumberUtils.toLong(live.getId())) {
                return true;
            }
            live.setRecordId(null);
            boolean result = liveRepository.save(live);
            LOG.info("update live : {} record id to : {}, result : {}.", live.getId(), playbackId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(live.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.LETV_LIVE).build();
            SwiftMessageApis.sendMsgSync(message);


        }
        Live newLive = liveRepository.findOne(liveId);
        if (null != newLive) {
            newLive.setRecordId(playbackId);
            boolean result = liveRepository.save(live);
            LOG.info("update live : {} record id to : {}, result : {}.", live.getId(), playbackId, result);
            LeMessage message = LeMessageBuilder.create().setEntityId(live.getId())
                    .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.LETV_LIVE).build();
            SwiftMessageApis.sendMsgSync(message);
        }
        return true;
    }

    @Override
    public void updateLiveStatus() {
        // no start -> starting
        updateLiveStatusToStarting();

        // starting -> end
        updateLiveStatusToEnd();
    }

    public List<LiveBranchDTO> buildLiveBranchDTO(Live live, List<LiveBranchDTO> old) {
        List<LiveBranchDTO> liveBranchDTOList;
        LiveBranchDTO liveBranchDTO;
        if (!CollectionUtils.isEmpty(old)) {
            liveBranchDTOList = old;
            liveBranchDTO = old.get(0);
        } else {
            liveBranchDTOList = new ArrayList<>();
            liveBranchDTO = new LiveBranchDTO();
            liveBranchDTOList.add(liveBranchDTO);
        }

        liveBranchDTO.setLiveid(live.getId());
        liveBranchDTO.setBegintime(LeDateUtils.parseYYYYMMDDHHMMSS(live.getStartTime()));
        liveBranchDTO.setEndtime(LeDateUtils.parseYYYYMMDDHHMMSS(live.getEndTime()));
        liveBranchDTO.setBelongarea(null != live.getBelongArea() ? live.getBelongArea().name() : BelongAreaType.CHINA.toString());
        liveBranchDTO.setBranchname(live.getCommentaryLanguage());
        liveBranchDTO.setCibnselectid(null != live.getCibnChannelId() ? live.getCibnChannelId().intValue() : null);
        liveBranchDTO.setGroupname(live.getCommentaryLanguage());
        liveBranchDTO.setOrderno(0);
        liveBranchDTO.setSelectid(null != live.getChannelId() ? live.getChannelId().intValue() : null);
        liveBranchDTO.setStreamsourcetype(null != live.getStreamSourceType() ? live.getStreamSourceType().getValue() : null);
        return liveBranchDTOList;
    }

    public LiveMainDTO buildLiveMainDTO(Live live, LiveMainDTO old) {
        LiveMainDTO liveMainDTO;
        if (null == old) {
            liveMainDTO = new LiveMainDTO();
        } else {
            liveMainDTO = old;
        }
        liveMainDTO.setLiveid(live.getId());
        liveMainDTO.setStreamsourcetype(null != live.getStreamSourceType() ? live.getStreamSourceType().getValue() : null);
        liveMainDTO.setSelectid(null != live.getChannelId() ? live.getChannelId().intValue() : null);
        liveMainDTO.setBelongarea(null != live.getBelongArea() ? live.getBelongArea().name() : BelongAreaType.CHINA.toString());
        liveMainDTO.setBranchtype("0");//分支类型，0是单分支，1是多分支
//        liveMainDTO.setCreatetime(entity.getCreateAt());
        liveMainDTO.setCreatetime(LeDateUtils.parseYYYYMMDDHHMMSS(live.getCreateAt()));
        liveMainDTO.setCreator(live.getCreator());
        liveMainDTO.setIsbranch("0");
        liveMainDTO.setLastupdateby(live.getUpdater());
        liveMainDTO.setLastupdatetime(LeDateUtils.parseYYYYMMDDHHMMSS(live.getUpdateAt()));
        liveMainDTO.setThirdlivepageurl(live.getThirdLivePageUrl());
        liveMainDTO.setStatus((byte) (live.getStatus().getValue() + 1));
        liveMainDTO.setDrmflag(LeNumberUtils.toBoolean(live.getIsDrm()) ? "3" : "0");//如果是drm，对应大乐视那边 Cisco DRM
        liveMainDTO.setTitle(live.getCommentaryLanguage());
        if (CollectionUtils.isNotEmpty(live.getPlayPlatforms())) {
            StringBuffer sb = new StringBuffer();
            Set<Integer> platforms = getLivePlatformsByClientPlatforms(live.getPlayPlatforms());
            for (Integer platform : platforms) {
                sb.append(platform).append(",");
            }
            liveMainDTO.setPlatform(sb.toString());
        }
        liveMainDTO.setLivetype(LiveType.SPORTS.toString());
        liveMainDTO.setIstest((byte) 0);
        liveMainDTO.setIsprecise(true);
        liveMainDTO.setOperator(0);
        return liveMainDTO;
    }

    public LiveStatusDTO buildLiveStatusDTO(Live live, LiveStatusDTO old) {
        LiveStatusDTO liveStatusDTO;
        if (null == old) {
            liveStatusDTO = new LiveStatusDTO();
        } else {
            liveStatusDTO = old;
        }
        liveStatusDTO.setLiveid(live.getId());
        if (live.getIsPay()) {
            Episode episode = episodeService.findOne(live.getEid());
            liveStatusDTO.setScreenings(episode.getLiveUniqueId());
            liveStatusDTO.setPaybegintime(LeStringUtils.isNotEmpty(live.getPayBeginTime()) ?
                    LeDateUtils.parseYYYYMMDDHHMMSS(live.getPayBeginTime()) : LeDateUtils.parseYYYYMMDDHHMMSS(live.getStartTime()));
            if (CollectionUtils.isNotEmpty(live.getPayPlatforms())) {
                StringBuffer sb = new StringBuffer();
                Set<String> platforms = getSplitIdsByClientPlatforms(live.getPayPlatforms());
                for (String platform : platforms) {
                    sb.append(platform).append(",");
                }
                liveStatusDTO.setPayplatform(sb.toString());
            }
        } else {
            liveStatusDTO.setPayplatform(null);
            liveStatusDTO.setScreenings(null);
            liveStatusDTO.setPaybegintime(null);
        }
        return liveStatusDTO;
    }

    public LiveChatDTO buildLiveChatDTO(Live live, LiveChatDTO old) {
        LiveChatDTO liveChatDTO;
        if (null == old) {
            liveChatDTO = new LiveChatDTO();
        } else {
            liveChatDTO = old;
        }
        liveChatDTO.setLiveid(live.getId());
        if (live.getEid() != null && live.getEid() > 0) {
            //获取节目信息
            Episode episode = episodeService.findOne(live.getEid());
            if (episode != null && episode.getChatRoom() != null) {
                Episode.ChatRoom chatRoom = episode.getChatRoom();
                liveChatDTO.setLiveid(live.getId());
                liveChatDTO.setChatstarttime(LeDateUtils.parseYYYYMMDDHHMMSS(chatRoom.getStartTime()));
                liveChatDTO.setChatendtime(LeDateUtils.parseYYYYMMDDHHMMSS(chatRoom.getEndTime()));
            }
        }
        liveChatDTO.setIsdanmaku(true);
        liveChatDTO.setIschat(true);
        liveChatDTO.setIsquestion(true);
        return liveChatDTO;
    }

    public LiveSportsDTO buildLiveSportsDTO(Live live, LiveSportsDTO old) {
        LiveSportsDTO liveSportsDTO;
        if (null == old) {
            liveSportsDTO = new LiveSportsDTO();
        } else {
            liveSportsDTO = old;
        }
        liveSportsDTO.setLiveid(live.getId());
        if (LeNumberUtils.toLong(live.getEid()) <= 0) {
            return liveSportsDTO;
        }
        //获取节目信息
        Episode episode = episodeService.findOne(live.getEid());
        liveSportsDTO.setBegintime(LeDateUtils.parseYYYYMMDDHHMMSS(episode.getStartTime()));
        liveSportsDTO.setEndtime(LeDateUtils.parseYYYYMMDDHHMMSS(episode.getEndTime()));
        liveSportsDTO.setCommentarylanguage(live.getCommentaryLanguage());
        //fixme
//        liveSportsDTO.setLevel1(String.valueOf(episode.getChannelId()));
//        liveSportsDTO.setLevel2(String.valueOf(episode.getCid()));
        liveSportsDTO.setLevel1("142");
        liveSportsDTO.setLevel2("204");
        Competition competition = SbdCompetitionInternalApis.getCompetitionById(episode.getCid());
        if (null != competition) {
            liveSportsDTO.setLevel2imgurl(LeStringUtils.trimToEmpty(competition.getOfficialUrl()));
        }
        return liveSportsDTO;
    }

    private LiveMediaDTO buildLiveMediaDTO(Live live, LiveMediaDTO old) {
        LiveMediaDTO liveMediaDTO;
        if (null == old) {
            liveMediaDTO = new LiveMediaDTO();
        } else {
            liveMediaDTO = old;
        }
        liveMediaDTO.setLiveid(live.getId());
        liveMediaDTO.setWeight((byte) 0);
//        liveMediaDTO.setFocuspic();
//        liveMediaDTO.setViewpic();
        return liveMediaDTO;
    }

    public LiveWrapperDTO buildLiveWrapperDTO(Live live) {
        LiveWrapperDTO liveWrapperDTO = null;
        if (LeNumberUtils.toLong(live.getId()) > 0) {
            try {
                liveWrapperDTO = liveRoomSportsQueryAPI.getLiveRoomWithBranchByLiveId(urusAuth, live.getId());
            } catch (UrusLiveRoomException e) {
            }
            if (null == liveWrapperDTO) {
                throw new HedwigException(LiveErrorCode.GET_EXISTING_LIVE_FAIL.name());
            }
        } else {
            liveWrapperDTO = new LiveWrapperDTO();
        }
        liveWrapperDTO.setLiveBranchDTOs(buildLiveBranchDTO(live, liveWrapperDTO.getLiveBranchDTOs()));
        liveWrapperDTO.setLiveMainDTO(buildLiveMainDTO(live, liveWrapperDTO.getLiveMainDTO()));
        liveWrapperDTO.setLiveStatusDTO(buildLiveStatusDTO(live, liveWrapperDTO.getLiveStatusDTO()));
        liveWrapperDTO.setLiveChatDTO(buildLiveChatDTO(live, liveWrapperDTO.getLiveChatDTO()));
        liveWrapperDTO.setLiveSportsDTO(buildLiveSportsDTO(live, liveWrapperDTO.getLiveSportsDTO()));
        liveWrapperDTO.setLiveMediaDTO(buildLiveMediaDTO(live, liveWrapperDTO.getLiveMediaDTO()));
        return liveWrapperDTO;
    }

    @Override
    protected boolean doCreate(Live entity) {
        updateLiveStatus(entity);
        updateLiveFromCopyright(entity);
        updateIsPayFromChannel(entity);
        checkLive(entity);
        //创建大乐视直播
        LiveWrapperDTO liveWrapperDTO = buildLiveWrapperDTO(entity);
        Long liveId = 0L;
        try {
            LOG.info("Save live {} to cloud.", JSONObject.toJSONString(liveWrapperDTO));
            liveId = liveRoomSportsWriterAPI.save(urusAuth, liveWrapperDTO, entity.getCreator(), "127.0.0.1");
        } catch (UrusLiveRoomException urusLiveRoomException) {
            LOG.error("[liveService][doCreate][commentaryLanguage={}, errorCode={}, errorField={}, errorDesc={}]",
                    entity.getCommentaryLanguage(), urusLiveRoomException.getErrorCode(), urusLiveRoomException.getErrorFeild(),
                    urusLiveRoomException.getErrorDesc(), urusLiveRoomException);
            sendWarn(entity, liveWrapperDTO, urusLiveRoomException, "/sbc/live/doCreate");
            throw new HedwigException(LiveErrorCode.SAVE_LIVE_TO_CLOUD_FAIL.name(), urusLiveRoomException);
        } catch (Exception e) {
            LOG.error("[liveService][doCreate][commentaryLanguage={}]", entity.getCommentaryLanguage(), e);
        }
        if (liveId <= 0) {
            LOG.info("[liveService][doCreate][liveId lt 0][commentaryLanguage={}]", entity.getCommentaryLanguage());
            return false;
        }
        entity.setId(liveId);
        entity.setDeleted(false);
        liveRepository.save(entity);

        return true;
    }

    @Override
    protected boolean doAfterCreate(Live entity) {
        episodeService.updateLive(entity.getEid(), entity);
        createLiveShield(entity);
        updateOnlineClientConfig(entity);
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setBusinessTypes(ActionType.ADD, Lists.newArrayList()).setIdType(IdType.LETV_LIVE).build();
        SwiftMessageApis.sendMsgSync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Live entity) {
        episodeService.updateLive(entity.getEid(), entity);
        updateLiveStatus(entity);
        updateLiveFromCopyright(entity);
        updateIsPayFromChannel(entity);
        //更新大乐视直播
        LiveWrapperDTO liveWrapperDTO = buildLiveWrapperDTO(entity);
        try {
            LOG.info("Save live {} to cloud {}.", JSONObject.toJSONString(entity), JSONObject.toJSONString(liveWrapperDTO));
            liveRoomSportsWriterAPI.update(urusAuth, liveWrapperDTO, entity.getCreator(), "127.0.0.1");
        } catch (UrusLiveRoomException urusLiveRoomException) {
            LOG.error("[liveService][doCreate][commentaryLanguage={}，errorCode={},errorDesc={}]",
                    entity.getCommentaryLanguage(), urusLiveRoomException.getErrorCode(), urusLiveRoomException.getErrorDesc(), urusLiveRoomException);
            sendWarn(entity, liveWrapperDTO, urusLiveRoomException, "/sbc/live/doCreate");
            throw new HedwigException(LiveErrorCode.SAVE_LIVE_TO_CLOUD_FAIL.name(), urusLiveRoomException);
        } catch (Exception e) {
            LOG.error("[liveService][doCreate][commentaryLanguage={}]", entity.getCommentaryLanguage(), e);
        }
        entity.setDeleted(false);
        return liveRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Live entity) {
        updateLiveShield(entity);
        updateOnlineClientConfig(entity);
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setBusinessTypes(ActionType.UPDATE, Lists.newArrayList()).setIdType(IdType.LETV_LIVE).build();
        SwiftMessageApis.sendMsgSync(message);
        return true;
    }

    @Override
    protected boolean doAfterDelete(Live entity) {
        episodeService.deleteLive(entity.getEid(), entity.getId());
        //调取大乐视接口去删除直播内容
        Live live = findOne(entity.getId());
        LiveWrapperDTO liveWrapperDTO = buildLiveWrapperDTO(live);
        try {
            liveRoomSportsWriterAPI.deleteLive(urusAuth, liveWrapperDTO, live.getCreator(), "");
        } catch (UrusLiveRoomException liveRoomException) {
            LOG.error("[hessian delete live fail][id={}，errorCode={},errorDesc={}]", entity.getId(),
                    liveRoomException.getErrorCode(), liveRoomException.getErrorDesc(), liveRoomException);
            sendWarn(entity, liveWrapperDTO, liveRoomException, "/sbc/live/doCreate");
            throw new HedwigException(LiveErrorCode.DELETE_LIVE_IN_CLOUD_FAIL.name(), liveRoomException);
        } catch (Exception e) {
            LOG.error("[hessian delete live fail][id={}]", entity.getId(), e);
        }

        try {
            liveShieldConfigAPI.deleteLiveShieldByShieldId(urusAuth, entity.getShieldRowId());
        } catch (UrusLiveRoomException e) {
            LOG.error("{}", e.getMessage(), e);
            throw new HedwigException(LiveErrorCode.DELETE_LIVE_SHIELD_FAIL.name(), e);
        }
        //删除节目上的live
        Episode episode = episodeService.findOne(entity.getEid());
        Set<Episode.LiveStream> liveStreams = episode.getLiveStreams();
        Iterator<Episode.LiveStream> liveStreamIterator = liveStreams.iterator();
        while (liveStreamIterator.hasNext()) {
            Episode.LiveStream liveStream = liveStreamIterator.next();
            if (liveStream.getId().equals(entity.getId())) {
                liveStreamIterator.remove();
            }
        }
        episodeService.save(episode);

        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setBusinessTypes(ActionType.DELETE, Lists.newArrayList()).setIdType(IdType.LETV_LIVE).build();
        SwiftMessageApis.sendMsgSync(message);
        return true;
    }

    @Override
    protected Live doFindExistEntity(Live entity) {
        return liveRepository.findOne(entity.getId());
    }

    @Override
    public List<Live> findByIds(List<Long> longs) {
        return super.findByIds(longs);
    }

    @Override
    public List<Live> findByQuery(InternalQuery internalQuery) {
        return super.findByQuery(internalQuery);
    }

    @Override
    public List<Long> findIdByQuery(InternalQuery internalQuery) {
        return super.findIdByQuery(internalQuery);
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return liveRepository;
    }

    /**
     * 更新媒体云上线终端
     *
     * @param entity
     * @return
     */
    private boolean updateOnlineClientConfig(Live entity) {
        try {
            List<OpClientLiveRoomDTO> opClientLiveRoomDTOs = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(entity.getPlayPlatforms())) {
                Set<String> platforms = getSplitIdsByClientPlatforms(entity.getPlayPlatforms());
                for (String platform : platforms) {
                    OpClientLiveRoomDTO opClientLiveRoomDTO = new OpClientLiveRoomDTO();
                    opClientLiveRoomDTO.setLiveid(entity.getId());
                    opClientLiveRoomDTO.setClientid(platform);
                    opClientLiveRoomDTO.setLivetype(LiveType.SPORTS.toString());
                    opClientLiveRoomDTO.setIsChecked(OpClientLiveRoomDTO.CHECKED);
                    opClientLiveRoomDTOs.add(opClientLiveRoomDTO);
                }
            }
            String json = JSONObject.toJSONString(opClientLiveRoomDTOs);
            liveRoomSportsWriterAPI.saveConfig(urusAuth, json, entity.getId());
            LOG.info("Save live online config : {} for live {}", json, entity.getId());
        } catch (UrusLiveRoomException ulre) {
            LOG.error("{}", ulre.getMessage(), ulre);
            throw new HedwigException(LiveErrorCode.SAVE_ONLINE_CLIENT_TO_CLOUD_FAIL.name(), ulre);
        }
        return true;
    }

    private boolean createLiveShield(Live entity) {
        try {
            LiveShieldConfigDTO liveShieldConfigDTO = getLiveShieldConfigDTOFromEpisode(entity);
            Long rowId = liveShieldConfigAPI.saveLiveShield(urusAuth, liveShieldConfigDTO);
            LOG.info("Save live shield config : {}, {} for live {}", JSONObject.toJSONString(liveShieldConfigDTO), rowId, entity.getId());
            if (LeNumberUtils.toLong(rowId) > 0) {
                entity.setShieldRowId(rowId);
                liveRepository.save(entity);
            }
        } catch (UrusLiveRoomException ulre) {
            LOG.error("{}", ulre.getMessage(), ulre);
            throw new HedwigException(LiveErrorCode.SAVE_LIVE_SHIELD_FAIL.name(), ulre);
        }
        return true;
    }


    private boolean updateLiveShield(Live entity) {
        try {
            LiveShieldConfigDTO liveShieldConfigDTO = getLiveShieldConfigDTOFromEpisode(entity);
            Integer rowId = liveShieldConfigAPI.updateLiveShield(urusAuth, liveShieldConfigDTO);
            LOG.info("Save live shield config : {}, {} for live {}", JSONObject.toJSONString(liveShieldConfigDTO),
                    LeNumberUtils.toInt(rowId) > 0 ? true : false, entity.getId());
        } catch (UrusLiveRoomException ulre) {
            LOG.error("{}", ulre.getMessage(), ulre);
            throw new HedwigException(LiveErrorCode.SAVE_LIVE_SHIELD_FAIL.name(), ulre);
        }
        return true;
    }

    private Set<Integer> getLivePlatformsByClientPlatforms(Set<Long> clientPlatforms) {
        if (CollectionUtils.isEmpty(clientPlatforms)) {
            return Sets.newHashSet();
        }
        Set<Integer> platforms = new HashSet<>();
        clientPlatforms.forEach(item -> {
            ClientPlatform clientPlatform = QmtClientPlatformInternalApis.getClientPlatformById(item);
            if (null != clientPlatform) {
                platforms.add(LIVE_PLATFORM_MAPPING.get(clientPlatform.getPlatform()));
            }
        });
        return platforms;
    }

    private Set<String> getSplitIdsByClientPlatforms(Set<Long> clientPlatforms) {
        if (CollectionUtils.isEmpty(clientPlatforms)) {
            return Sets.newHashSet();
        }
        Set<String> platforms = new HashSet<>();
        clientPlatforms.forEach(item -> {
            ClientPlatform clientPlatform = QmtClientPlatformInternalApis.getClientPlatformById(item);
            if (null != clientPlatform) {
                platforms.add(clientPlatform.getClientId());
            }
        });
        return platforms;
    }

    private void updateLiveStatus(Live live) {
        if (null != live.getOpStatus()) {
            live.setStatus(live.getOpStatus());
            return;
        }
        String startTime = live.getStartTime();
        String endTime = live.getEndTime();
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        if (now.compareTo(startTime) < 0) {
            live.setStatus(LiveStatus.LIVE_NOT_START);
        } else if (now.compareTo(endTime) < 0) {
            live.setStatus(LiveStatus.LIVING);
        } else if (now.compareTo(endTime) > 0) {
            live.setStatus(LiveStatus.LIVE_END);
        }
    }

    private void updateLiveFromCopyright(Live live) {
        if (LeNumberUtils.toLong(live.getCopyrightId()) <= 0) {
            return;
        }
        live.setPlayPlatforms(getClientPlatformIdsByCopyrightId(live.getCopyrightId()));
    }

    private void updateIsPayFromChannel(Live live) {
        long channelId = 0;
        if (LeNumberUtils.toLong(live.getChannelId()) > 0) {
            channelId = LeNumberUtils.toLong(live.getChannelId());
        }
        if (LeNumberUtils.toLong(live.getCibnChannelId()) > 0) {
            channelId = LeNumberUtils.toLong(live.getCibnChannelId());
        }
        if (channelId <= 0) {
            live.setIsPay(false);
            return;
        }
        try {
            ChannelIdNameEnameDTO channel = liveRoomSportsQueryAPI.getLiveChannelById(urusAuth, channelId);
            live.setIsPay(LeNumberUtils.toBoolean(channel.getIspay()));
        } catch (UrusLiveRoomException e) {
            LOG.error("{}", e.getMessage(), e);
            throw new HedwigException(LiveErrorCode.GET_LIVE_CHANNEL_FAIL.name(), e);
        }
    }


    private Set<Long> getClientPlatformIdsByCopyrightId(long copyrightId) {
        Copyright copyright = QmtCopyrightInternalApis.getCopyrightById(copyrightId);
        if (null == copyright) {
            return Collections.emptySet();
        }
        return copyright.getClientPlatformId();
    }

    private void checkLive(Live live) {
        Episode episode = episodeService.findOne(live.getEid());
        if (null == episode) {
            throw new HedwigException("Fail to save live, episode is null");
        }

        if (live.getIsPay() && LeStringUtils.isEmpty(episode.getLiveUniqueId())) {
            throw new HedwigException("Fail to save live, live is pay but no screenings.");
        }

        if (live.getIsPay() && CollectionUtils.isEmpty(live.getPayPlatforms())) {
            throw new HedwigException("Fail to save live, live is pay but no pay platforms.");
        }
    }

    private void sendWarn(Live entity, LiveWrapperDTO liveWrapperDTO, Exception e, String method) {
        LogContent logContent = new LogContent();
        logContent.setEntryId(String.valueOf(entity.getId()));
        logContent.setMethodPath(method);
        logContent.setOperator(entity.getUpdater());
        logContent.setIdType(IdType.LETV_LIVE); //索引类型
        logContent.setSendTo("pangchuanxiao@le.com"); //多个邮件用逗号分隔
        logContent.setSubject("直播大厅保存到云失败"); //邮件标题
        logContent.setMemo(e.getMessage() + JSONObject.toJSONString(liveWrapperDTO)); //邮件内容
        LoggerHandler.sendEMail(logContent);
    }
}
