package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

/**
 * Created by lufei1 on 2016/11/4.
 */
@Service("episodeRepository")
public class EpisodeRepositoryImpl extends AbstractMongoRepository<Episode, Long> implements EpisodeRepository {
    @Override
    protected Class<Episode> getEntityType() {
        return Episode.class;
    }

    @Override
    protected Long getId(Episode entity) {
        return entity.getId();
    }
}
