package com.lesports.qmt.sbc.helper;

import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.sbc.model.*;
import com.lesports.qmt.sbc.service.*;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.SbdMatchInternalApis;
import com.lesports.qmt.sbd.SbdPlayerInternalApis;
import com.lesports.qmt.sbd.SbdTeamInternalApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.utils.CallerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by denghui on 2016/12/11.
 */
@Component("relatedHelper")
public class RelatedHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RelatedHelper.class);

    @Resource
    private NewsService newsService;
    @Resource
    private VideoService videoService;
    @Resource
    private EpisodeService episodeService;
    @Resource
    private TopicService topicService;
    @Resource
    private ProgramAlbumService programAlbumService;

    public String getNameByEntityId(Long id, CallerParam caller) {
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
            ProgramAlbum programAlbum = programAlbumService.findOne(id);
            if (programAlbum != null) {
                return programAlbum.getName();
            }
        } else if (idType == IdType.EPISODE) {
            Episode episode = episodeService.findOne(id);
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
            News news = newsService.findOne(id);
            if (news != null) {
                return news.getName();
            }
        } else if (idType == IdType.VIDEO) {
            Video video = videoService.findOne(id);
            if (video != null) {
                return video.getTitle();
            }
        } else if (idType == IdType.TOPIC) {
            Topic topic = topicService.findOne(id);
            if (topic != null) {
                return topic.getName();
            }
        }
        return null;
    }

}
