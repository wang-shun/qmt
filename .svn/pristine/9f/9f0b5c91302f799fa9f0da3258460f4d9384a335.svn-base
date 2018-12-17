package com.lesports.qmt.web.api.core.service.impl;

import client.SopsApis;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.service.MenuService;
import com.lesports.qmt.web.api.core.service.MixedService;
import com.lesports.qmt.web.api.core.service.NewsService;
import com.lesports.qmt.web.api.core.util.AppResourceContentIdConstants;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.MixedVo;
import com.lesports.qmt.web.api.core.vo.RecommendVo;
import com.lesports.sms.api.common.ConfigType;
import com.lesports.sms.api.vo.TConfig;
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.LeZhangyuApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
* Created by gengchengliang on 2016/4/15.
*/
@Service("mixedService")
public class MixedServiceImpl implements MixedService {

    private static final long MIXED_REFRESH_MENU_ID = LeProperties.getLong("lesports.mixed.refresh.menu.id", 300019018);

	//热门推荐的tagId
	private static final Long RECOMMEND_TAG_ID = LeProperties.getLong("lesports.recommend.tag.id", 100153008l);

    private static final Logger LOG = LoggerFactory.getLogger(MixedServiceImpl.class);

	private static final List<Long> DIVERSION_2_ZHANGYU_CALLER_LIST = Lists.newArrayList(1003l, 1016l);

    @Resource
    private NewsService newsService;

    @Resource
    private EpisodeService episodeService;

	@Resource
	private MenuService menuService;


	/**
	 * 构建mixed
	 *
	 * @param channelId
	 * @param type
	 * @param callerParam
	 * @return
	 */
	@Override
	public MixedVo getMixed(long channelId, String type, String episodeSource, CallerParam callerParam) {
		long carouselResourceId = 0l;
		long matchResourceId = 0l;
		TMenuItem menuItem = null;
		if (channelId == RECOMMEND_TAG_ID) {//首页推荐
			carouselResourceId = AppResourceContentIdConstants.RECOMMEND_CAROUSEL_RESOURCEID;
			matchResourceId = AppResourceContentIdConstants.RECOMMEND_MATCH_RESOURCEID;
		} else {
			TMenu tMenu = menuService.getMenuWithFallback(MIXED_REFRESH_MENU_ID, callerParam);
			if (tMenu == null) {
				return null;
			}
			Long index = Constants.RESOURCEID_2_INDEX.get(channelId);

			if (index != null && index > 0) {//原app菜单item
				menuItem = getChannelItemByIndex(index, tMenu);
			} else {//新增的item
				menuItem = getChannelItem(channelId, tMenu);
			}
			if (menuItem == null) {
				return null;
			}
			carouselResourceId = menuItem.getCarouselResourceId();
			matchResourceId = menuItem.getMatchResourceId();
		}

		MixedVo mixedVo = new MixedVo();
		if (carouselResourceId > 0) {
			PageParam pageParam = new PageParam();
			pageParam.setPage(0);
			pageParam.setCount(20);
			List<RecommendVo> focus = newsService.getCmsFocusNews4APP(carouselResourceId, type, pageParam, callerParam);
			if (CollectionUtils.isNotEmpty(focus)) {
				mixedVo.setFocus(focus);
			}
		}
		List<HallEpisodeVo> hallEpisodes = Lists.newArrayList();
		if (matchResourceId > 0) {
			PageParam pageParam = new PageParam();
			pageParam.setPage(0);
			pageParam.setCount(5);
			hallEpisodes = episodeService.getResourceContentEpisodes(matchResourceId, pageParam, callerParam);
		}

		List<Integer> sources = LeStringUtils.commaString2IntegerList(episodeSource);

		if (CollectionUtils.isNotEmpty(hallEpisodes)) {
			//章鱼导流: 只有基线版和超级手机有,并且sms后台的倒流开关打开,并且请求中带有章鱼参数,并且只有首页有
			if (sources.contains(1) && DIVERSION_2_ZHANGYU_CALLER_LIST.contains(callerParam.getCallerId()) && checkSmsSwitch(callerParam) && channelId == RECOMMEND_TAG_ID) {
				addZhangyuLiveEpisode(hallEpisodes);
			}
			if (CollectionUtils.isNotEmpty(hallEpisodes)) {
				//兼容线上版本,之前版本没有章鱼倒流功能
				Iterator<HallEpisodeVo> itemIterator = hallEpisodes.iterator();
				while (itemIterator.hasNext()) {
					HallEpisodeVo hallEpisode = itemIterator.next();
					if (!sources.contains(LeNumberUtils.toInt(hallEpisode.getEpisodeSource()))) {
						itemIterator.remove();
					}
				}
			}
		} else if (sources.contains(1) && DIVERSION_2_ZHANGYU_CALLER_LIST.contains(callerParam.getCallerId()) && checkSmsSwitch(callerParam) && channelId == RECOMMEND_TAG_ID) {
			addZhangyuLiveEpisode(hallEpisodes);
		}
		if (CollectionUtils.isNotEmpty(hallEpisodes)) {
			mixedVo.setEpisodes(hallEpisodes);
		}
		if (menuItem != null && menuItem.getMatchCid() > 0) {
			mixedVo.setCid(menuItem.getMatchCid());
			mixedVo.setIsMatch(1);
		}
		if (menuItem != null && menuItem.getToplistCid() > 0) {
			mixedVo.setCid(menuItem.getToplistCid());
			mixedVo.setIsTopList(1);
		}
		return mixedVo;
	}

