package com.lesports.qmt;

import com.lesports.utils.LeProperties;

/**
 * User: ellios
 * Time: 16-3-21 : 下午2:29
 */
public interface QmtConstants {

    int DEFAULT_PAGE_SIZE = 20;
    int MAX_PAGE_SIZE = 100;
    int MAX_BATCH_SIZE = 20;

    int MAX_RECOMMEND_COUNT = 100;
    int MAX_TAG_RECOMMEND_COUNT = 20;

    int LIVE_MESSAGE_PAGE_SIZE = 20;


    String IMAGE_RATE_11 = "11";
    String IMAGE_RATE_34 = "34";
    String IMAGE_RATE_1610 = "1610";
    String IMAGE_RATE_169 = "169";
    String IMAGE_RATE_21 = "21";
    String IMAGE_RATE_43 = "43";



    String IMAGE_SIZE_320_200 = "320*200";

    String IMAGE_SIZE_160_90 = "160*120";

    String IMAGE_SIZE_120_90 = "120*90";
    String IMAGE_SIZE_120_90_2 = "120_90";

    String IMAGE_SIZE_200_150 = "200*150";
    String IMAGE_SIZE_200_150_2 = "200_150";

    String LIVE_STREAM_IMAGE_SIZE_400_225 = "pic3_400_225";

    String LIVE_STREAM_IMAGE_SIZE_160_90 = "pic4_160_90";

    //视频新闻的图片4:3
    String VIDEO_NEWS_IMAGE_43 = "400*300";
    String VIDEO_NEWS_IMAGE_43_2= "400_300";
    //视频图片 需要确认大图 16:9的
    String VIDEO_NEWS_IMAGE_BIG_169 = "960*540";

    String VIDEO_NEWS_IMAGE_169 = "400*225";
    String VIDEO_NEWS_IMAGE_169_2= "400_225";

    String VIDEO_NEWS_IMAGE_200_150 = "200*150";

    //乐词类型
    String LE_THESAURUS_PLAYER = "391"; //球员类型id
    String LE_THESAURUS_TEAM = "394"; //球队类型id
    String IMAGE_SIZE_160_120 = "160*120";


    //首页推荐标签
    final long RECOMMEND_TAG_ID = LeProperties.getLong("lesports.recommend.tag.id", 100153008);

    //后台每页大小
    int DEFAULT_WEB_PAGE_SIZE = 15;
    // yyyy-MM-dd HH:mm:ss
    String REGEX_YMDHMS = "^\\d\\d\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[0-5][0-9]):(0[0-9]|[0-5][0-9])$";
}
