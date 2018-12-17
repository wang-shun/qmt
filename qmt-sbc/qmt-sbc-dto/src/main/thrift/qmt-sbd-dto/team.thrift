/*************************
 * .thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto

include "api_common.thrift"
include "player.thrift"
include "sbd_common.thrift"

//队伍
struct TTeam{
    1: i64 id,
    //简体中文名
    2: optional string name,
    3: optional list<api_common.LangString> multiLangNames,
    //缩写
    4: optional string abbreviation,
    5: optional list<api_common.LangString> multiLangAbbrs,
    //昵称
    6: optional string nickname,
    7: optional list<api_common.LangString> multiLangNicknames,
    //主场所在地
    8: optional string homeplace,
    9: optional list<api_common.LangString> multiLangHomeplaces,
    //城市
    10: optional string city,
    11: optional list<api_common.LangString> multiLangCities,
    //球队logo图片url
    12: optional string logoUrl,
    //主场队服图片
    13: optional string homeShirtImage,
    //客场队服图片
    14: optional string awayShirtImage,
    //等级
    15: optional i32 grade,
    //简体中文描述
    16: optional string desc,
    17: optional list<api_common.LangString> multiLangDesc,
    //比赛大项的数据字典id,如足球:4
    18: optional string gameFirstType,
    //球队类型(1:俱乐部，2：国家队，3：虚拟球队)
    19: optional i32 teamType,
    //背板url
    20: optional string bgWebUrl,
    //队歌id
    21: optional i64 songId,
    //队服颜色
    22: optional string shirtColor,
    //背景色
    23: optional string backgroupColor,
    //队员
    24: optional list<player.TSimplePlayer> players,
	//球队png logo
	26: optional string pngLogo,
	//联盟id
	27:optional i64 conferenceId,
	//分区id
    28:optional i64 regionId,
    //联盟名称
    29:optional string conference,
    //分区名称
    30:optional string region,
	//是否可以被关注
	31:optional bool isFocused,
	//伯乐id
    32: optional i64 boleId,
    //阵营
    33: optional sbd_common.Camp campEntity,
    //允许被关注的国家
    34: optional list<api_common.CountryCode> isFocusedCountries,
    //当前赛事id
    35: optional i64 currentCid,
    //当前赛季id
    36: optional i64 currentCsid,
    //当前teamSeasonId
    37: optional i64 currentTsid,
    //所获荣誉
    38: optional list<string> honors,
    //世界排名
    39: optional list<TRank> ranks;
    //比赛大项的数据字典id,如足球:4
    40: optional i64 gameFTypeId,
    //教练
    41: optional TTeamPlayer cocah;
    //大项
    42: optional list<api_common.LangString> multiLangGameFType,
    //队长
    43: optional TTeamPlayer captain,
    //核心球员
    44: optional list<TTeamPlayer> corePlayers,
    //球队logo多语言
    45: optional list<api_common.CounString> multiCounLogos,
    //球队夺冠次数
    46: optional string championNum,
    //全称
    47: optional string officialName,
    //球队全称多语言
    48: optional list<api_common.LangString> multiLangOfficialNames,
    //联盟名称多语言
    49: optional list<api_common.LangString> multiLangConferences,
    //分区名称多语言
    50: optional list<api_common.LangString> multiLangRegions,
}

//世界排名
struct TRank {
    //排名时间
    1: optional string time;
    //排名
    2: optional i32 rank;
}

//队员
struct TTeamPlayer {
    //球员id
    1:i64 pid;
    //球员号码
    2:i64 number;
    //球员名称
    3:optional string name;
    4: optional list<api_common.LangString> multiLangNames,
    //位置名称
    5:optional string positionName;
    6: optional list<api_common.LangString> multiLangPositionNames,
    //位置排序
    7:optional i32 positionOrder;
    //球员logo
    8:optional string logo;
    //位置id
    9:optional i64 positionId;

}

//球队赛事
struct TTeamSeason {
     1:i64 id;
     //球队id
     2:i64 tid;
     //赛季id
     3:i64 csid;
     //队员列表
     4:list<TTeamPlayer> players;
     //教练id
     5:optional i64 cocahId;
     //教练名称
     6:optional string cocahName;
     7: optional list<api_common.LangString> multiLangCocahnames,
     //教练logo
     8:optional string cocahLogo;
     //球队名称
     9:optional string teamName;
     10:optional list<api_common.LangString> multiLangTeamNames;
     //球队logo
     11:optional string teamLogo;
}
