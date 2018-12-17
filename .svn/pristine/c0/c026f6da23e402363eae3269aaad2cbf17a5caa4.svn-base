package com.lesports.qmt.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

/**
 * Created by wangjichuan on 2016/12/23.
 *
 * 资源位数据类
 */
@Path("/data")
public class DataResource {

	private static final Logger LOG = LoggerFactory.getLogger(DataResource.class);

    /**
     *
     * @param id 资源位Id
     * @param callerBean
     * @param pageBean
     * @return
     */
	@LOG_URL
	@GET
	@LJSONP
	@Path("/{id}")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public ComboResource getData(@PathParam("id") long id,
							 @BeanParam CallerBean callerBean,
                             @BeanParam PageBean pageBean) {
		try {
            return ComboResourceCreaters.getComboResource(id, pageBean.getPageParam(), callerBean.getCallerParam());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
