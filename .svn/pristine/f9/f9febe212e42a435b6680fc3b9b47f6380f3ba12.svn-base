package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Country;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/11/7.
 */
public class QmtCountryInternalApis extends QmtConfigInternalApis {
    public static boolean deleteCountry(long id) {
        return deleteEntity(id, Country.class);
    }

    public static Country getCountryById(long id) {
        return getEntityById(id, Country.class);
    }

    public static Country getCountryByCode(String code) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("code").is(code));
        internalQuery.addCriteria(new InternalCriteria("deleted").is(false));
        List<Country> countries = getCountryByQuery(internalQuery);
        if (CollectionUtils.isEmpty(countries)) {
            return null;
        }
        return countries.get(0);
    }

    public static List<Country> getCountryByQuery(InternalQuery query) {
        List<Long> ids = getEntityIdsByQuery(query, Country.class);
        if (true == CollectionUtils.isEmpty(ids)) return null;
        return getEntitiesByIds(ids, Country.class);
    }

    public static List<Country> getCountryByIds(List<Long> ids) {
        if (true == CollectionUtils.isEmpty(ids)) return null;
        return getEntitiesByIds(ids, Country.class);
    }

    public static long saveCountry(Country country) {
        return saveEntity(country, Country.class, true);
    }

    public static long countCountryCountByQuery(InternalQuery query) {
        return countByQuery(query, Country.class);
    }
}
