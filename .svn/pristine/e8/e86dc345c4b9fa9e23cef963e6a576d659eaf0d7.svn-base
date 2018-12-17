/*************************
 * player.thrift
 * Lesports Sms
 *************************************/
namespace java com.lesports.qmt.sbd.api.dto

include "api_common.thrift"

enum CoachType{
    //主教练
    MAIN = 0,
    //助理
    ASSIST = 1
}

struct TPlayer{
    1: i64 id,
    2: optional string name;
    3: optional list<api_common.LangString> multiLangNames,
    //身高
    4: optional i32 height,
    //体重
    5: optional i32 weight,
    //生日
    6: optional string birthday,
    //性别
    7: optional i32 gender,
    //国籍
    8: optional string nationality,
    9: optional list<api_common.LangString> multiLangNationalities,
    //城市
    10: optional string city,
    11: optional list<api_common.LangString> multiLangCities,
    //球员图片
    12: optional string imageUrl,
    //背景图
    13: optional string backgroupImageUrl,
    //大项
    14: optional string gameFType,
    //小项
    15: optional string gameSType,
    //简体中文描述
    16: optional string desc,
    17: optional list<api_common.LangString> multiLangDesc,
    //昵称
    18: optional string nickname,
    //多语言昵称
    19: optional list<api_common.LangString> multiLangNicknames,
	//号码
	21: optional i32 number,
	//场上位置
	22: optional string position,
    //背板url
    23: optional string bgWebUrl,
    //球员阵容顺序
    24: optional i32 squadOrder
    //伯乐id
    25: optional i64 boleId,
	//场上位置id
	26: optional i64 positionId,
    //大项
    27: optional list<api_common.LangString> multiLangGameFType,
    //所在国家队信息
    28: optional PlayingTeam nationalTeam,
    //所在俱乐部信息
    29: optional PlayingTeam clubTeam,
    //大项ID
    30: optional i64 gameFTypeId,
    //身价
    31: optional string careerValue,
    //身价多语言
    32: optional list<api_common.LangString> multiLangCareerValue,
    //所效力过的俱乐部
    33: optional list<PlayingTeam> careerTeams,
    //场上位置多语言
    34: optional list<api_common.LangString> multiLangPositionNames,
    //球龄
    35: optional string experience,
    //选秀情况
    36: optional string draft,
    //选秀情况多语言
    37: optional list<api_common.LangString> multiLangDraft,
    //年薪
    38: optional string salary,
    //学校
    39: optional string school,
}
struct PlayingTeam {
    //球队id
	1: i64 teamId,
	//球队名称
    2: string name,
    //球队名称多语言
    3: optional list<api_common.LangString> multiLangNames,
    //球队号码
    4: i32 number,
	//球队logo
	5: string logo;
    //球队类型(1:俱乐部，2：国家队，3：虚拟球队)
    6: optional i32 teamType,
    //球队logo多语言
    7: optional list<api_common.CounString> multiCounLogos,
    //当前赛季
    8: optional i64 currentCsid,
}
struct TSimplePlayer{
    1: i64 id,
    //球员名称
    2: optional string name,
    //号码
    3: optional i32 number,
    //场上位置
    4: optional string position,
    //是否首发
    5: optional bool starting,
    //统计的内容
    6: optional map<string,string> stats,
	//球员阵容顺序
	7: optional i32 squadOrder,
	//dnp 是否未上场1：是 0：否
    8: optional i32 dnp
    //球员扩展信息
    9: optional map<string,string> extendInfos,
    //队员成绩信息
    10: optional map<string,string> resultExtendInfos,
    //场上位置id
    11: optional i64 positionId,
    //国家图标
    12: optional string countryImgUrl
    //位置
    13: optional list<api_common.LangString> multiLangPosition,
    //名称多语言字段
    14: optional list<api_common.LangString> multiLangNames,
}
//教练
struct TSimpleCoach{
    1: i64 id,
    //名称
    2: optional string name,
    //英文名
    3: optional string englishName,
    //国籍Id
    4: optional i64 countryId,
    //国籍
    5: optional string nationality,
    //国家图标
    6: optional string countryImgUrl,
	//本人头像
    7: optional string imageUrl,
	//教练类型
    8: optional CoachType coachType,
    //扩展信息
    9: optional map<string,string> extendInfos,
    //名称多语言字段
    10: optional list<api_common.LangString> multiLangNames,
}

/**
* 球员生涯统计
**/
struct TPlayerCareerStat{
    1: i64 id,
    //球员名称
    2: optional string name,
    //球队id
    3: optional i64 teamId,
    //球队名称
    4: optional string teamName,
    //国家队or俱乐部(1:俱乐部，2：国家队，3：虚拟球队)
    5: optional i32 teamType,
    //统计
    6: optional map<string,string> stats,
    //球员id
    7: optional i64 playerId,
    //球队logo
    8: optional string teamLogo,
}