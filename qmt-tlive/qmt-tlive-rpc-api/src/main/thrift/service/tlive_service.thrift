/**************************************
 * tlive_service.thrift
 * Lesports qmt Service
 *************************************/
include "tlive_common.thrift"
include "text_live.thrift"
include "vote.thrift"


namespace java com.lesports.qmt.tlive.api.service

service TTextLiveService{

    ######################## textLive ################################
    /**
    * 根据id查询图文直播信息
    **/
    text_live.TTextLive getTextLiveById(1: i64 id),

    /**
    * 根据eid查询图文直播信息
    **/
    text_live.TTextLive getMainTTextLiveByEid(1: i64 eid),

    /**
    * 根据图文直播id获取直播消息id列表
    **/
    list<string> getLiveMessageIdsByTextLiveId(1: i64 textLiveId),

    /**
    * 批量id获取直播消息
    **/
    list<text_live.TTextLiveMessage> getLiveMessageByIds(1: list<i64> ids),


   /**
    * 根据条件直播消息id
    **/
    list<string> getLiveMessagesIds(1: i64 textLiveId ,2: i64 section ,3: tlive_common.TextLiveMessageType type),


    /**
    * 获取最新消息index
    **/
    i32 getLiveMessageLatestIndex(1: i64 textLiveId,2: i64 section ),


    /**
    * 按页获取消息数据
    **/
    list<text_live.TTextLiveMessage> getLiveMessageByPage(1: i64 textLiveId,2: i64 section,3:i32 page),

    ######################## textLive ################################


    ######################## vote ################################

    /**
    * 增加投票
    **/
    vote.TVote addVote(1: i64 voteId ,2: i64 optionId),


    /**
    * 查询投票结果
    **/
    vote.TVote getVote(1: i64 voteId),

   /**
    * 在线人数上报接口
    **/
    i64 reportOnlineCount(1: i64 eid),


    /**
    * 在线人数查询接口
    **/
    i64 getOnlineCount(1: i64 eid),

    /**
    * 直播员顶踩接口
    **/
    text_live.TAnchor upDownAnchor(1: i64 textLiveId ,2: i64 anchorId , 3: tlive_common.UpDownAct act),


    /**
    * 获取直播员顶踩结果
    **/
    text_live.TAnchor getAnchorUpDown(1: i64 textLiveId ,2: i64 anchorId ),

    ######################## vote ################################

}