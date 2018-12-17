package com.lesports.qmt.transcode.service.support;

import com.lesports.LeConstants;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.IndexResult;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeProperties;
import com.lesports.utils.QueryUtils;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * User: ellios
 * Time: 16-3-20 : 上午11:03
 */
abstract public class AbstractTranscodeService<T extends QmtModel, ID extends Serializable>  {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTranscodeService.class);
    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    private static final String SEARCH_INDEX_URL = LeProperties.getString("search.index.url", "http://s.qmt.lesports.com/search/v2/i/qmt/{0}/{1}?caller=1007");

    protected abstract MongoCrudRepository<T, ID> getInnerRepository();

    public List<T> findByIds(List<ID> ids) {
        return getInnerRepository().findByIds(ids);
    }

    public List<ID> findIdsByQuery(InternalQuery internalQuery) {
        Query query = QueryUtils.buildQuery(internalQuery);
        return getInnerRepository().findIdByQuery(query);
    }

    public List<T> findByQuery(InternalQuery internalQuery) {
        Query query = QueryUtils.buildQuery(internalQuery);
        return getInnerRepository().findByQuery(query);
    }

    public List<ID> findIdByQuery(InternalQuery internalQuery) {
        Query query = QueryUtils.buildQuery(internalQuery);
        return getInnerRepository().findIdByQuery(query);
    }

    public long countByQuery(InternalQuery internalQuery) {
        Query query = QueryUtils.buildQuery(internalQuery);
        return getInnerRepository().countByQuery(query);
    }

    protected boolean indexEntity(ID id, String idType) {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/x-www-form-urlencoded");
        HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

        int tryCount = 0;
        while (tryCount++ < LeConstants.MAX_TRY_COUNT) {
            try {
                LOG.info("indexing {}: {}, url:{}", idType, id, SEARCH_INDEX_URL);
                ResponseEntity<IndexResult> result = TEMPLATE.exchange(SEARCH_INDEX_URL, HttpMethod.PUT, httpEntity, IndexResult.class, StringUtils.lowerCase(idType), id);
                if (indexSucceed(result)) {
                    LOG.info("success to index {}: {}", idType, id);
                    return true;
                }
            } catch (Exception e) {
                LOG.error("fail to index " + idType + ": " + id + ", sleep and try again", e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                }
            }
        }
        return false;
    }

    private boolean indexSucceed(ResponseEntity<IndexResult> result) {
        if (null == result) {
            return false;
        }
        if (null == result.getBody()) {
            return false;
        }
        IndexResult ir = result.getBody();
        if (null == ir.getCode() || 200 != ir.getCode()) {
            return false;
        }
        if (null == ir.getData()) {
            return false;
        }
        if (!ir.getData().getSuccess()) {
            return false;
        }
        return true;
    }
}
