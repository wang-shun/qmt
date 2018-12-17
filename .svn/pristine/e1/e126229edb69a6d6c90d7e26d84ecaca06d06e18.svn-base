package com.lesports.qmt.mvc;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:15
 */
public interface QmtController<VO extends QmtVo, ID extends Serializable> {

    VO getOneById(ID id);

    default QmtPage<VO> list(Map<String, Object> params, QmtPageParam pageParam) {
        return new QmtPage<VO>(Collections.EMPTY_LIST, pageParam != null ? pageParam : new QmtPageParam(), 0);
    }

    default ID add(VO vo) {
        return null;
    }

    default boolean update(VO vo) {
        return false;
    }

    default boolean delete(ID id) {
        return false;
    }
}
