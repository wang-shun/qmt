package com.lesports.qmt.web.helper;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.utils.HtopicListApi;
import com.lesports.utils.TopicListApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Locale;

import static com.lesports.LeConstants.COMMA;

/**
* meta信息填充器
* User: ellios
* Time: 16-3-28 : 下午1:45
*/
@Component("metaFiller")
public class MetaFiller {
    private final static Logger LOG = LoggerFactory.getLogger(MetaFiller.class);

    private static final String LE_META_SCHEDULE_TITLE_KEY = "le.meta.schedule.title";
    private static final String LE_META_SCHEDULE_KEYWORDS_KEY = "le.meta.schedule.keywords";
    private static final String LE_META_SCHEDULE_DESCRIPTION_KEY = "le.meta.schedule.description";

    private static final String LE_META_SEARCH_TITLE_KEY = "le.meta.search.title";
    private static final String LE_META_SEARCH_KEYWORDS_KEY = "le.meta.search.keywords";
    private static final String LE_META_SEARCH_DESCRIPTION_KEY = "le.meta.search.description";

    private static final String LE_META_LIVE_TITLE_KEY = "le.meta.live.title";

    private static final String LE_META_MATCH_AGAINST_TITLE_KEY = "le.meta.match.against.title";
    private static final String LE_META_MATCH_NOT_AGAINST_TITLE_KEY = "le.meta.match.not.against.title";
    private static final String LE_META_MATCH_DESCRIPTION_KEY = "le.meta.match.description";

    private static final String LE_META_VIDEO_TITLE_KEY = "le.meta.video.title";

    private static final String LE_META_NEWS_TITLE_KEY = "le.meta.news.title";
    private static final String LE_META_NEWS_IMAGE_ALBUM_KEYWORDS_KEY = "le.meta.news.image.album.keywords";
    private static final String LE_META_NEWS_ELSE_KEYWORDS_KEY = "le.meta.news.else.keywords";

    private static final String LE_META_TEAM_PLAYER_TITLE_KEY = "le.meta.team.player.title";
    private static final String LE_META_TEAM_PLAYER_KEYWORDS_KEY = "le.meta.team.player.keywords";

    private static final String LE_META_CSL_SCHEDULE_TITLE_KEY = "le.meta.csl.schedule.title";
    private static final String LE_META_CSL_SCHEDULE_KEYWORDS_KEY = "le.meta.csl.schedule.keywords";
    private static final String LE_META_CSL_SCHEDULE_DESCRIPTION_KEY = "le.meta.csl.schedule.description";

    private static final String LE_META_CSL_TOPLIST_TITLE_KEY = "le.meta.csl.toplist.title";
    private static final String LE_META_CSL_TOPLIST_KEYWORDS_KEY = "le.meta.csl.toplist.keywords";
    private static final String LE_META_CSL_TOPLIST_DESCRIPTION_KEY = "le.meta.csl.toplist.description";

    private static final String LE_META_TOPICLIST_TITLE_KEY = "le.meta.topiclist.title";

    private static final String LE_META_EURO_DESCRIPTION_KEY = "le.meta.euro.description";

    private static final String LE_META_EURO_NEWS_TITLE = "le.meta.euro.news.title";
    private static final String LE_META_EURO_NEWS_PHOTO_TITLE = "le.meta.euro.news.photo.title";
    private static final String LE_META_EURO_SCHEDULE_TITLE = "le.meta.euro.schedule.title";
    private static final String LE_META_EURO_STANDINGS_TITLE = "le.meta.euro.standings.title";
    private static final String LE_META_EURO_SCORER_TITLE = "le.meta.euro.scorer.title";
    private static final String LE_META_EURO_DATA_TITLE = "le.meta.euro.data.title";
    private static final String LE_META_EURO_TEAMS_TITLE = "le.meta.euro.teams.title";

