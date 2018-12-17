package com.lesports.qmt.cms.client;

import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.api.service.TCmsService;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiangbo
 * @since 2016.12.02
 */
public class QmtCmsApis {

    private static final Logger LOG = LoggerFactory.getLogger(QmtCmsApis.class);

    private static TCmsService.Iface cmsService = new ClientBuilder<TCmsService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TCmsService.Iface.class).build();

    /**
     * 根据模板id获取模板详情
     * @param id
     * @return
     * @throws TException
     */
    public static TLayout getTLayoutById(long id){
        try {
            return cmsService.getTLayoutById(id);
        } catch (Exception e) {
            LOG.error("fail to getTLayoutById. id : {}", id, e);
        }
        return null;
    }

    /**
     * 根据模板路径获取模板详情
     * @param path
     * @return
     * @throws TException
     */
    public static TLayout getTLayoutByPath(String path){
        try {
            return cmsService.getTLayoutByPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTLayoutByPath. path : {}", path, e);
        }
        return null;
    }

    /**
     * 根据组件id获取组件详情
     * @param id
     * @return
     * @throws TException
     */
    public static TWidget getTWidgetById(long id){
        try {
            return cmsService.getTWidgetById(id);
        } catch (Exception e) {
            LOG.error("fail to getTWidgetById. id : {}", id, e);
        }
        return null;
    }

    /**
     * 根据组件路径获取组件详情
     * @param path
     * @return
     * @throws TException
     */
    public static TWidget getTWidgetByPath(String path){
        try {
            return cmsService.getTWidgetByPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTWidgetByPath. path : {}", path, e);
        }
        return null;
    }

    /**
     * 根据栏目id获取栏目详情
     * @param id
     * @return
     * @throws TException
     */
    public static TColumn getTColumnById(long id){
        try {
            return cmsService.getTColumnById(id);
        } catch (Exception e) {
            LOG.error("fail to getTColumnById. id : {}", id, e);
        }
        return null;
    }

    /**
     * 根据栏目的url路径获取栏目详情
     * @param path
     * @return
     * @throws TException
     */
    public static TColumn getTColumnByFullPath(String path){
        try {
            return cmsService.getTColumnByFullPath(path);
        } catch (Exception e) {
            LOG.error("fail to getTColumnByFullPath. path : {}", path, e);
        }
        return null;
    }

    /**
     * 根据栏目页id获取栏目页详情
     * @param id
     * @return
     * @throws TException
     */
    public static TColumnPage getTColumnPageById(long id){
        try {
            return cmsService.getTColumnPageById(id);
        } catch (Exception e) {
            LOG.error("fail to getTColumnPageById. id : {}", id, e);
        }
        return null;
    }
}
