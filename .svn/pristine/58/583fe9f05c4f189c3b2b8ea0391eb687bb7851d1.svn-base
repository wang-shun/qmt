package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.vo.EpisodeVo;

import java.util.Map;

/**
 * Created by lufei1 on 2016/11/4.
 */
public interface EpisodeWebService extends QmtWebService<EpisodeVo, Long> {

    public EpisodeVo findOne(Long id, CallerParam caller);


    QmtPage<EpisodeVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller);

    boolean updateOnlineStatus(long id, PublishStatus publishStatus);
}
