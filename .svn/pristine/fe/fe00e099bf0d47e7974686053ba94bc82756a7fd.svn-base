/*************************
 * match.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto


include "sbd_common.thrift"
include "player.thrift"
include "api_common.thrift"
include "tag.thrift"

//比赛分段统计
struct TSectionResult{
    //分段顺序
    1: optional i32 order,
    //分段名称
    2: optional string section,
    //分段比赛结果
    3: optional string result,
    //分段Id
    4: optional i64 sectionId,
    //section多语言
    5: optional list<api_common.LangString> multiLangSection,
}

//比赛进行到什么时刻
struct TCurrentMoment{
    //阶段名称
    1: optional string sectionName,
    //比赛时间
    2: optional double time,
    //时间顺序
    3: optional sbd_common.TimeSort sort,
    //阶段多语言
    4: optional list<api_common.LangString> multiLangSectionNames,
}


//参赛者信息
struct TCompetitor{
    //队伍or队员id
    1: i64 id,
    //队伍名称
    2: string name,
    //参赛者类型
	3: optional sbd_common.CompetitorType competitorType,
    //队伍logo
    4: optional string logoUrl,
    //比赛结果,比分,耗时等
    5: optional string finalResult,
    //阵营
    6: optional i32 camp,
    //主客场
    7: optional sbd_common.GroundType ground,
    //顺序
    8: optional i32 order,
    //短名称
    9: optional string shortName,
    //参赛者统计信息
    10: optional list<TSectionResult> sectionResults,
    //乐词Id
    11: optional i64 leId,
	//队伍pngLogo(球员暂无pngLogo)
	12: optional string pngLogo,
    //背板url
    13: optional string bgWebUrl,
	//是否可以被关注
	14:optional bool isFocused,
	//多语言参赛者名
    15: optional list<api_common.LangString> multiLangNames,
    //多语言参赛者短名称
    16: optional list<api_common.LangString> multiLangShortNames,
    //阵营信息
    17: optional sbd_common.Camp campEntity,
    //扩展信息
    18: optional map<string,string> extendInfos,
      //国家图标
    19: optional string countryImgUrl,
    //队伍or队员id  国籍Id
    20: optional i64 competitorCountryId,
    //允许被关注的国家
    21: optional list<api_common.CountryCode> isFocusedCountries,
    //球队logo多语言
    22: optional list<api_common.CounString> multiCounLogos,
    //全称
    23: optional string officialName,
    //多语言参赛者全称
    24: optional list<api_common.LangString> multiLangOfficialNames,
}

//比赛分段统计
struct TCompetitorSectionStat{
    //分段顺序
    1: optional i32 order,
    //分段名称
    2: optional string section,
    //分段统计信息
    3: optional map<string, string> stats,

}

//参赛者信息
struct TCompetitorStat{
    //队伍or队员id
    1: i64 id,
	//队伍or队员名称
	2: string name,
    //比赛统计
    3: optional map<string, string> stats,
    //参赛者类型
    4: optional sbd_common.CompetitorType competitorType,
	//如果参赛者类型是球员的话需要补充球队id
	5: i64 tid,
	//如果参赛者类型是球员的话需要补充球队名称
	6: string teamName,
	//非对阵的需要进行排序
	7: i32 order,
    //比赛分段统计
    8: optional list<TCompetitorSectionStat> sectionStats,
    //多语言参赛者名
    9: optional list<api_common.LangString> multiLangNames,
    //多语言球队名
    10: optional list<api_common.LangString> multiLangTeamNames,
    //球员统计
    11: list<player.TSimplePlayer> playerStats,
}

//参赛者信息
struct TSquad{
    //队伍or队员id
    1: i64 tid,
    //队伍名称
    2: string name,
    //队员信息
    3: optional list<player.TSimplePlayer> players,
    //阵型
    4: optional string formation,
    //多语言队伍名称
    5: optional list<api_common.LangString> multiLangNames,
    //教练信息
    6: optional list<player.TSimpleCoach> coaches,
}

//单场比赛
struct  TMatch{
    1: i64 id,
    //比赛名称
    2: string name,
    //赛事id
    3: optional i64 cid,
    //赛事id
    4: optional i64 csid,
    //开始时间
    5: optional string startTime,
    //结束时间
    6: optional string endTime,
    //比赛场馆
    7: optional string venue,
    //比赛状态
    8: api_common.MatchStatus status,
    //比赛阶段,小组赛,半决赛
    9: optional string stage,
    //轮次:1,2,3
    10: optional string round,
    //分组A,B,C
    11: optional string group,
    //分站
    13: optional string substation,
    //官方比赛顺序
    14: optional i32 number,
    //参赛者信息
    15: optional list<TCompetitor> competitors,
    //主节目id
    17: optional i64 eid,
	//比赛进程到什么时刻如 上半场-20分钟
	18: optional string moment,
	//标签
	19: optional list<tag.TTag> tags,
	//章鱼猜球标志
	20: optional bool isOctopus,
	//章鱼猜球matchId
	21: optional i64 octopusMatchId,
	//伯乐id
	22: optional i64 boleId,
    //多语言比赛名
    23: optional list<api_common.LangString> multiLangNames,
    //多语言场馆
    24: optional list<api_common.LangString> multiLangVenues,
    //多语言比赛时刻
    25: optional list<api_common.LangString> multiLangMoments,
    //多节目id
    26: optional list<api_common.CountryLangId> eids,
	//是否可以同步到云平台
	27: optional bool isSyncToCloud,
	//晋级之路的
	28: optional i32 theRoadOrder,
    //是否对阵,true对阵，false非对阵
    29: optional bool vs,
    //扩展信息
    30: optional map<string,string> extendInfos,
    //比赛进行时刻
    31: optional TCurrentMoment currentMoment,
}

//单场比赛详细信息
struct  TDetailMatch{
    1: i64 id,
    //比赛名称
    2: string name,
    //赛事id
    3: optional i64 cid,
    //赛事id
    4: optional i64 csid,
    //开始时间
    5: optional string startTime,
    //结束时间
    6: optional string endTime,
    //比赛场馆
    7: optional string venue,
    //比赛状态
    8: api_common.MatchStatus status,
    //比赛阶段,小组赛,半决赛
    9: optional string stage,
    //轮次:1,2,3
    10: optional string round,
    //分组A,B,C
    11: optional string group,
    //分站
    13: optional string substation,
    //官方比赛顺序
    14: optional i32 number,
    //参赛者信息
    15: optional list<TCompetitor> competitors,
    //阵容信息
    16: optional list<TSquad> squads,
    //主节目id
    17: optional i64 eid,
    //参赛者统计信息
    18: optional list<TCompetitorStat> competitorStats,
	//比赛进程到什么时刻如 上半场-20分钟
	19: optional string moment,
	//标签
	20: optional list<tag.TTag> tags,
	//最佳球员统计
    21: optional list<TSquad> bestPlayerStats,
	//比赛天气
	22: optional string weather,
	//比赛主裁判
	23: optional string judge,
    //多语言比赛名
    24: optional list<api_common.LangString> multiLangNames,
    //多语言天气
    25: optional list<api_common.LangString> multiLangWeathers,
    //多语言裁判
    26: optional list<api_common.LangString> multiLangJudges,
    //多语言场馆
    27: optional list<api_common.LangString> multiLangVenues,
    //多语言比赛时刻
    28: optional list<api_common.LangString> multiLangMoments,
    //多节目
    29:optional list<api_common.CountryLangId> eids,
    //是否对阵,true对阵，false非对阵
    30: optional bool vs,
    //扩展信息
    31: optional map<string,string> extendInfos,
    32: optional i64 gameSType,
    //大项id
    33: optional i64 gameFType,
    //分项id
    34: optional i64 disciplineId,
    //第三方数据类型
    35: optional i32 partnerType,
    //比赛进行时刻
    36: optional TCurrentMoment currentMoment,
}

//比赛实时数据
struct TMatchAction{
    1: i64 id,
    //比赛id
    2: i64 mid,
    //队伍id
    3: optional i64 tid,
    //队员id
    4: optional i64 pid,
    //事件发生时比赛进行了多长时间,精确到秒
    5: optional double passedTime,
    //行为类型
    6: optional string type,
    //内容描述
    7: optional string content,
    //队伍名称
    8: optional string teamName,
    //队伍头像
    9: optional string teamImageUrl,
    //队员名称
    10: optional string playerName,
    //队员头像
    11: optional string playerImageUrl,
    //多语言行为类型
    12: optional list<api_common.LangString> multiLangTypes,
    //多语言内容描述
    13: optional list<api_common.LangString> multiLangContents,
    //多语言队伍名称
    14: optional list<api_common.LangString> multiLangTeamNames,
    //多语言队员名称
    15: optional list<api_common.LangString> multiLangPlayerNames,
    //行为类型id
    16: optional i64 typeId,
    //球队logo多语言
    17: optional list<api_common.CounString> multiCounLogos,
    //具体行为类型
    18: optional i64 detailTypeId,
    //多语言具体行为类型
    19: optional list<api_common.LangString> multiLangDetailTypes,
    //x坐标
    20: optional string coordinateX,
    //y坐标
    21: optional string coordinateY,
    //事件发生时本阶段进行了多长时间,精确到秒
    22: optional double time,
    //当前阶段
    23: optional string section,
    //多语言当前阶段
    24: optional list<api_common.LangString> multiLangSection,
    //行为类型
    25: optional string detailType,
}

//单场比赛
struct  TMatchStats {
    1: i64 id,
    //赛事id
    2: optional list<TCompetitorStat> competitorStats;
}
