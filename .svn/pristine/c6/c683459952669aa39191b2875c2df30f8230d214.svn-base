package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.client.QmtConfigApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by gengchengliang on 2015/7/4.
 */
@Path("/dict")
public class DictResource {
    private static final Logger LOG = LoggerFactory.getLogger(DictResource.class);


    @LJSONP
    @GET
    @Path("entries/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public TDictEntry getDictEntryById(@PathParam("id") long id,
                                       @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入id");
            TDictEntry dictEntry = QmtConfigApis.getTDictEntryById(id, callerBean.getCallerParam());
            return dictEntry;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("entries")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<TDictEntry> getDictEntryRegexName(@QueryParam("name") String name,
                                                  @BeanParam PageBean pageBean,
                                                  @BeanParam CallerBean callerBean) {
        try {
            if (StringUtils.isNotEmpty(name)) {
                GetDictEntriesParam p = new GetDictEntriesParam();
                p.setName(name);
                return QmtConfigApis.getDictEntrys4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
            }
            return QmtConfigApis.getDictEntrys4SimpleSearch(null, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
