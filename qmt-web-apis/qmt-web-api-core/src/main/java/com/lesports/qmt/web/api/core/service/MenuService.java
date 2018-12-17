package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.web.api.core.vo.MenuVo;

/**
 * Created by lufei1 on 2016/3/22.
 */
public interface MenuService {

    public TMenu getMenuWithFallback(long id, CallerParam callerParam);

	Long getNewsResourceId(long index);

	TMenuItem getNewsResourceItem(long index);
}
