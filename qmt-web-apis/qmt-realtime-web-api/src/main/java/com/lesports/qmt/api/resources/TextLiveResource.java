package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.TextLiveService;
import com.lesports.qmt.web.api.core.vo.TextLiveMessageVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by lufei1 on 2015/9/17.
 */
@Path("/tlive/v1")
public class TextLiveResource {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveResource.class);

    @Inject
    public TextLiveService textLiveService;

    @GET
    @LJSONP
    @Path("liveMessages")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TextLiveMessageVo> getLiveMessageByIds(@QueryParam("ids") String ids,
                                                       @BeanParam CallerBean caller) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个以英文逗号隔开");
            return textLiveService.getLiveMessageByIdsRealtime(ids, caller.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
