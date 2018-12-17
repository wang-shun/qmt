package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.sbc.repository.ProgramAlbumRepository;
import org.springframework.stereotype.Service;

/**
 * Created by denghui on 2016/11/15.
 */
@Service("programAlbumRepository")
public class ProgramAlbumRepositoryImpl extends AbstractMongoRepository<ProgramAlbum, Long> implements ProgramAlbumRepository {

    @Override
    protected Class<ProgramAlbum> getEntityType() {
        return ProgramAlbum.class;
    }

    @Override
    protected Long getId(ProgramAlbum entity) {
        return entity.getId();
    }
}
