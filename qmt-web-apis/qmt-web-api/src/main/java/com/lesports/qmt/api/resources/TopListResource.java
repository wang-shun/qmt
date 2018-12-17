package com.lesports.qmt.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.web.api.core.service.TopListService;
import com.lesports.qmt.web.api.core.util.CollectionTools;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

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
    public TopListVo getTopLists(@QueryParam("cid") long cid,
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
            TopListVo topList = topListService.getSeasonTopList(p,null,callerBean.getCallerParam());
            if (null == topList) {
                LOG.warn("fail to get getTopLists, cid : {}, csid : {}, type :{} ", cid, csid, type);
            }
            return topList;
        } catch (LeWebApplicationException e){
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据榜单id获取榜单数据
     * @param id 榜单id
     * @return
     */
    @GET
    @LJSONP
    @Path("/toplists/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TopListVo getTopLists(@PathParam("id") long id,
								 @BeanParam CallerBean callerBean) {
        try {
            TopListVo topList = topListService.getTopListById(id, callerBean.getCallerParam());
            if (null == topList) {
                LOG.warn("fail to get getTopListById, id : {}", id);
                throw new LeWebApplicationException("没有该榜单", LeStatus.PARAM_INVALID);
            }
            return topList;
        } catch (LeWebApplicationException e){
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取某赛事的榜单数据
     * @param cid 赛事id
     * @param csid 赛季id 如果为0的时候为最新赛季 默认为最新赛季 (默认为0)
     * @return
     */
    @GET
    @LJSONP
    @Path("/competitions/{cid}/toplists")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TopListVo> getSeasonTopLists(@PathParam("cid") long cid,
                                             @QueryParam("csid") @DefaultValue("0") long csid,
											 @QueryParam("type") @DefaultValue("100162000") long type,
											 @QueryParam("types") String types,
                                             @BeanParam CallerBean callerBean) {
        try {
            if(cid <= 0){
                throw new LeWebApplicationException("赛事参数非法", LeStatus.PARAM_INVALID);
            }
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
			p.setType(type);
			if (StringUtils.isNotBlank(types) && !types.equals("0")) {
				p.setTypes(CollectionTools.string2List(types));
				//type与types两个参数为互斥关系,优先以types为准,如果types不为空则type置为0(保留type是为了兼容之前在用的接口)
				p.setType(0);
			}
            return topListService.getSeasonTopLists(p,null,callerBean.getCallerParam());
        } catch (LeWebApplicationException e){
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * 获取香港NBA的榜单信息
	 * @return
	 */
	@GET
	@LJSONP
	@Path("/nba/toplists")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public Map<String, Map<String, List<TopListVo>>> getNBATopLists(@QueryParam("csid") @DefaultValue("0") long csid,
												  @BeanParam CallerBean callerBean) {
		try {
			Map<String, Map<String, List<TopListVo>>> topListMap = topListService.getNbaTopList(csid, callerBean.getCallerParam());
			return topListMap;
		} catch (LeWebApplicationException e){
			LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error("fail to get getTopLists, error : {}", e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
