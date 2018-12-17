package com.lesports.qmt.mvc;

import com.lesports.service.LeCrudService;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:48
 */
public interface QmtWebService<T extends QmtVo, ID extends Serializable> extends LeCrudService<T, ID> {

    default QmtPage<T> list(Map<String, Object> params, QmtPageParam pageParam) {
        return new QmtPage<T>(Collections.EMPTY_LIST, pageParam != null ? pageParam : new QmtPageParam(), 0);
    }

    default boolean save(T entity){
        throw new UnsupportedOperationException();
    }

    /**
     * not default to force save result return entity id not boolean
     * @param entity
     * @return
     */
    ID saveWithId(T entity);

}
