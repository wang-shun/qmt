package com.lesports.qmt.cms.admin.web.service.impl;

import com.lesports.qmt.cms.admin.web.service.MapperWebService;
import com.lesports.qmt.cms.admin.web.vo.MapperVo;
import com.lesports.qmt.cms.internal.client.CmsColumnInternalApis;
import com.lesports.qmt.cms.internal.client.CmsMappperInternalApis;
import com.lesports.qmt.cms.internal.client.utils.PageTypeMappingUtils;
import com.lesports.qmt.cms.model.Mapper;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: ellios
 * Time: 17-1-10 : 下午5:29
 */
@Service("mapperWebService")
public class MappperWebServiceImpl implements MapperWebService {

    @Override
    public MapperVo findOne(Long id) {
        return new MapperVo(CmsMappperInternalApis.getMapperById(id));
    }

    @Override
    public boolean delete(Long id) {
        return CmsMappperInternalApis.deleteMappper(id);
    }

    @Override
    public Long saveWithId(MapperVo entity) {
        Mapper model = entity.toModel();
        return CmsMappperInternalApis.saveMapper(model);
    }

    @Override
    public Long saveWithId(Map<String, Object> res, String type, String version) {
        Mapper mapper = CmsMappperInternalApis.getMapperByTypeAndVersion(type, version);
        if(mapper == null){
            mapper = new Mapper();
        }
        mapper.setType(PageTypeMappingUtils.mappingPageType(type));
        mapper.setVersion(version);
        if(MapUtils.isNotEmpty(res)){
            for(Map.Entry<String, Object> entry : res.entrySet()){
                mapper.addResource(entry.getKey(), entry.getValue());
            }
        }
        return CmsMappperInternalApis.saveMapper(mapper);
    }

    @Override
    public MapperVo findByTypeAndVersion(String type, String version) {
        Mapper mapper = CmsMappperInternalApis.getMapperByTypeAndVersion(type, version);
        if(mapper == null){
            return null;
        }
        return new MapperVo(mapper);
    }

    @Override
    public QmtPage<MapperVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        String type = MapUtils.getString(params, "type", null);
        String version = MapUtils.getString(params, "version", null);

        if (StringUtils.isNotEmpty(type)) {
            query.addCriteria(InternalCriteria.where("type").is(type));
        }
        if (StringUtils.isNotEmpty(version)) {
            query.addCriteria(InternalCriteria.where("version").is(version));
        }
        long total = CmsColumnInternalApis.countColumnsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(new PageRequest(pageParam.getPage() - 1, pageParam.getCount(), Sort.Direction.DESC, "create_at"));

        List<Mapper> mappers = CmsMappperInternalApis.getMapppersByQuery(query);
        if (CollectionUtils.isEmpty(mappers)) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<MapperVo> vos = mappers.stream().map(new Function<Mapper, MapperVo>() {
            @Override
            public MapperVo apply(Mapper mapper) {
                return new MapperVo(mapper);
            }
        }).collect(Collectors.toList());


        return new QmtPage(vos, pageParam, total);
    }
}
