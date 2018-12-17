package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.utils.VersionUpdateApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2015/9/25.
 */
@Path("/")
public class VersionResource {
    private static final Logger LOG = LoggerFactory.getLogger(VersionResource.class);

    @LJSONP
    @GET
    @Path("update/version")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public  Object getUpdateVersion(@QueryParam("terminalApplication") String terminalApplication,
                                    @QueryParam("terminalSeries")String terminalSeries,
                                    @QueryParam("terminalBrand") String terminalBrand,
                                    @QueryParam("installVersion") String installVersion,
                                    @QueryParam("mac") String mac,
                                    @QueryParam("wcode") String wcode,
                                    @QueryParam("langcode") String langcode,
                                    @QueryParam("client") String client,
                                    @QueryParam("clientUuid") String clientUuid,
                                    @QueryParam("identifyCode") String identifyCode,
                                    @QueryParam("appCode") String appCode,
                                    @QueryParam("bsChannel") String bsChannel,
                                    @QueryParam("terminalUuid") String terminalUuid,
                                    @QueryParam("terminalUnique") String terminalUnique,
                                    @QueryParam("terminalUiCode") String terminalUiCode) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalApplication), "请输入应用名称");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalSeries), "请输入设备型号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalBrand), "请输入品牌");
            Preconditions.checkArgument(StringUtils.isNotEmpty(installVersion), "请输入设备当前版本号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(mac), "请输入唯一标识字段,设备mac");
            Preconditions.checkArgument(StringUtils.isNotEmpty(client), "客户端操作系统，传android，即可");
            Preconditions.checkArgument(StringUtils.isNotEmpty(clientUuid), "clientUuid不能为空，请输入MD5");
            Preconditions.checkArgument(StringUtils.isNotEmpty(bsChannel), "请输入渠道号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalUnique), "请输入唯一标示，传入mac即可");

            Map<String,String> uriVariables = new HashMap<>();
            uriVariables.put("terminalApplication",terminalApplication);
            uriVariables.put("terminalSeries",terminalSeries);
            uriVariables.put("terminalBrand",terminalBrand);
            uriVariables.put("installVersion",installVersion);
            uriVariables.put("mac",mac);
            uriVariables.put("wcode",wcode);
            uriVariables.put("langcode",langcode);
            uriVariables.put("client",client);
            uriVariables.put("clientUuid",clientUuid);
            uriVariables.put("identifyCode",identifyCode);
            uriVariables.put("appCode",appCode);
            uriVariables.put("bsChannel",bsChannel);
            uriVariables.put("terminalUuid",terminalUuid);
            uriVariables.put("terminalUnique",terminalUnique);
            uriVariables.put("terminalUiCode",terminalUiCode);

            return VersionUpdateApis.updateVersion(uriVariables);

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
    @Path("update/new/version")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public  Object getUpdateNewVersion(@QueryParam("terminalApplication") String terminalApplication,
                                    @QueryParam("terminalSeries")String terminalSeries,
                                    @QueryParam("terminalBrand") String terminalBrand,
                                    @QueryParam("bsChannel") String bsChannel,
                                    @QueryParam("versionCode") String versionCode,
                                    @QueryParam("devId") String devId,
                                    @QueryParam("langcode") String langcode,
                                    @QueryParam("wcode") String wcode,
                                    @QueryParam("salesArea") String salesArea,
                                    @QueryParam("countryArea") String countryArea,
                                    @QueryParam("token") String token,
                                    @QueryParam("uid") String uid) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalApplication), "请输入应用名称");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalSeries), "请输入设备型号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalBrand), "请输入品牌");
            Preconditions.checkArgument(StringUtils.isNotEmpty(bsChannel), "请输入渠道唯一标识");
            Preconditions.checkArgument(StringUtils.isNotEmpty(versionCode), "请输入设备当前版本号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(devId), "请输入唯一标识字段,设备mac");

            Map<String,String> uriVariables = new HashMap<>();
            uriVariables.put("terminalApplication",terminalApplication);
            uriVariables.put("terminalSeries",terminalSeries);
            uriVariables.put("terminalBrand",terminalBrand);
            uriVariables.put("bsChannel",bsChannel);
            uriVariables.put("versionCode",versionCode);
            uriVariables.put("devId",devId);
            uriVariables.put("langcode",langcode);
            uriVariables.put("wcode",wcode);
            uriVariables.put("salesArea",salesArea);
            uriVariables.put("countryArea",countryArea);
            uriVariables.put("token",token);
            uriVariables.put("uid",uid);


            return VersionUpdateApis.updateNewVersion(uriVariables);

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
    @Path("cibn/update/version")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public  Object getCIBNUpdateVersion(@QueryParam("terminalApplication") String terminalApplication,
                                    @QueryParam("terminalSeries")String terminalSeries,
                                    @QueryParam("terminalBrand") String terminalBrand,
                                    @QueryParam("bsChannel") String bsChannel,
                                    @QueryParam("versionCode") String versionCode,
                                    @QueryParam("devId") String devId,
                                    @QueryParam("langcode") String langcode,
                                    @QueryParam("wcode") String wcode,
                                    @QueryParam("salesArea") String salesArea,
                                    @QueryParam("countryArea") String countryArea,
                                    @QueryParam("token") String token,
                                    @QueryParam("uid") String uid) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalApplication), "请输入应用名称");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalSeries), "请输入设备型号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(terminalBrand), "请输入品牌");
            Preconditions.checkArgument(StringUtils.isNotEmpty(bsChannel), "请输入渠道唯一标识");
            Preconditions.checkArgument(StringUtils.isNotEmpty(versionCode), "请输入设备当前版本号");
            Preconditions.checkArgument(StringUtils.isNotEmpty(devId), "请输入唯一标识字段,设备mac");

            Map<String,String> uriVariables = new HashMap<>();
            uriVariables.put("terminalApplication",terminalApplication);
            uriVariables.put("terminalSeries",terminalSeries);
            uriVariables.put("terminalBrand",terminalBrand);
            uriVariables.put("bsChannel",bsChannel);
            uriVariables.put("versionCode",versionCode);
            uriVariables.put("devId",devId);
            uriVariables.put("langcode",langcode);
            uriVariables.put("wcode",wcode);
            uriVariables.put("salesArea",salesArea);
            uriVariables.put("countryArea",countryArea);
            uriVariables.put("token",token);
            uriVariables.put("uid",uid);


            return VersionUpdateApis.updateCibnVersion(uriVariables);

        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
