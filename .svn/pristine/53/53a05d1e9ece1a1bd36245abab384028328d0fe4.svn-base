/*************************
 * episode.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto


include "match.thrift"
include "sbd_common.thrift"
include "api_common.thrift"
include "sbc_common.thrift"
include "tlive_common.thrift"
include "activity.thrift"
include "tag.thrift"

//直播流
struct TLiveStream{
    //直播流id
    1: string liveId,
    //直播流名称
    2: optional string name,
    //直播流状态
    3: optional api_common.LiveStatus status,
    //直播流相关图片
    4: optional map<string, string> images,
	//聊天室id
	5: optional string chatRoomId,
	//直播流展示顺序
	6: optional i32 order,
	//是否付费，1是付费，0是非付费
	7: optional i32 isPay,
	//付费客户端
	8: optional list<i64> payPlatforms,
    //是否是drm
	9: optional i32 drmFlag,
    //上线终端
    10: optional list<i64> platforms,
    //预览图
    11: optional string viewPic,
}

//视频
struct TSimpleVideo{
    //视频id
    1: i64 vid,
    //视频名称
    2: optional string name,
    //内容类型
    3: optional sbc_common.VideoContentType type,
    //图片默认图
    4: optional string imageUrl,
    //视频时长
    5: optional i64 duration,
    //视频图片
    6: optional map<string, string> images,
    //视频创建时间
    7: optional string createTime,
    //播放平台
    8: optional set<api_common.Platform> platforms,
    //付费平台
    9: optional set<api_common.Platform> payPlatforms,
    //是否付费
    10: optional i32 isPay;
}


//简单图文直播信息
struct TSimpleTextLive{
    //图文直播id
    1: i64 textLiveId,
    //图文直播类型
    2: optional tlive_common.TextLiveType type,
}

//简单节目信息
struct TSimpleEpisode{
    //节目id
    1: i64 id,
    //节目名称
    2: optional string name,
    //开始时间
    3: optional string startTime,
    //开始日期
    4: optional string startDate,
    //大项
    5: optional i64 gameType,
    //赛事ID
    6: optional i64 cid,
}

//简单活动
struct TSimpleActivity{
    //标题
    1: optional string name,
    //活动关联的资源url
    2: optional string resourceUrl,
    //资源类型
    3: optional activity.ActivityResourceType resourceType,
    //活动id
    4: i64 activityId,
	//活动关联的资源id(string类型的)
	5: optional string resourceId,

}

//复合的节目信息,组合了专辑和比赛
struct TComboEpisode{
    1: i64 id,
    //节目名称
    2: optional string name,
    //比赛id
    3: optional i64 mid,
    //大乐专辑专辑id
    4: optional i64 leecoAid,
    //开始时间
    5: optional string startTime,
    //比赛状态
    6: optional api_common.MatchStatus matchStatus,
    //直播展示状态
    7: optional api_common.LiveShowStatus status,
    //直播流
    8: optional list<TLiveStream> streams,
    //点播视频信息
    9: optional list<TSimpleVideo> videos,
    //比赛阶段,小组赛,半决赛
    10: optional string stage,
    //轮次:1,2,3
    11: optional string round,
    //分组A,B,C
    12: optional string group,
    // 分站
    13: optional string substation,
    //参赛者信息
    15: optional list<match.TCompetitor> competitors,
    //节目类型
    16: optional sbc_common.EpisodeType type,
    //是否对阵
    17: optional bool vs,
    //赛事id
    18: optional i64 cid,
    //大项id
    19: optional i64 gameFType,
    //小项id
    20: optional i64 gameSType,
    //直播的唯一标记,boss那边联调要用
    21: optional string liveUniqueId,
    //期数
    22: optional string periods,
	//赛事系统,杯赛,联赛
	23: optional sbd_common.MatchSystem matchSystem,
	//轮次id
	24: optional i64 roundId,
	//赛事logo
	25: optional string logo,
	//比赛进程到什么时刻如 上半场-20分钟
	26: optional string moment,
	//相关标签
	27: optional list<tag.TTag> tags,
	//是否在新英付费
	28: optional bool xinyingPay,
	//对应的新英比赛id
	29: optional i64 xinyingMatchId;
	//新英价格
    78: optional double price,
	//播放页地址
	30: optional string playLink,
	//图片信息
	31: optional map<string, string> images,
	//描述
	32: optional string desc,
	//时长
	33: optional i64 duration,
	//评论id
	34: optional string commentId,
	//赛事、专辑简称或者分类
	35: optional string abbreviation,
	//赛事名称
    36: optional string competitionName,
    //专辑名称
    38: optional string albumName,
    //图文直播信息
    39: optional list<TSimpleTextLive> textlives,
    //比赛进行时刻
    40: optional match.TCurrentMoment currentMoment,
    //图文直播地址
    41: optional string tLiveLink,
    //聊天室Id
    42: optional string chatRoomId,
	//章鱼猜球标志
	43: optional bool isOctopus,
	//章鱼猜球matchId
	44: optional i64 octopusMatchId,
	//图文直播的展示状态
	45: optional api_common.LiveShowStatus textLiveStatus,
    //APP活动集合
    46: optional list<TSimpleActivity> appActivities,
    //节目所属的直播平台
    47: optional set<api_common.Platform> livePlatforms,
	//sms后台的传图
	48: optional string imageUrl,
	//是否被删除
	50: optional bool deleted,
	//相关内容
    53: optional list<sbc_common.TRelatedItem> relatedItems;
	//比赛场馆
	54: optional string stadium,
	//比赛天气
	55: optional string weather,
	//比赛主裁判
	56: optional string judge,
    //是否体育付费
    57: optional bool isPay,
    //分享名称
    58: optional string shareName,
    //是否可以同步到云平台
    59: optional bool isSyncToCloud,
    //url是否需要跳转
    60: optional bool isUrlRedirect,
    //pc跳转的地址
    61: optional string pcRedirectUrl,
    //m站跳转的地址
    62: optional string mRedirectUrl,
    //晋级之路的
    63: optional i32 theRoadOrder,
    //比赛阶段
    64: optional list<api_common.LangString> multiLangStage,
    //轮次:1,2,3
    65: optional list<api_common.LangString> multiLangRound,
    //分组A,B,C
    66: optional list<api_common.LangString> multiLangGroup,
    //所属国家
    67: optional api_common.CountryCode countryCode,
    //名单页链接
    68: optional string playerPageLink,
    //成绩页链接
    69: optional string scorePageLink,
    //分项id
    70: optional i64 disciplineId,
    //比赛名称
    71: optional list<api_common.LangString> multiLangNames,
    //第三方数据类型
    72: optional i32 partnerType,
    //项目图片
    73: optional string itemUrl,
	//奖牌类型
	74: optional sbd_common.MedalType medalType,
    //分项
    75: optional string discipline,
    //分项多语言
    76: optional list<api_common.LangString> multiLangDiscipline,
    //分站
    77: optional list<api_common.LangString> multiLangSubstation,
    //qmt专辑id
    79: optional i64 aid,
    //专题页URL
    80: optional string topicUrl,
}