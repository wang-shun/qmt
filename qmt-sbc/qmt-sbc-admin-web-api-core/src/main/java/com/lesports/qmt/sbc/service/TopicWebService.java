package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.qmt.sbc.vo.TopicVo;

import java.util.List;

/**
 * Created by denghui on 2016/11/2.
 */
public interface TopicWebService extends QmtWebService<TopicVo, Long> {

    TopicVo findOne(Long id, CallerParam caller);

    /**
     * 修改专题上下线状态
     * @param id
     * @param status
     * @return
     */
    boolean updatePublishStatus(long id, PublishStatus status);

}
