package com.lesports.qmt.op.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.util.ImageUtils;
import com.lesports.qmt.op.web.api.core.vo.baidu.Serialinfo;
import com.lesports.qmt.op.web.api.core.vo.baidu.Site;
import com.lesports.qmt.op.web.api.core.vo.baidu.VideoVo;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleVideo;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PlayApis;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/20.
 */
@Component("competitionVoCreater")
public class CompetitionVoCreater {

    private static final String VIDEO_PLAY = "http://www.letv.com/ptv/vplay/%s.html";

    private static final String JOIN_MAKK = "$$";


    private static Map<VideoContentType, String> VIDEO_MAP = Maps.newHashMap();


    static {
        VIDEO_MAP.put(VideoContentType.HIGHLIGHTS, "集锦");
        VIDEO_MAP.put(VideoContentType.RECORD, "录像");
        VIDEO_MAP.put(VideoContentType.MATCH_REPORT, "战报");
        VIDEO_MAP.put(VideoContentType.FEATURE, "正片");
        VIDEO_MAP.put(VideoContentType.OTHER, "其他");

    }

    public void createVideoVO(List<VideoVo> videoVos, TComboEpisode comboEpisode, TCompetitionSeason competitionSeason) {
        List<TSimpleVideo> tSimpleVideos = comboEpisode.getVideos();
        if (CollectionUtils.isNotEmpty(tSimpleVideos)) {
            //是否赛程视频即集锦，保证每个赛程只有一个集锦
            boolean hasScheduleVideo = false;
            for (TSimpleVideo simpleVideo : tSimpleVideos) {
                VideoVo videoVo = new VideoVo();
                hasScheduleVideo = fillVideoInfo(videoVo, simpleVideo, hasScheduleVideo);
                fillEpisodeInfo(videoVo, comboEpisode, competitionSeason);
                videoVos.add(videoVo);
            }
        }
    }

    private Boolean fillVideoInfo(VideoVo videoVo, TSimpleVideo simpleVideo, boolean hasScheduleVideo) {
        videoVo.setVideoUrl(String.format(VIDEO_PLAY, simpleVideo.getVid()));
        videoVo.setHighlightsUrl(String.format(VIDEO_PLAY, simpleVideo.getVid()));
        videoVo.setVideoType(VIDEO_MAP.get(simpleVideo.getType()));
        videoVo.setImgUrl(simpleVideo.getImages().get(Constants.VIDEO_SIZE));
        videoVo.setTitle(simpleVideo.getName());
        if (!hasScheduleVideo && (simpleVideo.getType().equals(VideoContentType.HIGHLIGHTS) || simpleVideo.getType().equals(VideoContentType.MATCH_REPORT))) {
            videoVo.setIsSchedule("1");
            hasScheduleVideo = true;
        }
        videoVo.setLength(simpleVideo.getDuration() + "");
        videoVo.setSubtitle(simpleVideo.getName());
        videoVo.setUpdateTime(LeDateUtils.formatYMDHMS(LeDateUtils.parseYYYYMMDDHHMMSS(simpleVideo.getCreateTime())));
        videoVo.setWatchNum(PlayApis.getPlayNum(simpleVideo.getVid() + "") + "");
        videoVo.setUpdateStatus(LeDateUtils.formatYYYY_MM_DD(new Date()));
        videoVo.setBrief(simpleVideo.getName());
        return hasScheduleVideo;
    }

    private void fillEpisodeInfo(VideoVo videoVo, TComboEpisode comboEpisode, TCompetitionSeason season) {
        videoVo.setSeasonName(season.getSeason());
        videoVo.setGameTime(LeDateUtils.formatYYYY_MM_DD(LeDateUtils.parseYYYYMMDDHHMMSS(comboEpisode.getStartTime())));
        if (comboEpisode.getRound() != null) {
            videoVo.setGameRounds(comboEpisode.getRound().replaceAll("\\D", ""));
        }
        fillCompetitors(videoVo, comboEpisode);

    }


    private void fillCompetitors(VideoVo videoVo, TComboEpisode tComboEpisode) {
        List<TCompetitor> competitors = tComboEpisode.getCompetitors();
        StringBuffer team = new StringBuffer();
        if (CollectionUtils.isNotEmpty(competitors)) {
            StringBuffer score = new StringBuffer();
            for (TCompetitor competitor : competitors) {
                if (competitor.getGround().equals(GroundType.HOME)) {
                    videoVo.setTeam1logoUrl(ImageUtils.joinImageLogo(competitor.getLogoUrl(), "11_30_30"));
                    team.append(competitor.getName());
                    score.append(competitor.getFinalResult());
                } else if (competitor.getGround().equals(GroundType.AWAY)) {
                    videoVo.setTeam2logoUrl(ImageUtils.joinImageLogo(competitor.getLogoUrl(), "11_30_30"));
                    score.append(JOIN_MAKK).append(competitor.getFinalResult());
                    team.append(JOIN_MAKK).append(competitor.getName());
                }
            }
            videoVo.setGameScores(score.toString());
        }
        videoVo.setTeam(team.toString());
        videoVo.setGameTeam(team.toString());
        videoVo.setVideoTag(team.append(JOIN_MAKK).append(tComboEpisode.getAbbreviation()).toString());
    }

    public Site createSite(Long cid) {
        Site site = new Site();
        site.setLoc(Constants.SITE_MAP.get(cid));
        site.setLastmod(LeDateUtils.formatYYYY_MM_DD(new Date()));
        site.setPriority("1.0");
        site.setChangefreq("always");
        return site;
    }

    public Serialinfo createSerialinfo(TCompetition competition, TCompetitionSeason competitionSeason) {
        Serialinfo serialinfo = new Serialinfo();
        serialinfo.setLeagueUrl(Constants.SITE_MAP.get(competition.getId()));
        serialinfo.setLeaguelogoUrl(ImageUtils.joinImageLogo(competition.getLogoUrl(), "11_250_250"));
        serialinfo.setTitle(competition.getName());
        serialinfo.setShortTitle(Constants.URL_MAP.inverse().get(competition.getId()));
        serialinfo.setNation(competition.getNation());
        serialinfo.setBrief(competition.getIntroduction());
        serialinfo.setLastgame(LeDateUtils.formatYYYY_MM_DD(LeDateUtils.parseYYYYMMDDHHMMSS(competitionSeason.getEndTime())));
        serialinfo.setSport(competition.getGameFName());
        serialinfo.setState(competition.getContinent());
        serialinfo.setResourceTime(LeDateUtils.formatYMDHMS(new Date()));
        serialinfo.setIs_delete("0");
        return serialinfo;
    }


}
