package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.repository.CountryRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Repository("countryRepository")
public class CountryRepositoryImpl extends AbstractMongoRepository<Country, Long> implements CountryRepository {

    @Override
    protected Class<Country> getEntityType() {
        return Country.class;
    }

    @Override
    protected Long getId(Country country) {
        return country.getId();
    }
}
