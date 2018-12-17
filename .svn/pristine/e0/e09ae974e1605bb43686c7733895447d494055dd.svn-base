package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ETag;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.creater.MenuVoCreater;
import com.lesports.qmt.web.api.core.service.MenuService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 15-7-9 : 下午8:06
 */
@Path("/")
public class MenuResource {

    private static final Logger LOG = LoggerFactory.getLogger(MenuResource.class);

    @Inject
    MenuService menuService;

    @Inject
    MenuVoCreater menuVoCreater;


    @GET
    @ETag
    @LJSONP
    @Path("/menus")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public MenuVo getMenuByType(@QueryParam("location") String location,
                                @BeanParam CallerBean callerBean) {
        try {
            return menuVoCreater.createMenuVo(menuService.getMenuWithFallback(Constants.MENU_MAP.get(location+"_"+callerBean.getCallerParam().getPubChannel()), callerBean.getCallerParam()),callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GET
    @ETag
    @LJSONP
    @Path("/menus/menuItems")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getMenuItems(
            @QueryParam("resourceId") long resourceId,
            @BeanParam CallerBean callerBean) {
        Preconditions.checkArgument(resourceId != 0, "请传入resourceId");
        List<MenuVo.MenuItemVo> menuItemVoList = new ArrayList<>();
        try {
            TMenu tMenu = QmtConfigApis.getTMenuById(Constants.MENU_MAP.get("channel_" + callerBean.getPubChannel()));
            if(null != tMenu) {
                MenuVo menuVo = menuVoCreater.createMenuVo(tMenu, callerBean.getCallerParam());
                List<MenuVo.MenuItemVo> menuItemVos = menuVo.getItems();
                for (MenuVo.MenuItemVo menuItemVo : menuItemVos) {
                    if (menuItemVo.getLevel() == 1 && menuItemVo.getResourceId() == resourceId) {
                        menuItemVoList = menuItemVo.getSubItems();
                    }
                }
            }
            return ResponseUtils.createPageResponse(1, menuItemVoList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
