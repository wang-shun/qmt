package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.creater.STopicResultAdapter;
import com.lesports.qmt.web.api.core.creater.TopicResultAdapter;
import com.lesports.qmt.web.api.core.service.TopicService;
import com.lesports.qmt.web.api.core.vo.TopicInfo;
import com.lesports.qmt.web.api.core.vo.TopicItem;
import com.lesports.qmt.web.api.core.vo.TvTopicInfo;
import com.lesports.utils.CmsSubjectApis;
import com.lesports.utils.TopicApis;
import com.lesports.utils.pojo.SearchData;
import com.lesports.utils.pojo.SubjectData;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicServiceImpl.class);
    private static final int TOPIC_LIST_ORDER = 4;

    @Resource
    private TopicResultAdapter topicResultAdapter;

	@Resource
    private STopicResultAdapter sTopicResultAdapter;

    @Override
    public List<TopicItem> list(int channel, int page, int count) {
        List<TopicItem> items = new ArrayList<>();
        List<SubjectData.SubjectItem> subjectItems = CmsSubjectApis.listSubjects(channel, page, count, TOPIC_LIST_ORDER);
        if (CollectionUtils.isNotEmpty(subjectItems)) {
            items = topicResultAdapter.adapt(subjectItems);
        }
        return items;
    }

	@Override
	public List<TopicItem> searchCmsList(int channel, int page, int count, int secondCg) {
		List<TopicItem> items = new ArrayList<>();
		List<SearchData.SubjectData> subjectDatas = TopicApis.searchTopicSubjectData(page, count, secondCg);
		if (CollectionUtils.isNotEmpty(subjectDatas)) {
			items = sTopicResultAdapter.adapt(subjectDatas);
		}
		return items;
	}

	@Override
    public List<TopicInfo.VideoInfo> getVideos(int id, int page, int count) {
        SubjectData.SubjectInfo info = CmsSubjectApis.getSubject(id);
        if (null != info) {
            List<TopicInfo.VideoInfo> videoInfos = topicResultAdapter.adaptTjPackage(info.getPackageIds(), Integer.MAX_VALUE);
            if (CollectionUtils.isNotEmpty(videoInfos)) {
                List<List<TopicInfo.VideoInfo>> infos  = Lists.partition(videoInfos, count);
                return page <= infos.size() ? infos.get(page -1) : null;
            }
        }
        return null;
    }

    @Override
    public TopicInfo get(int id) {
        TopicInfo topicInfo = null;
        SubjectData.SubjectInfo info = CmsSubjectApis.getSubject(id);
        if (null != info) {
            topicInfo = topicResultAdapter.adapt(info);
        }
        return topicInfo;
    }

    @Override
    public TvTopicInfo getTvTopicById(int id,CallerParam caller){
        TvTopicInfo tvTopicInfo = null;
        SubjectData.SubjectInfo info =  CmsSubjectApis.getSubject(id);
        if (null != info) {
            tvTopicInfo = topicResultAdapter.adaptTvSubject(info,caller);
        }

        return tvTopicInfo;

    }
}
