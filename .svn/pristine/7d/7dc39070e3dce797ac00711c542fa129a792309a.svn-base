package com.lesports.qmt.config.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.qmt.config.client.QmtConfigSuggestInternalApis;
import com.lesports.qmt.config.model.Suggest;
import com.lesports.qmt.config.service.SuggestWebService;
import com.lesports.qmt.config.service.impl.support.AbstractConfigWebService;
import com.lesports.qmt.config.vo.SuggestVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/12/9.
 */
@Service("suggestWebService")
public class SuggestWebServiceImpl extends AbstractConfigWebService implements SuggestWebService {

    private static final Logger LOG = LoggerFactory.getLogger(SuggestWebServiceImpl.class);

    private static final Function<Suggest, SuggestVo> VO_FUNCTION = new Function<Suggest, SuggestVo>() {
        @Nullable
        @Override
        public SuggestVo apply(@Nullable Suggest input) {
            return input == null ? null : new SuggestVo(input);
        }
    };

    @Override
    public Long saveWithId(SuggestVo entity) {
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    private Long doUpdate(SuggestVo entity) {
        Suggest existsSuggest = QmtConfigSuggestInternalApis.getSuggestById(entity.getId());
        existsSuggest.setSuggest(entity.getSuggest());
        existsSuggest.setUpdater(getOperator());
        return QmtConfigSuggestInternalApis.saveSuggest(existsSuggest);
    }

    private Long doInsert(SuggestVo entity) {
        entity.setCreator(getOperator());
        long id = QmtConfigSuggestInternalApis.saveSuggest(entity.toModel());
        entity.setId(id);
        return id;
    }

    @Override
    public SuggestVo findOne(Long id) {
        Suggest suggest = QmtConfigSuggestInternalApis.getSuggestById(id);
        return suggest == null ? null : new SuggestVo(suggest);
    }

    @Override
    public boolean delete(Long id) {
        return QmtConfigSuggestInternalApis.deleteSuggest(id);
    }

    @Override
    public QmtPage<SuggestVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        addParamsCriteriaToQuery(query, params, pageParam);

        long total = QmtConfigSuggestInternalApis.countSuggestByQuery(query);
        if (total <= 0) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(pageParam.toPageable(new Sort(Sort.Direction.ASC, "order")));
        List<Suggest> activities = QmtConfigSuggestInternalApis.getSuggestsByQuery(query);
        return new QmtPage<>(Lists.transform(activities, VO_FUNCTION), pageParam, total);
    }

    @Override
    public boolean resetOrder(List<Long> ids) {
        // check indexes
        int order = 1;
        for (Long id : ids) {
            Suggest suggest = QmtConfigSuggestInternalApis.getSuggestById(id);
            if (suggest != null) {
                suggest.setOrder(order ++);
                boolean result = QmtConfigSuggestInternalApis.saveSuggest(suggest) > 0;
                if (!result) {
                    LOG.error("reset order of suggest error, id:{}", id);
                    return false;
                }
            }
        }
        return true;
    }
}
