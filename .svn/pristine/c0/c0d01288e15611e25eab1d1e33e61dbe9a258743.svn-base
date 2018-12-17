package com.lesports.qmt.web.api.core.rconverter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.EpisodeCvo;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.web.api.core.creater.EpisodeVoCreater;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by gengchengliang on 2016/12/29.
 */
@Component("episodeResConverter")
public class EpisodeResConverter implements BaseResourceContentConverter<HallEpisodeVo, BaseCvo> {

	@Resource
	private EpisodeVoCreater episodeVoCreater;

	@Override
	public HallEpisodeVo getVo() {
		return new HallEpisodeVo();
	}

	@Override
	public HallEpisodeVo fillVo(BaseCvo contentBaseVo, Object... args) {
		HallEpisodeVo episodeVo = getVo();
		adapterEpisode(episodeVo, (EpisodeCvo) contentBaseVo, args);
		episodeVo.setId(LeNumberUtils.toLong(contentBaseVo.getId()));
		episodeVo.setName(contentBaseVo.getName());
		return episodeVo;
	}

	@Override
	public void adapterEpisode(HallEpisodeVo hallEpisodeVo, EpisodeCvo content, Object... args) {
		TComboEpisode tComboEpisode = content.getDetailInfo();
		episodeVoCreater.createHallEpisodeVo4App(hallEpisodeVo, tComboEpisode, (CallerParam) args[0]);
	}
}
