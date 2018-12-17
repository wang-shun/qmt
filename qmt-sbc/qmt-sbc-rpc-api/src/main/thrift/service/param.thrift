/**************************************
 * param.thrift
 * Lesports Param
 *************************************/
include "api_common.thrift"
include "sbc_common.thrift"

namespace java com.lesports.qmt.sbc.api.service

/**
* 各种直播类型的请求
**/
enum LiveTypeParam{
    //只要视频直播
    ONLY_LIVE = 0,
    //视频直播或者是重点节点
    LIVE_OR_KEY = 1,
    //不过滤直播，相当于各种直播加各种非直播
    NOT_ONLY_LIVE = 2,
    //只要重点节目
    ONLY_KEY = 3,
    //目前同5
    LIVE_OR_TLIVE = 4,
    //视频直播,图文直播,重点节目
    LIVE_TLIVE_KEY = 5,
    //app首页推荐
    ONLY_APP_HOMEPAGE_RECOMMEND = 6,
    //除图文直播以外的
    NOT_TLIVE = 7,
}

/**
* 各种直播状态的请求
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
* 节目类型
**/
enum EpisodeTypeParam{
    //比赛
    MATCH = 0,
    //专辑
    PROGRAM = 1,
}


enum SortParam{
    //比赛
    DESC = 0,
    //专辑
    ASC = 1,
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
* 获取相关视频的请求参数
**/
struct GetRelatedVideosParam{
    //相关id
    1: required i64 relatedId,
    //赛事id
    2: optional i64 cid,
    //视频类型
    3: optional list<sbc_common.VideoContentType> types,
}

/**
* 获取推荐的请求参数
**/
struct GetRecommendParam {
	//推荐类型的type类型
    1: optional string type,
	//新闻列表中的新闻类型(新闻改版以后新闻列表需求)
	2: optional string newsType,
	//relatedId(tagId)
	3: optional i64 relatedId,
}

/**
* 获取相关视频的请求参数
**/
struct GetRelatedNewsParam{
    //相关id
    1: optional list<i64> relatedIds,
    //新闻类型
    2: optional list<sbc_common.NewsType> types,
	//是否需要会员/付费新闻 0:免费 1:付费 2:全部
	3: optional i32 member,
	//星级 0:全部 1:3星 2:2星及以上
	4: optional i32 star,
}

/**
* 获取当前的节目列表的请求参数
**/
struct GetCurrentEpisodesParam{
    //大项,小项,赛事id,标签id都可以支持
    1: optional i64 gameType,
    //赛事id
    2: optional list<i64> cids,
    //直播状态
    3: optional LiveShowStatusParam liveShowStatusParam,
    //直播类型
    4: optional LiveTypeParam liveTypeParam,
    //是否滚球精英
    5: optional bool octopus = false,
	//赛季id
	6: optional i64 csid,
	//节目类型
	7: optional EpisodeTypeParam episodeTypeParam
    //开始日期
    8: optional string startDate,
    //直播券ID
    9: optional string ticketId,
    //直播券type
    10: optional sbc_common.TicketType ticketType,
}

/**
* 获取章鱼节目列表的请求参数
**/
struct GetZhangyuEpisodesParam{
    //赛程id
    1: list<i64> mids,
    //直播类型
    2: optional LiveTypeParam liveTypeParam,
}

/**
* 取得若干天每天的节目数量
**/
struct CountEpisodesByDayParam{
     //开始日期
     1: optional string startDate,
     //多少天
     2: optional i32 days,
     //大项,小项,赛事id,标签id都可以支持
     3: optional i64 gameType,
     //直播类型
     4: optional LiveTypeParam liveTypeParam,
     //节目类型
     5: optional EpisodeTypeParam episodeTypeParam,
     //直播状态
     6: optional LiveShowStatusParam liveShowStatusParam,
     //时区
     7: optional i32 timezone = 8,
}

/**
* 取得某天的的赛事or节目
**/
struct GetSomeDayEpisodesParam{
     //某天
     1: optional string date,
     //大项,小项,赛事id,标签id都可以支持
     2: optional i64 gameType,
     //赛事id
     3: optional list<i64> cids,
     //直播类型
     4: optional LiveTypeParam liveTypeParam,
     //直播状态
     6: optional LiveShowStatusParam liveShowStatusParam,
     //结束日期
     7: optional string endDate,
     //是否滚球精英
     8: optional bool octopus = false,
     //节目类型
     9: optional EpisodeTypeParam episodeTypeParam,
     //排序方式
     10: optional SortParam sortParam,
     //时区
     11: optional i32 timezone = 8,
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
* 获取某赛季下某一字典项下的节目,如:
* 某一轮的节目
* 某一站的节目
**/
struct GetEpisodesOfSeasonByMetaEntryIdParam{
     //赛事id
     1: optional i64 cid,
     //赛季id
     2: optional i64 csid,
     //字典项id
     3: optional i64 entryId,
     //直播状态
     4: optional LiveShowStatusParam liveShowStatusParam,
     //直播类型
     5: optional LiveTypeParam liveTypeParam
     //开始时间
     6: optional string startDate,
     //结束时间
     7: optional string endDate,
}

/**
* 获取某节目附近的其他节目,上一场和下一场
* 某一轮的节目
* 某一站的节目
**/
struct GetEpisodesNearbySomeEpisodeParam{
     //赛事id
     1: optional i64 episodeId,
     //直播状态
     2: optional LiveShowStatusParam liveShowStatusParam,
     //直播类型
     3: optional LiveTypeParam liveTypeParam
}

/**
* 获取通栏比赛卡片资源位里的节目
**/
struct GetEpisodes4CommonCompetitionResourceParam {
     //赛事id 为空代表取全部赛事
     1: optional list<i64> cids,
     //未开始场次 0表示取全部未开始场次
     2: optional i32 numOfNotStart,
     //直播中场次 0表示取全部直播中场次
     3: optional i32 numOfLiving,
     //已结束场次 0表示取全部已结束场次
     4: optional i32 numOfEnd,
}