package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.repository.AlbumRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxudong@le.com on 2016/10/26.
 */
@Service("albumRepository")
public class AlbumRepositoryImpl extends AbstractMongoRepository<Album, Long> implements AlbumRepository {

    @Override
    protected Class<Album> getEntityType() {
        return Album.class;
    }

    @Override
    protected Long getId(Album entity) {
        return entity.getId();
    }
}
