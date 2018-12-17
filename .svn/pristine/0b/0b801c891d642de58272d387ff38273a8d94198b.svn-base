package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.TvSuggestService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.ReCommendTheme;
import com.lesports.utils.SSOApi;
import com.lesports.utils.pojo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
* Created by ruiyuansheng on 2016/1/11.
*/
@Path("/suggests")
public class SuggestResource {



    private static final Logger LOG = LoggerFactory.getLogger(SuggestResource.class);


    @Inject
    TvSuggestService tvSuggestService;


    /**
     * 获取tv推荐
     * @return
     */
    @GET
    @LJSONP
    @Path("/")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getTvSuggest(@QueryParam("type") int type,
                                            @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(type != 0, "请传入type类型，1是TV桌面，2是TV首页，3是比赛大厅推荐,4是TV桌面2.0");
            Map<String, Object> results = Maps.newHashMap();
            switch (type){
                case 1:
                    return  tvSuggestService.getTvDesktopVo(callerBean.getCallerParam());
                case 2:
                    return  tvSuggestService.getTvHomePageVo(callerBean.getCallerParam());
                case 3:
                    return  ResponseUtils.createPageResponse(1, tvSuggestService.getTvMatchHallVo(callerBean.getCallerParam()));
                case 4:
                    return  tvSuggestService.getTvDesktopVo(callerBean.getCallerParam());//tv桌面2.0
                case 5:
                    return  tvSuggestService.getTvDesktopVo(callerBean.getCallerParam());//tv桌面2.1，带轮播图
                default: return  results;
            }

        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取tv推荐
     * @return
     */
    @GET
    @LJSONP
    @Path("/recommends")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<Object> getTvRecommends(@QueryParam("userToken") String userToken,
                                             @QueryParam("lc") String lc,
                                             @QueryParam("type") @DefaultValue("1") int type,
                                             @QueryParam("page") @DefaultValue("1") int page,
                                             @BeanParam CallerBean callerBean) {
        try {
            String uid = "";
            //根据用户tk获取uid
            if(StringUtils.isNotEmpty(userToken)) {
                UserVo userVo = SSOApi.getUserByToken(userToken);
                if (null != userVo && StringUtils.isNotEmpty(userVo.getUid())) {
                    uid = userVo.getUid();
                }
            }
            return  tvSuggestService.getRecommends(uid,lc,type,page,callerBean.getCallerParam());

        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取tv推荐
     * @return
     */
    @GET
    @LJSONP
    @Path("/themes/{themeId}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public ReCommendTheme getTvSuggestByThemeId(@PathParam("themeId") String themeId,
                                                @BeanParam CallerBean callerBean) {
        try {
            return  tvSuggestService.getTvSuggestByThemeId(themeId);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
