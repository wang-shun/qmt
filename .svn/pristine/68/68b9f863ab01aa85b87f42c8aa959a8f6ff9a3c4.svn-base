/*************************
 * video.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto

include "api_common.thrift"
include "sbc_common.thrift"
include "tag.thrift"

struct TVideo{
    1: i64 id,
    //视频名称
    2: optional string name,
    //关联的比赛id
    3: optional set<i64> mids,
    //关联的队伍id
    4: optional set<i64> tids,
    //关联的队员id
    5: optional set<i64> pids,
    //大项
    6: optional i64 gameFType,
    //小项
    7: optional i64 gameSType,
    //赛事id
    8: optional i64 cid,
    //视频内容类型
    9: optional sbc_common.VideoContentType contentType,
    //播放平台
    10: optional set<api_common.Platform> platforms,
    //描述
    11: optional string desc,
    //默认图片尺寸
    12: optional string imageUrl,
    //是否是克隆视频
    13: optional bool cloneVideo,
    //是否有大图
    14: optional bool hasBigImage,
    //时长,精确到秒
    15: optional i64 duration,
    //节目id
    16: optional set<i64> eids,
    //创建时间
    17:optional string createAt,
    //视频图片
    18:optional map<string, string> images,
	//媒资专辑id
	19: optional i64 albumId,
	//专辑id
	20: optional i64 aid,
	//标签
	21: optional list<tag.TTag> tags,
	//播放页地址
	22: optional string playLink,
	//评论id
	23: optional string commentId,
	//多言语名称
	24: optional list<api_common.LangString> multiLangNames,
	//多语言描述
	25: optional list<api_common.LangString> multiLangDesc,
	//允许国家
    26: optional list<api_common.CountryCode> allowCountries,
    //该视频允许播放的牌照方
    27: optional list<sbc_common.TvLicence> validLicences,
    //是否是drm
	28: optional i32 drmFlag,
    //是否付费
    29: optional i32 isPay;
    //乐视网媒资id
    30: optional i64 leecoVid;
}

/**
* 获取视频的集合信息
**/
struct TVideoInfo {
	//相关视频的总数
	1: i64 total,
	//视频集合
	2: list<TVideo> videos,
}

struct TVideoIndexInfo{
    1: required i64 index,
    2: required i64 total,
}

struct TVideoListInfo{
    1: optional i64 size,
    2: optional i64 total,
    3: optional list<i64> vids,
}

/**
* 获取短视频在专辑非正片视频集合里面的index和非正片视频的总数
**/
struct TNonPositiveInfo {
	//视频在集合中的index
	1: i32 index,
	//专辑非正片的视频总数
	2: i64 total,
}

/**
* 获取专辑正片视频集合以及该专辑的起始结束时间
**/
struct TFeatureInfo {
	//专辑第一期的时间(年和月: 201501)
	1: string startTime,
	//专辑最新一期的时间(年和月: 201501)
	2: string endTime,
	//专辑正片的视频集合
	3: list<TVideo> videos,
}