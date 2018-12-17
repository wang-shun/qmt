package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.Album;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午9:14
 */
public class QmtSbcAlbumInternalApis extends QmtSbcInternalApis {

    public static long saveAlbum(Album album) {
        return saveEntity(album, Album.class);
    }

    public static Album getAlbumById(Long id) {
        return getEntityById(id, Album.class);
    }

    public static List<Album> getAlbumByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Album.class);
    }

    public static List<Album> getAlbumByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Album.class);
    }

    public static long getAlbumCountByQuery(InternalQuery query) {
        return countByQuery(query, Album.class);
    }
}
