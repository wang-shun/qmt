package com.lesports.qmt.sbc.service;

import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.vo.LiveVo;

import java.util.List;

/**
 * Created by wangjichuan on 2016/10/26.
 */
public interface LiveWebService  extends QmtWebService<LiveVo, Long> {

//    long save(Live live);
//    boolean delete(long id);
//    Live getById(long id);
    List<Live> batchGet(List<Long> liveIds);
    boolean batchDelete(List<Long> liveIds);
    SaveResult saveWithResult(LiveVo live);
}
