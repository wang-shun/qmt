/**************************************
 * tlive_internal_service.thrift
 * Lesports qmt Service
 *************************************/

include "tlive_common.thrift"
include "text_live.thrift"

namespace java com.lesports.qmt.tlive.api.service

/**
 * Lesports Text Live Internal Service Definition.
 * @author lufei
 */
service TTextLiveInternalService{

    ####################### crud ################################

    i64 saveEntity(1: binary bf, 2: binary classType);

    bool deleteEntity(1: i64 id, 2: binary classType);

    binary getEntityBytesById(1: i64 id, 2: binary classType);

    binary getEntityBytesByIds(1: list<i64> ids, 2: binary classType);

    binary getEntitiesBytesByQuery(1: binary query, 2: binary classType);

    i64 countByQuery(1: binary query, 2: binary classType);

    ####################### text live ################################

    /**
    * 人工干预在线人数
    **/
    bool setOnlineCount(1:i64 eid, 2:i32 onlineCount);

    /**
    * 人工干预直播员顶踩
    **/
    bool setUpDownAnchor(1:i64 textLiveId, 2:i64 anchorId, 3:map<tlive_common.UpDownAct, i32> upDownActMap);

    /**
    * 发送图片或投票消息
    * true: image, false: vote
    **/
    bool sendImageOrVoteMessage(1:bool imageOrVote, 2:i64 id, 3:binary message);

	/**
    * 在线人数查询接口
    **/
    i64 getOnlineCount(1: i64 eid);

   /**
   * 获取直播员顶踩结果
   **/
   text_live.TAnchor getAnchorUpDown(1: i64 textLiveId ,2: i64 anchorId ),
}