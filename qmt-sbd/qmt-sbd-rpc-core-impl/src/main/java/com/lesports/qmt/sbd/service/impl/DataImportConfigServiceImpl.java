package com.lesports.qmt.sbd.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbd.model.DataImportConfig;
import com.lesports.qmt.sbd.repository.DataImportConfigRepository;
import com.lesports.qmt.sbd.service.DataImportConfigService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by qiaohongxin on 2017/02/07
 */
@Service("dataImportConfigService")
public class DataImportConfigServiceImpl extends AbstractSbdService<DataImportConfig, Long> implements DataImportConfigService {

    @Resource
    private DataImportConfigRepository dataImportConfigRepository;


    @Override
    public DataImportConfig findOne(Long id) {
        return dataImportConfigRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(DataImportConfig entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(DataImportConfig entity) {
        entity.setId(LeIdApis.nextId(IdType.DATA_IMPORT_CONFIG));
        entity.setDeleted(false);
        return dataImportConfigRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(DataImportConfig entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(DataImportConfig entity) {
        return dataImportConfigRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(DataImportConfig entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return dataImportConfigRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(DataImportConfig entity) {

        return true;
    }

    @Override
    protected DataImportConfig doFindExistEntity(DataImportConfig entity) {
        return dataImportConfigRepository.findOne(entity.getId());
    }


    @Override
    protected MongoCrudRepository getInnerRepository() {
        return dataImportConfigRepository;
    }

}
