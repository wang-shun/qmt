package com.lesports.qmt.app.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.SearchService;
import com.lesports.qmt.web.api.core.vo.SearchResult;
import com.lesports.qmt.web.api.core.vo.SearchSuggestItem;
import com.lesports.qmt.web.api.core.vo.SearchSuggestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * lesports-projects
 * Adapt search data for le sports app.
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
@Path("/search")
public class SearchResource {
    private static final Logger LOG = LoggerFactory.getLogger(SearchResource.class);

    @Inject
	SearchService searchService;

    @GET
    @LJSONP
    @Path("/")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public SearchResult search(@QueryParam("q") @DefaultValue("") String word,
                                     @QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("count") @DefaultValue("20") int count,
                                     @QueryParam("uid") @DefaultValue("") String uid,
                                     @QueryParam("imei") @DefaultValue("") String imei,
                                     @QueryParam("hl") @DefaultValue("") String hl,
                                     @QueryParam("device_id") @DefaultValue("") String deviceId,
                                     @BeanParam CallerBean callerBean) {
        try {
            if (searchService.valid(word)) {
                return searchService.search(word, page, count, uid, imei,hl,callerBean.getCallerParam(),deviceId);
            } else {
                SearchResult result = new SearchResult();
                result.setPage(0);
                result.setCount(0);
                return result;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/suggest")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public SearchSuggestResult top(@QueryParam("q") @DefaultValue("") String word,
                                   @QueryParam("count") @DefaultValue("10") int count,
                                   @QueryParam("caller") long callerId) {
        try {
            SearchSuggestResult result = new SearchSuggestResult();
            if (searchService.valid(word)) {
                List<SearchSuggestItem> items= searchService.suggest(word,callerId, count);
                result.setSuggests(items);
                result.setPage(1);
                result.setCount(null != items ? items.size() : 0);
            } else {
                result.setCount(0);
                result.setPage(0);
            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
