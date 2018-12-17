package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.MatchStatus;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.service.GetPlayerMatchesParam;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.Partner;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by lufei on 2016/10/24.
 */
public interface MatchService extends QmtService<Match, Long> {
    /**
     * 赛事的名称,logo,tagIds更新以后更新相应的赛程
     *
     * @param competition
     */
    public void updateMatchsByCompetitionWhenItChange(Competition competition);

    /**
     * 获取球队某赛季的所有比赛.
     *
     * @param competitorId
     * @param csid
     */
    List<Match> findByCompetitorIdAndCsid(Long competitorId, Long csid);

    /**
     * 获取某支球队的所有比赛.
     *
     * @param competitorId
     * @param pageable
     */
    Page<Match> findByCompetitorId(Long competitorId, Pageable pageable);



    /**
     * 获取本赛事的所有比赛
     *
     * @param cid
     * @return
     */
    Page<Match> findByCid(Long cid, Pageable pageable);

    /**
     * 获取本赛季的赛程数据
     *
     * @param csid
     * @return
     */
    Page<Match> findByCsid(Long csid, Pageable pageable);

    boolean updateMatchResult(Long id, Set<Match.Competitor> competitors);


    /**
     * 更新赛程比分（直播调用）.
     *
     * @param episodeId
     * @param homeScore
     * @param awayScore
     */
    void updateMatchScore(long episodeId, int homeScore, int awayScore);

    List<Match> findByGameTypeAndStatus(long gameType, MatchStatus status, Pageable pageable);

    List<TMatch> getTMatchesByIds(List<Long> ids, CallerParam caller);

    List<Match> findByCIdAndCsidByStatus(Long cid, Long csid, Boolean stated, String matchTime, MatchStatus status);

    TMatch getTMatchById(long id, CallerParam caller);

    List<TDetailMatch> queryTMatches(Match param, long countryId, Pageable page, CallerParam caller);

    TDetailMatch getTDetailMatchById(long id, CallerParam caller);


    /**
     * 通过比赛开始时间和对阵双方查找比赛id
     *
     * @param startTime
     * @param com1
     * @param com2
     * @return
     */
    long findMatchIdByStartTimeAndCompetitors(String startTime, long com1, long com2);

    /**
     * 查找第三方的赛事
     *
     * @param partner
     * @return
     */
    Match getMatchByPartner(Partner partner) ;


    /**
     * 通过对阵双方查找比赛
     *
     * @param comId1
     * @param comId2
     * @return
     */
    Page<Match> findMatchByCompetitors(String startTime, long comId1, long comId2, Pageable pageable);

    /**
     * 通过cid查找最近比赛信息
     *
     * @param startTime
     * @param comId
     * @return
     */
    Page<Match> findNearMatchByCid(String startTime, long comId, Pageable pageable);

    /**
     * 通过cid查找未来比赛信息
     *
     * @param startTime
     * @param comId
     * @return
     */
    Page<Match> findAfterMatchByCid(String startTime, long comId, Pageable pageable);


    boolean updateMatchSyncToCloud(long matchId, Boolean syncToCloud);

    Page<Match> findByParams4Page(Match param, long countryId, Pageable page, CallerParam caller);

    List<Long> findEidsByParams4Page(Match param, long countryId, Pageable page, CallerParam caller);


    /**
     * 获取当前轮次
     *
     * @param cid
     * @param csid
     * @return
     */
    int getMatchCurrentRound(long cid, long csid);

    /**
     * 获取总轮次
     *
     * @param cid
     * @param csid
     * @return
     */
    int getMatchFullRound(long cid, long csid);

    /**
     * 清理rpc缓存
     *
     * @param id
     * @return
     */
    boolean deleteRpcCache(long id);

    /**
     * 获取参赛者的所有比赛
     *
     * @param competitorId
     * @param csid
     * @param pageable
     * @param caller
     * @return
     */
    List<TMatch> getMatchesByCompetitorId(long competitorId, int csid, Pageable pageable, CallerParam caller);

    List<Long> getTheRoadOfAdvanceEids(long cid, long csid, CallerParam caller);

    /**
     * 根据球员id获取最近比赛
     *
     * @param pid
     * @param status
     * @param page
     * @param caller
     * @return
     */
    List<TDetailMatch> getTDetailMatchesByPid(long pid, MatchStatus status, PageParam page, CallerParam caller);

    /**
     * 获取球员的比赛
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<TDetailMatch> getTDetailMatches(GetPlayerMatchesParam p, PageParam page, CallerParam caller);

    /**
     * 按轮次、阶段、分站等查询赛程
     *
     * @param cid
     * @param csid
     * @param entryId
     * @param page
     * @return
     */
    List<Long> getMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, PageParam page);

    public boolean doUpdate(Match entity);

}
