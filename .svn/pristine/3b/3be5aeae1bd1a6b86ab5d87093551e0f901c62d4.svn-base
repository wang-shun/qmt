package com.lesports.qmt.sbd.service.support;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User: ellios
 * Time: 16-3-20 : 上午11:03
 */
abstract public class AbstractSbdService<T extends QmtModel, ID extends Serializable> extends AbstractQmtService<T, ID> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSbdService.class);


    protected CriteriaDefinition getCountryCriteria(CallerParam caller) {
        if (caller.getCountry() == null) {
            return null;
        }
        return where("allow_countries").is(caller.getCountry());
    }

    protected CriteriaDefinition getOnlineLanguageCriteria(CallerParam caller) {
        if (caller.getLanguage() == null) {
            return null;
        }
        return where("online_languages").is(caller.getLanguage());
    }


    protected void addInternationalCriteriaToQuery(Query q, CallerParam caller) {
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        CriteriaDefinition languageCriteria = getOnlineLanguageCriteria(caller);
        if (languageCriteria != null) {
            q.addCriteria(languageCriteria);
        }
    }

	protected void addCountryCriteriaToQuery(Query q, CallerParam caller) {
		CriteriaDefinition countryCriteria = getCountryCriteria(caller);
		if (countryCriteria != null) {
			q.addCriteria(countryCriteria);
		}
	}


    protected Pageable check(Pageable pageable) {
        return pageable == null? new PageRequest(0, Integer.MAX_VALUE) : pageable;
    }

    protected Pattern getNamePattern(String name) {
        Pattern pattern;
        try {
            pattern = Pattern.compile("^.*" + name.trim() + ".*$", Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            throw new IllegalArgumentException("can not compile pattern from name");
        }
        return pattern;
    }

    public List<ID> findIdsByParams(Map<String, Object> params, Pageable pageable) {
        Query query = QueryUtils.buildQuery(params, pageable);
        return getInnerRepository().findIdByQuery(query);
    }

}
