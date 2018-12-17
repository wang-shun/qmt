package com.lesports.qmt.api.resources;

import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.SearchService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.SearchResult;
import com.lesports.qmt.web.api.core.vo.SearchSuggestItem;
import com.lesports.qmt.web.api.core.vo.SearchSuggestResult;
import com.lesports.utils.pojo.SearchData;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * lesports-projects
 * Adapt search data for lesports.
 *
 * @author ruiyuansheng
 * @since 15-11-20
 */
@Path("/")
public class SearchResource {
    private static final Logger LOG = LoggerFactory.getLogger(SearchResource.class);

    @Inject
    SearchService searchService;


    @GET
    @LJSONP
    @Path("/search/stars")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> searchStars(@QueryParam("wd") @DefaultValue("") String word,
                                          @QueryParam("page") @DefaultValue("1") int page,
                                          @QueryParam("count") @DefaultValue("20") int count,
                                          @QueryParam("from") @DefaultValue("pc") String from,
                                          @QueryParam("uid") @DefaultValue("") String uid,
                                          @QueryParam("imei") @DefaultValue("") String imei,
                                          @QueryParam("hl") @DefaultValue("") String hl,
                                          @QueryParam("device_id") @DefaultValue("") String deviceId,
                                          @QueryParam("caller") long callerId) {
        try {
            if (searchService.valid(word)) {
                return ResponseUtils.createPageResponse(page, searchService.searchStars(word, page, count, callerId, uid, imei, hl, deviceId));
            } else {
                SearchResult result = new SearchResult();
                result.setPage(0);
                result.setCount(0);
                return ResponseUtils.createEmptyPageResponse(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @LJSONP
    @Path("/search/lives")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> searchLives(@QueryParam("wd") @DefaultValue("") String word,
                                          @QueryParam("page") @DefaultValue("1") int page,
                                          @QueryParam("count") @DefaultValue("20") int count,
                                          @QueryParam("from") @DefaultValue("pc") String from,
                                          @QueryParam("uid") @DefaultValue("") String uid,
                                          @QueryParam("imei") @DefaultValue("") String imei,
                                          @QueryParam("hl") @DefaultValue("") String hl,
                                          @QueryParam("device_id") @DefaultValue("") String deviceId,
                                          @BeanParam CallerBean callerBean) {
        try {
            if (searchService.valid(word)) {
                return ResponseUtils.createPageResponse(page, searchService.searchLives(word, page, count, uid, imei,hl,callerBean.getCallerParam(),deviceId));
            } else {
                SearchResult result = new SearchResult();
                result.setPage(0);
                result.setCount(0);
                return ResponseUtils.createEmptyPageResponse(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/search/subjects")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> searchSubjects(@QueryParam("wd") @DefaultValue("") String word,
                                          @QueryParam("page") @DefaultValue("1") int page,
                                          @QueryParam("count") @DefaultValue("20") int count,
                                          @QueryParam("from") @DefaultValue("pc") String from,
                                          @QueryParam("uid") @DefaultValue("") String uid,
                                          @QueryParam("imei") @DefaultValue("") String imei,
                                          @QueryParam("hl") @DefaultValue("") String hl,
                                          @QueryParam("device_id") @DefaultValue("") String deviceId,
                                          @QueryParam("caller") long callerId) {
        try {
            if (searchService.valid(word)) {
                return ResponseUtils.createPageResponse(page, searchService.searchSubjects(word, page, count, callerId, uid, imei,hl,deviceId));
            } else {
                SearchResult result = new SearchResult();
                result.setPage(0);
                result.setCount(0);
                return ResponseUtils.createEmptyPageResponse(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/search/albums")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> searchAlbums(@QueryParam("wd") @DefaultValue("") String word,
                                             @QueryParam("page") @DefaultValue("1") int page,
                                             @QueryParam("count") @DefaultValue("20") int count,
                                             @QueryParam("from") @DefaultValue("pc") String from,
                                             @QueryParam("uid") @DefaultValue("") String uid,
                                             @QueryParam("imei") @DefaultValue("") String imei,
                                             @QueryParam("hl") @DefaultValue("") String hl,
                                             @QueryParam("device_id") @DefaultValue("") String deviceId,
                                             @QueryParam("caller") long callerId) {
        try {
            if (searchService.valid(word)) {
                return ResponseUtils.createPageResponse(page, searchService.searchAlbums(word, page, count, callerId, uid, imei,hl,deviceId));
            } else {
                SearchResult result = new SearchResult();
                result.setPage(0);
                result.setCount(0);
                return ResponseUtils.createEmptyPageResponse(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/search/videos")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> searchVideos(@QueryParam("wd") @DefaultValue("") String word,
                                           @QueryParam("page") @DefaultValue("1") int page,
                                           @QueryParam("count") @DefaultValue("20") int count,
                                           @QueryParam("from") @DefaultValue("pc") String from,
                                           @QueryParam("uid") @DefaultValue("") String uid,
                                           @QueryParam("imei") @DefaultValue("") String imei,
                                           @QueryParam("hl") @DefaultValue("") String hl,
                                           @QueryParam("dur") @DefaultValue("") String dur,
                                           @QueryParam("release_range") @DefaultValue("") String releaseRange,
                                           @QueryParam("style") @DefaultValue("") String style,
                                           @QueryParam("or") @DefaultValue("") String or,
                                           @QueryParam("device_id") @DefaultValue("") String deviceId,
                                           @QueryParam("caller") long callerId) {
        try {
            if (searchService.valid(word)) {
                Map<String, Object> results = Maps.newHashMap();
                List<SearchData.VideoInfo> videoInfos = searchService.searchVideos(word, page, count,callerId, uid, imei,hl,dur,releaseRange,style,or,deviceId);
                if(CollectionUtils.isEmpty(videoInfos)){
                    return ResponseUtils.createEmptyPageResponse(0);
                }
                results.put(LeConstants.PAGE_KEY, page);
                results.put(LeConstants.COUNT_KEY, videoInfos.size());
                results.put("videoCount", searchService.searchVideoCount(word, page, count,callerId, uid, imei,hl,dur,releaseRange,style,or,deviceId));
                results.put(LeConstants.ENTRIES_KEY, videoInfos);
                return results;
            } else {
                return ResponseUtils.createEmptyPageResponse(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/search/suggests")
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
