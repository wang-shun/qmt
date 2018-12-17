package com.lesports.qmt.cms.thrift;

import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.api.service.TCmsService;
import com.lesports.qmt.cms.cache.TColumnPageCache;
import com.lesports.qmt.cms.service.ColumnPageService;
import com.lesports.qmt.cms.service.ColumnService;
import com.lesports.qmt.cms.service.LayoutService;
import com.lesports.qmt.cms.service.WidgetService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: ellios
 * Time: 16-12-6 : 下午3:02
 */
@Service("thriftCmsService")
public class TCmsServiceAdapter implements TCmsService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TCmsServiceAdapter.class);

    @Resource
    private LayoutService layoutService;

    @Resource
    private WidgetService widgetService;

    @Resource
    private ColumnService columnService;

    @Resource
    private ColumnPageService columnPageService;

    @Resource
    private TColumnPageCache columnPageCache;

    @Override
    public TLayout getTLayoutById(long id) throws TException {
        try {
            return layoutService.getTLayoutById(id);
        } catch (Exception e) {
            LOG.error("fail to getTLayoutById. id : {}", id, e);
        }
        return null;
    }

    @Override
    public TLayout getTLayoutByPath(String path) throws TException {
        try {
            return layoutService.getTLayoutByPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTLayoutByPath. path : {}", path, e);
        }
        return null;
    }

    @Override
    public TWidget getTWidgetById(long id) throws TException {
        try {
            return widgetService.getTWidgetById(id);
        } catch (Exception e) {
            LOG.error("fail to getTWidgetById. id : {}", id, e);
        }
        return null;
    }

    @Override
    public TWidget getTWidgetByPath(String path) throws TException {
        try {
            return widgetService.getTWidgetByPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTWidgetByPath. path : {}", path, e);
        }
        return null;
    }

    @Override
    public TColumn getTColumnById(long id) throws TException {
        try {
            return columnService.getTColumnById(id);
        } catch (Exception e) {
            LOG.error("fail to getTColumnById. id : {}", id, e);
        }
        return null;
    }

    @Override
    public TColumn getTColumnByFullPath(String path) throws TException {
        try {
            return columnService.getTColumnByFullPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTColumnByFullPath. path : {}", path, e);
        }
        return null;    }

    @Override
    public TColumnPage getTColumnPageById(long id) throws TException {
        try {
            TColumnPage columnPage = columnPageCache.findOne(id);
            if(columnPage == null){
                columnPage = columnPageService.getTColumnPageById(id);
                columnPageCache.save(columnPage);
            }
            return columnPage;
        } catch (Exception e) {
            LOG.error("fail to getTColumnPageById. id : {}", id, e);
        }
        return null;
    }
}
