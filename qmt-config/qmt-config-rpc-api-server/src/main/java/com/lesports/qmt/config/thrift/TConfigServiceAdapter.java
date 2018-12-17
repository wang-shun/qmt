package com.lesports.qmt.config.thrift;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.config.api.dto.*;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.api.service.TConfigService;
import com.lesports.qmt.config.service.*;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.IdCheckUtils;
import com.lesports.utils.PageUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * Created by denghui on 2016/12/3.
 */
@Service("thriftConfigService")
@Path("/config")
@Produces({APPLICATION_X_THRIFT})
public class TConfigServiceAdapter implements TConfigService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(TConfigServiceAdapter.class);

    @Resource
    private CallerService callerService;
    @Resource
    private DictService dictService;
    @Resource
    private TagService tagService;
    @Resource
    private MenuService menuService;
    @Resource
    private ActivityService activityService;
    @Resource
    private SuggestService suggestService;

    @Override
    public TCaller getTCallerById(long id) throws TException {
        try {
            return callerService.getTCallerById(id);
        } catch (Exception e) {
            LOG.error("fail to getTCallerById. id:{}", id, e);
        }
        return null;
    }


    @Override
    public List<Long> getTCallerBySplatId(long splatId) throws TException {
        try {
            return callerService.getTCallerBySplatId(splatId);
        } catch (Exception e) {
            LOG.error("fail to getTCallersByIds. splatId:{}", splatId, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TDictEntry getTDictEntryById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return dictService.getTDictEntryById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDictEntryById. id:{}, caller:{}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TDictEntry> getTDictEntriesByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return dictService.getTDictEntriesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDictEntriesByIds. ids:{}, caller:{}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTDictEntryIds4SimpleSearch(GetDictEntriesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return dictService.getDictEntryIds4SimpleSearch(p, pageable);
        } catch (Exception e) {
            LOG.error("fail to getDictEntryIds4SimpleSearch. p:{}, page:{}, caller:{}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public DictEntryTopType getDictEntryTopType(long id) throws TException {
        try {
            return dictService.getDictEntryTopType(id);
        } catch (Exception e) {
            LOG.error("fail to getDictEntryTopType. id:{}", id, e);
        }
        return null;
    }

    @Override
    public TTag getTTagById(long id) throws TException {
        try {
            return tagService.getTTagById(id);
        } catch (Exception e) {
            LOG.error("fail to getTTagById. id:{}", id, e);
        }
        return null;
    }

    @Override
    public TTag getTTagByName(String name) throws TException {
        try {
            return tagService.getTTagByName(name);
        } catch (Exception e) {
            LOG.error("fail to getTTagByName. name:{}", name, e);
        }
        return null;
    }

    @Override
    public List<TTag> getTTagsByIds(List<Long> ids) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            return tagService.getTTagsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTTagsByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TMenu getTMenuById(long id) throws TException {
        try {
            return menuService.getTMenuById(id);
        } catch (Exception e) {
            LOG.error("fail to getTMenuById. id:{}", id, e);
        }
        return null;
    }

    @Override
    public TActivity getTActivityById(long id) throws TException {
        try {
            return activityService.getTActivityById(id);
        } catch (Exception e) {
            LOG.error("fail to getTActivityById. id:{}", id, e);
        }
        return null;
    }

    @Override
    public List<TActivity> getTActivitiesByIds(List<Long> ids) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            return activityService.getTActivitiesByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTActivitiesByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getActivityIdsByEid(long eid) throws TException {
        try {
            return activityService.getActivityIdsByEid(eid);
        } catch (Exception e) {
            LOG.error("fail to getActivityIdsByEid. eid:{}", eid, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTActivityIds4SimpleSearch(GetActivitiesParam p, PageParam page) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            return activityService.getTActivityIds4SimpleSearch(p, pageable);
        } catch (Exception e) {
            LOG.error("fail to getTActivityIds4SimpleSearch. p:{}, page:{}", p, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTSuggestIds(PageParam page) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            return suggestService.getTSuggestIds(pageable);
        } catch (Exception e) {
            LOG.error("fail to getTSuggestIds. page:{}", page, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<TSuggest> getTSuggestsByIds(List<Long> ids) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            return suggestService.getTSuggestsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTSuggestsByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }
}
