package com.lesports.qmt.sbd.repository;

import com.lesports.api.common.MatchStatus;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface MatchRepository extends MongoCrudRepository<Match, Long> {
    /**
     * 获取球队某赛季的所有比赛.
     *
     * @param competitorId
     * @param csid
     */
    List<Match> findByCompetitorIdAndCsid(Long competitorId, Long csid);

    Match findBasicOne(Long id);

    /**
     * 获取某支球队的所有比赛.
     *
     * @param competitorId
     * @param pageable
     */
    Page<Match> findByCompetitorId(Long competitorId, Pageable pageable);

    /**
     * 批量获取赛程
     *
     * @param ids
     * @return
     */
    public List<Match> findByIds(List<Long> ids);

    /**
     *
     * @param competitorId
     * @return
     */
    List<Long> findAllIdsByCompetitorId(Long competitorId);

    /**
     *
     * @param cid
     * @return
     */
    List<Match> findAllIdsByCid(Long cid);
    /**
     * 获取有效的比赛数据
     */
    List<Match> findByDateAndStatusAndPartnerType(String date,String Status,Integer PartnerType);

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

    /**
     * 分页查找所有的比赛
     *
     * @param pageable
     * @return
     */
    Page<Match> findAll(Pageable pageable);

    /**
     * 按照条件批量查询比赛ids
     */
    Page<Long> findEidsByQuery(Query query, Pageable pageable);

    List<Long> findEidListByQuery(Query query, Pageable pageable);

    /**
     * 获取提供商提供Id所对应的数据
     * @param partnerId
     * @param partnerType
     * @return
     */

    List<Match> findBypartnerIdAndType(String partnerId, Integer partnerType);

    List<Match> findByCIdAndCsidByStatus(Long cid, Long csid, Boolean stated, String matchTime, MatchStatus status);

    /**
     * 获取结束的比赛信息基于提供商Id
     * @param partnerType
     * @return
     */
    List<Match> findEndedMatchByPartnerType(Integer partnerType);
    /**
     * 获取只包含基本信息的比赛对象
     *
     * @param q
     */
    Match findBasicOneByQuery(Query q);

    List<Long> findMatchIdsOfCompetition(long cid, long csid, Pageable pageable);

    List<Long> findMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, Pageable pageable);

    boolean updateMSyncToCloud(long matchId, Boolean syncToCloud);

    List<Long> getTheRoadOfAdvance(long cid, long csid);
}
