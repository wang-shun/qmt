/*************************
 * layout.thrift
 * Lesports Quan Mei Ti CMS
 *************************************/
namespace java com.lesports.qmt.cms.api.dto

include "api_common.thrift"
include "cms_common.thrift"

//组件
struct TWidget{
    //组件id
    1: i64 id,
    //组件名称
    2: optional string name,
    //组件的路径
    3: optional string path,
    //组件描述
    4: optional string desc,
    //组件类型
    5: optional string type,
    //组件对应的页面类型
    6: optional cms_common.PageType pageType,
    //组件版本
    7: optional string version,
    //组件的vm
    8: optional string vm;
}
