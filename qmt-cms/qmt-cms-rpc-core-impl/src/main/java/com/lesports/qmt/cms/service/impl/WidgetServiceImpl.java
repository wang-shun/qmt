package com.lesports.qmt.cms.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.cache.TWidgetCache;
import com.lesports.qmt.cms.converter.WidgetConverter;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.qmt.cms.repository.WidgetRepository;
import com.lesports.qmt.cms.service.WidgetService;
import com.lesports.qmt.cms.service.support.AbstractCmsService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-11-30 : 下午6:57
 */
@Service("widgetService")
public class WidgetServiceImpl extends AbstractCmsService<Widget, Long> implements WidgetService {

    @Resource
    protected WidgetRepository widgetRepository;

    @Resource
    private WidgetConverter widgetConverter;

    @Resource
    private TWidgetCache widgetCache;

    @Override
    protected MongoCrudRepository<Widget, Long> getInnerRepository() {
        return widgetRepository;
    }

    @Override
    public Widget findOne(Long id) {
        if (null == id) return null;
        return widgetRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Widget entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Widget entity) {
        entity.setId(LeIdApis.nextId(IdType.CMS_WIDGET));
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        entity.setDeleted(false);
        entity.setCreateAt(now);
        entity.setUpdateAt(now);
        return widgetRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Widget entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Widget entity) {

        return widgetRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Widget entity) {
        widgetCache.delete(entity.getId());
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        Update up = update("deleted", true);
        return widgetRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Widget entity) {
        widgetCache.delete(entity.getId());
        return true;
    }

    @Override
    protected Widget doFindExistEntity(Widget entity) {
        return widgetRepository.findOne(entity.getId());
    }

    @Override
    public TWidget getTWidgetById(long id) {
        Widget model = widgetRepository.findOne(id);
        if(model == null){
            LOG.warn("fail to getTWidgetById, entity no exist! id : {}", id);
            return null;
        }
        TWidget dto = widgetConverter.toDto(model);
        return dto;
    }

    @Override
    public TWidget getTWidgetByPath(String path) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("path").is(path));
        Long id = widgetRepository.findOneIdByQuery(q);
        if(LeNumberUtils.toLong(id) <= 0){
            LOG.warn("fail to getTWidgetByPath, entity no exist! path : {}", path);
            return null;
        }
        TWidget widget = widgetCache.findOne(id);
        if(widget == null){
            widget = getTWidgetById(id);
            widgetCache.save(widget);
        }
        return widget;
    }
}