    private static final String LE_META_EURO_NEWS_KEYWORDS = "le.meta.euro.news.keywords";
    private static final String LE_META_EURO_NEWS_PHOTO_KEYWORDS = "le.meta.euro.news.photo.keywords";
    private static final String LE_META_EURO_SCHEDULE_KEYWORDS = "le.meta.euro.schedule.keywords";
    private static final String LE_META_EURO_STANDINGS_KEYWORDS = "le.meta.euro.standings.keywords";
    private static final String LE_META_EURO_SCORER_KEYWORDS = "le.meta.euro.scorer.keywords";
    private static final String LE_META_EURO_DATA_KEYWORDS = "le.meta.euro.data.keywords";
    private static final String LE_META_EURO_TEAMS_KEYWORDS = "le.meta.euro.teams.keywords";

    private static final String LE_META_OLYMPIC_NAMELIST_TITLE = "le.meta.olympic.namelist.title";
    private static final String LE_META_OLYMPIC_RESULTS_TITLE = "le.meta.olympic.results.title";



    @Resource
    private MessageSource messageSource;

    /**
     * 搜索的meta
     *
     * @param model
     */
    public void fillSearchMeta(Model model,String wd, Locale locale) {
        model.addAttribute("title", messageSource.getMessage(LE_META_SEARCH_TITLE_KEY, new String[]{wd}, locale));
        model.addAttribute("keywords", messageSource.getMessage(LE_META_SEARCH_KEYWORDS_KEY, new String[]{wd, wd, wd}, locale));
        model.addAttribute("description", messageSource.getMessage(LE_META_SEARCH_DESCRIPTION_KEY, null, locale));
    }

    /**
     * 填充比赛大厅的
     *
     * @param model
     */
    public void fillScheduleMeta(Model model, Locale locale) {
        model.addAttribute("title", messageSource.getMessage(LE_META_SCHEDULE_TITLE_KEY, null, locale));
        model.addAttribute("keywords", messageSource.getMessage(LE_META_SCHEDULE_KEYWORDS_KEY, null, locale));
        model.addAttribute("description", messageSource.getMessage(LE_META_SCHEDULE_DESCRIPTION_KEY, null, locale));
    }

    /**
     * 直播的meta
     *
     * @param model
     */
    public  void fillLiveMeta(Model model, TComboEpisode episode,CallerParam caller,Locale locale) {
        String metaTitle =  messageSource.getMessage(LE_META_LIVE_TITLE_KEY, new String[]{episode.getName()}, locale);
        StringBuilder keywordSb = new StringBuilder();
        keywordSb.append(episode.getName());
        if (episode.getGameFType() != 0) {
            TDictEntry gameFDic = QmtConfigApis.getTDictEntryById(episode.getGameFType(), caller);
            if (gameFDic != null) {
                keywordSb.append(COMMA).append(gameFDic.getName());
            }
        }
        if (episode.getGameSType() != 0) {
            TDictEntry gameSDic = QmtConfigApis.getTDictEntryById(episode.getGameSType(),caller);
            if (gameSDic != null) {
                keywordSb.append(COMMA).append(gameSDic.getName());
            }
        }
        model.addAttribute("title", metaTitle);
        model.addAttribute("keywords", keywordSb.toString());
        model.addAttribute("description", episode.getName());
    }

    public void fillOlympicNameListMeta(Model model, TComboEpisode episode, Locale locale) {
        String metaTitle =  messageSource.getMessage(LE_META_OLYMPIC_NAMELIST_TITLE, new String[]{episode.getName()}, locale);
        model.addAttribute("title",metaTitle);
    }

    public void fillOlympicResultsMeta(Model model, TComboEpisode episode, Locale locale) {
        String metaTitle =  messageSource.getMessage(LE_META_OLYMPIC_RESULTS_TITLE, new String[]{episode.getName()}, locale);
        model.addAttribute("title",metaTitle);
    }


