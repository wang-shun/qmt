/**************************************
 * param.thrift
 * Lesports Param
 *************************************/
include "api_common.thrift"
include "sbd_common.thrift"

namespace java com.lesports.qmt.sbd.api.service


/**
* 各种直播状态的参数
**/
enum LiveShowStatusParam{
    //未开始
    LIVE_NOT_START = 0,
    //直播中
    LIVING = 1,
    //直播结束
    LIVE_END = 2,
    //未结束
    LIVE_NOT_END = 3,
}

/**
* 奖牌类型
**/
enum MedalTypeParam{
    GOLD = 0,
    SILVER = 1,
    BRONZE = 2,
}

/**
* 分组类型
**/
enum GroupTypeParam{
    NATIONAL = 1,
    DISCIPLINE = 2,
}

/**
* 支持简单模糊搜索球队的请求参数
**/
struct GetTeams4SimpleSearchParam{
    //首字母
    1: optional string firstLetter,
    //搜索的名字
    2: optional string name,
    //队伍类型
    3: optional i32 teamType,
    //乐词id
    4: optional i64 leciId,
    //章鱼名称
    5: optional string octopusName,
}

/**
* 获取某赛事的所有球队
**/
struct GetTeamsOfCompetitionParam{
     //赛事id
     1: optional i64 cid,
     //大项或者小项
     2: optional i64 gameType,
}

/**
* 获取某赛事或者赛季的所有球队
**/
struct GetTeamsOfSeasonParam{
     //赛事id
     1: optional i64 cid,
     //赛季id,如为0，取cid的最新赛季
     2: optional i64 csid,
}



/**
* 根据条件查询赛事信息
**/
struct GetTeamSeasonsParam {
     //球队id
     1: optional i64 tid,
     //球员id
     2: optional i64 pid,
     //赛季id
     3: optional i64 csid,
}

/**
* 根据条件查询赛事信息
**/
struct GetCompetitionsParam {
     //比赛类型
     1: optional i64 gameType,
     //是否付费:0 免费，1 付费
     2: optional i32 chargeable,
}

/**
* 支持简单模糊搜索球员的请求参数
**/
struct GetPlayers4SimpleSearchParam{
    //首字母
    1: optional string firstLetter,
    //搜索的名字
    2: optional string name,
    //乐词id
    3: optional i64 leciId,
    //赛事id
    4: optional i64 cid,
}

/**
* 获取字典请求参数
**/
struct GetDictEntriesParam {
    //父级Id
    1: optional i64 parentId,
    //名称
    2: optional string name,
}

/**
* 获取比赛动作参数
**/
struct GetMacthActionsParam {
    //比赛Id
    1: i64 mid,
    //动作类型
    2: optional i64 skipId,
    //动作类型
    3: optional list<i64> types,
}

/**
* 获取赛季的榜单列表
**/
struct GetSeasonTopListsParam {
      //赛事id
      1: required i64 cid,
      //赛季id
      2: optional i64 csid,
      //榜单类型
      3: optional i64 type,
      //分组类型
      4: optional i64 group,
      //榜单类型集合
      5: optional list<i64> types,
      //作用域的Id(全联盟Id，东部联盟Id，西部联盟Id，分区Id，小组Id，分站Id 球队Id)
      6: optional i64 scope,
      //作用域的类型（联盟，分区，分站，球队，小组）
      7: optional sbd_common.ScopeType scopeType,
      //榜单中item项的类型，球员或者球队
      8: optional sbd_common.CompetitorType competitorType,
}

struct GetCompetitorSeasonStatsParam {
     //参赛者Id
     1: optional i64 competitorId,
     //赛事Id
     2: optional i64 cid,
     //赛季Id
     3: optional i64 csid,
     //是否查询当前赛季
     4: optional bool queryCurrent,
     //多个参赛者
     5: optional list<i64> competitorIds,
}

/**
* 奖牌榜
**/
struct GetMedalRankingParam{
    //赛事id
    1: optional i64 cid,
    //大项
    2: optional i64 gameFType,
    //分项
    3: optional i64 discpilineId,
    //奖牌类型
    4: optional MedalTypeParam medalTypeParam,
    //某天
    5: optional string date,
	//国家Id
	6: optional i64 countryId,
	//分组类型（1：国家维度 2：项目维度）
    7: optional GroupTypeParam groupType,
}

/**
* 获取赛程or赛事的请求参数
**/
struct GetMatchsEpisodesParam{
    //大项
    1: optional i64 gameFType,
    //小项
    2: optional i64 gameSType,
    //奖牌类型
    3: optional MedalTypeParam medalTypeParam,
    //开始时间
    4: optional string startTime,
    //结束时间
    5: optional string endTime,
	//队伍or队员or比赛  国籍Id
	6: optional i64 countryId,
	//某天
    7: optional string date,
    //赛事id
    8: optional i64 cid,
    //分项
    9: optional i64 discpilineId,
}

/**
* 获取球员的赛程
**/
struct GetPlayerMatchesParam{
    //球员id
	1: optional i64 pid,
    //赛事id
    2: optional i64 cid,
    //赛季id
    3: optional i64 csid,
    //赛程状态
    4: optional api_common.MatchStatus status,
    //赛事阶段（常规赛或者季后赛）
    5: optional i64 stage,
}

/**
* 获取球员职业生涯统计请求参数
**/
struct GetPlayerCareerStatParam{
    //球员id
    1: optional i64 playerId,
    //球队类型
    2: optional sbd_common.CareerScopeType scopeType,
    //球队id
    3: optional i64 scopeId,
}
