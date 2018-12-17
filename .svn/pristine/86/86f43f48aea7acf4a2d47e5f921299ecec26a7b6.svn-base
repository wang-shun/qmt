package com.lesports.qmt.service.support;

import com.lesports.LeConstants;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.QueryUtils;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.lesports.qmt.QmtOperationType.CREATE;
import static com.lesports.qmt.QmtOperationType.DELETE;

/**
 * User: ellios
 * Time: 16-3-20 : 上午11:03
 */
abstract public class AbstractQmtService<T extends QmtModel, ID extends Serializable> implements QmtService<T, ID> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractQmtService.class);
    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    private static final String SEARCH_INDEX_URL = LeProperties.getString("search.index.url", "http://s.qmt.lesports.com/search/v2/i/qmt/{0}/{1}?caller=1007");

    protected abstract MongoCrudRepository<T, ID> getInnerRepository();

    @Override
    public T findOne(ID id) {
        return getInnerRepository().findOne(id);
    }

    @Override
    public boolean save(T entity) {
        return save(entity, false);
    }

    /**
     * 更新实体,默认实现是不允许的更新空字段,如果确实需要删除更新空字段, allowEmpty为true
     *
     * @param entity
     * @param allowEmpty 允许更新实体字段
     * @return
     */
    public boolean save(T entity, boolean allowEmpty) {
        boolean result = false;
        boolean isCreate = false;
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        QmtOperationType opType = getOpreationType(entity);
        if (opType == DELETE) {
            throw new IllegalArgumentException("save not support delete operation");
        }
        if (opType == CREATE) {
            //创建
            isCreate = true;
            entity.setCreateAt(now);
            entity.setUpdateAt(now);
            result = doCreate(entity);
        } else {
            //更新,待保存的entity
            T toSaveEntity = doFindExistEntity(entity);
            if (toSaveEntity == null) {
                if (allowEmpty == false) {
                    //不允许空,那就是非法的保存
                    LOG.error("fail to update entity : {} for no exist entity find", entity.getId());
                    return false;
                } else {
                    toSaveEntity = entity;
                }
            } else {
                if (allowEmpty == false) {
                    //这里为了防止犯错误，把数据都删掉,所以把置空的操作给去掉了
                    entity.setCreateAt(toSaveEntity.getCreateAt());
                    LeBeanUtils.copyNotEmptyPropertiesQuietly(toSaveEntity, entity);
                } else {
                    //为true的情况要慎重点,主要是全媒体后台调用吧
                    entity.setCreator(toSaveEntity.getCreator());
                    entity.setCreateAt(toSaveEntity.getCreateAt());
                    toSaveEntity = entity;
                }

            }
            toSaveEntity.setUpdateAt(now);
            result = doUpdate(toSaveEntity);
        }
        if (result) {
            LOG.info("success to save entity : {}, isCreate : {}, allowEmpty : {}", entity, isCreate, allowEmpty);
        } else {
            LOG.warn("fail to save entity : {}, isCreate : {}, allowEmpty : {}", entity, isCreate, allowEmpty);
        }
        afterOperation(entity, opType, result);
        return result;
    }

    @Override
    public boolean delete(ID id) {
        boolean result = doDelete(id);

        if (result) {
            LOG.info("success to delete entity : {}", id);
        } else {
            LOG.warn("fail to delete entity : {}", id);
        }
        afterOperation(findOne(id), DELETE, result);
        return result;

    }

    protected boolean afterOperation(T entity, QmtOperationType opType, boolean opResult) {
        boolean result = false;
        if (opResult) {
            switch (opType) {
                case CREATE:
                    result = doAfterCreate(entity);
                    break;
                case UPDATE:
                    result = doAfterUpdate(entity);
                    break;
                case DELETE:
                    result = doAfterDelete(entity);
                    break;
            }
        }
        if (result) {
            LOG.info("success to afterOperation. entity : {}, opType : {}, opResult : {}", entity, opType, opResult);
        } else {
            LOG.info("fail to afterOperation. entity : {}, opType : {}, opResult : {}", entity, opType, opResult);
        }
        return result;

    }

    /**
     * 获取操作类型
     *
     * @param entity
     * @return
     */
    protected abstract QmtOperationType getOpreationType(T entity);

    /**
     * 新建entity
     *
     * @param entity
     * @return
     */
    protected abstract boolean doCreate(T entity);

    /**
     * 新建成功后的一些关联操作
     *
     * @param entity
     * @return
     */
    protected abstract boolean doAfterCreate(T entity);

    /**
     * 更新entity
     *
     * @param entity
     * @return
     */
    protected abstract boolean doUpdate(T entity);

    protected abstract boolean doAfterUpdate(T entity);

    protected abstract boolean doDelete(ID id);

    protected abstract boolean doAfterDelete(T entity);

    protected abstract T doFindExistEntity(T entity);

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
    public boolean  changeOrder(ID id,int increment){
        Update up = new Update();
        up.inc("order",increment);
        return getInnerRepository().update(id, up);};
}
