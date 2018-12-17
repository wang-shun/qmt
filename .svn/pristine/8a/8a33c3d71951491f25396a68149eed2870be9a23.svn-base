/*************************
 * news.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto

include "api_common.thrift"
include "sbc_common.thrift"
include "tag.thrift"

struct TNews{
    //新闻id
    1: i64 id,
    //是否转链接
    2: optional bool isJump;
    //转链接地址类型
    3: optional JumpUrlType jumpUrlType;
    //转链接地址
    4: optional string jumpUrl;
    //新闻标题
    5: optional string name;
    //短标题
    6: optional string shortName;
    //分享标题
    7: optional string shareName;
    //描述
    8: optional string desc;
    //分享描述
    9: optional string shareDesc;
    //频道
    10: optional api_common.Channel channel;
    //项目
    11: optional api_common.Channel subChannel;
    //星级
    12: optional i64 starLevel;
    //来源
    13: optional string source;
    //作者
    14: optional string author;
    //所属赛事
    15: optional i64 cid;
    //标签
    16: optional set<tag.TTag> tags;
    //上线状态
    17: optional api_common.PublishStatus online;
    //新闻类型
    18: optional sbc_common.NewsType type;
    //播放平台
    19: optional set<api_common.Platform> platforms;
    //新闻内容
    20: optional string content;
    //是否删除
    21: optional bool deleted;
    //发布时间
    22: optional string publishAt;
    //声明
    23: optional list<string> statements;
    //是否允许评论
    24: optional bool allowComment;
    //封面图
    25: optional map<string,string> coverImage;
    //相关内容
    28: optional list<sbc_common.TRelatedItem> relatedItems;
    //新闻相关的图片
    29: optional list<TNewsImage> images;

    /*************************视频新闻属性*****************************/
    //视频ID
    30: optional i64 vid;
    //媒资视频ID
    31: optional i64 leecoVid;
    //是否付费
    32: optional i32 isPay;
    //付费平台
    33: optional set<api_common.Platform> payPlatforms;
    //该视频新闻是否是drm视频: 0:不是 1:是
    34: optional i32 drmFlag;
    //视频新闻播放时长,精确到秒
    35: optional i64 duration,
    //视频新闻图片
    36:optional map<string, string> videoImages,
    //是否有大图
    37:optional bool hasBigImage,
    //评论id
    38: optional string commentId,

    //新闻落地页地址
    39: optional string playLink;
}

/**
* 新闻图片
**/
struct TNewsImage{
    //图片id
    1: i64 id,
    //图片url
    2: optional string imageUrl,
    //图片标题
    3: optional string name,
    //图片描述
    4: optional string desc,
    //是否封面
    5: optional bool cover,
    //展示顺序
    6: optional i32 showOrder,
    //是否PC聚合页封面
    7: optional bool aggCover,
}

//新闻跳转链接类型
enum JumpUrlType{
    //图集
    IMAGE_ALBUM = 0,
    //专题
    TOPIC = 1,
}


