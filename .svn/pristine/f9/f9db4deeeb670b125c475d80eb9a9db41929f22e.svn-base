/*************************
 * activity.thrift
 * Lesports Quan Mei Ti
 *************************************/
namespace java com.lesports.qmt.config.api.dto

include "api_common.thrift"

//活动类型
enum ActivityType{
    //单场推广位
    APP_ACTIVITY = 3,
	//条款
	TERMS = 5,
	//TV关机图
    TV_EXIT_LOGO = 11
}

//活动资源类型
enum ActivityResourceType{
	//节目
    EPISODE = 0,
    //活动 web url
    WEB_URL = 1,
    //帖子
    POST = 2,
    //阵营
    CAMP = 4,
	//分享激励 浮球
	FLOATER = 5,
}

struct TActivity{
    1: i64 id,
    //标题
    2: optional string name,
    //活动关联的资源id
    3: optional string resourceId,
    //活动关联的资源url
    4: optional string resourceUrl,
    //资源类型
    5: optional ActivityResourceType resourceType,
    //活动类型
    6: optional ActivityType type,
    //活动图片url
    8: optional string imageUrl,
    //状态
    11: optional i32 status,
}


/**
* 获取活动的请求参数
**/
struct GetActivitiesParam {
    //类型
    1: optional ActivityType activityType,
}