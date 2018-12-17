package com.lesports.qmt.play.api.resources;

//import client.SopsApis;

import client.SopsApis;
import com.google.common.collect.Lists;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.CarouselService;
import com.lesports.qmt.web.api.core.vo.CarouselListResult;
import com.lesports.sms.api.vo.TCarousel;
import com.lesports.utils.CarouselApis;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.LiveChannelDetail;
import com.lesports.utils.pojo.LiveChannels;
import com.lesports.utils.pojo.LivePlayBill;
import com.lesports.utils.pojo.LiveStreamRows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.lesports.sms.api.common.Platform;
//import com.lesports.sms.api.vo.TCarousel;
//import com.lesports.sms.api.web.core.service.CarouselService;
//import com.lesports.sms.api.web.core.vo.CarouselListResult;
//import com.lesports.qmt.sbc.api.dto.TCarousel;

/**
 * 轮播台
 *
 * @author: pangchuanxiao
 * @since: 2015/7/14
 */
@Path("/sms/app/v1/carousels")
public class AppCarouselResource {
    private static final Logger LOG = LoggerFactory.getLogger(AppCarouselResource.class);

    @Inject
    private CarouselService carouselService;

    @LJSONP
    @GET
    @Path("/channels")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<LiveChannels.LiveChannel> getChannels(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                                      @BeanParam CallerBean callerBean) {
        try {
            List<LiveChannels.LiveChannel> returnList = Lists.newLinkedList();
            //获取轮播台map: key: channelId  value:对应的数据
            Map<Long, LiveChannels.LiveChannel> channelsMap = CarouselApis.getChannelsMap(clientId);
            if (channelsMap.size() <= 0) {
                return Collections.EMPTY_LIST;
            }
            //获取数据库里面的轮播台的顺序
            List<TCarousel> tCarousels = SopsApis.getTCarouselsByPlatform(com.lesports.sms.api.common.Platform.MOBILE, callerBean.getCallerParam());
            //按照数据库的排序从channelsMap中获取数据返回
            for (TCarousel tCarousel : tCarousels) {
                Long channelId = tCarousel.getChannelId();
                LiveChannels.LiveChannel channel = channelsMap.get(channelId);
                if (channel != null) {
                    returnList.add(channel);
                }
            }
            return returnList;
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
    @Path("/playbill/current")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LivePlayBill.CurrentRow getCurrent(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                              @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            return CarouselApis.getCurrent(clientId, channelId);
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
    @Path("/playbill/current/batch")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public CarouselListResult getCurrents(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                          @QueryParam("channelIds") @DefaultValue("0") String channelIdString) {
        CarouselListResult listResult = new CarouselListResult();
        try {
            String[] channelIds = StringUtils.split(channelIdString, ",");
            listResult.setPage(1);
            if (null == channelIds) {
                listResult.setCount(0);
            } else {
                listResult.setCount(channelIds.length);
                for (String channelId : channelIds) {
                    LivePlayBill.CurrentRow row = CarouselApis.getCurrent(clientId, LeNumberUtils.toInt(channelId));
                    if (null == row) {
                        row = new LivePlayBill.CurrentRow();
                        row.setChannelId(LeNumberUtils.toInt(channelId));
                    }
                    listResult.addEntriy(row);
                }
            }

            return listResult;
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
    @Path("/playbill/wholeday")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<LivePlayBill.RowContent> getWholeDay(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                                     @QueryParam("channelId") @DefaultValue("0") int channelId,
                                                     @QueryParam("date") @DefaultValue("0") String date) {
        try {
            return CarouselApis.getWholeDay(clientId, channelId, date);
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
    @Path("/stream")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveStreamRows getStream(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                    @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            LiveStreamRows rows = CarouselApis.getStreams(clientId, channelId);
            if (null != rows && CollectionUtils.isNotEmpty(rows.getRows())) {
                Iterator<LiveStreamRows.LiveStreamRow> iterable = rows.getRows().iterator();
                while (iterable.hasNext()) {
                    LiveStreamRows.LiveStreamRow row = iterable.next();
                    if (null != row && row.getRateType().contains("1080p")) {
                        iterable.remove();
                    }
                }
            }

            return rows;
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
    @Path("/channelDetail")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveChannelDetail getChannelDetail(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                              @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            return carouselService.getLiveChannelDetail(clientId, channelId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
