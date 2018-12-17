/*************************
 * layout.thrift
 * Lesports Quan Mei Ti CMS
 *************************************/
namespace java com.lesports.qmt.cms.api.dto

include "api_common.thrift"
include "cms_common.thrift"


//栏目页
struct TColumnPage{
    //栏目页id
    1: i64 id,
    //栏目页标题
    2: optional string name,
    //布局id
    3: optional i64 layoutId,
    //栏目id
    4: optional i64 columnId,
    //栏目页数据
    5: optional string data,
    //页面类型
    7: optional cms_common.PageType type,
    //vm模板
    8: optional string vm,
    //页面上用到的widget的路径
    9: optional list<string> wPaths,
    //栏目页M站数据
    10: optional string mData,
}
