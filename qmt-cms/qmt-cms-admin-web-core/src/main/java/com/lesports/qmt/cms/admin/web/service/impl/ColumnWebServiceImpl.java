package com.lesports.qmt.cms.admin.web.service.impl;

import com.lesports.LeConstants;
import com.lesports.qmt.cms.admin.web.service.ColumnWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnVo;
import com.lesports.qmt.cms.admin.web.vo.SimpleColumnPage;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.internal.client.CmsColumnInternalApis;
import com.lesports.qmt.cms.internal.client.CmsColumnPageInternalApis;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
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
 * Time: 16-12-5 : 下午6:53
 */
@Service("columnWebService")
public class ColumnWebServiceImpl implements ColumnWebService {
    private final String URL_PREFIX = "http://www.lesports.com";

    @Override
    public ColumnVo findOne(Long id) {
        ColumnVo vo = new ColumnVo(CmsColumnInternalApis.getColumnById(id));
        fillPagesToColumn(vo);
        return vo;
    }

    @Override
    public boolean delete(Long id) {
        return CmsColumnInternalApis.deleteColumn(id);
    }

    @Override
    public Long saveWithId(ColumnVo entity) {
        Column model = entity.toModel();
        model.setFullPath(buildFullPath(model));
        return CmsColumnInternalApis.saveColumn(model);
    }

    private void fillPagesToColumn(ColumnVo entity) {
        if (entity == null) {
            return;
        }
        List<ColumnPage> pages = CmsColumnPageInternalApis.getColumnPagesByColumnId(entity.getId());
        if (CollectionUtils.isNotEmpty(pages)) {
            for (ColumnPage page : pages) {
                if (entity.getMPage() == null) {
                    if (page.getType() == PageType.MOBILE) {
                        entity.setMPage(SimpleColumnPage.create(page));
                    }
                }
                if (entity.getPcPage() == null) {
                    if (page.getType() == PageType.PC) {
                        entity.setPcPage(SimpleColumnPage.create(page));
                    }
                }
                if (entity.getDummyPage() == null) {
                    if (page.getType() == PageType.DUMMY) {
                        entity.setDummyPage(SimpleColumnPage.create(page));
                    }
                }
            }
        }
    }

    /**
     * 获取完成的url访问路径
     *
     * @param column
     * @return
     */
    private String buildFullPath(Column column) {
        Column child = column;
        if (child != null && LeNumberUtils.toLong(child.getParentId()) > 0) {
            Column parent = CmsColumnInternalApis.getColumnById(child.getParentId());
            if (parent != null) {
                return parent.getFullPath() + LeConstants.SLASH + child.getPath();
            }
        } else {
            return LeConstants.SLASH + child.getPath();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public QmtPage<ColumnVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        Long id = MapUtils.getLong(params, "id", null);
        String name = MapUtils.getString(params, "name", null);
        Long parentId = MapUtils.getLong(params, "parentId", null);
        String path = MapUtils.getString(params, "path", null);
        String sortBy = MapUtils.getString(params, "sortBy", "create_at");
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotEmpty(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        if (parentId != null) {
            query.addCriteria(InternalCriteria.where("parentId").is(parentId));
        }
        if (StringUtils.isNotEmpty(path)) {
            query.addCriteria(InternalCriteria.where("full_path").regex(path));
        }
        long total = CmsColumnInternalApis.countColumnsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(new PageRequest(pageParam.getPage() - 1, pageParam.getCount(), Sort.Direction.DESC, sortBy));

        List<Column> pages = CmsColumnInternalApis.getColumnsByQuery(query);
        if (CollectionUtils.isEmpty(pages)) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<ColumnVo> vos = pages.stream().map(new Function<Column, ColumnVo>() {
            @Override
            public ColumnVo apply(Column column) {
                ColumnVo vo = new ColumnVo(column);
                fillPagesToColumn(vo);
                return vo;
            }
        }).collect(Collectors.toList());


        return new QmtPage(vos, pageParam, total);
    }

}
