package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.utils.PlayRecordApis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 播放记录
 * Created by ruiyuansheng on 2015/9/17.
 */
@Path("/")
public class PlayRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChatResource.class);

    /**
     * 提交点播记录
     * @param cid
     * @param pid
     * @param vid
     * @param from
     * @param product
     * @param htime
     * @param longitude
     * @param latitude
     * @param sso_tk
     * @param rid
     * @return
     */
    @LJSONP
    @GET
    @Path("submit/playRecord")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> submitPlayRecord(@QueryParam("cid") long cid,
                                                 @QueryParam("pid") long pid,
                                                 @QueryParam("vid") long vid,
                                                 @QueryParam("oid") long oid,
                                                 @QueryParam("type")@DefaultValue("1") int type,
                                                 @QueryParam("sso_tk") String sso_tk,
                                                 @QueryParam("from") @DefaultValue("4") int from,
                                                 @QueryParam("product") String product,
                                                 @QueryParam("htime") long htime,
                                                 @QueryParam("longitude") double longitude,
                                                 @QueryParam("latitude") double latitude,
                                                 @QueryParam("rid") int rid) {
        try {
            Map<String, Object> results = Maps.newHashMap();
            Preconditions.checkArgument(from != 0, "请输入视频来源");
            Preconditions.checkArgument(htime != 0, "请输入观看时间点");
            Preconditions.checkArgument(StringUtils.isNotEmpty(sso_tk), "请输入token值");
            //如果是点播
            if(type == 1){
                Preconditions.checkArgument(vid != 0, "请输入视频ID");
                Map<String,Object> map = PlayRecordApis.uploadPlayRecord(cid, pid, vid, from, product, htime, longitude, latitude, sso_tk, rid);
                if(null != map  && ((int)map.get("code") == 200)){
                    results =  Collections.EMPTY_MAP;
                }else{
                    throw new LeWebApplicationException((String)map.get("data"), LeStatus.INTERNAL_SERVER_ERROR);
                }
            }else{//如果是直播、轮播、卫视
                Preconditions.checkArgument(oid != 0, "请输入OID");
                Map<String,Object> map = PlayRecordApis.uploadPlayRecordNotVOD(oid, type, from, product, htime, sso_tk, rid);
                if(null != map  && ((int)map.get("code") == 200)){
                    results =  Collections.EMPTY_MAP;
                }else{
                    throw new LeWebApplicationException((String)map.get("data"), LeStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return results;

        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取播放记录
     * @param sso_tk
     * @param btime
     * @param type
     * @param videotype
     * @param from
     * @param rid
     * @param page
     * @param count
     * @return
     */
//    @LJSONP
//    @GET
//    @Path("get/playRecord")
//    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
//    public Map<String, Object> getPlayRecord(  @QueryParam("sso_tk") String sso_tk,
//                                                @QueryParam("btime") long btime,
//                                                @QueryParam("type")@DefaultValue("0") String type,
//                                                @QueryParam("videotype") String videotype,
//                                                @QueryParam("from") @DefaultValue("4") int from,
//                                                @QueryParam("rid") int rid,
//                                                @QueryParam("page") @DefaultValue("1") int page,
//                                                @QueryParam("count") @DefaultValue("20") int count) {
//        try {
//            Preconditions.checkArgument(StringUtils.isNotEmpty(sso_tk), "请输入token值");
//            List playRecords = PlayRecordApis.getPlayRecord(sso_tk, btime, type, videotype, from, rid);
//            List results = Lists.newArrayList();
//            if(CollectionUtils.isNotEmpty(playRecords)) {
//                int pageSize = page * count > playRecords.size() ? playRecords.size() : page * count;
//                for (int i = (page - 1) * count; i < pageSize; i++) {
//                    results.add(playRecords.get(i));
//                }
//            }
//            return ResponseUtils.createPageResponse(page, results);
//        } catch (LeWebApplicationException e) {
//            LOG.error(e.getMessage(), e);
//            throw e;
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
