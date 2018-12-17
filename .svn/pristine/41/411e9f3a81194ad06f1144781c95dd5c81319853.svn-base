package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TMatchStats;
import com.lesports.utils.CallerUtils;
import org.apache.thrift.TException;

/**
 * Created by zhonglin on 2016/12/9.
 */
public class SbdMatchStatsApis extends SbdApis {

    public TMatchStats getTMatchStatsById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return sbdService.getTMatchStatsById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchStatsById. id:{}", id, e);
        }
        return null;
    }
}
