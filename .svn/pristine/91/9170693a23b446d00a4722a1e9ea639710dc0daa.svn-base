package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TMatchStats;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.MatchReview;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.repository.MatchReviewRepository;
import com.lesports.qmt.sbd.repository.MatchStatsRepository;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.service.MatchService;
import com.lesports.qmt.sbd.service.MatchStatsService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhonglin on 2016/12/16.
 */
@Service("matchStatsService")
public class MatchStatsServiceImpl extends AbstractSbdService<MatchStats, Long> implements MatchStatsService {

    protected static final Logger LOG = LoggerFactory.getLogger(MatchStatsServiceImpl.class);

    @Resource
    private MatchStatsRepository matchStatsRepository;
    @Resource
    private PlayerRepository playerRepository;

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return matchStatsRepository;
    }

    @Override
    public TMatchStats getTMatchStatsByMatchId(long id, CallerParam caller) {
        Query query = new Query(Criteria.where("_id").is(id));
        MatchStats matchStats = matchStatsRepository.findOneByQuery(query);
        if (null == matchStats) {
            return null;
        }

        return toTMatchStats(matchStats);
    }


    @Override
    protected QmtOperationType getOpreationType(MatchStats entity) {
        if (entity.getId() != null && LeNumberUtils.toLong(entity.getId()) > 0) {
            if (matchStatsRepository.findOne(LeNumberUtils.toLong(entity.getId())) != null) {
                return QmtOperationType.UPDATE;
            }
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doDelete(Long id) {
        return true;
    }

    @Override
    protected boolean doAfterUpdate(MatchStats entity) {
//        LeMessage message = LeMessageBuilder.create().
//                setEntityId(entity.getId()).
//                setIdType(IdType.M).
//                setData(JSON.toJSONString(entity)).
//                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC)).
//                build();
//        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(MatchStats entity) {
        return matchStatsRepository.save(entity);
    }

    @Override
    protected boolean doAfterDelete(MatchStats entity) {
//        LeMessage message = LeMessageBuilder.create().
//                setEntityId(entity.getId()).
//                setIdType(IdType.TEAM_SEASON).
//                setData(JSON.toJSONString(entity)).
//                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC)).
//                build();
//        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doAfterCreate(MatchStats entity) {
//        LeMessage message = LeMessageBuilder.create().
//                setEntityId(entity.getId()).
//                setIdType(IdType.TEAM_SEASON).
//                setData(JSON.toJSONString(entity)).
//                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC)).
//                build();
//        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doCreate(MatchStats entity) {
//        entity.setId(LeIdApis.nextId(IdType.TEAM_SEASON));
//        entity.setDeleted(false);
        return matchStatsRepository.save(entity);
    }

    @Override
    protected MatchStats doFindExistEntity(MatchStats entity) {
        return matchStatsRepository.findOne(entity.getId());
    }


    private TMatchStats toTMatchStats(MatchStats matchStats) {
        if (null == matchStats) return null;
        TMatchStats tMatchStats = new TMatchStats();
        tMatchStats.setId(matchStats.getId());


        return tMatchStats;
    }

    private TSimplePlayer createSimplePlayer(MatchStats.PlayerStat playerStat) {
        TSimplePlayer tSimplePlayer = new TSimplePlayer();
        tSimplePlayer.setId(playerStat.getPlayerId());
        Player player = playerRepository.findOne(playerStat.getPlayerId());
        tSimplePlayer.setName(player.getName());
        Map<String, String> tStatsMap = Maps.newHashMap();
        if (MapUtils.isNotEmpty(playerStat.getStats())) {
            for (Map.Entry<String, Object> entry : playerStat.getStats().entrySet()) {
                tStatsMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        tSimplePlayer.setStats(tStatsMap);
        return tSimplePlayer;
    }
}
