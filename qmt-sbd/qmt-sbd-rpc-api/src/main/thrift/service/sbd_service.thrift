/**************************************
 * sbd_service.thrift
 * Lesports Qmt Service
 *************************************/


include "api_common.thrift"
include "sbd_common.thrift"

include "live.thrift"
include "resource.thrift"

include "param.thrift"



include "competition.thrift"
include "competitor_season_stat.thrift"
include "match.thrift"
include "match_review.thrift"
include "medal.thrift"
include "player.thrift"
include "record.thrift"
include "team.thrift"
include "toplist.thrift"


namespace java com.lesports.qmt.sbd.api.service

/**
 * Lesports Manage System Service Definition.
 * @author ellios
 * 过滤调用者平台的赛事接口服务
 */
service TSbdService{

    ####################### player ################################

	/**
	*  通过id获取球员信息
	**/
    player.TPlayer getTPlayerById(1: i64 id, 2: api_common.CallerParam caller)

    /**
    * 批量获取球员
    **/
    list<player.TPlayer> getTPlayersByIds(1: list<i64> ids, 2: api_common.CallerParam caller)

    /**
    * 通过首字母或者模糊匹配查找队员信息
    **/
    list<i64> getPlayerIds4SimpleSearch(1: param.GetPlayers4SimpleSearchParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller)

    ####################### playerCareerStat ################################

    list<player.TPlayerCareerStat> getPlayerCareerStat(1: param.GetPlayerCareerStatParam p, 2: api_common.CallerParam caller)

    ####################### team ################################

    /**
    * 通过id查找球队
    **/
    team.TTeam getTTeamById(1: i64 id, 2: api_common.CallerParam caller)

    /**
    * 通过阵营id查找球队
    **/
    team.TTeam getTTeamByCampId(1: i64 id, 2: api_common.CallerParam caller)

    /**
    * 通过id列表查询球队
    **/
    list<team.TTeam> getTTeamsByIds(1: list<i64> ids, 2: api_common.CallerParam caller)

    /**
    * 通过首字母查找队伍信息
    **/
    list<i64> getTeamIds4SimpleSearch(1: param.GetTeams4SimpleSearchParam p, 2: api_common.PageParam page,  3: api_common.CallerParam caller)

    /**
    *  获取某赛事或者赛季的所有球队
    **/
    list<i64> getTeamIdsOfSeason(1: param.GetTeamsOfSeasonParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller)

    /**
    *  获取某赛事的所有球队
    **/
    list<i64> getTeamIdsOfCompetition(1: param.GetTeamsOfCompetitionParam p, 2: api_common.PageParam page,  3: api_common.CallerParam caller)

    ####################### team season ##############################

    /**
     * 根据id获取球队赛季
    **/
    team.TTeamSeason getTTeamSeasonById(1:i64 id, 2: api_common.CallerParam caller),

    /**
     * 根据id列表获取球队赛季
    **/
    list<team.TTeamSeason> getTTeamSeasonsByIds(1:list<i64> ids, 2: api_common.CallerParam caller),

    /**
     * 根据条件查询球队赛季
    **/
    list<i64> getTeamSeasonIds(1: param.GetTeamSeasonsParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller)

#################### match ##################################

    /**
    * 获取比赛详情
    **/
    match.TDetailMatch getTDetailMatchById(1: i64 id,2: api_common.CallerParam caller),

    /**
    * 获取比赛基本信息
    **/
    match.TMatch getTMatchById(1: i64 id,2: api_common.CallerParam caller),

    /**
    * 批量获取比赛基本信息
    **/
    list<match.TMatch> getTMatchesByIds(1: list<i64> ids,2: api_common.CallerParam caller),

    /**
    * 通过球队id获取该球队的赛程
    **/
    list<match.TMatch> getMatchesByCompetitorId(1: i64 competitorId,2: i32 csid, 3: api_common.PageParam pageParam, 4: api_common.CallerParam caller),

     /**
     * 滚球精英接口-获取某天有章鱼猜球的数据
     **/
     map<string, string> syncZhangyuGames(),


    /**
    * 根据球员id批量获取比赛
    **/
    list<match.TDetailMatch> getTDetailMatchesByPid(1: i64 pid,2: api_common.MatchStatus status, 3: api_common.PageParam pageParam, 4: api_common.CallerParam caller),

    /**
    * 根据条件批量获取比赛
    **/
    list<match.TDetailMatch> getTDetailMatches(1: param.GetPlayerMatchesParam p, 2: api_common.PageParam pageParam, 3: api_common.CallerParam caller),


    /**
    * 按轮次、阶段、分站等查询赛程
    **/
    list<i64> getMatchIdsByCidAndMetaEntryId(1: i64 cid, 2: i64 csid, 3: i64 entryId,  4: api_common.PageParam pageParam),
    #################### MatchAction ##################################

    /**
    * 获取比赛实况基本信息
    **/
    match.TMatchAction getTMatchActionById(1: i64 id,2: api_common.CallerParam caller),

    /**
    * 批量获取比赛实况基本信息
    **/
    list<match.TMatchAction> getTMatchActionsByIds(1: list<i64> ids,2: api_common.CallerParam caller),

	/**
	* 通过动作类型获取本场比赛的所有实况
	* mid : 比赛id
	* type : 哪个类型的实况(进球,黄牌,红牌)
	**/
	list<i64> getMatchActionsOfMatch(1: param.GetMacthActionsParam p, 2: api_common.CallerParam caller),

    #################### MatchStats ##################################

    /**
    * 获取比赛统计基本信息
    **/
    match.TMatchStats getTMatchStatsById(1: i64 id,2: api_common.CallerParam caller),

    #################### competition ##################################

    competition.TCompetition getTCompetitionById(1: i64 id,2: api_common.CallerParam caller),

    /**
    * 批量查询赛事
    **/
    list<competition.TCompetition> getTCompetitionByIds(1: list<i64> ids,2: api_common.CallerParam caller),


    /**
    * 根据赛事类型获取赛事信息
    **/
    list<i64> getTCompetitonIds4SimpleSearch(1: param.GetCompetitionsParam p, 2: api_common.PageParam page,3: api_common.CallerParam caller),

    /**
    * 根据赛事code获取赛事
    **/
    competition.TCompetition getTCompetitionByCode(1: string code,2: api_common.CallerParam caller),

    #################### competitionSeason ##################################


    competition.TCompetitionSeason getTCompetitionSeasonById(1: i64 id,2: api_common.CallerParam caller),

    list<competition.TCompetitionSeason> getTCompetitionSeasonsByIds(1: list<i64> ids,2: api_common.CallerParam caller),

    /**
    * 获取某赛事的赛季
    **/
    list<i64> getSeasonIdsOfCompetition(1: i64 cid,2: api_common.CallerParam caller),

    /**
    * 获取最新赛季数据
    **/
    competition.TCompetitionSeason getLatestTCompetitionSeasonsByCid(1: i64 cid,2: api_common.CallerParam caller),

    /**
    * 获取最新赛季数据
    **/
    competition.TCompetitionSeason getTCompetitionSeasonByCidAndSeason(1: i64 cid,2: string season, 3: api_common.CallerParam caller),



    ####################### toplist ##############################

    /**
    * 根据榜单id获取榜单信息
    **/
    toplist.TTopList getTTopListById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 根据榜单id列表获取榜单信息
    **/
    list<toplist.TTopList> getTTopListsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 查询赛季榜单
    **/
    list<i64> getSeasonTopListIds(1: param.GetSeasonTopListsParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 获取球队或球员的技术榜单
    **/
    list<toplist.TTopList> getCompetitorTTopLists(1: param.GetSeasonTopListsParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller)

   #################### competitorSeasonStats ##################################

    /**
    * 根据id列表获取赛季统计信息
    **/
    list<competitor_season_stat.TCompetitorSeasonStat> getTCompetitorSeasonStatsByIds(1: list<i64> ids),

    /**
    * 获取赛季球队或者球员技术统计
    **/
    list<i64> getCompetitorSeasonStatIds(1:param.GetCompetitorSeasonStatsParam p),

    #################### matchReview ##################################
    /**
    * 获取比赛历史对阵
    **/
    match_review.TMatchReview getTMatchReviewById(1:i64 id,2: api_common.CallerParam caller),


}


