package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.helper.ShareHelper;
import com.lesports.qmt.web.model.Share;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/20
 */
@Path("/share")
public class ShareResource {
    private static final Logger LOG = LoggerFactory.getLogger(ShareResource.class);

    @Inject
    private ShareHelper shareHelper;

    @GET
    @LJSONP
    @Path("/")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Share list(@QueryParam("id") @DefaultValue("0") String id,
                      @QueryParam("type") @DefaultValue("0") int type,
                      @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(id), "请传入id");
            Share.ShareType shareType = Share.ShareType.findByValue(type);
            return shareHelper.getShareByType(id, shareType, callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
