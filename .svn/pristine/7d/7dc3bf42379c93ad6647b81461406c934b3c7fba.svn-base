package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TMatchReview;
import com.lesports.qmt.sbd.cache.TMatchReviewCache;
import com.lesports.qmt.sbd.converter.TMatchReviewVoConverter;
import com.lesports.qmt.sbd.model.MatchReview;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.MatchReviewRepository;
import com.lesports.qmt.sbd.service.MatchReviewService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei1 on 2016/2/23.
 */
@Service("matchReviewService")
public class MatchReviewServiceImpl extends AbstractSbdService<MatchReview,Long> implements MatchReviewService {

    protected static final Logger LOG = LoggerFactory.getLogger(MatchReviewServiceImpl.class);

    @Resource
    private MatchReviewRepository matchReviewRepository;

    @Resource
    private TMatchReviewVoConverter matchReviewVoConverter;

    @Resource
    private TMatchReviewCache matchReviewCache;


    @Override
    public boolean save(MatchReview entity) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        boolean result = false;
        if (entity.getId() == null) {
            LOG.warn("fail to save, not found id. entity : {}", entity);
            return false;
        }
        MatchReview existEntity = matchReviewRepository.findOne(entity.getId());
        if (existEntity == null) {
            entity.setCreateAt(now);
            entity.setUpdateAt(now);
            result = matchReviewRepository.save(entity);
        } else {
            LeBeanUtils.copyNotEmptyPropertiesQuietly(existEntity, entity);
            existEntity.setUpdateAt(now);
            result = matchReviewRepository.save(existEntity);
            if(result){
                matchReviewCache.delete(existEntity.getId());
            }
        }
        return result;
    }

    @Override
    public MatchReview findOne(Long id) {
        return matchReviewRepository.findOne(id);
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }


    @Override
    public TMatchReview getTMatchReviewById(long id,CallerParam caller) {
        TMatchReview vo = matchReviewCache.findOne(id);
        if (vo == null) {
            MatchReview matchReview = matchReviewRepository.findOne(id);
            if (matchReview == null) {
                LOG.warn("fail to getTMatchReviewById, player no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            vo = matchReviewVoConverter.toDto(matchReview);
            if (vo == null) {
                LOG.warn("fail to getTMatchReviewById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            matchReviewCache.save(vo);
        }
        matchReviewVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return matchReviewRepository;
    }


    @Override
    protected QmtOperationType getOpreationType(MatchReview entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doDelete(Long id) {
        return true;
    }

    @Override
    protected boolean doAfterUpdate(MatchReview entity) {
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
    protected boolean doUpdate(MatchReview entity) {
        return matchReviewRepository.save(entity);
    }

    @Override
    protected boolean doAfterDelete(MatchReview entity) {
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
    protected boolean doAfterCreate(MatchReview entity) {
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
    protected boolean doCreate(MatchReview entity) {
//        entity.setId(LeIdApis.nextId(IdType.TEAM_SEASON));
//        entity.setDeleted(false);
        return matchReviewRepository.save(entity);
    }

    @Override
    protected MatchReview doFindExistEntity(MatchReview entity) {
        return matchReviewRepository.findOne(entity.getId());
    }
}
