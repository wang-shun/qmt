package com.lesports.qmt.sbd.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.model.LiveEvent;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.LiveEventRepository;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.utils.Constants;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by ruiyuansheng on 2016/4/1.
 */
@Component("matchActionVoConverter")
public class TMatchActionVoConverter implements TDtoConverter<MatchAction,TMatchAction> {

    private static final Logger LOG = LoggerFactory.getLogger(TMatchActionVoConverter.class);


    @Resource
    private TeamRepository teamRepository;

    @Resource
    private PlayerRepository playerRepository;

    @Resource
    private LiveEventRepository liveEventRepository;

    @Override
    public TMatchAction toDto(MatchAction matchAction) {
        TMatchAction tAction = new TMatchAction();
        fillTMatchActionWithAction(tAction, matchAction);
        return tAction;
    }

    @Override
    public TMatchAction adaptDto(TMatchAction vo, CallerParam caller) {

        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);

        adaptVoContent4Caller(vo, caller);
        adaptVoPlayerName4Caller(vo,caller);
        adaptVoTeamName4Caller(vo,caller);
        adaptVoType4Caller(vo,caller);

        return vo;
    }

    private void adaptVoContent4Caller(TMatchAction vo, CallerParam caller){
        String langCont = CallerUtils.getValueOfLanguage(vo.getMultiLangContents(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langCont)){
            vo.setContent(langCont);
        }
        vo.setMultiLangContentsIsSet(false);
    }

    private void adaptVoPlayerName4Caller(TMatchAction vo, CallerParam caller){
        String langPlayerName = CallerUtils.getValueOfLanguage(vo.getMultiLangPlayerNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langPlayerName)){
            vo.setPlayerName(langPlayerName);
        }
        vo.setMultiLangPlayerNamesIsSet(false);
    }

    private void adaptVoTeamName4Caller(TMatchAction vo, CallerParam caller){
        String langTeamName = CallerUtils.getValueOfLanguage(vo.getMultiLangTeamNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langTeamName)){
            vo.setTeamName(langTeamName);
        }

        String langTeamLogo = CallerUtils.getValueOfCountry(vo.getMultiCounLogos(), caller.getCountry());
        if(StringUtils.isNotEmpty(langTeamLogo)){
            vo.setTeamImageUrl(langTeamLogo);
        }
        vo.setMultiLangTeamNamesIsSet(false);
        vo.setMultiCounLogosIsSet(false);
    }

    private void adaptVoType4Caller(TMatchAction vo, CallerParam caller){
        String langType = CallerUtils.getValueOfLanguage(vo.getMultiLangTypes(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langType)){
            vo.setType(langType);
        }
        vo.setMultiLangTypesIsSet(false);
    }

    private void fillTMatchActionWithAction(TMatchAction tAction, MatchAction action) {
        tAction.setId(action.getId());
        tAction.setMid(action.getMid());
        if (action.getFirstPid() != null) {
            tAction.setPid(action.getFirstPid());
            Player player = playerRepository.findOne(action.getFirstPid());
            if (player != null) {
                tAction.setPlayerName(player.getName());
                if (CollectionUtils.isNotEmpty(player.getMultiLangNames())) {
                    tAction.setMultiLangPlayerNames(player.getMultiLangNames());
                }
                if(player.getImage() != null){
                    tAction.setPlayerImageUrl(player.getImage().getUrl());
                }
            } else {
                LOG.error("this player is null! playerId = {}", action.getFirstPid());
            }
        }
        if (action.getTid() != null) {
            tAction.setTid(action.getTid());
            Team team = teamRepository.findOne(action.getTid());
            if (team != null) {
                tAction.setTeamName(team.getName());
                if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
                    tAction.setMultiLangTeamNames(team.getMultiLangNames());
                }
                if(team.getLogo() != null){
                    tAction.setTeamImageUrl(team.getLogo().getUrl());
                }
                if(CollectionUtils.isNotEmpty(team.getMultiCounLogos())) {
                    tAction.setMultiCounLogos(team.getMultiCounLogos());
                }
            } else {
                LOG.error("this team is null! teamId = {}", action.getTid());
            }
        }
        tAction.setPassedTime(action.getPassedTime());
        if (action.getEventType() != null) {
            LiveEvent eventType = liveEventRepository.findOne(action.getEventType());
            LiveEvent detailEventType = liveEventRepository.findOne(action.getEventDetailType());

            if (eventType != null) {
                tAction.setType(eventType.getName());
                tAction.setTypeId(LeNumberUtils.toLong(action.getEventType()));
                if (CollectionUtils.isNotEmpty(eventType.getMultiLangNames())) {
                    tAction.setMultiLangTypes(eventType.getMultiLangNames());
                }
            } else {
                LOG.error("LiveEvent is null! liveEvent id = {}", action.getEventType());
            }
            //具体行为类型
            if(action.getEventDetailType() != null){
                if (detailEventType != null) {
                    tAction.setDetailType(detailEventType.getName());

                    if (CollectionUtils.isNotEmpty(detailEventType.getMultiLangNames())) {
                        tAction.setMultiLangDetailTypes(detailEventType.getMultiLangNames());
                    }
                } else {
                    LOG.error("LiveEvent is null! liveEvent id = {}", action.getEventDetailType());
                }
            }

            //拼接足球json格式的content
            if(Constants.DICT_EVENT_ID_MAP.get(action.getEventType()) != null){
                tAction.setTypeId(Constants.DICT_EVENT_ID_MAP.get(action.getEventType()));
                String content = "";
                HashMap<String, Long> map = new HashMap();
                //进球
                if(tAction.getTypeId() == 100159000L && eventType != null && Constants.DICT_EVENT_ID_MAP.get(eventType.getId()) != null){
                    map.put("goalType", eventType.getId());
                    content = JSON.toJSONString(map);
                }
                //换人
                else if(tAction.getTypeId() == 104656000L ){
                    map.put("playerOut", action.getSecondPid());
                    map.put("playerIn", action.getFirstPid());
                    content = JSON.toJSONString(map);
                }
                tAction.setContent(content);
            }
        }

        tAction.setCoordinateX(action.getCoordinateX());
        tAction.setCoordinateY(action.getCoordinateY());

        if (action.getSection() != null) {
            DictEntry section = QmtConfigDictInternalApis.getDictById(action.getSection());
            if (section != null) {
                tAction.setSection(section.getName());
                if (CollectionUtils.isNotEmpty(section.getMultiLangNames())) {
                    tAction.setMultiLangSection(section.getMultiLangNames());
                }

            } else {
                LOG.error("this section is null! sectionId = {}", action.getSection());
            }
        }
    }
}
