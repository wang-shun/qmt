package com.lesports.qmt.tlive.converter;

import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.tlive.api.dto.TSimpleAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.repository.TextLiveMessageRepository;
import com.lesports.qmt.tlive.repository.TextLiveRepository;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lufei1 on 2015/9/15.
 */
@Component("textLiveMessageVoCreator")
public class TextLiveMessageVoCreator {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveMessageVoCreator.class);

    @Resource
    private TextLiveMessageRepository textLiveMessageRepository;
    @Resource
    private TextLiveRepository textLiveRepository;


    public TTextLiveMessage createTLiveMessage(long liveMessageId) {
        TextLiveMessage liveMessage = textLiveMessageRepository.findOne(liveMessageId);
        if (liveMessage == null) {
            return null;
        }
        TTextLiveMessage tLiveMessage = new TTextLiveMessage();
        fillTLiveMessage(tLiveMessage, liveMessage);
        return tLiveMessage;
    }

    private void fillTLiveMessage(TTextLiveMessage tLiveMessage, TextLiveMessage liveMessage) {
        tLiveMessage.setId(liveMessage.getId());
        if (liveMessage.getAnchor() != null) {
            tLiveMessage.setAnchor(createTSimpleAnchor(liveMessage.getAnchor()));
        }
        tLiveMessage.setContent(liveMessage.getContent());
        tLiveMessage.setMatchResult(liveMessage.getMatchResult());
        tLiveMessage.setSectionId(LeNumberUtils.toLong(liveMessage.getSection()));
        if (liveMessage.getSection() != null && liveMessage.getSection() != 0L) {
            TDictEntry dictEntry = QmtConfigApis.getTDictEntryById(liveMessage.getSection(), CallerUtils.getDefaultCaller());
            if (dictEntry != null) {
                tLiveMessage.setSection(dictEntry.getName());
            }
        }
        tLiveMessage.setSendTime(liveMessage.getCreateAt());
        tLiveMessage.setTextLiveId(liveMessage.getTextLiveId());
        TextLive textLive = textLiveRepository.findOne(liveMessage.getTextLiveId());
        if (textLive != null) {
            tLiveMessage.setTextLiveType(textLive.getType());
        }
        tLiveMessage.setTime(liveMessage.getTime());
        tLiveMessage.setType(liveMessage.getType());
        tLiveMessage.setDeleted(liveMessage.getDeleted());
    }

    private TSimpleAnchor createTSimpleAnchor(TextLiveMessage.SimpleAnchor simpleAnchor) {
        TSimpleAnchor tSimpleAnchor = new TSimpleAnchor();
        tSimpleAnchor.setName(simpleAnchor.getName());
        tSimpleAnchor.setLogo(simpleAnchor.getLogo());
        return tSimpleAnchor;
    }
}
