package com.lesports.qmt.sbc.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.cache.TNewsCache;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.repository.NewsImageRepository;
import com.lesports.qmt.sbc.service.NewsImageService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by ruiyuansheng on 2015/7/7.
 */
@Service("newsImageService")
public class NewsImageServiceImpl extends AbstractSbcService<NewsImage, Long> implements NewsImageService {
    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Resource
    private NewsImageRepository newsImageRepository;
    @Resource
    private TNewsCache newsCache;

    @Override
    protected QmtOperationType getOpreationType(NewsImage entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(NewsImage entity) {
        entity.setId(LeIdApis.nextId(IdType.NEWS_IMAGE));
        entity.setDeleted(false);
        return newsImageRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(NewsImage entity) {
        return newsCache.delete(entity.getId());
    }

    @Override
    protected boolean doUpdate(NewsImage entity) {
        return newsImageRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(NewsImage entity) {
        return newsCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return newsImageRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(NewsImage entity) {
        return true;
    }

    @Override
    protected NewsImage doFindExistEntity(NewsImage entity) {
        return newsImageRepository.findOne(entity.getId());
    }

    @Override
    public List<NewsImage> findByNewsId(Long newsId) {
        return newsImageRepository.findByNewsId(newsId);
    }

    @Override
    protected MongoCrudRepository<NewsImage, Long> getInnerRepository() {
        return newsImageRepository;
    }


}
