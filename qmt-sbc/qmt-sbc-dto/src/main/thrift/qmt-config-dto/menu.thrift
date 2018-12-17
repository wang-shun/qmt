/*************************
 * menu.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.config.api.dto

include "api_common.thrift"

//菜单的推荐类型
enum MenuRecommendType {
	//不推荐
	NO_RECOMMEND = 0,
	//强制推荐 - 直接插入到热门后面
	FORCE_RECOMMEND = 1,
}

//菜单资源类型
enum MenuResourceType{
    //h5
    H5 = 7,
    //轮播台
    CAROUSEL = 9,
    //奥运会
    OLYMPIC = 12,
    //世界杯预选赛12强赛
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
    //新闻资源位id
    3: optional i64 newsResourceId,
    //菜单项级别
    4: optional i32 level,
    //子菜单项
    5: optional list<TMenuItem> subItems,
    //菜单项名称
    6: optional string name,
	//是否是最新赛事
	7: optional bool isNew,
	//是否是hot赛事
	8: optional bool isHot,
	//菜单的顺序
	9: optional i32 order,
	//菜单图片
    10: optional map<string,string> imageUrl,
	//是否被推荐,及app3.0的频道是否是默认的
	11: optional bool isRecommend;
	//关联榜单
	12: optional i64 toplistCid;
	//关联赛程
    13: optional i64 matchCid;
	//url地址
	14: optional string url;
	//推荐频道的排序字段,客户端进行推荐频道的排序
	15: optional i32 recommendOrder;
	//社区阵营分类ID
	16: optional string campCategoryId;
	//社区阵营ID
	17: optional string campId;
	//是否强制推荐
	18: optional bool isForceRecommend;
	//强制推荐菜单的顺序
	19: optional i32 forceRecommendOrder;
	//赛程表类型
    20: optional ScheduleType scheduleType;
    //渠道号
    21: optional list<api_common.PubChannel> pubChannels;
    //比赛资源位id
    22: optional i64 matchResourceId;
    //轮播图资源位id
    23: optional i64 carouselResourceId;
    //是否展示球队数据
    24: optional bool isTeamData;
    //是否展示球员数据
    25: optional bool isPlayerData;
}
