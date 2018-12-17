package com.lesports.qmt.config.service.impl.support;

import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.spring.security.authentication.LeCasAuthenticationToken;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by denghui on 2016/12/13.
 */
public abstract class AbstractConfigWebService<T extends QmtVo, ID extends Serializable> {

    protected String getOperator() {
        LeCasAuthenticationToken token = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getName();
    }

    protected void addParamsCriteriaToQuery(InternalQuery query, Map<String, Object> params, QmtPageParam pageParam) {
        Long id = MapUtils.getLong(params,"id",null);
        String name = MapUtils.getString(params, "name", null);
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        query.with(pageParam.toPageable(new Sort(Sort.Direction.DESC, "create_at", "_id")));
    }

    protected void addDictCriteriaToQuery(InternalQuery query, Map<String, Object> params, QmtPageParam pageParam) {
        Long id = MapUtils.getLong(params,"id",null);
        String name = MapUtils.getString(params, "name", null);
        Long parentId = MapUtils.getLong(params,"parentId",null);

        query.addCriteria(InternalCriteria.where("deleted").is(false));
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(InternalCriteria.where("multi_lang_names.value").regex(name));
        }
        if (parentId != null) {
            query.addCriteria(InternalCriteria.where("parent_id").is(parentId));
        }
        query.with(pageParam.toPageable(new Sort(Sort.Direction.DESC, "create_at", "_id")));
    }
}
