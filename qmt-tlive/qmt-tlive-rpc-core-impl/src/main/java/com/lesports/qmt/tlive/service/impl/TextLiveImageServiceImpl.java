package com.lesports.qmt.tlive.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.tlive.helper.TextLiveMessageHelper;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.repository.TextLiveImageRepository;
import com.lesports.qmt.tlive.service.TextLiveImageService;
import com.lesports.qmt.tlive.service.TextLiveMessageService;
import com.lesports.qmt.tlive.service.support.AbstractTextLiveService;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Service("textLiveImageService")
public class TextLiveImageServiceImpl extends AbstractTextLiveService<TextLiveImage, Long> implements TextLiveImageService {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveImageServiceImpl.class);

    @Resource
    private TextLiveImageRepository textLiveImageRepository;
    @Resource
    private TextLiveMessageHelper textLiveMessageHelper;
    @Resource
    private TextLiveMessageService textLiveMessageService;


    @Override
    protected MongoCrudRepository<TextLiveImage, Long> getInnerRepository() {
        return textLiveImageRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(TextLiveImage entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(TextLiveImage entity) {
        entity.setId(LeIdApis.nextId(IdType.TEXT_LIVE_IMAGE));
        entity.setDeleted(false);
        return textLiveImageRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(TextLiveImage entity) {
        return false;
    }

    @Override
    protected boolean doUpdate(TextLiveImage entity) {
        return textLiveImageRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(TextLiveImage entity) {
        return false;
    }

    @Override
    protected boolean doDelete(Long id) {
        return textLiveImageRepository.delete(id);
    }

    @Override
    protected boolean doAfterDelete(TextLiveImage entity) {
        return false;
    }

    @Override
    protected TextLiveImage doFindExistEntity(TextLiveImage entity) {
        return textLiveImageRepository.findOne(entity.getId());
    }

    @Override
    public void sendImageMessage(long id, TextLiveMessage liveMessage) {
        LOG.info("send voteMessage.id:{},liveMessage:{}", id, liveMessage);
        liveMessage = textLiveMessageHelper.buildWithTextLiveImage(id, liveMessage);
        textLiveMessageService.save(liveMessage);
    }

}
