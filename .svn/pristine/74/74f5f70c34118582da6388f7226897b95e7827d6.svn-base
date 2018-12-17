package com.lesports.qmt.web.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/12/29.
 */
public class Constants {
    //体育直播id前缀
    public final static String SPORTS_LIVEID_PREFIX = "10";

    //积分榜
    public static final Long TOPLIST_TYPE_TABLE = 100162000L;
    //射手榜、助攻榜
    public static final List<Long> TOPLIST_TYPE_OTHER = Lists.newArrayList(100160000L, 100161000L);
    //NBA西部排行榜
    public static final Long TOPLIST_TYPE_WEST_LIST = 100204000L;
    //NBA东部排行榜
    public static final Long TOPLIST_TYPE_EAST_LIST = 100205000L;
    //NBA赛事id
    public static final Long NBA_COMPETITION_ID = 44001L;
    //NBA联盟id
    public static final Long NBA_CONFERENCE_ID = 100009000L;
    //常规赛id
    public static final Long BK_REGULAR_ID = 100084000L;
    //季后赛id
    public static final Long BK_PLAYOFF_ID = 100085000L;


    //区域和榜单的映射map
    public final static Map<Long, Long> NBA_REGION_TOPLIST_MAP = Maps.newHashMap();
    //队内球员数据统计id和排行名字的映射map
    public final static Map<Long, String> NBA_PLAYER_STATS_MAP = Maps.newHashMap();
    //球队数据统计id和排行名字的映射map
    public final static Map<Long, String> NBA_TEAM_STATS_MAP = Maps.newHashMap();
    //区域排名的映射map
    public final static Map<Long, String> NBA_REGION_NAME_MAP = Maps.newHashMap();
    //联盟和分区的映射map
    public final static Map<Long, String> NBA_CONFERENCE_REGION_NAME_MAP = Maps.newHashMap();
    //球员和球队排行榜id和排行名字的映射map
    public final static Map<Long, String> NBA_TOPLIST_MAP = Maps.newHashMap();
    //球员和球队赛季技术统计字段名字的映射map
    public final static Map<String, String> NBA_COMPETITION_SEASON_STATS_MAP = Maps.newHashMap();

    static {
        //东部
        NBA_REGION_TOPLIST_MAP.put(100130000L, 100205000L);
        //西部
        NBA_REGION_TOPLIST_MAP.put(100131000L, 100204000L);
        //东南区
        NBA_REGION_TOPLIST_MAP.put(100132000L, 104650000L);
        //大西洋
        NBA_REGION_TOPLIST_MAP.put(100133000L, 104648000L);
        //中部区
        NBA_REGION_TOPLIST_MAP.put(100134000L, 104649000L);
        //西南区
        NBA_REGION_TOPLIST_MAP.put(100135000L, 104652000L);
        //西北区
        NBA_REGION_TOPLIST_MAP.put(100136000L, 104653000L);
        //太平洋区
        NBA_REGION_TOPLIST_MAP.put(100137000L, 104651000L);

        //NBA球员统计项
        NBA_PLAYER_STATS_MAP.put(116805000L,"Pts/G");
        NBA_PLAYER_STATS_MAP.put(116902000L,"Reb/G");
        NBA_PLAYER_STATS_MAP.put(116703000L,"Ast/G");
        NBA_PLAYER_STATS_MAP.put(116806000L,"Stl/G");
        NBA_PLAYER_STATS_MAP.put(116807000L,"Blk/G");
        NBA_PLAYER_STATS_MAP.put(116808000L,"FG%");
        NBA_PLAYER_STATS_MAP.put(116704000L,"3G%");

        //NBA球队统计项
        NBA_TEAM_STATS_MAP.put(116805000L,"points");
        NBA_TEAM_STATS_MAP.put(116902000L,"rebounds");
        NBA_TEAM_STATS_MAP.put(116703000L,"assists");
        NBA_TEAM_STATS_MAP.put(116806000L,"steals");
        NBA_TEAM_STATS_MAP.put(116807000L,"blockedshots");
        NBA_TEAM_STATS_MAP.put(116808000L,"fieldgoal_percentage");
        NBA_TEAM_STATS_MAP.put(116704000L,"threepoint_percentage");


        //排行榜
        NBA_TOPLIST_MAP.put(116805000L,"points");
        NBA_TOPLIST_MAP.put(116902000L,"total_rebounds");
        NBA_TOPLIST_MAP.put(116703000L,"assists");
        NBA_TOPLIST_MAP.put(116806000L,"steals");
        NBA_TOPLIST_MAP.put(116807000L,"blockedshots");
        NBA_TOPLIST_MAP.put(116704000L,"threepoint_percentage");


        //东南区
        NBA_REGION_NAME_MAP.put(100132000L, "Southeast");
        //大西洋
        NBA_REGION_NAME_MAP.put(100133000L, "Atlantic");
        //中部区
        NBA_REGION_NAME_MAP.put(100134000L, "Central");
        //西南区
        NBA_REGION_NAME_MAP.put(100135000L, "Southwest");
        //西北区
        NBA_REGION_NAME_MAP.put(100136000L, "Northwest");
        //太平洋区
        NBA_REGION_NAME_MAP.put(100137000L, "Pacific");


        //东部
        NBA_CONFERENCE_REGION_NAME_MAP.put(100132000L, "east");
        NBA_CONFERENCE_REGION_NAME_MAP.put(100133000L, "east");
        NBA_CONFERENCE_REGION_NAME_MAP.put(100134000L, "east");
        //西部
        NBA_CONFERENCE_REGION_NAME_MAP.put(100135000L, "west");
        NBA_CONFERENCE_REGION_NAME_MAP.put(100136000L, "west");
        NBA_CONFERENCE_REGION_NAME_MAP.put(100137000L, "west");



        NBA_COMPETITION_SEASON_STATS_MAP.put("assists","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("blockedshots","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("defensive_rebounds","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("fieldgoal_percentage","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("fieldgoals_attempted","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("fieldgoals_made","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("freethrow_percentage","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("freethrows_attempted","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("freethrows_made","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("games","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("minute","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("offensive_rebounds","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("personalfouls","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("points","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("rebounds","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("steals","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("threepoint_attempted","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("threepoint_made","2");
        NBA_COMPETITION_SEASON_STATS_MAP.put("threepoint_percentage","1");
        NBA_COMPETITION_SEASON_STATS_MAP.put("turnovers","1");
    }
}
