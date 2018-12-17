package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.web.datacenter.vo.H5Vo;

import java.util.List;

/**
 * create by wangjichuan  date:16-12-21 time:14:35
 */
public class H5Process implements DataProcess<String,H5Vo> {
    @Override
    public H5Vo getEntity() {
        return new H5Vo();
    }

    @Override
    public void constructManualRemoteData(H5Vo h5Vo, String h5Url, CallerParam callerParam) {
        h5Vo.setUrl(h5Url);
    }
}
