package com.lesports.qmt.sbc.converter;

import com.lesports.qmt.sbc.api.dto.TSimpleEpisode;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;

/**
 * Created by lufei on 2016/12/25.
 */
@Component("simpleEpisodeVoConverter")
public class TSimpleEpisodeVoConverter extends AbstractSbcTDtoConverter<Episode, TSimpleEpisode> {

    @Override
    protected void fillDto(TSimpleEpisode dto, Episode episode) {
        dto.setId(episode.getId());
        dto.setName(episode.getName());
        dto.setStartTime(episode.getStartTime());
        dto.setStartDate(episode.getStartDate());
        dto.setGameType(LeNumberUtils.toLong(episode.getGameFType()));
        dto.setCid(LeNumberUtils.toLong(episode.getCid()));
    }

    @Override
    protected TSimpleEpisode createEmptyDto() {
        return new TSimpleEpisode();
    }
}
