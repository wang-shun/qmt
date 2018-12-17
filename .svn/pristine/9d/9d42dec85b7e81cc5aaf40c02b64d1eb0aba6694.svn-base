package com.lesports.qmt.sbc.service.impl;

import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.client.QmtSbcLiveInternalApis;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.service.LiveWebService;
import com.lesports.qmt.sbc.vo.LiveVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjichuan on 2016/10/26.
 */
@Service
public class LiveWebServiceImpl implements LiveWebService {

    private static final Logger LOG = LoggerFactory.getLogger(LiveWebServiceImpl.class);

    /**
     * 1.保存信息到大乐视那边的直播后台里面
     * 2.保存信息到全媒体后台里面
     *
     * @param live
     * @return
     */
    @Override
    public boolean save(LiveVo live) {
        SaveResult result = QmtSbcLiveInternalApis.saveLive(live.toModel());
        if(result.isSuccess()){
            live.setId(result.getId());
            return true;
        }
        return false;
    }

    @Override
    public SaveResult saveWithResult(LiveVo live) {
        SaveResult result = QmtSbcLiveInternalApis.saveLive(live.toModel());
        if(result.isSuccess()){
            live.setId(result.getId());
        }
        return result;
    }

    @Override
    public Long saveWithId(LiveVo entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return QmtSbcLiveInternalApis.deleteLive(id);
    }

    @Override
    public List<Live> batchGet(List<Long> liveIds) {
        List<Live> lives = null;
        try {
            LOG.info("[LiveWebService][batchGet start][liveIds={}]", liveIds);
            lives = QmtSbcLiveInternalApis.getLiveByIds(liveIds);
            LOG.info("[LiveWebService][batchGet end][liveIds={}]", liveIds);
        } catch (Exception e) {
            LOG.error("[LiveWebService][batchGet][liveIds={}]", liveIds, e);
        }
        return lives;
    }

    @Override
    public boolean batchDelete(List<Long> liveIds) {
        boolean result = true;
        for(Long liveId : liveIds){
            result = result && delete(liveId);
        }
        return result;
    }

    @Override
    public LiveVo findOne(Long id) {
        Live live = QmtSbcLiveInternalApis.getLiveById(id);
        return new LiveVo(live);
    }
}
