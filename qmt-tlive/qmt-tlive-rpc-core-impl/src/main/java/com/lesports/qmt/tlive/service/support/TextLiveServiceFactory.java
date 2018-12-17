package com.lesports.qmt.tlive.service.support;

import com.lesports.qmt.exception.NoServiceException;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.service.TextLiveImageService;
import com.lesports.qmt.tlive.service.TextLiveMessageService;
import com.lesports.qmt.tlive.service.TextLiveService;
import com.lesports.qmt.tlive.service.VoteService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/3/29
 */
@Component("textLiveServiceFactory")
public class TextLiveServiceFactory {
    @Resource
    private TextLiveService textLiveService;
    @Resource
    private TextLiveMessageService textLiveMessageService;
    @Resource
    private TextLiveImageService textLiveImageService;
    @Resource
    private VoteService voteService;

    public QmtService getService(Class clazz) throws NoServiceException {
        if (clazz == TextLive.class) {
            return textLiveService;
        } else if (clazz == TextLiveMessage.class) {
            return textLiveMessageService;
        } else if (clazz == TextLiveImage.class) {
            return textLiveImageService;
        } else if (clazz == Vote.class) {
            return voteService;
        }
        throw new NoServiceException("No service for class : " + clazz);
    }
}
