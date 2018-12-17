package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.MenuResourceType;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.MenuVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by ruiyuansheng on 2016/8/24.
 */
@Component("appMenuVoCreater")
public class AppMenuVoCreater {

    public MenuVo createMenuVo(TMenu tMenu,CallerParam callerParam) {
        MenuVo menuVo = new MenuVo();
        menuVo.setId(tMenu.getId());
        if(CollectionUtils.isNotEmpty(tMenu.getItems())){
            List<MenuVo.MenuItemVo> items = Lists.newArrayList();
            for(TMenuItem item : tMenu.getItems()){
                MenuVo.MenuItemVo menuItemVo = convertToMenuItemVo(item, callerParam);
                if(CollectionUtils.isNotEmpty(item.getSubItems())){
                    menuItemVo.setSubItems(convertToMenuItemVos(item.getSubItems(), callerParam));
                }
                if(null != menuItemVo) {
                    items.add(menuItemVo);
                }
            }
            menuVo.setItems(items);
        }

        return menuVo;
    }

    public List<MenuVo.MenuItemVo> convertToMenuItemVos(List<TMenuItem> items,CallerParam callerParam){
        if(CollectionUtils.isEmpty(items)){
            return Collections.EMPTY_LIST;
        }
        List<MenuVo.MenuItemVo> menuItemVos = Lists.newArrayList();
        for(TMenuItem tMenuItem : items){
            menuItemVos.add(convertToMenuItemVo(tMenuItem,callerParam));
        }

        return menuItemVos;
    }

    private MenuVo.MenuItemVo convertToMenuItemVo(TMenuItem item, CallerParam callerParam){
        MenuVo.MenuItemVo menuItemVo = new MenuVo.MenuItemVo();
        menuItemVo.setIndex(item.getIndex());
        menuItemVo.setLevel(item.getLevel());

		Long resourceId = Constants.INDEX_2_RESOURCEID.get(item.getIndex());
		if (resourceId != null) {
			menuItemVo.setResourceId(resourceId);
		} else {
			if(item.getResourceType() == MenuResourceType.COMMON){//如果不是旧的菜单index,并且是普通的菜单,则用新闻的资源位ID来传递
				menuItemVo.setResourceId(item.getNewsResourceId());
			}
		}
		if(resourceId != null && item.getResourceType() == MenuResourceType.COMMON){//如果是旧的菜单ID,则resourceType为之前的保持一致为 TAG
			menuItemVo.setResourceType(MenuResourceType.TAG.getValue());
		} else if (menuItemVo.getResourceId() == 0 && item.getMatchCid() > 0) {//原来的赛事菜单,如 1018
			menuItemVo.setResourceType(MenuResourceType.COMPETITION.getValue());
			menuItemVo.setResourceId(item.getMatchCid());
		} else {
			menuItemVo.setResourceType(item.getResourceType().getValue());
		}

        menuItemVo.setName(item.getName());
        if(null != item.getImageUrl()) {
            menuItemVo.setImageUrl(item.getImageUrl().get("11"));
        }
        //如果数据字段不存在就默认给0
        menuItemVo.setIsHot(item.isIsHot()?1:0);
        //如果数据字段不存在就默认给0
        menuItemVo.setIsNew(item.isIsNew()?1:0);
        //菜单的排序字段
        menuItemVo.setOrder(item.getOrder());
        //TV端比赛大厅是否推荐
        menuItemVo.setIsSuggest(item.isIsRecommend()?1:0);
        menuItemVo.setIsMatch(item.getMatchCid() > 0 ? 1 : 0);
        menuItemVo.setIsTopList(item.getToplistCid() > 0 ? 1: 0);
        menuItemVo.setCampCategoryId(item.getCampCategoryId());
        menuItemVo.setCampId(item.getCampId());
        menuItemVo.setIsRecommend(item.isIsRecommend()?1:0);
        menuItemVo.setCid(LeNumberUtils.toLong(item.getMatchCid()));
        menuItemVo.setH5Url(item.getUrl());
        menuItemVo.setRecommendOrder(item.getRecommendOrder());
        menuItemVo.setMenuRecommendType(item.getForceRecommendOrder());
        menuItemVo.setForceRecommendOrder(item.isIsForceRecommend()?1:0);
		return menuItemVo;
    }



}
