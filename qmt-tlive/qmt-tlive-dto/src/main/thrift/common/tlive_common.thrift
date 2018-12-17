/*************************
 * common.thrift
 * Lesports qmt
 *************************************/
namespace java com.lesports.qmt.tlive.api.common




//图文直播类型
enum TextLiveType{
    //直播播员图文直播
    ANCHOR = 1,
    //play_by_play直播
    PLAY_BY_PLAY = 2,
}

//直播员身份
enum AnchorRole{
	//互动直播员（主角）
    LEAD_ROLE = 1,
    //比分直播员 （配角）
    SUPPORT_ROLE = 2,
}

//图文直播消息类型
enum TextLiveMessageType{
	//文本
    TEXT = 1,
    //图片
    IMAGE = 2,
    //投票
    VOTE = 3,
    //视频
    VIDEO = 4,
    //比赛数据
    MATCH_DATA = 5,
    //链接
    LINK = 6,
    //微博
    WEIBO = 7,
}

//顶踩类型
enum UpDownAct{
	//顶
    UP = 1,
    //踩
    DOWN = 2,
}