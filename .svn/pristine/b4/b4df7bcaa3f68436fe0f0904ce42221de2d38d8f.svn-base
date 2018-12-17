package com.lesports.qmt.cms.admin.web.service.impl;

import com.lesports.qmt.cms.admin.web.service.LayoutWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnPageVo;
import com.lesports.qmt.cms.admin.web.vo.LayoutVo;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.internal.client.CmsLayoutInternalApis;
import com.lesports.qmt.cms.internal.client.utils.PageTypeMappingUtils;
import com.lesports.qmt.cms.model.Layout;
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
 * Time: 16-12-6 : 上午10:11
 */
@Service("layoutWebService")
public class LayoutWebServiceImpl implements LayoutWebService {

    @Override
    public LayoutVo findOne(Long id) {
        return new LayoutVo(CmsLayoutInternalApis.getLayoutById(id));
    }

    @Override
    public boolean delete(Long id) {
        return CmsLayoutInternalApis.deleteLayout(id);
    }

    @Override
    public Long saveWithId(LayoutVo entity) {
        return CmsLayoutInternalApis.saveLayout(entity.toModel());
    }


    @Override
    public QmtPage<LayoutVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        Long id = MapUtils.getLong(params, "id", null);
        String name = MapUtils.getString(params, "name", null);
        String path = MapUtils.getString(params, "path", null);
        String type = MapUtils.getString(params, "type", null);
        String isFinished = MapUtils.getString(params, "isFinished",null);
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotEmpty(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        if (StringUtils.isNotEmpty(path)) {
            query.addCriteria(InternalCriteria.where("path").is(path));
        }
        if (StringUtils.isNotEmpty(type)) {
            query.addCriteria(InternalCriteria.where("type").is(PageTypeMappingUtils.mappingPageType(type)));
        }
        if(StringUtils.isNotEmpty(isFinished)){
            query.addCriteria(InternalCriteria.where("is_finished").is(Boolean.valueOf(isFinished)));
        }
        long total = CmsLayoutInternalApis.countLayoutsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(new PageRequest(pageParam.getPage() - 1, pageParam.getCount(), Sort.Direction.DESC, "create_at"));

        List<Layout> pages = CmsLayoutInternalApis.getLayoutsByQuery(query);
        if(CollectionUtils.isEmpty(pages)){
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<LayoutVo> vos = pages.stream().map(new Function<Layout, LayoutVo>() {
            @Override
            public LayoutVo apply(Layout layout) {
                return new LayoutVo(layout);
            }
        }).collect(Collectors.toList());


        return new QmtPage(vos, pageParam, total);
    }
}