    /**
     * 添加比赛页 meta信息
     *
     * @param model
     * @param competition
     * @param episode
     */
    public void fillMatchMeta(Model model, TCompetition competition, TComboEpisode episode,Locale locale) {
        try {
            String metaTitle = "";
            String metaKeywords = "";
            String metaDescription = "";
            if (episode.isVs()) {
                String homeTeam = "";
                String awayTeam = "";
                if (episode.getCompetitors() != null) {
                    for (TCompetitor competitor : episode.getCompetitors()) {
                        if (competitor.getGround().equals(GroundType.HOME)) {
                            homeTeam = competitor.getName();
                        }
                        if (competitor.getGround().equals(GroundType.AWAY)) {
                            awayTeam = competitor.getName();
                        }
                    }
                }

                metaTitle = messageSource.getMessage(LE_META_MATCH_AGAINST_TITLE_KEY,new String[]{homeTeam + "VS" + awayTeam + "_" + competition.getName()},locale);
                StringBuilder keywordSb = new StringBuilder();
                if(null != competition) {
                    keywordSb.append(homeTeam).append(COMMA).append(awayTeam).append(COMMA).append(homeTeam).append("VS").append(awayTeam)
                            .append(COMMA).append(competition.getName());
                    if (StringUtils.isNotBlank(competition.getGameSName())) {
                        keywordSb.append(COMMA).append(competition.getGameSName());
                    }
                    if (StringUtils.isNotBlank(competition.getGameFName())) {
                        keywordSb.append(COMMA).append(competition.getGameFName());
                    }
                }
                metaKeywords = keywordSb.toString();
                //"乐视体育比赛页为你呈现：【红队VS黑队】，【赛季赛事名称】直播，【红队VS黑队】，【赛季赛事名称】预测，【红队VS黑队】，【赛季赛事名称】比赛数据，【红队VS黑队】，【赛季赛事名称】比赛集锦"
                String vs = homeTeam+"VS"+awayTeam+COMMA+competition.getName();
                String[] descriptions = new String[]{vs,COMMA+vs,COMMA+vs,COMMA+vs};
                metaDescription = messageSource.getMessage(LE_META_MATCH_DESCRIPTION_KEY,descriptions,locale);
            } else {
                //【比赛名称】_【赛事名称】_赛事直播_对阵数据-乐视体育
                metaTitle = messageSource.getMessage(LE_META_MATCH_NOT_AGAINST_TITLE_KEY,new String[]{episode.getName() + "_" + competition.getName()},locale);
                //【比赛名称】,【赛季赛事名称】直播，【小项】,【大项】
                StringBuilder keywordSb = new StringBuilder();
                keywordSb.append(episode.getName()).append(COMMA).append(competition.getName());
                if (StringUtils.isNotBlank(competition.getGameSName())) {
                    keywordSb.append(COMMA).append(competition.getGameSName());
                }
                if (StringUtils.isNotBlank(competition.getGameFName())) {
                    keywordSb.append(COMMA).append(competition.getGameFName());
                }
                metaKeywords = keywordSb.toString();

                //乐视体育比赛页为你呈现：【比赛名称】，【赛事名称】直播，【比赛名称】，【赛事名称】预测，【比赛名称】，【赛事名称】比赛数据，【比赛名称】，【赛事名称】比赛集锦
                String noVs = episode.getName()+COMMA+competition.getName();
                String[] descriptions = new String[]{noVs,COMMA+noVs,COMMA+noVs,COMMA+noVs};
                metaDescription = messageSource.getMessage(LE_META_MATCH_DESCRIPTION_KEY,descriptions,locale);
            }

            model.addAttribute("title", metaTitle);
            model.addAttribute("keywords", metaKeywords);
            model.addAttribute("description", metaDescription);
        } catch (Exception e) {
            LOG.error("add match mate error! competition = {}, episode = {}, e = {}", competition, episode, e);
        }
    }

    /**
     * 添加播放页 meta信息
     *
     * @param model
     * @param tVideo
     */
    public void fillVideoMeta(Model model, TVideo tVideo,Locale locale) {
        String metaTitle = messageSource.getMessage(LE_META_VIDEO_TITLE_KEY,new String[]{tVideo.getName()},locale);
        String metaDescription = "";
        if (StringUtils.isNotBlank(tVideo.getName())) {
            metaDescription = tVideo.getName();
        }
        if (StringUtils.isNotBlank(tVideo.getDesc())) {
            metaDescription = metaDescription + COMMA + tVideo.getDesc();
        }
        model.addAttribute("title", metaTitle);
        model.addAttribute("keywords", tVideo.getName());
        model.addAttribute("description", metaDescription);
    }

