package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.helper.MatchHelper;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ruiyuansheng on 2016/3/22.
 */
@Component("matchVoConverter")
public class TMatchVoConverter implements TDtoConverter<Match, TMatch> {

    private static final Logger LOG = LoggerFactory.getLogger(TMatchVoConverter.class);


    @Resource
    private CompetitionRepository competitionRepository;

    @Resource
    private MatchHelper matchHelper;

    @Override
    public TMatch toDto(Match match) {
        Preconditions.checkNotNull(match);
        TMatch tMatch = new TMatch();
        fillTMatchWithMatch(tMatch, match);
        return tMatch;
    }

    @Override
    public TMatch adaptDto(TMatch vo, CallerParam caller) {

        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);

        adaptVoName4Caller(vo, caller);
        adaptVoVenue4Caller(vo, caller);
        adaptVoMoment4Caller(vo,caller);
        adaptVoEids4Caller(vo,caller);
        adaptVoCompetitors4Caller(vo,caller);
        return vo;
    }

    private void adaptVoEids4Caller(TMatch vo, CallerParam caller){
        long eid = CallerUtils.getIdofCountryAndLanguage(vo.getEids(), caller.getCountry(),caller.getLanguage());
        if(eid > 0){
            vo.setEid(eid);
        }

    }

    private void adaptVoName4Caller(TMatch vo, CallerParam caller){
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langName)){
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }

    private void adaptVoVenue4Caller(TMatch vo, CallerParam caller){
        String langVenue = CallerUtils.getValueOfLanguage(vo.getMultiLangVenues(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langVenue)){
            vo.setVenue(langVenue);
        }
        vo.setMultiLangVenuesIsSet(false);
    }

    private void adaptVoMoment4Caller(TMatch vo, CallerParam caller){
        String langMoment = CallerUtils.getValueOfLanguage(vo.getMultiLangMoments(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langMoment)){
            vo.setMoment(langMoment);
        }
        vo.setMultiLangVenuesIsSet(false);
    }

    private void adaptVoCompetitors4Caller(TMatch vo, CallerParam caller) {
        Iterator<TCompetitor> competitorsIterator = vo.getCompetitorsIterator();
        if (null != competitorsIterator) {
            while (competitorsIterator.hasNext()) {
                TCompetitor tCompetitor = competitorsIterator.next();
                if (null != tCompetitor) {
                    adaptVoCompetitor4Caller(caller, tCompetitor);
                }
            }
        }
    }

    private void adaptVoCompetitor4Caller(CallerParam caller, TCompetitor tCompetitor) {
        String langName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            tCompetitor.setName(langName);
        }
        tCompetitor.setMultiLangNamesIsSet(false);

        if(tCompetitor.getCompetitorType() == CompetitorType.TEAM) {
            String teamLogo = CallerUtils.getValueOfCountry(tCompetitor.getMultiCounLogos(), caller.getCountry());
            if (StringUtils.isNotEmpty(teamLogo)) {
                tCompetitor.setLogoUrl(teamLogo);
            }
            tCompetitor.setMultiCounLogosIsSet(false);
        }

        String langOfficialName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangOfficialNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langOfficialName)) {
            tCompetitor.setOfficialName(langOfficialName);
        }
        tCompetitor.setMultiLangOfficialNamesIsSet(false);
    }

    private void fillTMatchWithMatch(TMatch tMatch, Match match) {
        tMatch.setId(match.getId());
        // eid -> eids
        tMatch.setEids(match.getEids());
        tMatch.setName(Strings.nullToEmpty(match.getName()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangNames())) {
            tMatch.setMultiLangNames(match.getMultiLangNames());
        }
        tMatch.setCid(LeNumberUtils.toLong(match.getCid()));
        tMatch.setCsid(LeNumberUtils.toLong(match.getCsid()));
        tMatch.setStartTime(match.getStartTime());
        tMatch.setEndTime(match.getEndTime());
        tMatch.setVenue(Strings.nullToEmpty(match.getVenue()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangVenues())) {
            tMatch.setMultiLangVenues(match.getMultiLangVenues());
        }
        tMatch.setStatus(match.getStatus());
//        tMatch.setMoment(Strings.nullToEmpty(match.getMoment()));
//        if (CollectionUtils.isNotEmpty(match.getMultiLangMoments())) {
//            tMatch.setMultiLangMoments(match.getMultiLangMoments());
//        }
		tMatch.setTheRoadOrder(LeNumberUtils.toInt(match.getTheRoadOrder()));
		//是否可同步到云平台
//		tMatch.setIsSyncToCloud(LeNumberUtils.toBoolean(match.getSyncToCloud()));

        long cid = tMatch.getCid();
        Competition competition = competitionRepository.findOne(cid);
        if (competition != null) {
            Set<Long> tagIds = competition.getTagIds();
            if (CollectionUtils.isNotEmpty(tagIds)) {
                for (Long tagId : tagIds) {
                    Tag tag = QmtConfigTagInternalApis.getTagById(tagId);
                    if (tag != null) {
                        TTag tTag = new TTag();
                        tTag.setId(tag.getId());
                        tTag.setName(tag.getName());
                        tMatch.addToTags(tTag);
                    }
                }
            }
        }
        if (match.getRound() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getRound());
            if (dictEntry != null) {
                tMatch.setRound(dictEntry.getReadableName());
            }
        }
        if (match.getStage() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getStage());
            if (dictEntry != null) {
                tMatch.setStage(dictEntry.getReadableName());
            }
        }
        if (match.getGroup() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getGroup());
            if (dictEntry != null) {
                tMatch.setGroup(dictEntry.getReadableName());
            }
        }
        if (match.getSubstation() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getSubstation());
            if (dictEntry != null) {
                tMatch.setSubstation(dictEntry.getReadableName());
            }
        }
        tMatch.setNumber(LeNumberUtils.toInt(match.getNumber()));
        Set<Match.Competitor> competitors = match.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (Match.Competitor competitor : competitors) {
                tMatch.addToCompetitors(matchHelper.createTCompetitor(competitor));
            }
        }
        if (CollectionUtils.isNotEmpty(tMatch.getCompetitors())) {
            //nba赛事，主客场对调
            if (LeConstants.REVERSE_HOME_AWAY_TEAMS_CIDS.contains(tMatch.getCid())) {
                Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
                    @Override
                    public int compare(TCompetitor o1, TCompetitor o2) {
                        return o2.getGround().ordinal() - o1.getGround().ordinal();
                    }
                });
//            } else if(Constants.OLYMPIC_PARTNER_TYPE .equals(match.getPartnerType())) {
//                Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
//                    @Override
//                    public int compare(TCompetitor o1, TCompetitor o2) {
//                        return o1.getOrder()- o2.getOrder();
//                    }
//                });
            }else {
                Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
                    @Override
                    public int compare(TCompetitor o1, TCompetitor o2) {
                        return o1.getGround().ordinal() - o2.getGround().ordinal();
                    }
                });
            }
        }
    }

}
