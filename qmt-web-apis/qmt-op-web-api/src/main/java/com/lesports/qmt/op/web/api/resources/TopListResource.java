package com.lesports.qmt.op.web.api.resources;

import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ENCRYPT;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.op.web.api.core.service.TopListService;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.vo.TopListVo;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/23.
 */
@Path("/")
public class TopListResource {

    private static final Logger LOG = LoggerFactory.getLogger(TopListResource.class);


    @Inject
    private TopListService topListService;

    /**
     * 百度定制榜单
     *
     * @param abbreviation
     * @return
     */
    @GET
    @LJSONP
    @ENCRYPT
    @Path("toplists/baidu/{abbreviation}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getTopListForBaidu(@PathParam("abbreviation") String abbreviation) {
        try {
            Long cid = Constants.URL_MAP.get(abbreviation);
            if (cid == null) {
                throw new LeWebApplicationException("不支持该赛事", LeStatus.PARAM_FROM_INVALID);
            }

            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(0L);

            PageParam pageParam = new PageParam();
            pageParam.setCount(100);
            pageParam.setPage(1);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return topListService.getTopListForBaidu(p,pageParam,callerParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

}
