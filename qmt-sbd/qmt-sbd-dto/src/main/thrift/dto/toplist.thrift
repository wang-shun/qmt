/*************************
 * album.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto
include "sbd_common.thrift"
include "api_common.thrift"

struct TTopList{
    //id
    1: i64 id,
    //赛事
    2: optional i64 cid,
    //赛事名称
    3: optional string cname,
    //赛季id
    4:optional i64 csid,
    //赛季名称
    5: optional string season,
    //榜单类型,字典id
    6: optional i64 type,
    //榜单名称
    7: optional string typeName,
    //分站
    8: optional string substation,
    //轮次
    9:optional string round,
    //阶段
    10:optional string stage,
    //分组
    11:optional string group,
	//h5地址
	12:optional string url,
	//描述
	13:optional string desc,
    //榜单内容
    14:optional list<TTopListItem> items,
	//自定义榜单名称
	15:optional string name,
	//在榜单中得排名
	16:optional i32 order,
	17:optional list<api_common.LangString> multiLangCNames,
	18:optional list<api_common.LangString> multiLangTypeNames,
	19:optional list<api_common.LangString> multiLangDesc,
	20:optional list<api_common.LangString> multiLangNames,
}

struct TTopListItem{
    //队伍或者是队员的id
    1:i64 competitorId,
    //参赛者名称
    2:optional string competitorName,
    //参赛者类型
    3:optional sbd_common.CompetitorType competitorType,
    //在榜单中得排名
    4:optional i32 rank,
    //展示顺序,方便运营调整顺序
    5: optional i32 showOrder,
    //统计的内容
    6: optional map<string,string> stats,
    //参赛者头像
    7:optional string logoUrl,
    //球员所属球队id
    8:optional i64 teamId,
    //球员所属球队名称
    9:optional string teamName,
    //如果参赛者是人的话,则需要球队的logo
    10:optional string teamLogo,
    11:optional list<api_common.LangString> multiLangCompetitorNames,
    12:optional list<api_common.LangString> multiLangTeamNames,
    //场上位置
	13: optional string position,
	//场上位置id
	14: optional i64 positionId,
    //场上位置多语言
    15: optional list<api_common.LangString> multiLangPositionNames,
	//号码
	16: optional i32 number,
	//球队logo多语言
    17: optional list<api_common.CounString> multiCounLogos,
    //球队全称
    18: optional string teamOfficialName,
    //球队全称多语言
    19: optional list<api_common.LangString> multiLangTeamOfficialNames,
}