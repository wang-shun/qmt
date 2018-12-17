package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.datacenter.vo.SubjectVo;
import com.lesports.qmt.web.datacenter.vo.TopicVo;
import com.lesports.utils.TopicListApi;

/**
 * create by wangjichuan  date:16-12-20 time:17:50
 * 小专题 数据处理器
 */
public class SubjectDataProcess implements DataProcess<TTopic,SubjectVo> {

    @Override
    public SubjectVo getEntity() {
        return new SubjectVo();
    }

    @Override
    public void constructManualRemoteData(SubjectVo subjectVo, String itemId, CallerParam callerParam) {

    }
}
