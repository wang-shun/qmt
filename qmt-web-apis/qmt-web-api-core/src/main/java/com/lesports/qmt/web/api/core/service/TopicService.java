package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.TopicInfo;
import com.lesports.qmt.web.api.core.vo.TopicItem;
import com.lesports.qmt.web.api.core.vo.TvTopicInfo;

import java.util.List;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
public interface TopicService {
    /**
     * get all subjects by page and count.(改搜索之前的方法,因为CMS不支持和自己的CMS未上线,所以用搜索接口暂时替换)
     * @return
     */
    List<TopicItem> list(int channel, int page, int count);

	/**
	 * get all subjects by page and count.(因为CMS不支持和自己的CMS未上线,所以用搜索接口暂时替换)
	 * @return
	 */
	List<TopicItem> searchCmsList(int channel, int page, int count, int secondCg);

    /**
     * get info of one subject by id.
      * @param id
     * @return
     */
    TopicInfo get(int id);


    /**
     * get videos of one subject by id.
     * @param id
     * @return
     */
    List<TopicInfo.VideoInfo> getVideos(int id, int page, int count);

    /**
     * 获取TV需要的专题
     * @param id
     * @return
     */
    TvTopicInfo getTvTopicById(int id, CallerParam caller);


}
