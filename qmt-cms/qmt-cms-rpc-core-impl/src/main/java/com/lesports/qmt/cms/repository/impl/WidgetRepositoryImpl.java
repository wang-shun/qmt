package com.lesports.qmt.cms.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.qmt.cms.repository.WidgetRepository;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-11-29 : 下午5:42
 */
@Repository("widgetRepository")
public class WidgetRepositoryImpl extends AbstractMongoRepository<Widget, Long> implements WidgetRepository {

    @Override
    protected Class<Widget> getEntityType() {
        return Widget.class;
    }

    @Override
    protected Long getId(Widget entity) {
        return entity.getId();
    }
}
