package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.api.dto.MenuResourceType;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.cache.impl.FallbackCache;
import com.lesports.qmt.web.api.core.creater.MenuVoCreater;
import com.lesports.qmt.web.api.core.service.MenuService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.MenuVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/3/22.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Inject
    private FallbackCache fallbackCache;
	@Inject
	private MenuVoCreater menuVoCreater;

    @Override
    public TMenu getMenuWithFallback(long id,CallerParam callerParam) {
		TMenu tMenu = null;
        try {
            tMenu = QmtConfigApis.getTMenuById(id);
        } catch (Exception e) {
            LOG.error("get menu error. id : {}", id, e);
        }
        if (tMenu == null) {
            LOG.info("menu list begin to enter fallback.id : {}", id);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("id", String.valueOf(id));
            tMenu = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_MENU, paramMap, TMenu.class);
        }

        return tMenu;

    }

	@Override
	public Long getNewsResourceId(long index) {
		TMenu tMenu = null;
		try {
			tMenu = QmtConfigApis.getTMenuById(300019018l);
			if (null != tMenu) {
				List<TMenuItem> items = tMenu.getItems();
				for (TMenuItem item : items) {
					List<TMenuItem> subItems = item.getSubItems();
					if (CollectionUtils.isNotEmpty(subItems)) {
						for (TMenuItem subItem : subItems) {
							if (subItem.getIndex() == index) {
								return subItem.getNewsResourceId();
							}
						}
					}
					if (item.getIndex() == index) {
						return item.getNewsResourceId();
					}
				}
			}
		} catch (Exception e) {
			LOG.error("get news resourceId error. index : {}", index, e);
		}
		return null;
	}


	@Override
	public TMenuItem getNewsResourceItem(long index) {
		TMenu tMenu = null;
		try {
			tMenu = QmtConfigApis.getTMenuById(300019018l);
			if (null != tMenu) {
				List<TMenuItem> items = tMenu.getItems();
				for (TMenuItem item : items) {
					List<TMenuItem> subItems = item.getSubItems();
					if (CollectionUtils.isNotEmpty(subItems)) {
						for (TMenuItem subItem : subItems) {
							if (subItem.getIndex() == index) {
								return subItem;
							}
						}
					}
					if (item.getIndex() == index) {
						return item;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("get news resourceId error. index : {}", index, e);
		}
		return null;
	}
}
