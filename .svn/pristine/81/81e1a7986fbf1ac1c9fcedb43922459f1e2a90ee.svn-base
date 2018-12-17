/*************************
 * resource.thrift
 * Lesports Quan Mei Ti Resource
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto

include "api_common.thrift"
include "sbc_common.thrift"
include "tag.thrift"

//资源位上的链接信息
struct TResourceLink{
    //链接名称
    1: optional string name,
    //链接url
    2: optional string url,
    //顺序
    3: optional i32 order
}

//资源位
struct TResource{
    1: i64 id,
    //资源类型
    2: optional ResourceDataType type,
    //是否组资源位
    3: optional bool group,
    //名称
    4: optional string name,
    //短名称
    5: optional string shortName,
    //描述
    6: optional string desc,
    //图片url
    7: optional string imageUrl,
    //资源位内容更新方式
    8: optional ResourceUpdateType updateType,
    //资源位上附加的一些链接位置
    9: optional list<TResourceLink> links,
    //资源位自身的链接地址
    10: optional string linkUrl,
    //标签
    11: optional list<tag.TTag> tags,
    //子id 资源位组里面会有这个
    12: optional list<i64> childIds,
    //资源位版本号
    13: optional i32 version,
    //已发布资源位的线上id
    14: optional i64 onlineId,
}

//资源位内容
struct TResourceContent{
    1: i64 id,
    //资源位id
    2: i64 resourceId,
    //资源位内容关联的数据id
    3: optional string itemId,
    //资源位内容的排序
    4: optional i32 order,
    //名称
    5: optional string name,
    //封面图
    6: optional map<string, string> coverImage,
    //资源位内容上附加的一些链接位置
    7: optional list<TResourceLink> links,
    //一些标签 存的是id
    8: optional list<i64> tagIds,
    //item的类型
    9: optional ResourceItemType type,
    //item的子类型
    10: optional ResourceItemType subType,
    //自动通用数据列表等类型里多选的内容类型
    11: optional list<ResourceItemType> dataSearch,
    //TV桌面推荐主题的子内容
    12: optional list<TContentItem> contentItems,
    //是否显示标题
    13: optional bool showTitle,
    //h5url
    14: optional string h5Url,
    //TV内容 其他
    15: optional string otherContent,
    16: optional string mobileImg;
    17: optional string ipadImg;
    18: optional string tvImg;
    19: optional i32 durationTime;
    21: optional i32 starLevel;
    22: optional i32 timeOrder;
    23: optional i32 overdRound;
    24: optional i32 livingRound;
    25: optional i32 toStartRound;
    26: optional string startTime;
    27: optional string endTime;
}

//TV桌面推荐主题的子内容
struct TContentItem {
    //标题
    1: optional string title,
    //是否显示标题
    2: optional bool showTitle,
    //类型
    3: optional ResourceItemType type,
    //可能ID 可能h5Url
    4: optional string value,
    //图片url
    5: optional string image,

}

enum ResourceItemType{
    //资源位
    RESOURCE = 0,
    //新闻 资讯
    NEWS = 1,
    //视频 点播
    VIDEO = 2,
    //专辑
    ALBUM = 3,
    //专题 突发时间专题
    TOPIC = 4,
    //比赛 直播
    EPISODE = 5,
    //直播
    LIVE = 6,
    //球队
    TEAM = 7,
    //球员
    PLAYER = 8,
    //帖子
    POST = 9,
    //H5
    H5 = 10,
    //正文
    RICHTEXT = 11,
    //图集
    IMAGE_ALBUM = 12,
    //轮播台 精选
    CAROUSEL = 13,
    //小专题
    SUBJECT = 14,
    //视频新闻
    VIDEO_NEWS = 15,
    //tv内容类型
    TV_CONTENT = 16,
    //tv模块
    TV_MODULE = 17,
    //首页
    HOME_PAGE = 18,
    //比赛大厅(全部)
    HALL_ALL = 19,
    //比赛大厅(未开始)
    HALL_NOT_START = 20,
    //比赛大厅(已结束)
    HALL_END = 21,
    //赛事
    COMPETITION = 23,
    //会员
    VIP = 24,
    //收银台
    COUNTER = 25,
    //搜索
    SEARCH = 26,
    //tv频道
    TV_CHANNEL = 27,
    //原创
    PROGRAM_ALBUM = 28,
    //其他
    OTHER = 29,
    //应用商店专题页
    APP_SHOP_TOPIC = 30,
    //游戏中心详情页
    GAME_CENTER_DETAIL = 31,
    //游戏中心专题页
    GAME_CENTER_TOPIC = 32,

    //编辑自己维护的图文
    TEXT_IMAGE = 33
}

enum ResourceUpdateType{
    //自动
    AUTO = 0,
    //手动
    MANUAL = 1
}

enum ResourceDataType {
    //通栏比赛卡
    COMMON_COMPETITION = 0,
    //通用内容列表
    COMMON_CONTENT_LIST = 1,
    //数据榜单
    DATA_LIST = 2,
    //内容排行榜
    DATA_RANK = 3,
    //比赛模块
    COMPETITION_MODULE = 4,
    //球队模块
    PLAYER_MODULE = 5,
    //TV桌面主题
    TV_DESKTOP_THEME = 6,
    //TV 内容
    TV_CONTENT = 7,
    //移动端开机图
    MOBILE_START_IMG = 8,
    //通用图片
    COMMON_IMG = 9,
    //组合卡片资源位
    COMBO_CARD = 10
}