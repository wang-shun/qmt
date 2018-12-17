package com.lesports.qmt.web.api.core.rconverter;

import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.NewsCvo;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.web.api.core.vo.Activity;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by gengchengliang on 2016/12/29.
 */
@Component("activityResConverter")
public class ActivityResConverter implements BaseResourceContentConverter<Activity, BaseCvo> {

	@Override
	public Activity getVo() {
		return new Activity();
	}

	@Override
	public Activity fillVo(BaseCvo contentBaseVo, Object... args) {
		Activity activity = getVo();
		activity.setId(contentBaseVo.getId());
		activity.setName(contentBaseVo.getName());
		activity.setShareName(contentBaseVo.getName());
		activity.setDesc(contentBaseVo.getDesc());
		activity.setImageUrl(contentBaseVo.getMobileImg());
		activity.setIpadImageUrl(contentBaseVo.getIpadImg());
		activity.setResourceSId(contentBaseVo.getId());
		activity.setShowSeconds(LeNumberUtils.toLong(contentBaseVo.getDurationTime()));
		activity.setStartTime(contentBaseVo.getStartTime());
		activity.setEndTime(contentBaseVo.getEndTime());
		activity.setStatus(1);
		activity.setOrder(LeNumberUtils.toInt(contentBaseVo.getOrder()));
		activity.setStartupImage(contentBaseVo.getMobileImg());
		activity.setResourceUrl(contentBaseVo.getUrl());
		activity.setType(Integer.parseInt(args[0].toString()));
		if (contentBaseVo.getType() != ResourceItemType.POST && StringUtils.isNotBlank(contentBaseVo.getId())){
			activity.setResourceId(LeNumberUtils.toLong(contentBaseVo.getId()));
		}
		adapterVoType(activity, contentBaseVo.getType(), args);
		adapterNewsType(activity, contentBaseVo);
//		if (contentBaseVo.getType() == contentType.APP_STARTUP_LOGO) { todo 开机图增加广告位判断
//			//默认false
//			activity.setAdUse(false);
//			Config config = getConfig();
//			if (config != null) {
//				activity.setAdUse(LeNumberUtils.toBoolean(config.getData().get("aduse")));
//			}
//		}
		return activity;
	}

	@Override
	public void adapterNews(Activity activity, NewsCvo newsVo) {
		activity.setNewsType(newsVo.getNewsType().ordinal());
	}

	@Override
	public void adapterVoType(Activity activity, ResourceItemType rType, Object... args) {
		Integer activityRtype = null;
		if (rType == ResourceItemType.EPISODE) {
			activityRtype = 0;
		} else if (rType == ResourceItemType.H5) {
			activityRtype = 1;
		} else if (rType == ResourceItemType.POST) {
			activityRtype = 2;
		} else if (rType == ResourceItemType.NEWS || rType == ResourceItemType.RICHTEXT || rType == ResourceItemType.IMAGE_ALBUM || rType == ResourceItemType.VIDEO_NEWS) {
			activityRtype = 3;
		}
		if (activityRtype != null) {
			activity.setResourceType(activityRtype);
		}
	}

	@Override
	public void adapterNewsType(Activity activity, BaseCvo contentBaseVo) {
		if (contentBaseVo.getType() == ResourceItemType.RICHTEXT) {
			activity.setNewsType(0);
		} else if (contentBaseVo.getType() == ResourceItemType.IMAGE_ALBUM) {
			activity.setNewsType(3);
		} else if (contentBaseVo.getType() == ResourceItemType.VIDEO_NEWS) {
			activity.setNewsType(1);
		} else if (contentBaseVo.getType() == ResourceItemType.NEWS) {
			adapterNews(activity, (NewsCvo) contentBaseVo);
		}
	}
}
