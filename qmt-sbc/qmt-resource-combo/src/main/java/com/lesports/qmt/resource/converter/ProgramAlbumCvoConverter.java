package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.ProgramAlbumCvo;
import com.lesports.qmt.resource.cvo.RankCvo;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.math.LeNumberUtils;

/**
 * User: ellios
 * Time: 16-12-28 : 下午9:58
 */
public class ProgramAlbumCvoConverter extends AbstractCvoConverter<ProgramAlbumCvo, TProgramAlbum> {
    @Override
    public ProgramAlbumCvo doToCvo(TResourceContent content, TProgramAlbum dto) {
        ProgramAlbumCvo cvo = new ProgramAlbumCvo();
        cvo.setId(content.getItemId());
        return cvo;
    }

    @Override
    public ProgramAlbumCvo doToCvo(TResourceContent content, CallerParam caller) {
        TProgramAlbum tProgramAlbum = QmtSbcApis.getTProgramAlbumById(LeNumberUtils.toLong(content.getItemId()),caller);
        return toCvo(content,tProgramAlbum);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case PROGRAM_ALBUM:
                return true;
            default:
                return false;
        }
    }
}
