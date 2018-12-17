/*************************
 * sbd_common.thrift
 * Lesports Qmt
 *************************************/
namespace java com.lesports.qmt.sbd.api.common


//主客场类型
enum GroundType{
    //主场
    HOME = 0,
    //客场
    AWAY = 1,
}

//比赛体系,
enum MatchSystem{
    //杯赛
    CUP,
    //联赛
    LEAGUE
}

//主客队逻辑
enum GroundOrder{
    //先主后客
    HOMEVSAWAY=0,
    //先客后主
    AWAYVSHOME=1,
}


//参赛者类型
enum CompetitorType{
    //队伍
    TEAM = 0,
    //队员
    PLAYER = 1,
}
//参赛者类型
enum CoachType{
   //主教练
  MAIN = 0,
   //助教
   ASSIST = 1,
}

//球员类型
enum PlayerType{
    //球员
    PLAYER = 0,
    //教练
    COACH = 1,
}


//性别
enum Gender{
    //男性
    MALE = 0,
    //女性
    FEMALE = 1,
}

//队伍类型
enum TeamType{
    //国家队
    NATIONAL_TEAM = 0,
    //俱乐部队
    CLUB_TEAM = 1,
}

//球员职业生涯的范围
enum CareerScopeType{
    //国家队
    NATIONAL_TEAM = 0,
    //俱乐部队
    CLUB_TEAM = 1,
    //赛事
     COMPETITION = 2,
      //某一个球队
     SINGLE_TEAM = 3,
}
//球员技术统计的类型
enum CareerStatType{
    //总计
    TOTAL= 0,
    //场均
   AVG = 1,
    //单场最佳
    TOP = 2,
}

enum OnlineStatus{
    //下线
    OFFLINE = 0,
    //上线
    ONLINE = 1,
}

//阵营
struct Camp{
	//阵营id
    1: string id,
    //阵营名称
    2: string name,
	//阵营图标(不支持动态缩放,固定尺寸的)
	3: string picture,
}

//比赛时间顺序
enum TimeSort{
	//正序（比赛进行了多少时间）
    ASC = 1,
    //逆序（比赛还剩多少时间）
    DESC = 2,
}

//比赛结果
enum MatchResult{
	//胜
    WIN = 0,
    //负
    LOSE = 1,
    //平
    FLAT = 2,
}

//奖牌类型
enum MedalType{
    //金牌
    GOLD = 0,
    //银牌
    SILVER= 1,
    //铜牌
    BRONZE= 2,
}

//榜单作用域类型
enum ScopeType{
    //联盟
    CONFERENCE = 1,
    //分区
    DIVISION= 2,
    //球队
    TEAM= 3,
    //小组
    GROUP= 4,
}


//国家code
enum CountryCode{
    ALL = 0,
    //内地
    CN = 1,
    //香港
    HK = 2,
    //美国
    US = 3,
}

//语言code
enum LanguageCode{
    //简体中文
    ZH_CN = 0,
    //香港繁体中文
    ZH_HK = 1,
}
