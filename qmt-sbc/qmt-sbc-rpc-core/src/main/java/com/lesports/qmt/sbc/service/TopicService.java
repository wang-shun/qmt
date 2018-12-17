package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * Created by denghui on 2016/11/2.
 */
public interface TopicService extends QmtService<Topic, Long> {

    TTopic getTTopicById(long id, CallerParam caller);

    List<TTopic> getTTopicsByIds(List<Long> ids, CallerParam caller);
}
