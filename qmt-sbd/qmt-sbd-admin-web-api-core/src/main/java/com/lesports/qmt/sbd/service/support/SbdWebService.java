package com.lesports.qmt.sbd.service.support;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.query.InternalQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2016/10/26.
 */
public interface SbdWebService<T extends QmtVo, ID extends Serializable> extends QmtWebService<T, ID> {

    /**
     * Get entity by id.
     *
     * @param id
     * @param caller
     * @return
     */
    T findOne(ID id, CallerParam caller);

    /**
     * Save entity.
     *
     * @param entity
     * @param caller
     * @return
     */
    boolean save(T entity, CallerParam caller);

    /**
     * @param query
     * @param caller
     * @return
     */
    long countByQuery(InternalQuery query, CallerParam caller);

    /**
     * @param query
     * @param caller
     * @return
     */
    List<T> findByQuery(InternalQuery query, CallerParam caller);


    /**
     * @param params
     * @param caller
     * @return
     */
    List<T> findByParams(Map<String, Object> params, CallerParam caller);

    /**
     * @param query
     * @param caller
     * @return
     */
    QmtPage<T> list(InternalQuery query, QmtPageParam pageParam, CallerParam caller);


    QmtPage<T> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller);

    /**
     * @param query
     * @param caller
     * @return
     */
    T findOneByQuery(InternalQuery query, CallerParam caller);

    /**
     * 更新上线/下线状态
     * @param id
     * @param online
     * @param caller
     * @return
     */
    boolean updateOnlineStatus(long id, boolean online,CallerParam caller);
}
