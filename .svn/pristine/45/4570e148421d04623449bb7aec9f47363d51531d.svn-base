/*************************
 * competitorSeasonStats.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto

include "sbd_common.thrift"

//赛季球队技术统计
struct TCompetitorSeasonStat{
    //id
    1:i64 id
    //比赛id
    2: i64 mid,
    //队伍或者队员的id
    3: optional i64 competitorId,
    //是队伍还是队员
    4: optional sbd_common.CompetitorType type,
    //赛季id
    5: optional  i64  csid,
    //赛事id
    6: optional  i64  cid,
    //统计数据
    7: optional map<string,string> stats,
    //赛季年份
    8: optional string season;
    //赛事名称
    9: optional string  cName,
    //球队类型
    10: optional i32 teamType,
    //平均统计数据
    11: optional map<string,string> avgStats,
    //赛季最高
    12: optional map<string,string> topStats,
    //球队id
    13: optional  i64  tid,
    //球队名称
    14: optional string  teamName,
}

