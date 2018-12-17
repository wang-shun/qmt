package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.qmt.web.datacenter.vo.*;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wangjichuan  date:16-12-20 time:18:36
 */
public class DataProcessFactory {
    private static Map<Class,DataProcess> dataProcessMap = new HashMap<>();
    static {
        dataProcessMap.put(NewsVo.class,new NewsDataProcess());
        dataProcessMap.put(EpisodeVo.class,new EpisodeDataProcess());
        dataProcessMap.put(PostVo.class,new PostDataProcess());
        dataProcessMap.put(H5Vo.class,new H5Process());
        dataProcessMap.put(TvContentVo.class,new TvContentProcess());
        dataProcessMap.put(TopicVo.class,new TopicDataProcess());
        dataProcessMap.put(VideoVo.class,new VideoDataProcess());
        dataProcessMap.put(SubjectVo.class,new SubjectDataProcess());
        dataProcessMap.put(RankVo.class,new RankDataProcess());
    }
    private DataProcessFactory(){

    }
    public  static <T extends ContentBaseVo> DataProcess getDataProcess(Class<T> dataType){
        return dataProcessMap.get(dataType);
    }
}
