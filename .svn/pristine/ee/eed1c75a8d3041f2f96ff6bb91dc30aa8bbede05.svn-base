package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.repository.MenuRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2015/7/2.
 */
@Repository("menuRepositoryImpl")
public class MenuRepositoryImpl extends AbstractMongoRepository<Menu, Long> implements MenuRepository {
    @Override
    protected Class<Menu> getEntityType() {
        return Menu.class;
    }

    @Override
    protected Long getId(Menu entity) {
        return entity.getId();
    }
}
