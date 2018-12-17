package com.lesports.qmt.service.support;


import com.lesports.qmt.model.support.QmtModel;
import com.lesports.query.InternalQuery;
import com.lesports.service.LeCrudService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denghui on 2016/3/24.
 */
public interface QmtService<T extends QmtModel, ID extends Serializable> extends LeCrudService<T, ID> {

    List<T> findByIds(List<ID> ids);

    List<ID> findIdsByQuery(InternalQuery internalQuery);

    List<T> findByQuery(InternalQuery internalQuery);

    long countByQuery(InternalQuery internalQuery);

    public boolean  changeOrder(ID id,int increment);
}
