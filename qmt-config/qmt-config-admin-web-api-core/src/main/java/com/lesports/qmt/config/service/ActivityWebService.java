package com.lesports.qmt.config.service;

import com.lesports.qmt.config.vo.ActivityVo;
import com.lesports.qmt.mvc.QmtWebService;

/**
 * Created by denghui on 2016/12/9.
 */
public interface ActivityWebService extends QmtWebService<ActivityVo, Long> {

    /**
     * 上下线
     * @param id
     * @param online
     * @return
     */
    boolean updateOnlineStatus(long id, Boolean online);
}
