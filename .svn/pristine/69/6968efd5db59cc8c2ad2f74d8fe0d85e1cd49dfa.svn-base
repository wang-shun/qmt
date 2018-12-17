package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.vo.NewsVo;

/**
 * Created by denghui on 2016/10/21.
 */
public interface NewsWebService extends QmtWebService<NewsVo, Long> {

    NewsVo findOne(Long id, CallerParam caller);

    /**
     * 修改新闻上下线状态
     * @param id
     * @param status
     * @return
     */
    boolean updatePublishStatus(long id, PublishStatus status);

}
