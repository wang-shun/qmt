package com.lesports.qmt.app.api.resources;

import com.google.common.collect.Maps;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.web.api.core.service.AlbumService;
import com.lesports.qmt.web.api.core.vo.AlbumVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 自制节目专辑
 * Created by ruiyuansheng on 2015/6/30.
 */
@Path("/")
public class AlbumResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumResource.class);
    @Inject
	AlbumService albumService;

    /**
     * 获取自制节目专辑
     *
     * @return
     */
    @LJSONP
    @GET
    @Path("/albums")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getAllTAlbums(@BeanParam PageBean pageBean,
											 @QueryParam("tagId") @DefaultValue("0") long tagId,
                                             @BeanParam CallerBean callerBean) {

        try {
			PageParam pageParam = new PageParam();
			//兼容超级手机节目线上问题
			if (callerBean.getCallerId() == 1016 && pageBean.getCount() == 2 && pageBean.getPage() == 1) {
				pageParam = pageBean.getPageParam(pageBean.getPage(), 10);
			} else {
				pageParam = pageBean.getPageParam();
			}
			List<AlbumVo> albumVos = albumService.getTAlbums(tagId, pageParam, callerBean.getCallerParam());
            if (albumVos == null) {
                albumVos = Collections.EMPTY_LIST;
            }
            Map<String, Object> result = Maps.newHashMap();
            result.put("entries", albumVos);
            result.put("page", pageBean.getPage());
            result.put("count", albumVos.size());
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
