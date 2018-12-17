package com.lesports.qmt.cms.internal.client;

import com.lesports.qmt.cms.internal.client.support.QmtCmsInternalBaseApis;
import com.lesports.qmt.cms.model.Mapper;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * User: ellios
 * Time: 17-1-10 : 下午4:33
 */
public class CmsMappperInternalApis extends QmtCmsInternalBaseApis {

    private static final Logger LOG = LoggerFactory.getLogger(CmsMappperInternalApis.class);

    public static List<Mapper> getMapppersByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Mapper.class);
    }

    public static long saveMapper(Mapper mapper) {
        return saveEntity(mapper, Mapper.class);
    }

    public static Mapper getMapperById(Long id) {
        return getEntityById(id, Mapper.class);
    }

    /**
     * 通过name获取widget
     *
     * @param type
     * @param version
     * @return
     */
    public static Mapper getMapperByTypeAndVersion(String type, String version) {
        if (StringUtils.isEmpty(type)) {
            LOG.warn("fail to getMapperByTypeAndVersion. type is empty.");
            return null;
        }
        type = StringUtils.upperCase(type);
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("type").is(type));
        if (StringUtils.isNotEmpty(version)) {
            query.addCriteria(InternalCriteria.where("version").is(version));
        }
        query.with(new PageRequest(0, 1, Sort.Direction.DESC, "version"));
        List<Mapper> mappers = getEntitiesByQuery(query, Mapper.class);
        if (CollectionUtils.isEmpty(mappers)) {
            return null;
        }
        return mappers.get(0);
    }


    public static boolean deleteMappper(Long id) {
        return false;
    }

    public static List<Mapper> getMapppersByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Mapper.class);
    }

    public static long countMappersByQuery(InternalQuery query) {
        return countByQuery(query, Mapper.class);
    }
}
