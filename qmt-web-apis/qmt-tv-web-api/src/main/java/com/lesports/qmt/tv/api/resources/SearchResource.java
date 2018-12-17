package com.lesports.qmt.tv.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.service.SearchService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.SearchResult4Tv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

/**
 * lesports-projects
 * Adapt search data for le sports app.
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
@Path("/")
public class SearchResource {
    private static final Logger LOG = LoggerFactory.getLogger(SearchResource.class);

    @Inject
    SearchService searchService;

    @Inject
    VideoService videoService;

    @GET
    @LJSONP
    @Path("search")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public SearchResult4Tv search(@QueryParam("q") @DefaultValue("") String word,
                                     @QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("count") @DefaultValue("20") int count,
                                     @QueryParam("uid") @DefaultValue("") String uid,
                                     @QueryParam("imei") @DefaultValue("") String imei,
                                     @QueryParam("hl") @DefaultValue("") String hl,
                                     @QueryParam("device_id") @DefaultValue("") String deviceId,
                                     @BeanParam CallerBean callerBean) {
        try {
            if (searchService.valid(word)) {
                return searchService.searchTV(word, page, count,callerBean.getCallerParam(), uid, imei,hl,deviceId);
            } else {
                SearchResult4Tv result = new SearchResult4Tv();
                result.setPage(0);
                result.setCount(0);
                return result;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GET
//    @LJSONP
//    @Path("searchNullSuggest")
//    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
//    public Map<String,Object> searchNullSuggest(@QueryParam("lc") @DefaultValue("") String lc,
//                               @QueryParam("uid") @DefaultValue("") String uid,
//                               @QueryParam("count") @DefaultValue("10") int count,
//                               @BeanParam CallerBean callerBean) {
//        try {
//           return ResponseUtils.createPageResponse(1, videoService.getVideosBySuggest(lc, uid, count, callerBean.getCallerParam()));
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GET
    @LJSONP
    @Path("search/suggest")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> top(@QueryParam("q") @DefaultValue("") String word,
                                   @QueryParam("count") @DefaultValue("10") int count,
                                   @QueryParam("caller") long callerId) {
        try {
            return ResponseUtils.createPageResponse(1, searchService.suggest(word,callerId, count));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GET
//    @LJSONP
//    @Path("keywords/suggest")
//    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
//    public Map<String,Object> keyWords(@BeanParam PageBean pageBean,
//                                       @BeanParam CallerBean callerBean) {
//        try {
//            return ResponseUtils.createPageResponse(pageBean.getPage(), QmtConfigApis.getTSuggests(pageBean.getPageParam(), callerBean.getCallerParam()));
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
