package com.lesports.qmt.sbd.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by zhonglin on 2017/1/3.
 */
public class Constants {

    public static final BiMap<Long, Long> DICT_EVENT_ID_MAP = HashBiMap.create();

    static {
        //进球
        DICT_EVENT_ID_MAP.put(20001L, 20001L);
        //红牌
        DICT_EVENT_ID_MAP.put(29001L, 29001L);
        //黄牌
        DICT_EVENT_ID_MAP.put(35001L, 35001L);
        //换人
        DICT_EVENT_ID_MAP.put(100037001L, 100037001L);

    }
}
