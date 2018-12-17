package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.web.api.core.service.AlbumService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.AlbumVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * 自制节目专辑
 * Created by ruiyuansheng on 2015/6/30.
 */
@Path("/albums")
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
    @Path("/")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getAllTAlbums (@BeanParam PageBean pageBean,
                                              @BeanParam CallerBean callerBean){

        try {
            List<AlbumVo> albumVos = albumService.getAllAlbums(pageBean.getPageParam(),callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), albumVos);
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public AlbumVo getAlbumById(@PathParam("id") long id,
                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入专辑id");

            return albumService.getAlbumById(id,callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
