package com.lesports.qmt.cms.admin.web.service.impl;

import com.google.common.collect.Maps;
import com.lesports.id.api.IdType;
import com.lesports.qmt.cms.admin.web.param.ColumnPageCopyParam;
import com.lesports.qmt.cms.admin.web.service.ColumnPageWebService;
import com.lesports.qmt.cms.admin.web.service.ColumnWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnPageVo;
import com.lesports.qmt.cms.internal.client.CmsColumnPageInternalApis;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.msg.core.*;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: ellios
 * Time: 16-12-2 : 下午2:53
 */
@Service("columnPageWebService")
public class ColumnPageWebServiceImpl implements ColumnPageWebService {

    private static final Logger LOG = LoggerFactory.getLogger(ColumnPageWebServiceImpl.class);

    @Resource
    private ColumnWebService columnWebService;

    @Override
    public Long saveWithId(ColumnPageVo entity) {
        return CmsColumnPageInternalApis.saveColumnPage(entity.toModel());
    }

    @Override
    public ColumnPageVo findOne(Long id) {
        return new ColumnPageVo(CmsColumnPageInternalApis.getColumnPageById(id));
    }

    @Override
    public boolean delete(Long id) {
        return CmsColumnPageInternalApis.deleteColumnPage(id);
    }

    @Override
    public long copyColumnPage(ColumnPageCopyParam param) {
        if(StringUtils.isEmpty(param.getName())){
            throw new LeWebApplicationException("illegal param", LeStatus.BAD_REQUEST);
        }
        ColumnPage cp = CmsColumnPageInternalApis.getColumnPageById(param.getFromPageId());
        if(cp == null){
            LOG.error("fail to copyColumnPage. fromPageId : {}, name : {}", param.getFromPageId(), param.getName());
        }
        cp.setId(-1L);
        cp.setName(param.getName());
        cp.setColumnId(param.getColumnId() != null ? param.getColumnId() : -1);
        cp.setCreateAt(null);
        return CmsColumnPageInternalApis.saveColumnPage(cp);
    }
    @Override
    public void copyColumnPage(Long srcColumnId,Long desColumnId,String name){
        Map<String,Object> params = Maps.newHashMap();
        params.put("columnId",srcColumnId);
        List<ColumnPage> columnPageList = findByParam(params);
        for(ColumnPage columnPage : columnPageList){
            columnPage.setId(-1L);
            columnPage.setColumnId(desColumnId);
            columnPage.setCreateAt(null);
            columnPage.setUpdateAt(null);
            columnPage.setName(name);
            CmsColumnPageInternalApis.saveColumnPage(columnPage);
        }
    }

    @Override
    public QmtPage<ColumnPageVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        Long id = MapUtils.getLong(params, "id", null);
        String name = MapUtils.getString(params, "name", null);//params.get("name") != null ? params.get("name").toString() : null;
        Long columnId = MapUtils.getLong(params, "columnId", null);
        Long layoutId = MapUtils.getLong(params, "layoutId", null);
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        if (columnId != null) {
            query.addCriteria(InternalCriteria.where("columnId").is(columnId));
        }
        if (layoutId != null) {
            query.addCriteria(InternalCriteria.where("layoutId").is(layoutId));
        }

        long total = CmsColumnPageInternalApis.countNewsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        query.with(new PageRequest(pageParam.getPage() - 1, pageParam.getCount(), Sort.Direction.DESC, "create_at"));

        List<ColumnPage> pages = CmsColumnPageInternalApis.getColumnPagesByQuery(query);
        if(CollectionUtils.isEmpty(pages)){
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<ColumnPageVo> vos = pages.stream().map(new Function<ColumnPage, ColumnPageVo>() {
            @Override
            public ColumnPageVo apply(ColumnPage columnPage) {
                return new ColumnPageVo(columnPage);
            }
        }).collect(Collectors.toList());


        return new QmtPage(vos, pageParam, total);
    }

    @Override
    public List<ColumnPage> findByParam(Map<String, Object> params) {
        InternalQuery query = new InternalQuery();
        Long id = MapUtils.getLong(params, "id", null);
        String name = MapUtils.getString(params, "name", null);//params.get("name") != null ? params.get("name").toString() : null;
        Long columnId = MapUtils.getLong(params, "columnId", null);
        Long layoutId = MapUtils.getLong(params, "layoutId", null);
        if (id != null) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        if (columnId != null) {
            query.addCriteria(InternalCriteria.where("columnId").is(columnId));
        }
        if (layoutId != null) {
            query.addCriteria(InternalCriteria.where("layoutId").is(layoutId));
        }
        List<ColumnPage> pages = CmsColumnPageInternalApis.getColumnPagesByQuery(query);
        return pages;

    }

    /**
     * 发布栏目页面，给消息中心发送消息，删除页面缓存
     * @param columnPageId
     */
    @Override
    public void publish(Long columnPageId) {
        ColumnPage columnPage = findOne(columnPageId);
        if(columnPage == null || columnPage.getColumnId() <=0){
            return;
        }
        Column column = columnWebService.findOne(columnPage.getColumnId());
        if(column == null || StringUtils.isBlank(column.getFullPath())){
            return;
        }
        String url = column.getFullPath();
        LOG.info("publish page  columnPageId={},url={}",columnPage,url);
        MessageContent messageContent = new MessageContent();
        messageContent.setKey(url);
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(columnPageId)
                .setIdType(IdType.CMS_PAGE)
                .setBusinessTypes(ActionType.DELETE_CACHE, Arrays.asList(BusinessType.CMS_CBASE_DELETE))
                .setContent(messageContent)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
    }
}
