package com.lesports.qmt.app.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.web.api.core.service.TopListService;
import com.lesports.qmt.web.api.core.util.CollectionTools;
import com.lesports.qmt.web.api.core.vo.AppCompetitionSeasonTopListVo;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

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
	 * @return
	 */
	@GET
	@LJSONP
	@Path("/competitions/{cid}/toplists")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public AppCompetitionSeasonTopListVo getTopLists(@PathParam("cid") long cid,
                                 @QueryParam("csid") @DefaultValue("0") long csid,
								 @BeanParam CallerBean callerBean) {
		try {
            if(cid <= 0){
                throw new LeWebApplicationException("赛事参数非法", LeStatus.PARAM_INVALID);
            }
			//香港NBA榜单前端未做国际化,运维未配置
//			if (callerBean.getCallerId() == 3003 && cid == 44001l) {
//				return null;
//			}
			GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
			return topListService.getSeasonTopLists4App(p,callerBean.getCallerParam());
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
	@Path("/competitions/{cid}/original/toplists")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public List<TopListVo> getSeasonTopLists(@PathParam("cid") long cid,
											 @QueryParam("csid") @DefaultValue("0") long csid,
											 @QueryParam("types") String types,
											 @BeanParam CallerBean callerBean) {
		try {
			if(cid <= 0){
				throw new LeWebApplicationException("赛事参数非法", LeStatus.PARAM_INVALID);
			}
			GetSeasonTopListsParam p = new GetSeasonTopListsParam();
			p.setCid(cid);
			p.setCsid(csid);
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

}
