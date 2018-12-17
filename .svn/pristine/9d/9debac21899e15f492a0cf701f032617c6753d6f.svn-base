/**************************************
 * congig_service.thrift
 * Lesports Config Service
 *************************************/
include "api_common.thrift"

include "param.thrift"

include "caller.thrift"
include "dict.thrift"
include "tag.thrift"
include "menu.thrift"
include "activity.thrift"
include "suggest.thrift"

namespace java com.lesports.qmt.config.api.service

/**
 * Lesports Quan Mei Ti Config Service Definition.
 * @author ellios
 */
service TConfigService{

    ######################## caller #####################################

    /**
    * 通过callerId获取Caller信息
    **/
    caller.TCaller getTCallerById(1: i64 id),

    /**
    * 根据splatid列表获取Caller信息
    **/
    list<i64> getTCallerBySplatId(1: i64 splatId),

    ######################## dict ################################

    /**
    *  根据id获取字典
    **/
    dict.TDictEntry getTDictEntryById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    *  根据id列表获取字典
    **/
    list<dict.TDictEntry> getTDictEntriesByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 查询字典id列表
    **/
    list<i64> getTDictEntryIds4SimpleSearch(1: param.GetDictEntriesParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    *  获取字典所属的顶级分类
    **/
    dict.DictEntryTopType getDictEntryTopType(1: i64 id),

    ######################## tag ################################

    /**
    * 根据id获取标签
    **/
    tag.TTag getTTagById(1: i64 id),

    /**
    * 根据id获取标签列表
    **/
    list<tag.TTag> getTTagsByIds(1: list<i64> ids),

    /**
    * 根据id获取标签
    **/
    tag.TTag getTTagByName(1: string name),

    ######################## menu ################################

    /**
    * 根据id获取菜单
    **/
    menu.TMenu getTMenuById(1: i64 id),

    ######################## activity ################################

	/**
	 * 根据活动id取活动信息
	**/
	activity.TActivity getTActivityById(1:i64 id),

    /**
	 * 根据活动id列表取活动信息
	**/
	list<activity.TActivity> getTActivitiesByIds(1: list<i64> ids),

    /**
	 * 根据节目id列表取单场推广位
	**/
	list<i64> getActivityIdsByEid(1: i64 eid),

    /**
	 * 根据条件查询活动
	**/
    list<i64> getTActivityIds4SimpleSearch(1: activity.GetActivitiesParam p, 2: api_common.PageParam page),

    ######################## suggest ################################

    /**
    * 分页查找所有的搜索关键字
    **/
    list<i64> getTSuggestIds(1: api_common.PageParam page),

    /**
    * 批量获取搜索关键字
    **/
    list<suggest.TSuggest> getTSuggestsByIds(1: list<i64> ids),

}
