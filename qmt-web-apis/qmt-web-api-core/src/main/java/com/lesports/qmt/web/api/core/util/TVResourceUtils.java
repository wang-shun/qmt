package com.lesports.qmt.web.api.core.util;

import com.google.common.collect.Maps;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.utils.LeProperties;

import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/12/23.
 */
public class TVResourceUtils {

    public static final Long HOMEPAGE_MENU_ID = LeProperties.getLong("homepage.menu.id",3401018L);

    public static final Long HOMEPAGE_RECOMMENDS = LeProperties.getLong("homepage.recommends.resourceid",8801052L);
    public static final Long HOMEPAGE_TOPICS = LeProperties.getLong("homepage.topics.resourceid",9601052L);
    public static final Long HOMEPAGE_ENTRANCES = LeProperties.getLong("homepage.entrances.resourceid",9301052L);
    public static final Long HOMEPAGE_NEWS = LeProperties.getLong("homepage.news.resourceid",9501052L);
    public static final Long HOMEPAGE_LIVES = LeProperties.getLong("homepage.lives.resourceid",9401052L);

    public static final Long HOMEPAGE_RECOMMENDS_TCL = LeProperties.getLong("homepage.recommends.tcl.resourceid",8801052L);
    public static final Long HOMEPAGE_TOPICS_TCL = LeProperties.getLong("homepage.topics.tcl.resourceid",9601052L);
    public static final Long HOMEPAGE_ENTRANCES_TCL = LeProperties.getLong("homepage.entrances.tcl.resourceid",9301052L);
    public static final Long HOMEPAGE_NEWS_TCL = LeProperties.getLong("homepage.news.tcl.resourceid",9501052L);
    public static final Long HOMEPAGE_LIVES_TCL = LeProperties.getLong("homepage.lives.tcl.resourceid",9401052L);

    public static final Long DESKTOP_RECOMMENDS = LeProperties.getLong("desktop.recommends.resourceid",309901052L);
    public static final Long DESKTOP_TOPICS = LeProperties.getLong("desktop.topics.resourceid",309901052L);
    public static final Long DESKTOP_ENTRANCES = LeProperties.getLong("desktop.entrances.resourceid",309901052L);
    public static final Long DESKTOP_CAROUSELS = LeProperties.getLong("desktop.carousels.resourceid",309901052L);
    public static final Long DESKTOP_AGGREGATIONS = LeProperties.getLong("desktop.aggregations.resourceid",309901052L);

    public static final Long DESKTOP_RECOMMENDS_TCL = LeProperties.getLong("desktop.recommends.tcl.resourceid",309901052L);
    public static final Long DESKTOP_TOPICS_TCL = LeProperties.getLong("desktop.topics.tcl.resourceid",309901052L);
    public static final Long DESKTOP_ENTRANCES_TCL = LeProperties.getLong("desktop.entrances.tcl.resourceid",309901052L);
    public static final Long DESKTOP_CAROUSELS_TCL = LeProperties.getLong("desktop.carousels.tcl.resourceid",309901052L);
    public static final Long DESKTOP_AGGREGATIONS_TCL = LeProperties.getLong("desktop.aggregations.tcl.resourceid",309901052L);

    public static final Long MATCHHALL_ENTITIES = LeProperties.getLong("macthhall.entities.resourceid",309901052L);

    public static final Long DESKTOP_BACKGROUND_IMAGE = LeProperties.getLong("desktop.background.image",313601052L);
    public static final Long EXIT_IMAGE = LeProperties.getLong("exit.image",313601052L);
    public static final Long PLAY_EXIT_IMAGE = LeProperties.getLong("play.exit.image",313601052L);

    public static final Map<ResourceItemType,Integer> RESOURCE_ITEM_TYPE_INTEGER_MAP = Maps.newHashMap();

    public static final Map<ResourceItemType,Integer> ACTION_TYPE_MAP = Maps.newHashMap();

    public static final Map<ResourceItemType,Integer> MODEL_MAP = Maps.newHashMap();

    static {
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.VIDEO,6);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.EPISODE,5);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.SUBJECT,2);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.IMAGE_ALBUM,8);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.TV_MODULE,0);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.TV_CHANNEL,1);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.PROGRAM_ALBUM,3);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.CAROUSEL,9);
        RESOURCE_ITEM_TYPE_INTEGER_MAP.put(ResourceItemType.H5,10);

        ACTION_TYPE_MAP.put(ResourceItemType.APP_SHOP_TOPIC,1);
        ACTION_TYPE_MAP.put(ResourceItemType.GAME_CENTER_DETAIL,2);
        ACTION_TYPE_MAP.put(ResourceItemType.GAME_CENTER_TOPIC,3);

        MODEL_MAP.put(ResourceItemType.HOME_PAGE,0);
        MODEL_MAP.put(ResourceItemType.NEWS,2);
        MODEL_MAP.put(ResourceItemType.TV_CHANNEL,3);
        MODEL_MAP.put(ResourceItemType.COMPETITION,4);
        MODEL_MAP.put(ResourceItemType.PROGRAM_ALBUM,5);


    }




}
