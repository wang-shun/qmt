package com.lesports.qmt.sbc.vo;

import com.alibaba.fastjson.JSONObject;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.config.client.QmtCopyrightInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.api.common.ShieldType;
import com.lesports.qmt.sbc.api.dto.StreamSourceType;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lufei1 on 2016/11/5.
 */
public class LiveVo extends Live implements QmtVo {
    private static final Logger LOG = LoggerFactory.getLogger(LiveVo.class);

    //直播状态
    private Integer opStatusParam;
    //关联
    private String relatedIdParam;
    //图片
    private String coverImageParam;
    //付费平台参数
    private String payPlatformParam;
    //播放平台参数
    private String playPlatformParam;
    //屏蔽国家参数
    private String shieldRegionParam;

    private Integer streamSourceTypeParam;

    private Integer shieldTypeParam;
    //返回的字符串类型的id
    private String idString;

    public Integer getShieldTypeParam() {
        return shieldTypeParam;
    }

    public void setShieldTypeParam(Integer shieldTypeParam) {
        this.shieldTypeParam = shieldTypeParam;
    }

    public Integer getOpStatusParam() {
        return opStatusParam;
    }

    public void setOpStatusParam(Integer opStatusParam) {
        this.opStatusParam = opStatusParam;
    }

    public String getRelatedIdParam() {
        return relatedIdParam;
    }

    public void setRelatedIdParam(String relatedIdParam) {
        this.relatedIdParam = relatedIdParam;
    }

    public String getCoverImageParam() {
        return coverImageParam;
    }

    public void setCoverImageParam(String coverImageParam) {
        this.coverImageParam = coverImageParam;
    }

    public String getPayPlatformParam() {
        return payPlatformParam;
    }

    public void setPayPlatformParam(String payPlatformParam) {
        this.payPlatformParam = payPlatformParam;
    }

    public String getPlayPlatformParam() {
        return playPlatformParam;
    }

    public void setPlayPlatformParam(String playPlatformParam) {
        this.playPlatformParam = playPlatformParam;
    }

    public String getShieldRegionParam() {
        return shieldRegionParam;
    }

    public void setShieldRegionParam(String shieldRegionParam) {
        this.shieldRegionParam = shieldRegionParam;
    }

    public Integer getStreamSourceTypeParam() {
        return streamSourceTypeParam;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public void setStreamSourceTypeParam(Integer streamSourceTypeParam) {
        this.streamSourceTypeParam = streamSourceTypeParam;
    }

    public LiveVo() {

    }

    public LiveVo(Live live) {
        LeBeanUtils.copyNotEmptyPropertiesQuietly(this, live);
        setIdString(String.valueOf(live.getId()));
    }

    public Live toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Live live = new Live();
        try {
            LeBeanUtils.copyProperties(live, this);

            //for statusParam->status
            if (null != opStatusParam) {
                live.setStatus(LiveStatus.findByValue(this.opStatusParam));
            }
            //for relatedIdParam->relatedIds
            live.setRelatedIds(getListFromSCV(this.relatedIdParam));
            //for coverImageParam->coverImage
            if (StringUtils.isNotEmpty(this.coverImageParam)) {
//                Type type = new TypeReference<Map<String, ImageUrlExt>>() {
//                }.getType();
//                Map<String, ImageUrlExt> coverImage = JSONObject.parseObject(this.coverImageParam, type);
//                live.setCoverImage(coverImage);
                ImageUrlExt coverImage = JSONObject.parseObject(coverImageParam, ImageUrlExt.class);
                live.setCoverImage(coverImage);
            }

            //付费平台参数
            //for payPlatformParam->payPlatform
            live.setPayPlatforms(getSetFromSCV(this.payPlatformParam));
            if (LeNumberUtils.toLong(this.getCopyrightId()) > 0) {
                Copyright copyright = QmtCopyrightInternalApis.getCopyrightById(this.getCopyrightId());
                if (null != copyright) {
                    //播放平台参数
                    //for playPlatformParam->playPlatformParam
                    live.setPlayPlatforms(copyright.getClientPlatformId());
                    //屏蔽国家参数
                    //for shieldRegionParam->Region
                    live.setShieldType(ShieldType.findByValue(copyright.getShieldAreaType()));

                    live.setShieldRegion(copyright.getShieldCountry().stream().
                            map(code -> QmtCountryInternalApis.getCountryByCode(code)).
                            filter(country -> null != country).
                            map(country1 -> country1.getId()).
                            collect(Collectors.toList()));
                }
            } else {
                //播放平台参数
                //for playPlatformParam->playPlatformParam
                live.setPlayPlatforms(getSetFromSCV(this.playPlatformParam));
                //屏蔽国家参数
                //for shieldRegionParam->Region
                live.setShieldRegion(getListFromSCV(this.shieldRegionParam));
                live.setShieldType(ShieldType.findByValue(this.shieldTypeParam));
            }

            //for streamSourceTypeParam->streamSourceType
            if (null != this.streamSourceTypeParam) {
                live.setStreamSourceType(StreamSourceType.findByValue(this.streamSourceTypeParam));
            }

            if (LeNumberUtils.toLong(this.getIdString()) > 0) {
                live.setId(LeNumberUtils.toLong(this.getIdString()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("{}", e.getMessage(), e);
            throw new LeWebApplicationException("Param error", LeStatus.BAD_REQUEST);
        }
        return live;
    }

    private Set<Long> getSetFromSCV(String value) {
        if (StringUtils.isEmpty(value)) {
            return Collections.emptySet();
        }
        Set<Long> ret = new HashSet<>();
        value = StringUtils.isEmpty(value) ? "" : value;
        Arrays.stream(value.split(",")).forEach(item -> {
            try {
                ret.add(Long.parseLong(item));
            } catch (Exception e) {
            }
        });
        return ret;
    }

    private List<Long> getListFromSCV(String value) {
        if (StringUtils.isEmpty(value)) {
            return Collections.emptyList();
        }
        List<Long> ret = new ArrayList<>();
        value = StringUtils.isEmpty(value) ? "" : value;
        Arrays.stream(value.split(",")).forEach(item -> {
            try {
                ret.add(Long.parseLong(item));
            } catch (Exception e) {
            }
        });
        return ret;
    }
}
