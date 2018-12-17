package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.model.DataImportConfig;
import com.lesports.qmt.sbd.model.Partner;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * User: qiaohongxin
 * Time: 17-02-07 : 下午14:32
 */
public class SbdDataImportConfigInternalApis extends SbdInternalApis {


    public static long saveDataImportConfig(DataImportConfig dataImportConfig) {
        return saveEntity(dataImportConfig, DataImportConfig.class);
    }

    public static long saveDataImportConfig(DataImportConfig dataImportConfig, boolean allowEmpty) {
        return saveEntity(dataImportConfig, DataImportConfig.class, allowEmpty);
    }

    public static boolean deleteDataImportConfig(Long id) {
        return deleteEntity(id, DataImportConfig.class);
    }

    public static DataImportConfig getDataImportConfigById(Long id) {
        return getEntityById(id, DataImportConfig.class);
    }

    public static List<DataImportConfig> getDataImportConfigByIds(List<Long> ids) {
        return getEntitiesByIds(ids, DataImportConfig.class);
    }

    public static List<DataImportConfig> getDataImportConfigsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, DataImportConfig.class);
    }

    public static List<Long> getDataImportConfigIdsByQuery(InternalQuery params) {
        return getEntityIdsByQuery(params, DataImportConfig.class);
    }

    public static long countDataImportConfigByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, DataImportConfig.class);
    }

    public static DataImportConfig getDataImportConfigByFileName(String fileName) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("file_name", "eq", fileName));
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<DataImportConfig> configs = getDataImportConfigsByQuery(query);
        if (CollectionUtils.isNotEmpty(configs)) {
            return configs.get(0);
        }
        return null;
    }

}
