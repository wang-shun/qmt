package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.web.datacenter.vo.H5Vo;
import com.lesports.qmt.web.datacenter.vo.RankVo;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wangjichuan  date:16-12-21 time:14:35
 */
public class RankDataProcess implements DataProcess<String,RankVo> {
    @Override
    public RankVo getEntity() {
        return new RankVo();
    }

    @Override
    public List<RankVo> constructAutoRemoteData(TResourceContent tResourceContent, PageParam pageParam, CallerParam callerParam) {
        List<RankVo> rankVos = new ArrayList<>();
        RankVo rankVo = new RankVo();
        rankVo.setId(tResourceContent.getItemId());
        rankVos.add(rankVo);
        return rankVos;
    }
}
