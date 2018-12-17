package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.service.support.SbdWebService;
import com.lesports.qmt.sbd.vo.PlayerVo;

import java.util.List;

/**
 * Created by lufei1 on 2016/10/25.
 */
public interface PlayerWebService extends SbdWebService<PlayerVo, Long> {

    List<PlayerVo> getPlayersByIds(String ids, CallerParam caller);
}
