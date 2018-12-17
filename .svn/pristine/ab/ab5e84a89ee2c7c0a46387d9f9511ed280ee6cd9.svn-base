/*************************
 * config.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto
include "sbd_common.thrift"
include "api_common.thrift"


enum RecordType{
    //队伍
    OLYMPIC = 0,
    //队员
    WORLD = 1,
}

/**
* 专辑信息
**/
struct TRecordSet{
    1: optional i64 id;
    //配置类型
    2: optional i64 gameSTypeId,
    //配置版本
    3: optional string partnerId,
    //配置数据
    4: optional i32 partnerType,
    5: optional list<TRecordData> records,
    //性别
    6: optional sbd_common.Gender gender,
    //赛季id
    7: optional i64 csid,
    //赛事id
    8: optional i64 cid,
}

struct TRecordData{
    //shifou
    1: optional bool isCurrentRecord,
    //
    2: optional string resultType,
    //
    3: optional string result,
    4: optional i64 countryId,
    5: optional string city,
    6: optional string genDate,
    //获奖牌参赛者id
    7: optional i64 competitorId,
   //参赛者类型 团队or个人
    8: optional sbd_common.CompetitorType  competitorType,
    //参赛者名称
    9: optional string competitorName,
    10: optional list<api_common.LangString> multiLangCompetitorNames,
    //国家名
    11: optional string countryName,
    12: optional list<api_common.LangString> multiLangCountryNames,
    //记录类型
    13: optional string countryLogo,
    //记录类型
    14: optional RecordType recordType,
}