	private TMenuItem getChannelItemByIndex (long index, TMenu tMenu) {
		List<TMenuItem> items = tMenu.getItems();
		if (CollectionUtils.isEmpty(items)) {
			return null;
		}
		TMenuItem channelItem = null;
		for (TMenuItem item : items) {
			if (item.getIndex() == index) {
				channelItem = item;
				break;
			}
			//二级菜单跳出循环条件
			boolean flag = false;
			if (CollectionUtils.isNotEmpty(item.getSubItems())) {
				for (TMenuItem menuItem : item.getSubItems()) {
					if (menuItem.getIndex() == index) {
						channelItem = menuItem;
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}
		if (channelItem == null) {
			return null;
		}
		return channelItem;
	}

	private TMenuItem getChannelItem (long resourceId, TMenu tMenu) {
		List<TMenuItem> items = tMenu.getItems();
		if (CollectionUtils.isEmpty(items)) {
			return null;
		}
		TMenuItem channelItem = null;
		for (TMenuItem item : items) {
			if (item.getNewsResourceId() == resourceId) {
				channelItem = item;
				break;
			}
			//二级菜单跳出循环条件
			boolean flag = false;
			if (CollectionUtils.isNotEmpty(item.getSubItems())) {
				for (TMenuItem menuItem : item.getSubItems()) {
					if (menuItem.getNewsResourceId() == resourceId) {
						channelItem = menuItem;
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}
		if (channelItem == null) {
			return null;
		}
		return channelItem;
	}





	private boolean checkSmsSwitch(CallerParam callerParam) {
		TConfig tConfig = SopsApis.getTConfigByTypeAndVersion(ConfigType.DIVERSION_2_ZHANGYU, "1.0", callerParam);
		if (tConfig == null || MapUtils.isEmpty(tConfig.getData())) {
			return false;
		}
		Map<String, String> tConfigData = tConfig.getData();
		String open = tConfigData.get("open");
		return LeNumberUtils.toBoolean(open);
	}

	private void addZhangyuLiveEpisode(List<HallEpisodeVo> hallEpisodes) {
		com.lesports.model.HallEpisodeVo leZhangyu = LeZhangyuApis.getLeZhangyu();
		if (leZhangyu == null) {
			return;
		}
		HallEpisodeVo hallEpisodeVo = new HallEpisodeVo();
		LeBeanUtils.copyNotEmptyPropertiesQuietly(hallEpisodeVo, leZhangyu);
		if (hallEpisodes.size() == 5) {
			hallEpisodes.remove(4);
		}
		hallEpisodes.add(hallEpisodeVo);
	}
}