package com.lesports.qmt.tv.api.resources;

import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.web.api.core.service.TopListService;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import com.lesports.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class TopListResource {

	private static final Logger LOG = LoggerFactory.getLogger(TopListResource.class);

	@Inject
    TopListService topListService;


    /**
     * 获取某赛事的某种榜单数据
     * @param cid 赛事id
     * @param csid 赛季id 如果为0的时候为最新赛季 默认为最新赛季 (默认为0)
     * @param type 榜单类型id
     * @return
     */
    @GET
    @LJSONP
    @Path("/toplists")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TopListVo getTopListsByType (@QueryParam("cid") long cid,
                                        @QueryParam("csid") @DefaultValue("0") long csid,
                                        @QueryParam("type") @DefaultValue("0") long type,
                                        @BeanParam CallerBean callerBean) {
        try {
            if(cid <= 0){
                throw new LeWebApplicationException("赛事参数非法", LeStatus.PARAM_INVALID);
            }
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
            p.setType(type);
            Sort sort = new Sort();
            sort.addToOrders(new Order("group", Direction.ASC));

            TopListVo topList = topListService.getSeasonTopList4TV(p, PageUtils.createPageParam(1, 20, sort), callerBean.getCallerParam());

            return topList;
        } catch (LeWebApplicationException e){
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