    /**
     * 添加新闻 meta信息
     *
     * @param model
     * @param tNews
     */
    public void fillNewsMeta(Model model, TNews tNews,Locale locale) {
        String metaDescription = "";
        String metaKeywords = "";
        if (StringUtils.isNotBlank(tNews.getName())) {
            metaDescription = tNews.getName();
        }
        if (StringUtils.isNotBlank(tNews.getDesc())) {
            metaDescription = metaDescription + COMMA + tNews.getDesc();
        }
        if (tNews.getType() == NewsType.IMAGE_ALBUM) {
            metaKeywords = messageSource.getMessage(LE_META_NEWS_IMAGE_ALBUM_KEYWORDS_KEY,new String[]{tNews.getName()},locale);
        } else {
            metaKeywords = messageSource.getMessage(LE_META_NEWS_ELSE_KEYWORDS_KEY,new String[]{tNews.getName()},locale);
        }
        if (CollectionUtils.isNotEmpty(tNews.getTags())) {
            for (TTag tag : tNews.getTags()) {
                metaKeywords = COMMA + tag.getName();
            }
        }
        model.addAttribute("title", messageSource.getMessage(LE_META_NEWS_TITLE_KEY,new String[]{tNews.getName()},locale));
        model.addAttribute("keywords", metaKeywords);
        model.addAttribute("description", metaDescription);
    }


    /**
     * 添加球队meta信息
     *
     * @param model
     * @param tTeam
     */
    public  void fillTeamMeta(Model model, TTeam tTeam,Locale locale) {
        String metaKeywords = "";
        //【球队名】，【球队名】赛程，【球队】数据，【球队名】新闻，【球队名】中超，中超
        String teamName = tTeam.getName();
        String[] keywords = new String[]{teamName+COMMA+teamName,COMMA+teamName,COMMA+teamName,COMMA+teamName,COMMA};
        metaKeywords = messageSource.getMessage(LE_META_TEAM_PLAYER_KEYWORDS_KEY,keywords,locale);

        model.addAttribute("title", messageSource.getMessage(LE_META_TEAM_PLAYER_TITLE_KEY,new String[]{teamName},locale));
        model.addAttribute("keywords", metaKeywords);
        model.addAttribute("description", tTeam.getDesc());
    }

    /**
     * 新的添加球队meta信息
     *
     * @param model
     * @param tTeam
     */
    public  void fillTeamMeta(Model model, TTeam tTeam,String competitcionSeasonName,Locale locale) {
        String metaKeywords = "";
        //【球队名】-【赛事】-乐视体育
        String teamName = tTeam.getName();
        String[] keywords = new String[]{teamName+COMMA+teamName,COMMA+teamName,COMMA+teamName};
        metaKeywords = messageSource.getMessage(LE_META_TEAM_PLAYER_KEYWORDS_KEY,keywords,locale);

        model.addAttribute("title", messageSource.getMessage(LE_META_TEAM_PLAYER_TITLE_KEY,new String[]{teamName,competitcionSeasonName},locale));
        model.addAttribute("keywords", metaKeywords);
        model.addAttribute("description", tTeam.getDesc());
    }

    /**
     * 添加球员meta信息
     *
     * @param model
     * @param tPlayer
     */
    public void fillPlayerMeta(Model model, TPlayer tPlayer,Locale locale) {
        String metaKeywords = "";

        //【球员名】，【球员名】赛程，【球员】数据，【球员名】新闻，【球员名】中超，中超
        String tPlayerName = tPlayer.getName();
        String[] keywords = new String[]{tPlayerName+COMMA+tPlayerName,COMMA+tPlayerName,COMMA+tPlayerName};
        metaKeywords = messageSource.getMessage(LE_META_TEAM_PLAYER_KEYWORDS_KEY,keywords,locale);

        model.addAttribute("title", messageSource.getMessage(LE_META_TEAM_PLAYER_TITLE_KEY,new String[]{tPlayerName},locale));
        model.addAttribute("keywords", metaKeywords);
        model.addAttribute("description", tPlayer.getDesc());
    }

