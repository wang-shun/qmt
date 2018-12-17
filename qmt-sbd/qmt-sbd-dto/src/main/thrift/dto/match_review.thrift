/*************************
 * matchreview.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto


include "sbd_common.thrift"
include "match.thrift"
include "api_common.thrift"

//历史比赛
struct THistoryMatch{
    //比赛id
    1: i64 mid,
    //赛季id
    2: optional i64 csid,
    //赛事id
    3: optional i64 cid,
    //比赛阶段
    4: optional string stage,
    //比赛轮次
    5: optional string round,
    //比赛开始时间
    6: optional string startTime,
    //精确到天的比赛开始时间,为了方便按天查询格式:yyyyMMdd
    7: optional string startDate,
    //比赛名称
    8: optional string name,
    //多语言比赛名称
    9: optional list<api_common.LangString> multiLangNames,
    //参赛者信息
    10: optional list<match.TCompetitor> competitors,
    //赛事名称缩写
    11: optional string abbreviation;
	//比赛结果 - 统计哪个参赛者就以哪个参赛者为基准
    12: optional sbd_common.MatchResult result,
    //多语言比赛阶段
    13: optional list<api_common.LangString> multiLangStages,
    //多语言比赛轮次
    14: optional list<api_common.LangString> multiLangRounds,
    //多语言赛事名称简写
    15: optional list<api_common.LangString> multiLangAbbrs,
}

//最近及未来比赛信息
struct TMatchInfo{
    //队伍or队员id
    1: i64 competitorId,
    //主客场
    2: optional sbd_common.GroundType ground,
    //是队伍还是队员
    3: optional sbd_common.CompetitorType type,
    //最近比赛列表
    4: optional list<THistoryMatch> nearMatches,
    //未来比赛列表
    5: optional list<THistoryMatch> afterMatches,
	//最近比赛统计
	6: optional map<string,string> stats,
}

//比赛回顾数据
struct TMatchReview{
    1: i64 id,
    //比赛名称
    2: optional string name,
    //多语言比赛名称
    3: optional list<api_common.LangString> multiLangNames,
    //历史交锋数据
    4: optional list<THistoryMatch> confrontations,
    //最近及未来比赛信息
    5: optional list<TMatchInfo> matchInfos,
	//历史交锋数据统计
	6: optional map<string,string> stats,
	//最近更新时间
	7: optional string updateAt,
}