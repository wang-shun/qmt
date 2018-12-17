package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.datacenter.vo.PostVo;
import com.lesports.qmt.web.datacenter.vo.TopicVo;
import com.lesports.utils.PostApis;
import com.lesports.utils.pojo.Post;

/**
 * create by wangjichuan  date:16-12-20 time:17:50
 * 突发事件专题 数据处理器
 */
public class TopicDataProcess implements DataProcess<TTopic,TopicVo> {

    @Override
    public TopicVo getEntity() {
        return new TopicVo();
    }

    @Override
    public void constructManualRemoteData(TopicVo topicVo, String itemId, CallerParam callerParam) {
        TTopic tTopic = QmtSbcApis.getTTopicById(Long.parseLong(itemId),callerParam);
        topicVo.setShareable(tTopic.isShareable());
        topicVo.setBannerImage(tTopic.getBannerImage());
        topicVo.setTags(tTopic.getTags());
        topicVo.setKeywords(tTopic.getKeywords());
//        topicVo.setUrl(tTopic.getP);
    }
}
