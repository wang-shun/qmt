/*************************
 * config.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto
include "sbd_common.thrift"
include "player.thrift"

/**
* 奖牌信息
**/
struct TMedal{
    1: optional i64 id;
     //大项Id
    2: optional i64 gameFTypeId,
    // 分项Id
    3: optional i64 disciplineId,
    //小项Id
    4: optional i64 gameSTypeId,
    5: optional i64  medalMatchId,
    //奖牌类型
    6: optional sbd_common.MedalType medalType,
    //国家
    7: optional i64 organisationId,
    //获奖牌参赛者id
    8: optional i64 competitorId,
    //参赛者类型 团队or个人
    9: optional sbd_common.CompetitorType  competitorType,
    //性别
    10: optional sbd_common.Gender gender,
    //赛季id
    11: optional i64 csid,
    //赛事id
    12: optional i64 cid,
    //参赛者名称
     13: optional string competitorName,
     //金牌成绩
     14: optional string result,
     //获取金牌的国家
     15: optional string organisationName,
     // 分项名称
     16: optional string disciplineName,
     // 小项名称
     17: optional string gameSTypeName,
     // 图片url
     18: optional string imageUrl,
     //奖牌的获取时间（比赛的结束时间）
     19: optional string medalAhieveTime,
     //获奖球员名单
     20: optional list<player.TSimplePlayer> playerList,
}

//奖牌统计
struct TMedalItem{
    //大项Id
    1: optional i64 gameFTypeId,
    // 分项Id
    2: optional i64 disciplineId,
    //小项Id
    3: optional i64 gameSTypeId,
    //项目名称
    4: optional string itemName,
    //日期
    5: optional string date,
    //金牌数量
    6: optional i32 goldMedalCount,
    //银牌数量
    7: optional i32 silverMedalCount,
    //铜牌数量
    8: optional i32 bronzeMedalCount,
    //国家
    9: optional i64 organisationId,
    //赛季id
    10: optional i64 csid,
    //赛事id
    11: optional i64 cid,
    //成绩
    12: optional string result,
    //国家名称
    13: optional string organisationName,
    // 图片url
    14: optional string imageUrl,
    // 排名
    15: optional i32 rank,
    // 性别
    16: optional sbd_common.Gender gender,
    // 排位情况
    17: optional RankSituationType rankSituationType,
}

//排名情况
enum RankSituationType{
    //上升
    RISE = 1,
    //持平
    FLAT = 2,
	//下降
	DECLINE = 3,
}

//奖牌排名统计
struct TMedalRankVo{
    //项目Id
    4: optional i64 gameType,
    // 项目名称
    5: optional string gameTypeName,
    //国家
    1: optional i64 countryId,
    //国家名称
    2: optional string countryName,
    //日期
    3: optional string date,
    //金牌数量
    6: optional i32 goldMedalCount,
    //银牌数量
    7: optional i32 silverMedalCount,
    //铜牌数量
    8: optional i32 bronzeMedalCount,
    //奖牌榜
    9: optional list<TMedalItem> medalItemList,
    // 图片url
    10: optional string imageUrl,
    //按日期查询
    11: optional string queryDate,
    //分页总数
    12: optional i32 pageTotal,
}
