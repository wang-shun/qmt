package com.lesports.qmt.config.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.cache.TCallerCache;
import com.lesports.qmt.config.converter.TCallerConverter;
import com.lesports.qmt.config.model.Caller;
import com.lesports.qmt.config.repository.CallerRepository;
import com.lesports.qmt.config.service.CallerService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User: ellios
 * Time: 15-11-13 : 下午5:49
 */
@Service("callerService")
public class CallerServiceImpl extends AbstractQmtService<Caller, Long> implements CallerService {

    private static final Logger LOG = LoggerFactory.getLogger(CallerServiceImpl.class);

    @Resource
    private TCallerConverter callerConverter;

    @Resource
    private CallerRepository callerRepository;

    @Resource
    private TCallerCache callerCache;

    @Override
    public TCaller getTCallerById(long id) {
        TCaller vo = callerCache.findOne(id);
        if (vo == null) {
            Caller caller = callerRepository.findOne(id);
            if (caller == null) {
                LOG.warn("fail to getTCallerById, caller no exists. id : {}", id);
                return null;
            }
            vo = callerConverter.toDto(caller);
            if (vo == null) {
                LOG.warn("fail to getTCallerById, toTVo fail. id : {}", id);
                return null;
            }
            callerCache.save(vo);
        }
        return vo;
    }

    @Override
    public List<TCaller> getTCallersByIds(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TCaller> returnList = Lists.newArrayListWithExpectedSize(ids.size());
        for(Long id : ids){
            TCaller tCaller = getTCallerById(id);
            if(tCaller != null){
                returnList.add(tCaller);
            }
        }
        return returnList;
    }


    @Override
    public boolean save(Caller entity) {
        Preconditions.checkNotNull(entity.getId());
        callerRepository.save(entity);
        callerCache.delete(entity.getId());
        return true;
    }

    @Override
    protected MongoCrudRepository<Caller, Long> getInnerRepository() {
        return callerRepository;
    }

    @Override
    public boolean delete(Long id) {
        callerCache.delete(id);
        return callerRepository.delete(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Caller entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Caller entity) {
        return callerRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Caller entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Caller entity) {
        return callerRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Caller entity) {
        return callerCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long id) {
        return callerRepository.delete(id);
    }

    @Override
    protected boolean doAfterDelete(Caller entity) {
        return callerCache.delete(entity.getId());
    }

    @Override
    protected Caller doFindExistEntity(Caller entity) {
        return findOne(entity.getId());
    }

    @Override
    public List<Long> getTCallerBySplatId(long splatId){
        Query q = new Query();
        q.addCriteria(where("splat_id").is(splatId));

        return callerRepository.findIdByQuery(q);
    }
}
