/*************************
 * album.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto
include "sbd_common.thrift"
include "api_common.thrift"



struct TMedalList{
    //id
    1: i64 id,
    //赛事
    2: optional i64 cid,
    //赛季
    3: optional i64 csid,
    //项目 id
    4:optional i64 itemId,
    //项目 名称
    5: optional string itemName,
    //榜单类型
    6: optional MedalListType type,
    //统计的内容
    7: optional map<string,string> stats,
    //创建时间
    8: optional string createAt,
    //修改时间
    9: optional string updateAt,
    //榜单内容
    10: optional list<TMedalItemDetail> items,
    //操作人类型
    11: optional OperateType operateType,
}

struct TMedalItemDetail{
    //参赛队 id
    1:i64 teamId,
    //参赛队 名称
    2:optional string teamName,
    //榜单类型
    3:optional MedalItemType type,
    //在榜单中得排名
    4:optional i32 rank,
    //展示顺序,方便运营调整顺序
    5: optional i32 showOrder,
    //统计的内容
    6: optional map<string,string> stats,
}

//榜单明细类型(关联数据表)
enum MedalItemType{
    //队伍表
    TEAM = 1,
    //字典表
    DICT = 2,
}

//奖牌榜类型
enum MedalListType{
    //完全奖牌榜
    FULL = 0,
    //项目奖牌榜
    ITEM = 1,
}

//操作人类型
enum OperateType{
    //自动
    AUTO = 1,
    //手动
    MANUAL = 2,
}