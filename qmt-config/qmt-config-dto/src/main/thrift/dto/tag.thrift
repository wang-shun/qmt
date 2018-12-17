/*************************
 * tag.thrift
 * Lesports Quan Mei Ti
 *************************************/
namespace java com.lesports.qmt.config.api.dto

include "api_common.thrift"

//标签
struct TTag{
    1: i64 id,
    //标签名称
    2: string name,
}