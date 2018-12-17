package com.lesports.qmt.sbc.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.sbc.client.*;
import com.lesports.qmt.sbc.model.*;
import com.lesports.qmt.sbc.vo.RelatedTagVo;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.SbdMatchInternalApis;
import com.lesports.qmt.sbd.SbdPlayerInternalApis;
import com.lesports.qmt.sbd.SbdTeamInternalApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by denghui on 2016/11/25.
 */
public class QmtSbcUtils {

    public static String getNameByEntityId(Long id, CallerParam caller) {
        if (id == null) {
            return null;
        }
        IdType idType = LeIdApis.checkIdTye(id);
        if (idType == IdType.COMPETITION) {
            Competition competition = SbdCompetitionInternalApis.getCompetitionById(id);
            if (competition != null) {
                return CallerUtils.getValueOfLanguage(competition.getMultiLangNames(), caller.getLanguage());
            }
        } else if (idType == IdType.MATCH) {
            Match match = SbdMatchInternalApis.getMatchById(id);
            if (match != null) {
                return CallerUtils.getValueOfLanguage(match.getMultiLangNames(), caller.getLanguage());
            }
        } else if (idType == IdType.PLAYER) {
            Player player = SbdPlayerInternalApis.getPlayerById(id);
            if (player != null) {
                return CallerUtils.getValueOfLanguage(player.getMultiLangNames(), caller.getLanguage());
            }
        } else if (idType == IdType.TEAM) {
            Team team = SbdTeamInternalApis.getTeamById(id);
            if (team != null) {
                return CallerUtils.getValueOfLanguage(team.getMultiLangNames(), caller.getLanguage());
            }
        } else if (idType == IdType.PROGRAM_ALBUM) {
            ProgramAlbum programAlbum = QmtSbcProgramAlbumInternalApis.getProgramAlbumById(id);
            if (programAlbum != null) {
                return programAlbum.getName();
            }
        } else if (idType == IdType.EPISODE) {
            Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(id);
            if (episode != null) {
                return episode.getName();
            }
        } else if (idType == IdType.TAG) {
            Tag tag = QmtConfigTagInternalApis.getTagById(id);
            if (tag != null) {
                return tag.getName();
            }
        } else if (idType == IdType.DICT_ENTRY) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(id);
            if (dictEntry != null) {
                return CallerUtils.getValueOfLanguage(dictEntry.getMultiLangNames(), caller.getLanguage());
            }
        } else if (idType == IdType.NEWS) {
            News news = QmtSbcNewsInternalApis.getNewsById(id);
            if (news != null) {
                return news.getName();
            }
        } else if (idType == IdType.VIDEO) {
            Video video = QmtSbcVideoInternalApis.getVideoById(id);
            if (video != null) {
                return video.getTitle();
            }
        } else if (idType == IdType.TOPIC) {
            Topic topic = QmtSbcTopicInternalApis.getTopicById(id);
            if (topic != null) {
                return topic.getName();
            }
        }
        return null;
    }

    public static List<RelatedTagVo> getRelatedTagVosByRelatedIds(Set<Long> relatedIds, CallerParam caller) {
        if (CollectionUtils.isEmpty(relatedIds)) {
            return Collections.EMPTY_LIST;
        }
        List<RelatedTagVo> result = Lists.newArrayList();
        for (Long relatedId : relatedIds) {
            String name = getNameByEntityId(relatedId, caller);
            if (StringUtils.isNotEmpty(name)) {
                RelatedTagVo relatedTagVo = new RelatedTagVo();
                relatedTagVo.setId(relatedId);
                relatedTagVo.setName(name);
                result.add(relatedTagVo);
            }
        }
        return result;
    }

    public static Set<Long> extractMids(Set<Long> relatedIds) {
        Set<Long> mids = Sets.newHashSet();
        relatedIds.removeIf(id -> {
            if (LeIdApis.checkIdTye(id) == IdType.MATCH) {
                mids.add(id);
                return true;
            }
            return false;
        });
        return mids;
    }
}
