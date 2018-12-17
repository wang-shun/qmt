/*************************
 * menu.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.config.api.dto

include "api_common.thrift"

//菜单资源类型
enum MenuResourceType{
    //赛事(TV)
    COMPETITION = 2,
    //标签 web接口适配使用
    TAG = 3,
    //赛程表(TV)
    MATCH = 6,
    //h5
    H5 = 7,
    //新闻(TV)
    NEWS = 8,
    //轮播台
    CAROUSEL = 9,
    //奥运会(TV)
    OLYMPIC = 12,
    //世界杯预选赛12强赛(TV)
    WORLD_CUP = 13,
    //普通类型
    COMMON = 14,
}

//赛程表类型
enum ScheduleType{
    //按轮次查询
    ROUND = 0,
    //按日期查询
    DATE = 1,
}

/**
* 菜单信息
**/
struct  TMenu{
    //菜单id
    1: i64 id,
    //菜单名称
    2: string name,
    //菜单下地内容
    3: optional list<TMenuItem> items,
}

/**
* 菜单项
**/
struct TMenuItem{
    //菜单相对于同一级的索引号
    1: i64 index,
    //资源类型
    2: optional MenuResourceType resourceType,
    //菜单元素数据源ID, 如轮播台
    3: optional i64 resourceId,
    //新闻手动资源位id
    4: optional i64 newsResourceId,
    //菜单项级别
    5: optional i32 level,
    //子菜单项
    6: optional list<TMenuItem> subItems,
    //菜单项名称
    7: optional string name,
	//是否是最新赛事
	8: optional bool isNew,
	//是否是hot赛事
	9: optional bool isHot,
	//菜单的顺序
	10: optional i32 order,
	//菜单图片
    11: optional map<string,string> imageUrl,
	//是否被推荐,及app3.0的频道是否是默认的
	12: optional bool isRecommend;
	//关联榜单
	13: optional i64 toplistCid;
	//关联赛程
    14: optional i64 matchCid;
	//url地址
	15: optional string url;
	//推荐频道的排序字段,客户端进行推荐频道的排序
	16: optional i32 recommendOrder;
	//社区阵营分类ID
	17: optional string campCategoryId;
	//社区阵营ID
	18: optional string campId;
	//是否强制推荐
	19: optional bool isForceRecommend;
	//强制推荐菜单的顺序
	20: optional i32 forceRecommendOrder;
	//赛程表类型
    21: optional ScheduleType scheduleType;
    //渠道号
    22: optional list<api_common.PubChannel> pubChannels;
    //比赛资源位id
    23: optional i64 matchResourceId;
    //轮播图资源位id
    24: optional i64 carouselResourceId;
    //是否展示球队数据
    25: optional bool isTeamData;
    //是否展示球员数据
    26: optional bool isPlayerData;
    //球队id
    27: optional i64 teamId;
    //新闻自动资源位
    28: optional i64 newsAutoResourceId;
    //M站菜单url
    29: optional string mUrl;
}
