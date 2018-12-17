package com.lesports.qmt.tlive.service;

import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;

/**
 * Created by lufei1 on 2015/9/14.
 */
public interface TextLiveImageService extends QmtService<TextLiveImage, Long> {

    /**
     * 发送图片消息
     * @param id
     * @param liveMessage
     */
    public void sendImageMessage(long id, TextLiveMessage liveMessage);

}
