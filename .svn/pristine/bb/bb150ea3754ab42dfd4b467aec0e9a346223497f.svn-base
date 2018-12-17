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

//屏蔽类型
enum ShieldType{
    //全部允许
    ALLOW_ALL = 0,
    //允许部分
    ALLOW = 1,
    //屏蔽部分
    DENY = 2
}


//视频内容类型
enum VideoContentType{
   //回放
   RECORD = 0,
   //集锦
   HIGHLIGHTS = 1,
   //新闻
   MATCH_REPORT = 2,
   //节目正片
   FEATURE = 3,
   //比赛片段
   MATCH_PIECE = 4,
   //节目片段
   FEATURE_PIECE = 5,
   //花絮花边
   FEATURE_TIDBITS = 6,
   //策划
   PLAN = 7,
   //其他
   OTHER = 20;

}

//视频在线状态
enum VideoOnlineStatus {
    //上线
    ONLINE
    //下线
    OFFLINE
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

// 相关内容类型
enum RelatedType {
    //正文
    RICHTEXT = 0,
    //图集
    IMAGE_ALBUM = 1
    //视频新闻
    VIDEO = 2,
    //专题
    TOPIC = 3,
    //节目
    EPISODE = 4,
    //自制
    PROGRAM_ALBUM = 5,
}

enum TicketType {
    //赛事券
    COMPETITION = 1,
    //单场券
    SINGLE = 2,
}

//转码状态
enum TranscodeStatus {
    TRANSCODING_NOT_START = 300000//	转码中,且有待派任务
    TRANSCODING_UPLOAD_SUCCESS = 300001//	转码上传成功
    TRANSCODING = 300002//	转码中
    TRANSCODING_SUCCESS = 300003	//转码成功
    WAITING_MERGE = 300004	//等待合并
    MERGE_SUCCESS = 300005	//合并成功
    DISPATCH_SUCCESS = 300006	//分发成功
    TRANSCODING_FAIL = 300007	//转码失败
    SHIELDING = 300008	//统一屏蔽
    CALL_BUSINESS_FAIL = 300009//	回调业务产品失败
    NOT_SURE = 300010	//待定
}

// 相关项
struct TRelatedItem {
    //Long型id
    1: optional i64 id;
    //相关类型
    2: optional RelatedType type;
    //名称
    3: optional string name;
    //序号
    4: optional i32 order;
}