package com.lesports.qmt.web.api.core.creater;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.TopicItem;
import com.lesports.utils.CmsSubjectApis;
import com.lesports.utils.LeConcurrentUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.SearchData;
import com.lesports.utils.pojo.SubjectData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pangchuanxiao on 2015/6/30.
 */
@Component
public class STopicResultAdapter {
    private static final int MAX_SUBJECT_VIDEO = 5;
    private static final String TOPIC_COMMENTID_PREFIX = "t_";

	@Resource
	private TopicResultAdapter topicResultAdapter;

    private final Function<SearchData.SubjectData, TopicItem> SUBJECT_ITEM_ADAPTER = new Function<SearchData.SubjectData, TopicItem>() {
        @Nullable
        @Override
        public TopicItem apply(@Nullable SearchData.SubjectData subjectItem) {
            return adapt(subjectItem);
        }
    };

    public List<TopicItem> adapt(List<SearchData.SubjectData> list) {
        List<TopicItem> topicItems = LeConcurrentUtils.parallelApply(list, SUBJECT_ITEM_ADAPTER);
        return topicItems;
    }

    public TopicItem adapt(SearchData.SubjectData item) {
        TopicItem topicItem = new TopicItem();
		long id = LeNumberUtils.toLong(item.getAid());
		SubjectData.SubjectInfo subjectInfo = CmsSubjectApis.getSubject(id);
        Map<String, String> imageUrl = Maps.newHashMap();
        if (null != subjectInfo) {
            topicItem.setVideos(topicResultAdapter.adaptTjPackage(subjectInfo.getPackageIds(), MAX_SUBJECT_VIDEO));
            topicItem.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(subjectInfo.getCtime())));
			//如果返回null的话则客户端崩溃.......
            imageUrl.put(Constants.VIDEO_NEWS_IMAGE_43, StringUtils.isNotBlank(subjectInfo.getPic43()) ? subjectInfo.getPic43() : "");
            imageUrl.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, StringUtils.isNotBlank(subjectInfo.getPic169()) ? subjectInfo.getPic169() : "");
        }
        topicItem.setImageUrl(imageUrl);
        topicItem.setId(id);
        topicItem.setName(item.getTitle());
        topicItem.setType(Constants.SUBJECT);
		topicItem.setCommentId(TOPIC_COMMENTID_PREFIX + id);
        return topicItem;
    }
}
