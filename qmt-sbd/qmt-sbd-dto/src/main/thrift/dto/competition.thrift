/*************************
 * competition.thrift
 * Lesports Qmt
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto

include "sbd_common.thrift"
include "api_common.thrift"
include "tag.thrift"

//赛事
struct  TCompetition{
    1: i64 id,
    //比赛大项如:足球
    2: i64 gameFType,
    //比赛小项,如国际足球
    3: i64 gameSType,
    //赛事创办时间
    4: optional string startTime,
    //赛事名称
    5: optional string name,
    //是否对阵 false:否,true:是
    6: optional bool vs,
    //赛事logo
    7: optional string logoUrl,
	//大项名称
	8: optional string gameFName,
	//小项名称
	9: optional string gameSName,
	//赛事系统,杯赛,联赛
	10: optional sbd_common.MatchSystem matchSystem,
	//赛事简称或者分类,主要用于移动端显示短标题，老数据将小项名称迁移至此
    11: optional string abbreviation,
    //标签
    12: optional list<tag.TTag> tags,
    //洲
    13: optional string continent,
    //国家
    14: optional string nation,
    //简介
    15: optional string introduction,
    //伯乐id
    16: optional i64 boleId,
    //多语言赛事名
    17: optional list<api_common.LangString> multiLangNames,
    //多语言赛事简称
    18: optional list<api_common.LangString> multiLangAbbrs,
    //多语言大项名
    19: optional list<api_common.LangString> multiLangGameFNames,
    //多语言小项名
    20: optional list<api_common.LangString> multiLangGameSNames,
    //多语言州名
    21: optional list<api_common.LangString> multiLangConts,
    //多语言国家名
    22: optional list<api_common.LangString> multiLangNations,
    //多语言简介
    23: optional list<api_common.LangString> multiLangIntros,

}

//赛季
struct  TCompetitionSeason{
    1: i64 id,
    //赛事id
    2: i64 cid,
    //大项
    3: i64 gameFType,
    //小项
    4: i64 gameSType,
    //哪一季,如2014-2015
    6: optional string season,
    //赛季名称
    8: optional string name,
    //赛事官方站点
    9: optional string officialUrl,
    //赛事logo
    10: optional string logoUrl,
    //所在城市
    11: optional string city,
    //状态 0:无效，1：有效
    12: optional i32 status,
    //屏蔽类型, 140全部允许,143中国大陆外全部屏蔽
    13: optional i32 shieldType,
    //是否对阵 false:否,true:是
    14: optional bool vs,
	//大项名称
	15: optional string gameFName,
	//小项名称
	16: optional string gameSName,
	//赛季开始时间
	17: optional string startTime,
	//赛季结束时间
	18: optional string endTime,
	//赛事系统,杯赛,联赛
	19: optional sbd_common.MatchSystem matchSystem,
	//赛季直播支持的平台
	20: optional set<api_common.Platform> livePlatforms,
    //多语言赛季名
    21: optional list<api_common.LangString> multiLangNames,
    //多语言所在城市
    22: optional list<api_common.LangString> multiLangCitys,
    //多语言大项名
    23: optional list<api_common.LangString> multiLangGameFNames,
    //多语言小项名
    24: optional list<api_common.LangString> multiLangGameSNames,
    //当前轮次
    25: optional i32 currentRound,
    //总轮次
    26: optional i32 totalRound,
    //当前轮次
    27: optional i64 currentRoundId,
    //是否跨赛季
    28: optional bool overSeason,
}

//赛事总表
struct  TTotalCompetition{
    //比赛分项
    2: optional i64 gameType,
	//分项名称
    5: optional string gameTypeName,
	//按项目统计比赛信息
    24: optional list<TMatchTotalList> matchTotalList,

}
struct  TMatchTotalList{
    //金牌数量
    1: optional i32 goldMedalCount,
    //日期
    2: optional string date,
    //是否开幕式 0:否 1：是
    3: optional i32 openingCeremonyFlag,
    //是否闭幕式 0:否 1：是
    4: optional i32 closingCeremonyFlag,
    //是否比赛日 0:否 1：是
    5: optional i32 matchDayFlag,
}

//奖牌排名统计
struct TTotalCompetitionVo{
    //赛事id
    1: optional i64 cid,
    //国家
    2: optional i64 countryId,
    //日期
    3: optional string date,
    //列表
    4: optional list<TTotalCompetition> totalCompetitionList,

}
