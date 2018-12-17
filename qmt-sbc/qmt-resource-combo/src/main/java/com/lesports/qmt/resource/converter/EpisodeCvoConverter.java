package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.EpisodeCvo;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.utils.math.LeNumberUtils;
import com.sun.corba.se.impl.presentation.rmi.IDLTypesUtil;

/**
 * User: ellios
 * Time: 16-12-28 : 下午6:31
 */
public class EpisodeCvoConverter extends AbstractCvoConverter<EpisodeCvo, TComboEpisode> {
    @Override
    public EpisodeCvo doToCvo(TResourceContent content, TComboEpisode dto) {
        EpisodeCvo cvo = new EpisodeCvo();
        cvo.setId(dto.getId() + "");
        cvo.setName(dto.getName());
        cvo.setCommentId(dto.getCommentId());
        cvo.setUrl(dto.getPlayLink());
        cvo.setDetailInfo(dto);
        return cvo;
    }

    @Override
    public EpisodeCvo doToCvo(TResourceContent content, CallerParam caller) {

        TComboEpisode dto = null;
        IdType idType = LeIdApis.checkIdTye(LeNumberUtils.toLong(content.getItemId()));
        if(idType == IdType.COMPETITION){
            dto = QmtSbcEpisodeApis.getEpisodeByMid(LeNumberUtils.toLong(content.getItemId()),caller);
        }else {
            dto = QmtSbcEpisodeApis.getTComboEpisodeById(LeNumberUtils.toLong(content.getItemId()), caller);
        }
        if (dto == null) {
            LOG.warn("fail to doToCvo. dto is null. itemId : {}, caller : {}", content.getItemId(), caller);
            return null;
        }

        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case EPISODE:
                return true;
            default:
                return false;
        }
    }
}
