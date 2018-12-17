package com.lesports.qmt.image.web.api.core;

import com.lesports.utils.LeProperties;

/**
 * Created by denghui on 2016/3/23.
 */
public interface Constants {

    String FILE_SEPARATOR = "/";
    String FILE_DOT = ".";
    String FILE_COMMA = ",";
    String FILE_LINE = "_";
    String IMAGE_FORMAT = ".jpg";
    String IMAGE_BAK = "_bak";

    String IMAGE_SOURCE_PATH = LeProperties.getString("image.source.path", "/sports-data/image/source/");
    String IMAGE_DEST_PATH = LeProperties.getString("image.dest.path", "/sports-data/image/dest/");
    Integer TASK_STATUS_SUCCESS = 1;
    Integer TASK_STATUS_FAILURE = -1;
    Integer TASK_STATUS_UNHANDLE = 2;
    String IMAGE_CHANNEL_SMS = "sms";
    String IMAGE_TYPE_PLAYER_HEAD = "ph";//球员头像
    String IMAGE_TYPE_PLAYER_BACK = "pb";//球员背板图
    String IMAGE_TYPE_PLAYER_TV = "pt";//tv球员用图
    String IMAGE_TYPE_TEAM_LOGO = "tl";//球队logo
    String IMAGE_TYPE_TEAM_LOGO_PNG = "tlp";//球队pnglogo
    String IMAGE_TYPE_TEAM_HOME_SHIRT = "ths";//球队主队队服
    String IMAGE_TYPE_TEAM_AWAY_SHIRT = "tas";//球队客队队服
    String IMAGE_TYPE_TEAM_BACK = "tb";//球队背板图
    String IMAGE_TYPE_COMPETITION_LOGO ="cl";//赛事logo
    String IMAGE_TYPE_COMPETITIONSEASON_LOGO="el";//赛季logo
    String IMAGE_TYPE_ALBUM_LOGO="al";//专辑logo
    String IMAGE_TYPE_ALBUM_TV_LOGO="altv";//专辑tv专用图
    String IMAGE_TYPE_NEWS ="news";//图片新闻
    String IMAGE_TYPE_LIVEMESSAGE = "liveMessage";//图文新闻消息图片
    String IMAGE_TYPE_ACTIVITY ="activity";//推广位图片
    String IMAGE_TYPE_ACTIVITY_ACT ="activitya";//活动图片
    String IMAGE_TYPE_STARTUPLOGO = "startupLogo";//开机图片
    String IMAGE_TYPE_STARTUPLOGOOLD = "startOld";//老用户开机图片
    String IMAGE_TYPE_TVSUGGEST = "tvSuggest";//
    String IMAGE_TYPE_EPISODELOGO = "episode";//
    String IMAGE_TYPE_EPISODE_PC_LOGO = "episodepc";
    String IMAGE_TYPE_MUNU_ITEM = "menuitem";
    String IMAGE_TYPE_CAROUSEL="carousel";
    String IMAGE_SCALE_11 = "1_1";//头像比例1：1
    String IMAGE_SCALE_34 = "3_4";//头像比例3：4

    String IMAGE_WEB = "/image";//页面访问图片的路径
    String IMAGE_WEB_2 = "/image2";//新的图片服务文件路径
    String IMAGE_WEB_3 = "/image3";//新的图片服务文件路径
    String UPLOAD_CHANNEL = "qmt";

    String IMAGE_SIZE_320_200 = "320*200";

    String IMAGE_SIZE_160_120 = "160*120";

    String LIVE_STREAM_IMAGE_SIZE_400_225 = "pic3_400_225";

    String LIVE_STREAM_IMAGE_SIZE_160_90 = "pic4_160_90";

    //视频新闻的图片4:3
    String VIDEO_NEWS_IMAGE_43 = "400*300";
    //视频图片 需要确认大图 16:9的
    String VIDEO_NEWS_IMAGE_BIG_169 = "960*540";

    String VIDEO_NEWS_IMAGE_169 = "400*225";

    String VIDEO_NEWS_IMAGE_200_150 = "200*150";
}
