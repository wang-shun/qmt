/*************************
 * dict.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.config.api.dto

include "api_common.thrift"

/**
* Lesports  QuanMeiTi Definition.
* @author ellios
*/
//字典目录顶级分类
enum DictEntryTopType{
    //大项
    GAME_FIRST_TYPE = 1,
    //分组
    GROUP = 3,
    //比赛阶段
    STAGE = 4,
    //轮次
    ROUND = 5,
    //分站
    STATION = 6,
    //角色
    ROLE = 7,
    //场上位置
    POSITION = 8,
}

//字典目录
struct TDictEntry{
 1: i64 id,
 //配置项名称
 2: string name,
 //父目录id
 3: optional i64 parentId,
 //最上得父目录id
 4: optional i64 topParentId,
 //对语言字典名称
 5: optional list<api_common.LangString> multiLangNames,
 //字典图片url
 6: optional string imageUrl
}