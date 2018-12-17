package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.datacenter.vo.EpisodeVo;
import com.lesports.qmt.web.datacenter.vo.NewsVo;

/**
 * create by wangjichuan  date:16-12-20 time:17:50
 */
public class EpisodeDataProcess implements DataProcess<TComboEpisode,EpisodeVo> {
    @Override
    public void constructManualRemoteData(EpisodeVo episodeVo,String itemId,CallerParam callerParam) {
        TComboEpisode tComboEpisode = QmtSbcEpisodeApis.getTComboEpisodeById(Long.parseLong(itemId), callerParam);
        episodeVo.setId(tComboEpisode.getId()+"");
        episodeVo.setName(tComboEpisode.getName());
        episodeVo.setCommentId(tComboEpisode.getCommentId());
        episodeVo.setUrl(tComboEpisode.getPlayLink());
    }
    @Override
    public EpisodeVo getEntity() {
        return new EpisodeVo();
    }
}
