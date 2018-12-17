package com.lesports.qmt.app.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ETag;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.creater.AppMenuVoCreater;
import com.lesports.qmt.web.api.core.service.MenuService;
import com.lesports.qmt.web.api.core.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

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
	AppMenuVoCreater appMenuVoCreater;

    @GET
    @ETag
    @LJSONP
    @Path("/menus/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public MenuVo search(@PathParam("id") long id,
                        @BeanParam CallerBean callerBean) {
        try {
            return appMenuVoCreater.createMenuVo(menuService.getMenuWithFallback(id, callerBean.getCallerParam()), callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
