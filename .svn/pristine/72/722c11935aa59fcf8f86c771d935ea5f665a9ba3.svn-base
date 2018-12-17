package com.lesports.qmt.config.service.impl;

import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.TSuggest;
import com.lesports.qmt.config.model.Suggest;
import com.lesports.qmt.config.repository.SuggestRepository;
import com.lesports.qmt.config.service.SuggestService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by ruiyuansheng on 2015/10/19.
 */
@Service("suggestService")
public class SuggestServiceImpl extends AbstractQmtService<Suggest,Long> implements SuggestService {

    @Resource
    private SuggestRepository suggestRepository;

    @Override
    protected QmtOperationType getOpreationType(Suggest entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Suggest entity) {
        entity.setId(LeIdApis.nextId(IdType.SUGGEST));
        entity.setOrder(getMaxOrder() + 1);
        return suggestRepository.save(entity);
    }

    private int getMaxOrder() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "order"));
        Suggest suggest = suggestRepository.findOneByQuery(query);
        return suggest == null ? 0 : LeNumberUtils.toInt(suggest.getOrder());
    }

    @Override
    protected boolean doAfterCreate(Suggest entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Suggest entity) {
        return suggestRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Suggest entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        return suggestRepository.delete(id);
    }

    @Override
    protected boolean doAfterDelete(Suggest entity) {
        return true;
    }

    @Override
    protected Suggest doFindExistEntity(Suggest entity) {
        return findOne(entity.getId());
    }

    @Override
    public List<Long> getTSuggestIds(Pageable pageable) {
        Query q = new Query();
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return suggestRepository.findIdByQuery(q);
    }

    @Override
    public List<TSuggest> getTSuggestsByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TSuggest> tSuggests = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            Suggest suggest = findOne(id);
            if (null != suggest) {
                TSuggest tSuggest = new TSuggest();
                tSuggest.setId(suggest.getId());
                tSuggest.setSuggest(suggest.getSuggest());
                tSuggests.add(tSuggest);
            }
        }
        return tSuggests;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return suggestRepository;
    }
}
