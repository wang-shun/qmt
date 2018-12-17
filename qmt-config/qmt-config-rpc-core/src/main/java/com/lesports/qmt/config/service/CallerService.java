package com.lesports.qmt.config.service;

import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.model.Caller;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * User: ellios
 * Time: 15-11-13 : 下午5:48
 */
public interface CallerService extends QmtService<Caller, Long> {

    TCaller getTCallerById(long callerId);

    List<TCaller> getTCallersByIds(List<Long> ids);

    List<Long> getTCallerBySplatId(long splatId);
}
