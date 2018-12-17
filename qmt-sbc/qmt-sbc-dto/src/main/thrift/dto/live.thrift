/*************************
 * live.thrift
 * Lesports Quan Mei Ti
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto


//所属地区 100:中国大陆 101:中国香港:102 美国
enum LiveBelongArea{
    CN = 100,
    HK = 101,
    US = 102
}


enum StreamSourceType {
    //乐视网
    LETV = 0,
    //第三方
    THRID_PARTY = 1
}

//直播流
struct TLive{
    //直播流id
    1: string liveId,
    //直播流名称
    2: optional string name
}