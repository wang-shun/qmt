package com.lesports.qmt.cms.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.client.QmtCmsApis;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.constants.DataType;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangbo
 * @since 2016.10.20
 */
@Controller
@RequestMapping(value = "/render")
public class ColumnPageController {

    private static final Logger LOG = LoggerFactory.getLogger(ColumnPageController.class);

    @RequestMapping(value = "/d/**", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String renderData(@RequestParam Map<String, Object> params, HttpServletRequest request,
                             Model model) {
        try {
            LOG.info("begin to render, params : {}", params);
            //准备data数据
            Map dataMap = prepareData(params, request);
            model.addAllAttributes(dataMap);
            model.addAttribute("alld", JSON.toJSONString(dataMap));
            return "ddata";
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/p/**", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String render(@RequestParam Map<String, Object> params, HttpServletRequest request,
                         Model model) {
        try {
            LOG.info("begin to render, params : {}", params);
            Map dataMap = prepareData(params, request);
            model.addAllAttributes(dataMap);
            String templateInfo = String.valueOf(dataMap.get("pageId"));
            if("MOBILE".equalsIgnoreCase(MapUtils.getString(params, "type"))){
                templateInfo = String.valueOf(dataMap.get("mTemplateUrl"));
            }
            return templateInfo;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String,Object> prepareData(Map<String, Object> params, HttpServletRequest request) {
        TColumn column = getColumn(params,request);
        PageType type = getPageType(params);
        TColumnPage tPage = getColumnPageId(column,type);
        //默认是浏览
        Integer viewType = MapUtils.getInteger(params, "viewType", DataType.VIEW);
        if (tPage == null) {
            throw new LeWebApplicationException("illegal argument!!!!", LeStatus.BAD_REQUEST);
        }
        Map<String,Object> dataMap = null;
        if(type == PageType.PC){
            dataMap = JSON.parseObject(tPage.getData(), HashMap.class);
        }else if(type == PageType.MOBILE){
            dataMap = JSON.parseObject(tPage.getMData(), HashMap.class);
        }
        if (MapUtils.isEmpty(dataMap)) {
            return Maps.newHashMap();
        }
        if(type == PageType.PC){
            pcDataFill(dataMap,viewType);
        }else {
            mDataFill(dataMap,viewType);
        }

        Map<String, String> widgetVersionMap = Maps.newHashMap();
        if(CollectionUtils.isNotEmpty(tPage.getWPaths())){
            for(String wPath : tPage.getWPaths()){
                String version = "1";
                TWidget widget = QmtCmsApis.getTWidgetByPath(wPath);
                if(widget != null){
                    version = widget.getVersion();
                }
                widgetVersionMap.put(wPath, version);
            }
        }
        dataMap.put("resVersion", widgetVersionMap);
        dataMap.put("pageId",tPage.getId());
        Map<String,Object> columnMap = Maps.newHashMap();
        columnMap.put("title",column.getTitle());
        columnMap.put("keyword",column.getKeyword());
        columnMap.put("desc",column.getDesc());
        dataMap.put("column",columnMap);
        dataMap.put("mTemplateUrl",column.getMTemplateUrl());
        return dataMap;
    }

    /**
     * pc站的数据填充
     * @param dataMap
     * @param viewType
     */
    public void pcDataFill(Map<String,Object> dataMap,int viewType){
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String wName = entry.getKey();
            Object wValue = entry.getValue();
            if(wValue instanceof Map){
                Map wData = (Map) entry.getValue();
                if (MapUtils.getIntValue(wData, "dataType") == 1) {
                    fillResourceData(wData, wName, viewType);
                }
                if (MapUtils.getIntValue(wData, "dataType") == 2) {
                    fillMenuData(wData,wName,viewType);
                }
            }
        }
    }

    private void fillMenuData(Map wData,String wName,int viewType){
        //菜单
        Map menuMap = Maps.newHashMap();
        wData.put("data", menuMap);
        long gMenuId = MapUtils.getLongValue(wData, "globalMenuId", -1);
        if(gMenuId <= 0){
            LOG.error("illegal globalMenuId for menu data. name : {}, data : {}", wName, wData);
            return;
        }
        TMenu gMenu = QmtConfigApis.getTMenuById(gMenuId);
        if(gMenu == null){
            LOG.error("illegal globalMenuId. menu no exist. name : {}, data : {}", wName, wData);
            return;
        }
        menuMap.put("global", gMenu);

        long pMenuId = MapUtils.getLongValue(wData, "pageMenuId", -1);
        if(pMenuId <= 0){
            LOG.error("illegal pageMenuId for menu data. name : {}, data : {}", wName, wData);
            return;
        }
        TMenu pMenu = QmtConfigApis.getTMenuById(pMenuId);
        if(pMenu == null){
            LOG.error("illegal pageMenuId. menu no exist. name : {}, data : {}", wName, wData);
            return;
        }
        menuMap.put("page", pMenu);
        String currentHightLightKey = "activeMenuIndex";
        menuMap.put(currentHightLightKey,MapUtils.getString(wData,currentHightLightKey));

    }

    private void fillResourceData(Map wData,String wName,int viewType){
        //资源位
        long resourceId = MapUtils.getLongValue(wData, "rsId", -1);
        if(resourceId <= 0){
            LOG.error("illegal resource for widget data. name : {}, data : {}", wName, wData);
            return;
        }
        PageParam pp = PageUtils.createPageParam(1, MapUtils.getIntValue(wData, "count", LeConstants.DEFAULT_PAGE_SIZE));
        wData.put("data", ComboResourceCreaters.getComboResources(resourceId, pp, null,viewType));
    }

    /**
     * M站的数据填充
     * @param dataMap
     * @param viewType
     */
    public void mDataFill(Map<String,Object> dataMap,int viewType){
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String wName = entry.getKey();
            if(entry.getValue() instanceof List){
                List<Map> listData = (List<Map>)entry.getValue();
                for(Map wData : listData){
                    List<Map> wList = (List)wData.get("wList");
                    for(Map wData1 : wList){
                        if (MapUtils.getIntValue(wData1, "dataType") == 1) {
                            fillResourceData(wData1,wName,viewType);
                        }
                        if (MapUtils.getIntValue(wData1, "dataType") == 2) {
                            fillMenuData(wData1,wName,viewType);
                        }
                    }
                }
            }
        }
    }

   /* private void prepareData(TColumnPage tPage, Map<String, Object> dataMap) {
        if (MapUtils.isEmpty(dataMap)) {
            return;
        }
        Map<String, String> widgetVersionMap = Maps.newHashMap();
        if(CollectionUtils.isNotEmpty(tPage.getWPaths())){
            for(String wPath : tPage.getWPaths()){
                String version = "1";
                TWidget widget = QmtCmsApis.getTWidgetByPath(wPath);
                if(widget != null){
                    version = widget.getVersion();
                }
                widgetVersionMap.put(wPath, version);
            }
        }
        dataMap.put("resVersion", widgetVersionMap);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String wName = entry.getKey();
            Map wData = (Map) entry.getValue();
            if (MapUtils.getIntValue(wData, "dataType") == 1) {
                //资源位
                long resourceId = MapUtils.getLongValue(wData, "rsId", -1);
                if(resourceId <= 0){
                    LOG.error("illegal resource for widget data. name : {}, data : {}", wName, wData);
                    continue;
                }
                PageParam pp = PageUtils.createPageParam(1, MapUtils.getIntValue(wData, "count", LeConstants.DEFAULT_PAGE_SIZE));
//                ComboResource resource = ;
                wData.put("data", ComboResourceCreaters.getComboResources(resourceId, pp, null));
            }
            if (MapUtils.getIntValue(wData, "dataType") == 2) {
                //菜单
                Map<String, TMenu> menuMap = Maps.newHashMap();
                wData.put("data", menuMap);

                long gMenuId = MapUtils.getLongValue(wData, "globalMenuId", -1);
                if(gMenuId <= 0){
                    LOG.error("illegal globalMenuId for menu data. name : {}, data : {}", wName, wData);
                    continue;
                }
                TMenu gMenu = QmtConfigApis.getTMenuById(gMenuId);
                if(gMenu == null){
                    LOG.error("illegal globalMenuId. menu no exist. name : {}, data : {}", wName, wData);
                    continue;
                }
                menuMap.put("global", gMenu);

                long pMenuId = MapUtils.getLongValue(wData, "pageMenuId", -1);
                if(pMenuId <= 0){
                    LOG.error("illegal pageMenuId for menu data. name : {}, data : {}", wName, wData);
                    continue;
                }
                TMenu pMenu = QmtConfigApis.getTMenuById(gMenuId);
                if(pMenu == null){
                    LOG.error("illegal pageMenuId. menu no exist. name : {}, data : {}", wName, wData);
                    continue;
                }
                menuMap.put("page", pMenu);
            }
        }
        return;
    }*/

    private TColumn getColumn(Map<String, Object> params, HttpServletRequest request){
        Long columnId = MapUtils.getLong(params, "columnId", null);
        String path = MapUtils.getString(params, "path", null);
        TColumn column = null;
        if (columnId != null) {
            //优先处理columnId参数
            column = QmtCmsApis.getTColumnById(columnId);
        }
        if (column == null) {
            //处理path参数
            if (StringUtils.isEmpty(path)) {
                //uri作为path
                path = request.getRequestURI();
            }
            column = QmtCmsApis.getTColumnByFullPath(path);
        }
        if (column == null) {
            LOG.error("column is null columnId={},path={}",columnId,path);
            throw new LeWebApplicationException("illegal argument!!!!", LeStatus.BAD_REQUEST);

        }
        return column;
    }
    private PageType getPageType(Map<String, Object> params){
        String typeValue = MapUtils.getString(params, "type", "PC");
        PageType type = PageType.valueOf(typeValue);
        if (type == null) {
            type = PageType.PC;
        }
        return type;
    }

    private TColumnPage getColumnPage(Map<String, Object> params, HttpServletRequest request) {
        return getColumnPageId(getColumn(params,request), getPageType(params));
    }

    private TColumnPage getColumnPageId(TColumn column, PageType type) {
       /* if (type == PageType.MOBILE) {
            return column.getMPage();
        }
        if (type == PageType.DUMMY){
            return column.getDummyPage();
        }*/
        //当前只有pc有页面，M站也是用pc的页面
        return column.getPcPage();
    }


}
