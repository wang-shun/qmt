package com.lesports.qmt.cms.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.repository.LayoutRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxudong@le.com on 2016/10/26.
 */
@Service("layoutRepository")
public class LayoutRepositoryImpl extends AbstractMongoRepository<Layout, Long> implements LayoutRepository {

    @Override
    protected Class<Layout> getEntityType() {
        return Layout.class;
    }

    @Override
    protected Long getId(Layout entity) {
        return entity.getId();
    }
}
