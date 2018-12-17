package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.ActivityType;
import com.lesports.qmt.config.api.dto.GetActivitiesParam;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.web.api.core.rconverter.ActivityResConverter;
import com.lesports.qmt.web.api.core.service.ActivityService;
import com.lesports.qmt.web.api.core.util.AppResourceContentIdConstants;
import com.lesports.qmt.web.api.core.util.TVResourceUtils;
import com.lesports.qmt.web.api.core.vo.Activity;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ruiyuansheng on 2016/3/28.
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Resource
	private ActivityResConverter activityResConverter;

	@Override
	public List<Activity> getActivities(Integer activityType, String resourceType, PageParam pageParam, CallerParam caller) {
		Long rescontentid = 0l;
		if (activityType == 1) {//推广位
			rescontentid = AppResourceContentIdConstants.APP_PROMOTION_RESCONTENTID;
		} else if (activityType == 2) {//开机图
			rescontentid = AppResourceContentIdConstants.APP_STARTUP_LOGO_RESCONTENTID;
		}else if (activityType == 10) {//桌面背景图
            rescontentid = TVResourceUtils.DESKTOP_BACKGROUND_IMAGE;
        }else if (activityType == 11) {//退出图
            rescontentid = TVResourceUtils.EXIT_IMAGE;
        }else if (activityType == 12) {//播放退出图
            rescontentid = TVResourceUtils.PLAY_EXIT_IMAGE;
        }else if (activityType == 5) {//条款
			return getTerms(resourceType);
		}
		ComboResource resourceVo = ComboResourceCreaters.getComboResource(rescontentid, pageParam, caller);
        if(null == resourceVo){
            return Collections.EMPTY_LIST;
        }
		List<BaseCvo> contents = resourceVo.getContents();
		if (CollectionUtils.isEmpty(contents)) {
			return null;
		}
		List<Activity> activities = transformResContent2Activity(contents, resourceType, caller, activityType);
		return activities;
	}

	private List<Activity> transformResContent2Activity(List<BaseCvo> contents, String resourceType, CallerParam caller, Integer activityType) {
		List<Activity> returnList = Lists.newArrayList();
		//资源类型
		List<Integer> resourceTypes = Lists.newArrayList();
		if (StringUtils.isNotBlank(resourceType)) {
			resourceTypes = LeStringUtils.commaString2IntegerList(resourceType);
		}
		for (BaseCvo content : contents) {
			if (activityType == 1) {
				content.setMobileImg(content.getImageUrl().get("169"));
				//todo ipad 的图
			}
			if (caller.getCallerId() == 1003 && StringUtils.isBlank(content.getMobileImg())) {
				continue;
			} else if (caller.getCallerId() == 1014 && StringUtils.isBlank(content.getIpadImg())) {
				continue;
			}
			Activity activity = activityResConverter.fillVo(content, activityType);
			//测试用
			if (activity.getType() == 2) {
				activity.setAdUse(0);
			}


			if (CollectionUtils.isNotEmpty(resourceTypes)) {
				if (resourceTypes.contains(activity.getResourceType())) {
					returnList.add(activity);
				}
			} else {
				returnList.add(activity);
			}
		}
		if (activityType == 2 && CollectionUtils.isNotEmpty(returnList)) {//开机图随机返回一张
			Random rand = new Random();
			return Arrays.asList(returnList.get(rand.nextInt(returnList.size())));
		}
		return returnList;
	}

	private List<Activity> getTerms(String resourceTypeString){
		List<Activity> returnList = Lists.newArrayList();
		GetActivitiesParam p = createGetActivitiesParam(ActivityType.findByValue(5), resourceTypeString);
		List<Long> ids = QmtConfigApis.getTActivityIds4SimpleSearch(p, null);
		if(CollectionUtils.isNotEmpty(ids)){
			List<TActivity> tActivities = QmtConfigApis.getTActivitiesById(ids);
			if(CollectionUtils.isNotEmpty(tActivities)){
				for (TActivity tActivity : tActivities) {
					Activity activity = activityResConverter.getVo();
					LeBeanUtils.copyNotEmptyPropertiesQuietly(activity, tActivity);
					returnList.add(activity);
				}
			}
		}
		return returnList;
	}

    @Override
    public TActivity getActivity(ActivityType activityType, String resourceTypeString) {

        GetActivitiesParam p = createGetActivitiesParam(activityType, resourceTypeString);
        List<Long> ids = QmtConfigApis.getTActivityIds4SimpleSearch(p, null);
        TActivity tActivity = null;
        if(CollectionUtils.isNotEmpty(ids)){
            tActivity = QmtConfigApis.getTActivityById(ids.get(0));
        }
        return tActivity;

    }

    @Override
    public TActivity getActivity(GetActivitiesParam p) {

        List<Long> ids = QmtConfigApis.getTActivityIds4SimpleSearch(p, null);
        TActivity tActivity = null;
        if(CollectionUtils.isNotEmpty(ids)){
            tActivity = QmtConfigApis.getTActivityById(ids.get(0));
        }
        return tActivity;

    }

    private GetActivitiesParam createGetActivitiesParam(ActivityType activityType, String resourceTypeString) {

        GetActivitiesParam p = new GetActivitiesParam();
        if(activityType != null){
            p.setActivityType(activityType);
        }
        return p;
    }
}
