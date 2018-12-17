package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * Created by denghui on 2016/11/15.
 */
public class QmtSbcProgramAlbumInternalApis extends QmtSbcInternalApis {


    public static long saveProgramAlbum(ProgramAlbum programAlbum) {
        return saveProgramAlbum(programAlbum, false);
    }

    public static long saveProgramAlbum(ProgramAlbum programAlbum, boolean allowEmpty) {
        return saveEntity(programAlbum, ProgramAlbum.class, allowEmpty);
    }

    public static ProgramAlbum getProgramAlbumById(Long id) {
        return getEntityById(id, ProgramAlbum.class);
    }

    public static List<ProgramAlbum> getProgramAlbumsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, ProgramAlbum.class);
    }

    public static long countProgramAlbumByQuery(InternalQuery query) {
        return countByQuery(query, ProgramAlbum.class);
    }

    public static List<Long> getProgramAlbumIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, ProgramAlbum.class);
    }

    public static List<ProgramAlbum> getProgramAlbumsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, ProgramAlbum.class);
    }

    public static boolean deleteProgramAlbum(Long id) {
        return deleteEntity(id, ProgramAlbum.class);
    }


}
