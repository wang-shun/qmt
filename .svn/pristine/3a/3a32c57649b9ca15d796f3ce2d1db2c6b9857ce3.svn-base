/*************************
 * layout.thrift
 * Lesports Quan Mei Ti CMS
 *************************************/
namespace java com.lesports.qmt.cms.api.dto

include "api_common.thrift"
include "cms_common.thrift"

include "page.thrift"


//直播流
struct TColumn{
    //组件id
    1: i64 id,
    //组件名称
    2: optional string name,
    //关键词
    3: optional string keyword,
    //描述
    4: optional string desc,
    //url路径
    5: optional string path,
    //频道id
    6: optional i64 channelId,
    //url中的完整路径
    7: optional string fullPath,
    //pc站的栏目页
    8: optional page.TColumnPage pcPage,
    //m站的栏目页
    9: optional page.TColumnPage mPage,
    //测试用得的栏目页
    10: optional page.TColumnPage dummyPage,
    //组件标题
    11: optional string title,
    //子频道id
    12: optional i64 subChannelId,
    //M站模版路径
    13: optional string mTemplateUrl,
}
