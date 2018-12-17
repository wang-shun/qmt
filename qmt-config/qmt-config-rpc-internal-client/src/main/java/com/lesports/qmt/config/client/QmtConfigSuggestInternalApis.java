package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Suggest;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * Created by denghui on 2016/12/9.
 */
public class QmtConfigSuggestInternalApis extends QmtConfigInternalApis {

    public static long saveSuggest(Suggest tag) {
        return saveSuggest(tag, false);
    }

    public static long saveSuggest(Suggest tag, boolean allowEmpty) {
        return saveEntity(tag, Suggest.class, allowEmpty);
    }

    public static boolean deleteSuggest(Long id) {
        return deleteEntity(id, Suggest.class);
    }

    public static Suggest getSuggestById(Long id) {
        return getEntityById(id, Suggest.class);
    }

    public static List<Suggest> getSuggestsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Suggest.class);
    }

    public static long countSuggestByQuery(InternalQuery query) {
        return countByQuery(query, Suggest.class);
    }

    public static List<Long> getSuggestIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Suggest.class);
    }

    public static List<Suggest> getSuggestsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Suggest.class);
    }

}
