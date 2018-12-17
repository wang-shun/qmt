package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.TextLiveMessageVo;

import java.util.List;

/**
 * Created by lufei1 on 2015/9/20.
 */
public interface TextLiveService {


    /**
     * 批量消息id获取直播消息
     *
     * @param ids
     * @return
     */
    public List<TextLiveMessageVo> getLiveMessageByIds(String ids);

    /**
     * 批量消息id获取直播消息realtime使用
     *
     * @param ids
     * @return
     */
    public List<TextLiveMessageVo> getLiveMessageByIdsRealtime(String ids, CallerParam caller);


    /**
     * 按页查询图文直播消息
     *
     * @param textLiveId
     * @param section
     * @param page
     * @return
     */
    public List<TextLiveMessageVo> getLiveMessageByPage(long textLiveId, long section, int page);

}
