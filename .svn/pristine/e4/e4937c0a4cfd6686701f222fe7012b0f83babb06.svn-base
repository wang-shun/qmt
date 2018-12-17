/*************************
 * vote.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.tlive.api.dto


include "common.thrift"


//投票
struct TVote{
    1: i64 id,
    //主题
    2: optional string topic,
    //选项
    3: optional list<TOption> options,

}


//选项
struct TOption{
    //选项id
    1: i64 optionId,
    //选项内容
    2: optional string title,
    //投票数
    3: optional i32 num,
    //顺序
    4: optional i32 order,
}