    /**
     * 添加球员meta信息
     *
     * @param model
     * @param tPlayer
     */
    public void fillPlayerMeta(Model model, TPlayer tPlayer,String competitcionSeasonName,Locale locale) {
        String metaKeywords = "";

        //【球员名】-【赛事】-乐视体育
        String tPlayerName = tPlayer.getName();
        String[] keywords = new String[]{tPlayerName+COMMA+tPlayerName,COMMA+tPlayerName,COMMA+tPlayerName};
        metaKeywords = messageSource.getMessage(LE_META_TEAM_PLAYER_KEYWORDS_KEY,keywords,locale);

        model.addAttribute("title", messageSource.getMessage(LE_META_TEAM_PLAYER_TITLE_KEY,new String[]{tPlayerName,competitcionSeasonName},locale));
        model.addAttribute("keywords", metaKeywords);
        model.addAttribute("description", tPlayer.getDesc());
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillCslScheduleMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_CSL_SCHEDULE_TITLE_KEY,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_CSL_SCHEDULE_KEYWORDS_KEY,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_CSL_SCHEDULE_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站榜单页meta信息
     *
     * @param model
     */
    public void fillCslToplistMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_CSL_TOPLIST_TITLE_KEY,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_CSL_TOPLIST_KEYWORDS_KEY,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_CSL_TOPLIST_DESCRIPTION_KEY,null,locale));
    }

	/**
	 * 添加专题列表页mata meta信息
	 *
	 * @param model
	 * @param topicList
	 */
	public void fillTopicListMeta(Model model, TopicListApi.TopicListVo topicList, Locale locale) {
		String metaTitle = messageSource.getMessage(LE_META_TOPICLIST_TITLE_KEY,new String[]{topicList.getName()},locale);
		String metaDescription = "";
		if (StringUtils.isNotBlank(topicList.getName())) {
			metaDescription = topicList.getName();
		}
		if (StringUtils.isNotBlank(topicList.getDescription())) {
			metaDescription = metaDescription + COMMA + topicList.getDescription();
		}
		model.addAttribute("title", metaTitle);
		model.addAttribute("keywords", topicList.getName());
		model.addAttribute("description", metaDescription);
	}

	/**
	 * 添加突发专题列表页mata meta信息
	 *
	 * @param model
	 * @param topicList
	 */
	public void fillHtopicListMeta(Model model, HtopicListApi.TopicListVo topicList, Locale locale) {
		String metaTitle = messageSource.getMessage(LE_META_TOPICLIST_TITLE_KEY,new String[]{topicList.getName()},locale);
		String metaDescription = "";
		if (StringUtils.isNotBlank(topicList.getName())) {
			metaDescription = topicList.getName();
		}
		if (StringUtils.isNotBlank(topicList.getDescription())) {
			metaDescription = metaDescription + COMMA + topicList.getDescription();
		}
		model.addAttribute("title", metaTitle);
		model.addAttribute("keywords", topicList.getName());
		model.addAttribute("description", metaDescription);
	}


    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroNewsMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_NEWS_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_NEWS_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加新闻图集页meta信息
     *
     * @param model
     */
    public void fillEuroNewsPhotoMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_NEWS_PHOTO_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_NEWS_PHOTO_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroScheduleMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_SCHEDULE_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_SCHEDULE_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroStandingsMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_STANDINGS_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_STANDINGS_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroScorerMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_SCORER_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_SCORER_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroDataMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_DATA_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_DATA_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }

    /**
     * 添加m站赛程页meta信息
     *
     * @param model
     */
    public void fillEuroTeamsMeta(Model model,Locale locale) {
        model.addAttribute("title",messageSource.getMessage(LE_META_EURO_TEAMS_TITLE,null,locale));
        model.addAttribute("keywords",messageSource.getMessage(LE_META_EURO_TEAMS_KEYWORDS,null,locale));
        model.addAttribute("description",messageSource.getMessage(LE_META_EURO_DESCRIPTION_KEY,null,locale));
    }
}
