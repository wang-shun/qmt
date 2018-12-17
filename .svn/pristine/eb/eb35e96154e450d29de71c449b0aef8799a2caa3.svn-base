package com.lesports.qmt.resource.converter.support;

import com.google.common.collect.Maps;
import com.lesports.qmt.resource.converter.*;
import com.lesports.qmt.resource.cvo.*;

import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-28 : 下午3:04
 */
public class CvoConverterBuilders {

    private static Map<Class, CvoConverter> converterMap = Maps.newHashMap();

    static {
        converterMap.put(NewsCvo.class, new NewsCvoConverter());
        converterMap.put(EpisodeCvo.class, new EpisodeCvoConverter());
        converterMap.put(PostCvo.class, new PostCvoConverter());
        converterMap.put(H5Cvo.class, new H5CvoConverter());
        converterMap.put(TvContentCvo.class, new TvContentCvoConverter());
        converterMap.put(TopicCvo.class, new TopicCvoConverter());
        converterMap.put(VideoCvo.class, new VideoCvoConverter());
        converterMap.put(SubjectCvo.class, new SubjectCvoConverter());
        converterMap.put(RankCvo.class, new RankCvoConverter());
        converterMap.put(ProgramAlbumCvo.class, new ProgramAlbumCvoConverter());
    }

    public static <T extends BaseCvo> CvoConverter getConverter(Class<T> clazz) {
        return converterMap.get(clazz);
    }
}
