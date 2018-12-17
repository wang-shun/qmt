package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ETag;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.web.api.core.service.CmsService;
import com.lesports.qmt.web.api.core.service.NewsService;
import com.lesports.qmt.web.api.core.service.TopListService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.BlockContentVo;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.sms.service.MedalService;
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
* Created by ruiyuansheng on 2015/10/20.
*/
@Path("/")
public class FocusResource {
    private static final Logger LOG = LoggerFactory.getLogger(FocusResource.class);

    @Inject
    private TopListService topListService;

    @Inject
    private NewsService newsService;

    @Inject
    private CmsService cmsService;


//    private static final  long euroCupId = LeProperties.getLong("euro.cup.cid", 100493001L);
//    private static final  long americaCupId = LeProperties.getLong("america.cup.cid", 234001L);
//    private static final  long eplId = LeProperties.getLong("epl.cid", 20001L);
//    private static final  long olympicId = LeProperties.getLong("olympic.cid", 100507001L);
    private static final  long worldCupId = LeProperties.getLong("worldcup.cid", 101425001L);

    @GET
    @ETag
    @LJSONP
    @Path("/focus/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<BlockContentVo> getFocusById(@PathParam("id") long id,
                                                   @QueryParam("type") @DefaultValue("0") int type,
                                                   @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入焦点id");
           if(id == worldCupId){
                return getCmsBolckContent4WorldCup(id, type, Constants.WORLDCUP_MAP, callerBean);
            }
            return cmsService.getBlockContents(id, callerBean);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private List<BlockContentVo> getCmsBolckContent4WorldCup(long id,int type,String map,CallerBean callerBean){
        List<BlockContentVo> blockContents = Lists.newArrayList();
        if(1 == type){
            //todo,china
            blockContents = cmsService.getBlockContents(0L, callerBean);
            GetRelatedNewsParam p  = new GetRelatedNewsParam();
            p.setRelatedIds(Lists.newArrayList(Constants.CHIAN_MAN_FOOTBALL_TAG));
            p.setTypes(parseNewsTypes("1"));
            List<NewsVo> newsVos = newsService.getNewsListByRelatedId(p, PageUtils.createPageParam(1, 18), callerBean.getCallerParam());
            if(CollectionUtils.isNotEmpty(newsVos)) {
                blockContents.addAll(cmsService.createBlockContents(newsVos,callerBean.getCallerParam()));
            }
            return blockContents;

        }else if(2 == type){
            //todo,asia
            blockContents = cmsService.getBlockContents(0, callerBean);
            GetRelatedNewsParam p  = new GetRelatedNewsParam();
            p.setRelatedIds(Lists.newArrayList(Constants.ASIA_MAN_FOOTBALL_TAG));
            p.setTypes(parseNewsTypes("1"));
            List<NewsVo> newsVos = newsService.getNewsListByRelatedId(p, PageUtils.createPageParam(1, 18), callerBean.getCallerParam());
            if(CollectionUtils.isNotEmpty(newsVos)) {
                blockContents.addAll(cmsService.createBlockContents(newsVos,callerBean.getCallerParam()));
            }
            return blockContents;
        }else if(3 == type){
            //todo,album
            return cmsService.getBlockContents(0, callerBean);
        }
        return cmsService.getBlockContents(id, callerBean);
    }

    private List<NewsType> parseNewsTypes(String typeString) {
        List<Long> typeArray = LeStringUtils.commaString2LongList(typeString);
        List<NewsType> typeList = Lists.newArrayList();
        for (Long typeValue : typeArray) {
            NewsType t = NewsType.findByValue(LeNumberUtils.toInt(typeValue));
            if (t != null) {
                typeList.add(t);
            }
        }
        return typeList;
    }


    /**
     * 欧洲杯和美洲杯,奥运，12强赛专用
     * @param id
     * @param callerBean
     * @return
     */
    @GET
    @ETag
    @LJSONP
    @Path("/suggest/focus/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> suggestFocus(@PathParam("id") long id,
                                           @BeanParam CallerBean callerBean) {
        Preconditions.checkArgument(id != 0, "请传入焦点id");
        Map<String, Object> results = Maps.newHashMap();
        try {
          if(id == worldCupId){
                results = getCmsSuggest4WorldCup(id, callerBean);
          }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return results;
    }




    private Map<String, Object> getCmsSuggest4WorldCup(long id, CallerBean callerBean) {
        Map<String, Object> results = Maps.newHashMap();
        //todo,suggest
        results.put("suggests", cmsService.getBlockContents(0L, callerBean));
        GetSeasonTopListsParam p = new GetSeasonTopListsParam();
        p.setCid(id);
        p.setType(100162000L);
        Sort sort = new Sort();
        sort.addToOrders(new Order("group", Direction.ASC));
        results.put("toplists", topListService.getSeasonTopLists(p, PageUtils.createPageParam(1, 20, sort), callerBean.getCallerParam()));
        return results;
    }



    //todo,会员引导图

//    /**
//     * 试看结束后会员引导图
//     * 22: 英超会员，21：欧洲杯会员，23：超级体育会员
//     * @return
//     */
//    @GET
//    @ETag
//    @LJSONP
//    @Path("/focus/user/guide/")
//    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
//    public List<BlockContentVo> getFocusUserGuide(@QueryParam("id") long id,
//                                                        @QueryParam("type") String type,
//                                                        @BeanParam CallerBean callerBean) {
//        try {
//                return cmsService.getCmsFocusData4TV(Constants.TV_USER_GUIDE_IMAGE_CN, callerBean.getCallerParam());
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
