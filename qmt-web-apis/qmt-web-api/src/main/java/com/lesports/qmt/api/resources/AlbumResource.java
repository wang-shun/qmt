package com.lesports.qmt.api.resources;

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
 * Created by ruiyuansheng on 2015/6/30.
 */
@Path("/")
public class AlbumResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumResource.class);
    @Inject
    AlbumService albumService;

    /**
     * 获取所有专辑列表
     *
     * @param pageBean
     * @param callerBean
     * @return
     */
    @LJSONP
    @GET
    @Path("albums")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getAllTAlbums (@BeanParam PageBean pageBean,
											  @QueryParam("tagId") @DefaultValue("0") long tagId,
                                              @BeanParam CallerBean callerBean){

        try {
            List<AlbumVo> albumVos = albumService.getTAlbums(tagId, pageBean.getPageParam(),callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), albumVos);
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
