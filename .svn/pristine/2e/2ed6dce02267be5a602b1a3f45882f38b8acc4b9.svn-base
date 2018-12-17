package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.MixedService;
import com.lesports.qmt.web.api.core.vo.MixedVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Created by gengchengliang on 2016/5/12.
 */
@Path("/")
public class MixedResource {

	private static final Logger LOG = LoggerFactory.getLogger(MixedResource.class);

	@Inject
	private MixedService mixedService;

	/**
	 * 查询赛程详情
	 *
	 * ids 赛程id,多个用英文逗号隔开,每次最多10个
	 */
	@GET
	@LJSONP
	@Path("mixed")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public MixedVo getMixed (@QueryParam("caller") long callerId,
										 @QueryParam("channelId") long channelId,
										 @QueryParam("type") @DefaultValue("0,1,2,3,4,5") String type,
										 @QueryParam("episodeSource") @DefaultValue("0") String episodeSource,
										 @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(channelId != 0, "请传入channelId");
			return mixedService.getMixed(channelId, type, episodeSource, callerBean.getCallerParam());
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
