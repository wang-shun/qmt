package com.lesports.qmt.web.utils;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TAlbum;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gengchengliang on 2015/9/20.
 */
public class ShowNameUtils {

    private static final String BLANK_SPACE = " ";

    public static String getShowName(TComboEpisode episode, CallerParam caller) {
        Preconditions.checkNotNull(episode);
        String showName = "";
        switch (episode.getType()) {
            case MATCH:
                TCompetition competition = SbdCompetitionApis.getTCompetitionById(episode.getCid(), caller);
                //赛事短名称
                String competitionName = competition.getAbbreviation();
                if (StringUtils.isBlank(competitionName)) {
                    break;
                }
				StringBuffer sb = new StringBuffer(competitionName);
				if (StringUtils.isNotBlank(episode.getGroup())) {
					sb.append(BLANK_SPACE).append(episode.getGroup());
				}
				if (StringUtils.isNotBlank(episode.getStage())) {
					sb.append(BLANK_SPACE).append(episode.getStage());
				}
				if (StringUtils.isNotBlank(episode.getRound())) {
					sb.append(BLANK_SPACE).append(episode.getRound());
				}
				showName = sb.toString();
				break;
            case PROGRAM:
                TProgramAlbum tAlbum = QmtSbcApis.getTProgramAlbumById(episode.getAid(), caller);
                if (tAlbum != null) {
                    //专辑短名称
                    String albumName = tAlbum.getName();
                    if (StringUtils.isBlank(albumName)) {
                        //如果专辑短名称是空就不拼接了
                        showName = tAlbum.getName();
                        break;
                    }
                    showName = albumName;
                } else {
                    String abbreviation = episode.getAbbreviation();
                    if (StringUtils.isNotBlank(abbreviation)) {
                        //其它节目的短名称
                        showName = abbreviation;
                    }
                }
                break;
        }
        return showName;
    }
}
