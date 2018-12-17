package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.utils.ChatApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

/**
 * 聊天室
 * Created by ruiyuansheng on 2015/9/16.
 */
@Path("/")
public class ChatResource {
    private static final Logger LOG = LoggerFactory.getLogger(ChatResource.class);

    /**
     * 获取聊天室记录
     * @param roomId
     * @param count
     * @return
     */
    @LJSONP
    @GET
    @Path("history/{roomId}/chat")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public  Object getChatHistory(@PathParam("roomId") String roomId,
                                    @QueryParam("count") @DefaultValue("50") int count,
                                    @QueryParam("server") @DefaultValue("true") boolean server,
                                    @QueryParam("version") @DefaultValue("2.0") String version,
                                    @QueryParam("protocol") @DefaultValue("socket") String protocol) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(roomId), "请输入聊天室ID");
            return ChatApis.getChatHistory(roomId, count, server, version, protocol);

        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
