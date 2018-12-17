/*************************
 * .thrift
 * Lesports qmt
 *************************************/
namespace java com.lesports.qmt.tlive.api.dto

include "tlive_common.thrift"
include "api_common.thrift"


//图文直播主体
struct TTextLive{
    1: i64 id,
    //状态
    2: optional api_common.LiveStatus status,
    //节目id
    3: optional i64 eid,
    //比赛id
    4: optional i64 mid,
    //在线人数
    5: optional i32 onlineCount,
    //主直播员信息
    6: optional TAnchor anchor,

}

//直播员信息
struct TAnchor{
    //直播员id
    1: i64 anchorId,
    //直播员昵称
    2: optional string name,
    //直播员头像
    3: optional string logo,
    //直播员角色
    4: optional tlive_common.AnchorRole role,
    //顶数
    5: optional i32 upNum,
    //踩数
    6: optional i32 downNum,
}

//图文直播消息
struct TTextLiveMessage{
    1: i64 id,
    //图文直播id
    2: optional i64 textLiveId,
    //比赛阶段
    3: optional string section,
    //比赛时间
    4: optional string time,
    //比赛结果
    5: optional string matchResult,
    //消息类型
    6: optional tlive_common.TextLiveMessageType type,
    //消息内容
    7: optional string content,
    //简单直播员信息
    8: optional TSimpleAnchor anchor,
    //发送时间
    9: optional string sendTime,
    //图文直播类型
    10: optional tlive_common.TextLiveType textLiveType,
    //比赛阶段Id
    11: optional i64 sectionId,
    //是否被删除
    12: optional bool deleted,
}


//简单直播员信息
struct TSimpleAnchor{
     //直播员昵称
    1: string name,
    //直播员头像
    2: optional string logo,
}


