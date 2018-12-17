package com.lesports.qmt.userinfo.service;

import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.userinfo.model.Workbench;
import com.lesports.qmt.userinfo.web.vo.SubscriptionVo;
import com.lesports.qmt.userinfo.web.vo.WorkbenchVo;

import java.util.List;

/**
 * Created by denghui on 2017/2/15.
 */
public interface WorkbenchService extends QmtService<Workbench, Long> {

    boolean subscribe(List<Long> ids);

    boolean unsubscribe(List<Long> ids);

    QmtPage<SubscriptionVo> listSubscription(QmtPageParam pageParam);
}
