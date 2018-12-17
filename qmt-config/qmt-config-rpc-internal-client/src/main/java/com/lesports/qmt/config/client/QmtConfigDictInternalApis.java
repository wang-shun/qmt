package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.DictEntry;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by denghui on 2016/11/7.
 */
public class QmtConfigDictInternalApis extends QmtConfigInternalApis {


    public static long saveDict(DictEntry dict) {
        return saveDict(dict, false);
    }

    public static long saveDict(DictEntry dict, boolean allowEmpty) {
        return saveEntity(dict, DictEntry.class, allowEmpty);
    }

    public static boolean deleteDict(Long id) {
        return deleteEntity(id, DictEntry.class);
    }

    public static DictEntry getDictById(Long id) {
        return getEntityById(id, DictEntry.class);
    }

    public static List<DictEntry> getDictsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, DictEntry.class);
    }

    public static long countDictByQuery(InternalQuery query) {
        return countByQuery(query, DictEntry.class);
    }

    public static List<Long> getDictIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, DictEntry.class);
    }

    public static List<DictEntry> getDictsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, DictEntry.class);
    }

    public static List<DictEntry> getDictsByParentId(long parentId) {
        return getDictsByParentId(parentId, null);
    }

    /**
     * 获取某字典下适用于某项目的子字典
     * @param parentId
     * @param gameFType 项目字典id
     * @return
     */
    public static List<DictEntry> getDictsByParentId(long parentId, Long gameFType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("parent_id").is(parentId));
        if (gameFType != null) {
            query.addCriteria(InternalCriteria.where("game_f_types").is(gameFType));
        }
        List<Long> ids = getEntityIdsByQuery(query, DictEntry.class);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return getEntitiesByIds(ids, DictEntry.class);
    }

    public static DictEntry getDictByParentIdAndName(long parentId, String name) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("parent_id").is(parentId));
        query.addCriteria(InternalCriteria.where("name").is(name));
        List<Long> ids = getEntityIdsByQuery(query, DictEntry.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getDictById(ids.get(0));
    }
}
