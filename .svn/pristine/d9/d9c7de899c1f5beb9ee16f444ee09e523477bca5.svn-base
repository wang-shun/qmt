package com.lesports.qmt.config.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.api.dto.*;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.api.service.TConfigService;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 配置Apis
 * Created by denghui on 2016/10/24.
 */
public class QmtConfigApis {

    private static final Logger LOG = LoggerFactory.getLogger(QmtConfigApis.class);

    private static TConfigService.Iface configService = new ClientBuilder<TConfigService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TConfigService.Iface.class).build();

    public static TCaller getTCallerById(long id) {
        try {
            return configService.getTCallerById(id);
        } catch (Exception e) {
            LOG.error("fail to getTCallerById. id:{", id, e);
        }
        return null;
    }

    public static Platform getPlatformOfCaller(long callerId) {
        try {
            TCaller caller = configService.getTCallerById(callerId);
            if (caller != null) {
                return caller.getPlatform();
            }
        } catch (Exception e) {
            LOG.error("fail to getPlatformOfCaller. callerId : {}", callerId, e);
        }
        return null;
    }


    public static List<Long> getTCallerBySplatId(long splatId) {
        try {
            return configService.getTCallerBySplatId(splatId);
        } catch (Exception e) {
            LOG.error("fail to getTCallersByIds. splatId:{}", splatId, e);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 根据字典id获取字典
     *
     * @param id
     * @param caller
     * @return
     */
    public static TDictEntry getTDictEntryById(long id, CallerParam caller) {
        try {
            return configService.getTDictEntryById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDictEntryById. id:{}, caller:{}", id, caller, e);
        }
        return null;
    }

    /**
     * 批量获取字典
     *
     * @param ids
     * @param caller
     * @return
     */
    public static List<TDictEntry> getTDictEntriesByIds(List<Long> ids, CallerParam caller) {
        try {
            return configService.getTDictEntriesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDictEntriesByIds. ids:{}, caller:{}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 获取字典所属的顶级分类
     * @param id
     * @return
     */
    public static DictEntryTopType getDictEntryTopType(long id) {
        try {
            return configService.getDictEntryTopType(id);
        } catch (Exception e) {
            LOG.error("fail to getDictEntryTopType. id:{}", id, e);
        }
        return null;
    }

    /**
     * 根据条件查找字典
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    public static List<TDictEntry> getDictEntrys4SimpleSearch(GetDictEntriesParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = configService.getTDictEntryIds4SimpleSearch(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTDictEntriesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getDictEntrys4SimpleSearch. p:{}, page:{}, caller:{}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 通过id获取标签
     * @param id
     * @return
     */
    public static TTag getTTagById(long id){
        try {
            return configService.getTTagById(id);
        } catch (Exception e) {
            LOG.error("fail to getTTagById. id:{}", id, e);
        }
        return null;
    }

    /**
     * 通过名字获取标签
     * @param name
     * @return
     */
    public static TTag getTTagByName(String name){
        try {
            return configService.getTTagByName(name);
        } catch (Exception e) {
            LOG.error("fail to getTTagByName. name:{}", name, e);
        }
        return null;
    }

    /**
     * 通过id批量获取标签
     * @param ids
     * @return
     */
    public static List<TTag> getTTagsByIds(List<Long> ids){
        try {
            return configService.getTTagsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTTagsByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 通过id获取菜单
     * @param id
     * @return
     */
    public static TMenu getTMenuById(long id){
        try {
            return configService.getTMenuById(id);
        } catch (Exception e) {
            LOG.error("fail to getTMenuById. id:{}", id, e);
        }
        return null;
    }

    /**
     * 获取单场推广位/条款
     * @param id
     * @return
     */
    public static TActivity getTActivityById(long id) {
        try {
            return configService.getTActivityById(id);
        } catch (Exception e) {
            LOG.error("fail to getTActivityById. id:{}", id, e);
        }
        return null;
    }

    /**
     * 批量获取单场推广位/条款
     *
     * @param ids
     * @return
     */
    public static List<TActivity> getTActivitiesById(List<Long> ids) {
        try {
            return configService.getTActivitiesByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTActivitiesById. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TActivity> getTActivitiesByEid(long eid) {
        try {
            List<Long> ids = configService.getActivityIdsByEid(eid);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return configService.getTActivitiesByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTActivitiesByEid. eid:{}", eid, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<Long> getTActivityIds4SimpleSearch(GetActivitiesParam p, PageParam page) {
        try {
            return configService.getTActivityIds4SimpleSearch(p, page);
        } catch (Exception e) {
            LOG.error("fail to getActivityIds4SimpleSearch. p:{}, page:{}", p, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TSuggest> getTSuggestIds(PageParam page){
        try {
            List<Long> ids =  configService.getTSuggestIds(page);
            if(CollectionUtils.isEmpty(ids)){
                return Collections.EMPTY_LIST;
            }
            return getTSuggestsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTSuggests. page:{},caller:{}", page, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TSuggest> getTSuggestsByIds(List<Long> ids){
        try {
            return configService.getTSuggestsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTSuggestsByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

}
