/*************************
 * topic.thrift
 * Lesports Quan Mei Ti Topic
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto

include "api_common.thrift"
include "sbc_common.thrift"
include "tag.thrift"

//专题模板类型
enum TopicTemplateType{
    //突发事件专题
    EMERGENCY = 0,
}

//专题内容类型
enum TopicItemType{
    //正文 富文本
    RICHTEXT = 0,
    //图集
    IMAGE_ALBUM = 1,
    //正文 视频新闻
    VIDEO = 2,
}

/**
* 专题
**/
struct TTopic {
    //专题id
    1: i64 id,
    //模板类型
    2: optional TopicTemplateType templateType;
    //标题
    3: optional string name;
    //短标题
    4: optional string shortName;
    //分享标题
    5: optional string shareName;
    //描述
    6: optional string desc;
    //分享描述
    7: optional string shareDesc;
    //频道
    8: optional api_common.Channel channel;
    //项目
    9: optional api_common.Channel subChannel;
    //所属赛事
    10: optional i64 cid;
    //URL发布名称
    11: optional string pubName;
    //页面meta信息中的关键词
    12: optional string keywords;
    //显示M站分享按钮
    13: optional bool shareable;
    //上线状态
    14: optional api_common.PublishStatus online;
    //推送平台
    15: optional set<api_common.Platform> platforms;
    //Banner图
    16: optional string bannerImage;
    //焦点图
    17: optional map<string,string> focusImages;
    //相关标签
    18: optional set<tag.TTag> tags;
    //发布时间
    19: optional string publishAt;
}


//专题包内容条目
struct TTopicItem {
    //关联的内容ID
    1: i64 itemId;
    //关联的内容类型
    2: optional TopicItemType type;
    //标题
    3: optional string name;
    //排序
    4: optional i32 order;
    //副标题
    5: optional string subName;
    //内容原标题
    6: optional string originalName;
}

//专题包
struct TTopicItemPackage {
    //id
    1: i64 id;
    //专题id
    2: optional i64 topicId;
    //内容包名称
    3: optional string name;
    //内容包顺序
    4: optional i32 order;
    //专题包内容
    5: optional list<TTopicItem> items;
}
