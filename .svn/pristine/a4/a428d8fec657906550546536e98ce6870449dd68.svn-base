package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Tag;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * Created by denghui on 2016/11/7.
 */
public class QmtConfigTagInternalApis extends QmtConfigInternalApis {

    public static long saveTag(Tag tag) {
        return saveTag(tag, false);
    }

    public static long saveTag(Tag tag, boolean allowEmpty) {
        return saveEntity(tag, Tag.class, allowEmpty);
    }

    public static boolean deleteTag(Long id) {
        return deleteEntity(id, Tag.class);
    }

    public static Tag getTagById(Long id) {
        return getEntityById(id, Tag.class);
    }

    public static List<Tag> getTagsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Tag.class);
    }

    public static long countTagByQuery(InternalQuery query) {
        return countByQuery(query, Tag.class);
    }

    public static List<Long> getTagIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Tag.class);
    }

    public static List<Tag> getTagsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Tag.class);
    }
}
