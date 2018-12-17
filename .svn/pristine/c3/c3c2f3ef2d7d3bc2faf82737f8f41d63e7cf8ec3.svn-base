package com.lesports.qmt.config.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.repository.CountryRepository;
import com.lesports.qmt.config.service.CountryService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Service("countryService")
public class CountryServiceImpl extends AbstractQmtService<Country, Long> implements CountryService {

//    private static final Logger LOG = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Resource
    private CountryRepository countryRepository;

    @Override
    protected MongoCrudRepository<Country, Long> getInnerRepository() {
        return this.countryRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Country entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Country entity) {
        if (null == entity) return false;
        entity.setId(LeIdApis.nextId(IdType.COUNTRY));
        return countryRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Country entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Country entity) {
        if (null == entity) return false;

        Country orig = null;
        if (null == entity.getId())
            entity.setId(LeIdApis.nextId(IdType.COUNTRY));
        else
            orig = countryRepository.findOne(entity.getId());

        boolean result = false;
        if (null != orig) {
            LeBeanUtils.copyNotEmptyPropertiesQuietly(orig, entity);
            result = countryRepository.save(orig);
        } else
            result = countryRepository.save(entity);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Country entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        if (null == id || id <= 0) return false;
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return countryRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Country entity) {
        return true;
    }

    @Override
    protected Country doFindExistEntity(Country entity) {
        if (null == entity) return new Country();
        return countryRepository.findOne(entity.getId());
    }
}
