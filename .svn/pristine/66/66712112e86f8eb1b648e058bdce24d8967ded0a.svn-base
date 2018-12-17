package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by denghui on 2016/11/15.
 */
public interface ProgramAlbumService extends QmtService<ProgramAlbum, Long> {

    List<TProgramAlbum> getTProgramAlbumsByIds(List<Long> ids, CallerParam caller);

    TProgramAlbum getTProgramAlbumById(long id, CallerParam caller);

    List<Long> getTProgramAlbums(long tagId, Pageable pageable, CallerParam caller);
}
