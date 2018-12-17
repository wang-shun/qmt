package com.lesports.qmt.cms.admin.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.qmt.cms.admin.web.service.WidgetWebService;
import com.lesports.qmt.cms.admin.web.vo.WidgetVo;
import com.lesports.qmt.cms.internal.client.CmsWidgetInternalApis;
import com.lesports.qmt.cms.internal.client.utils.PageTypeMappingUtils;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: ellios
 * Time: 16-12-6 : 上午10:05
 */
@Service("widgetWebService")
public class WidgetWebServiceImpl implements WidgetWebService {

    private static final Logger LOG = LoggerFactory.getLogger(WidgetWebServiceImpl.class);

    @Override
    public WidgetVo findOne(Long id) {
        return new WidgetVo(CmsWidgetInternalApis.getWidgetById(id));
    }

    @Override
    public Map<String, Map<String, Object>> findByIds5Fields(List<Long> ids, List<String> fields) {
        List<Widget> widgets = CmsWidgetInternalApis.getWidgetsByIds(ids);
        if (CollectionUtils.isEmpty(widgets)) {
            return Maps.newHashMap();
        }
        Map<String, Map<String, Object>> results = Maps.newHashMap();
        for (Widget widget : widgets) {
            Map<String, Object> m = popFieldsToMap(widget, fields);
            results.put(String.valueOf(widget.getId()), m);
        }
        return results;
    }

    @Override
    public Map<String, Map<String, Object>> findByNames5Fields(List<String> names, List<String> fields) {
        if (CollectionUtils.isEmpty(names)) {
            return Maps.newHashMap();
        }
        List<Widget> widgets = Lists.newArrayList();
        for (String name : names) {
            Widget widget = CmsWidgetInternalApis.getWidgetByName(name);
            if (widget != null) {
                widgets.add(widget);
            }
        }
        if (CollectionUtils.isEmpty(widgets)) {
            return Maps.newHashMap();
        }
        Map<String, Map<String, Object>> results = Maps.newHashMap();
        for (Widget widget : widgets) {
            Map<String, Object> m = popFieldsToMap(widget, fields);
            results.put(widget.getName(), m);
        }
        return results;
    }

    @Override
    public Map<String, Map<String, Object>> findByPaths5Fields(List<String> paths, String type, List<String> fields) {
        if (CollectionUtils.isEmpty(paths)) {
            return Maps.newHashMap();
        }
        List<Widget> widgets = Lists.newArrayList();
        for (String path : paths) {
            Widget widget = CmsWidgetInternalApis.getWidgetByPath(path, type);
            if (widget != null) {
                widgets.add(widget);
            }
        }
        if (CollectionUtils.isEmpty(widgets)) {
            return Maps.newHashMap();
        }
        Map<String, Map<String, Object>> results = Maps.newHashMap();
        for (Widget widget : widgets) {
            Map<String, Object> m = popFieldsToMap(widget, fields);
            results.put(widget.getPath(), m);
        }
        return results;
    }

    /**
     * 输出widget下的特定field到新的map里
     *
     * @param widget
     * @param fields
     * @return
     */
    private Map<String, Object> popFieldsToMap(Widget widget, List<String> fields) {
        Map<String, Object> m = Maps.newHashMap();
        m.put("id", widget.getId());
        m.put("name", widget.getName());
        m.put("path", widget.getPath());
        m.put("pageType", widget.getPageType());
        if (CollectionUtils.isNotEmpty(fields)) {
            for (String field : fields) {
                try {
                    m.put(field, PropertyUtils.getProperty(widget, field));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    LOG.error("fail to get field : {} from widget : {}", field, widget, e);
                }
            }
        }
        return m;
    }

    @Override
    public boolean delete(Long id) {
        return CmsWidgetInternalApis.deleteWidget(id);
    }

    @Override
    public Long saveWithId(WidgetVo entity) {
        return CmsWidgetInternalApis.saveWidget(entity.toModel());
    }

    @Override
    public QmtPage<WidgetVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        Long id = MapUtils.getLong(params, "id", null);
        String name = MapUtils.getString(params, "name", null);
        String path = MapUtils.getString(params, "path", null);
        String type = MapUtils.getString(params, "type", null);
        String pageType = MapUtils.getString(params, "pageType", null);
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
            //类型为0是通用的组件类型 搜索任何类型，都可以把通用的组建搜索出来
            query.addCriteria(InternalCriteria.where("type").in(Arrays.asList(type,"0")));
        }
        if (StringUtils.isNotEmpty(pageType)) {
            query.addCriteria(InternalCriteria.where("pageType").is(PageTypeMappingUtils.mappingPageType(pageType)));
        }

        long total = CmsWidgetInternalApis.countWidgetsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(new PageRequest(pageParam.getPage() - 1, pageParam.getCount(), Sort.Direction.DESC, "create_at"));

        List<Widget> pages = CmsWidgetInternalApis.getWidgetsByQuery(query);
        if (CollectionUtils.isEmpty(pages)) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<WidgetVo> vos = pages.stream().map(new Function<Widget, WidgetVo>() {
            @Override
            public WidgetVo apply(Widget widget) {
                return new WidgetVo(widget);
            }
        }).collect(Collectors.toList());


        return new QmtPage(vos, pageParam, total);
    }
}
