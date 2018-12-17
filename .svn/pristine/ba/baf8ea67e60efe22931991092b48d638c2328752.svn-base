package com.lesports.qmt.msg.constant;

import com.google.common.collect.Maps;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.msg.core.AreaType;
import com.lesports.utils.LeProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/27
 */
public abstract class Constants {
    static {
        LeProperties.loadProperties("hedwig.properties");
    }
    public static final String SIS_WEB_HOST = LeProperties.getString("sis.web.host", "http://static.api.lesports.com");
    public static final String SIS_WEB_MATCH_URI = LeProperties.getString("sis.web.match.uri", "/sis-web/app/match/get?id={0}&version=1.0");
    public static final String SMS_HOST = LeProperties.getString("sms.host", "http://static.api.sports.letv.com");
    public static final boolean DELETE_MEM_CACHE_ENABLE = LeProperties.getBoolean("delete.mem.cache.enable", true);
    public static final boolean DELETE_CDN_CACHE_ENABLE = LeProperties.getBoolean("delete.cdn.cache.enable", false);
    public static final List<String> playerBestStatics = new ArrayList<String>();//NBA 球员最佳技术项列表
    public static final Map<String, Long> avgRankingStats = Maps.newHashMap();//NBA 最佳排行配置；
    public static final int LIVE_MESSAGE_DATA_TYPE = 3;

    public static final LanguageCode LANGUAGE = LanguageCode.valueOf(LeProperties.getString("caller.language", "ZH_CN"));
    public static final CountryCode COUNTRY = CountryCode.valueOf(LeProperties.getString("caller.country", "CN"));
    public static final AreaType areaType = AreaType.valueOf(LeProperties.getString("hedwig.area", "CN"));

    static {
        playerBestStatics.add("points");
        playerBestStatics.add("assists");
        playerBestStatics.add("total_rebounds");

        avgRankingStats.put("points", 116805000L);
        avgRankingStats.put("rebounds", 116902000L);
        avgRankingStats.put("assists", 116703000L);
        avgRankingStats.put("steals", 116806000L);
        avgRankingStats.put("blockedshots", 116807000L);
        avgRankingStats.put("fieldgoal_percentage", 116808000L);
        avgRankingStats.put("threepoint_percentage", 116704000L);

    }
}
