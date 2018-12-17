package com.lesports.qmt.config.converter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.config.converter.support.AbstractConfigTDtoConverter;
import com.lesports.qmt.config.model.Menu;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by denghui on 2016/11/11.
 */
@Component("menuConverter")
public class TMenuConverter extends AbstractConfigTDtoConverter<Menu, TMenu> {

    /**
     * 排序用的function
     */
    private static enum ITEM_SORT_FUNCTIONS implements Function<Menu.MenuItem, Integer> {
        GET_LEVEL {
            @Override
            public Integer apply(Menu.MenuItem item) {
                return item.getLevel();
            }
        },
        GET_ORDER {
            @Override
            public Integer apply(Menu.MenuItem item) {
                return item.getOrder();
            }
        }
    }

    private final Ordering<Menu.MenuItem> itemOrdering = Ordering.natural()
            .onResultOf(ITEM_SORT_FUNCTIONS.GET_LEVEL)
            .compound(Ordering.natural().onResultOf(ITEM_SORT_FUNCTIONS.GET_ORDER));

    @Override
    protected void fillDto(TMenu dto, Menu model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        if(CollectionUtils.isNotEmpty(model.getItems())){
            List<Menu.MenuItem> sortedItems = itemOrdering.sortedCopy(model.getItems());
            List<TMenuItem> items = Lists.newArrayList();
            for(Menu.MenuItem item : sortedItems){
                if(item.getLevel() <= 1){
                    items.add(convertToTMenuItem(item));
                }else{
                    TMenuItem parentItem = findParentItem(items, item.getParentIndex(), item.getLevel()-1);
                    if(parentItem != null){
                        parentItem.addToSubItems(convertToTMenuItem(item));
                    }
                }
            }
            dto.setItems(items);
        }
    }

    @Override
    protected TMenu createEmptyDto() {
        return new TMenu();
    }

    private TMenuItem findParentItem(List<TMenuItem> items, long parentIndex, int parentLevel){
        for(TMenuItem item : items){
            if(item.getIndex() == parentIndex && item.getLevel() == parentLevel){
                return item;
            }
        }
        return null;
    }

    private TMenuItem convertToTMenuItem(Menu.MenuItem item){
        TMenuItem tMenuItem = new TMenuItem();
        tMenuItem.setIndex(item.getIndex());
        tMenuItem.setLevel(item.getLevel());
        tMenuItem.setResourceId(LeNumberUtils.toLong(item.getResourceId()));
        tMenuItem.setNewsResourceId(LeNumberUtils.toLong(item.getNewsResourceId()));
        tMenuItem.setNewsAutoResourceId(LeNumberUtils.toLong(item.getNewsAutoResourceId()));
        tMenuItem.setMatchResourceId(LeNumberUtils.toLong(item.getMatchResourceId()));
        tMenuItem.setCarouselResourceId(LeNumberUtils.toLong(item.getCarouselResourceId()));
        tMenuItem.setResourceType(item.getResourceType());
        tMenuItem.setName(item.getName());
        if (MapUtils.isNotEmpty(item.getImages())) {
            tMenuItem.setImageUrl(Maps.transformValues(item.getImages(), IMAGE_URL_FUNCTION));
        }
        //如果数据字段不存在就默认给0
        tMenuItem.setIsHot(LeNumberUtils.toBoolean(item.getHot()));
        //如果数据字段不存在就默认给0
        tMenuItem.setIsNew(LeNumberUtils.toBoolean(item.getNew()));
        //菜单的排序字段
        tMenuItem.setOrder(LeNumberUtils.toInt(item.getOrder()));
        tMenuItem.setTeamId(LeNumberUtils.toLong(item.getTeamId()));
        tMenuItem.setCampCategoryId(item.getCampCategoryId());
        tMenuItem.setCampId(item.getCampId());
        tMenuItem.setIsRecommend(LeNumberUtils.toBoolean(item.getRecommend()));
        tMenuItem.setToplistCid(LeNumberUtils.toLong(item.getToplistCid()));
        tMenuItem.setMatchCid(LeNumberUtils.toLong(item.getMatchCid()));
        tMenuItem.setUrl(item.getUrl());
        tMenuItem.setMUrl(item.getmUrl());
        tMenuItem.setRecommendOrder(LeNumberUtils.toInt(item.getRecommendOrder()));
        tMenuItem.setIsForceRecommend(LeNumberUtils.toBoolean(item.getForceRecommend()));
        tMenuItem.setForceRecommendOrder(LeNumberUtils.toInt(item.getForceRecommendOrder()));
        tMenuItem.setScheduleType(item.getScheduleType());
        tMenuItem.setPubChannels(item.getPubChannels());
        tMenuItem.setIsTeamData(LeNumberUtils.toBoolean(item.getTeamData()));
        tMenuItem.setIsPlayerData(LeNumberUtils.toBoolean(item.getPlayerData()));
        return tMenuItem;
    }

}
