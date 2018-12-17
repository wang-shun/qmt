/**************************************
 * sbc_common.thrift
 * Lesports Param
 *************************************/
namespace java com.lesports.qmt.sbc.api.common

//内容分级  （18+、13+、6+、全年龄段）
enum ContentRating {
    EITHTEEN = 18,
    THIRTEEN = 13,
    SIX = 6,
    ALL = 0,
}

//视频内容类型
enum VideoContentType{
    //回放
    RECORD = 0,
    //集锦
    HIGHLIGHTS = 1,
    //战报
    MATCH_REPORT = 2,
	//正片
	FEATURE = 3,
    //其他
    OTHER = 20,

}

//各大OTT TV牌照方
enum TvLicence{
    //国广
    CIBN,
    //芒果
    IMGO,
    //百视通
    BESTV,
    //央广
    ICNTV,
    //华数
    WASU,
    //南方传媒
    SMC,
    //银河
    GITV
}

//节目类型
enum EpisodeType{
    //比赛
    MATCH = 0,
    //专辑
    PROGRAM = 1,
    //其他节目
    OTHER=2,
}

//新闻类型
enum NewsType{
	//富文本新闻
	RICHTEXT = 0,
    //视频新闻
    VIDEO = 1,
    //图文新闻
    IMAGE_TEXT = 2,
    //图集新闻
    IMAGE_ALBUM = 3,
}

//国家code
enum CountryCode{
    ALL = 0,
    //内地
    CN = 1,
    //香港
    HK = 2,
    //美国
    US = 3,
    //台湾
    TW = 4,
}
// 相关内容类型
enum RelatedType {
    //正文
    RICHTEXT = 0,
    //图集
    IMAGE_ALBUM = 1
    //专题
    TOPIC = 2,
    //视频
    VIDEO = 3,
    //帖子
    POST = 4
}

// 相关项
struct TRelatedItem {
    //Long型id
    1: optional i64 id;
    //String型id 帖子
    2: optional string sid;
    //相关类型
    3: optional RelatedType type;
    //名称
    4: optional string name;
    //序号
    5: optional i32 order;
}

enum TicketType {
    //赛事券
    COMPETITION = 1,
    //单场券
    SINGLE = 2,
}