package com.lesports.qmt.tlive.service;

import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.query.TextLiveMessageQueryParams;

import java.util.List;

/**
 * Created by lufei1 on 2015/9/14.
 */
public interface TextLiveMessageService extends QmtService<TextLiveMessage, Long> {


    /**
     * @param id
     * @return
     */
    public TTextLiveMessage getTLiveMessageId(long id);

    /**
     * 批量id查询消息
     *
     * @param ids
     * @return
     */
    public List<TTextLiveMessage> getTLiveMessageByIds(List<Long> ids);

    /**
     * 根据textLiveId获取消息id
     *
     * @param textLiveId
     * @return
     */
    public List<String> getLiveMessageIdsByTextLiveId(long textLiveId);

    /**
     * 按条件查询消息内容
     *
     * @param textLiveId
     * @param section
     * @param type
     * @return
     */
    public List<String> getLiveMessagesIds(long textLiveId, long section, TextLiveMessageType type);


    /**
     * 获取最新消息index
     *
     * @param textLiveId
     * @param section
     * @return
     */
    public int getLatestIndex(long textLiveId, long section);


    /**
     * 获取每页的id列表
     *
     * @param textLiveId
     * @param section
     * @param page
     * @return
     */
    public List<String> getIdByPage(long textLiveId, long section, int page);

    /**
     * 按页获取消息列表
     *
     * @param textLiveId
     * @param section
     * @param page
     * @return
     */
    public List<TTextLiveMessage> getTLiveMessageByPage(long textLiveId, long section, int page);

    /**
     * @param liveMessageQueryParams
     * @return
     */
    public List<TextLiveMessage> findLiveMessageIdsByParams(TextLiveMessageQueryParams liveMessageQueryParams);
}
