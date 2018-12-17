package com.lesports.qmt.resource.cvo;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbd.api.common.MatchSystem;
import com.lesports.qmt.sbd.api.dto.TCompetitor;

import java.util.List;
import java.util.Map;

/**
 * create by wangjichuan  date:16-12-20 time:16:45
 */
public class EpisodeCvo extends BaseCvo {

    private static final long serialVersionUID = -9166710871611688216L;

    private TComboEpisode detailInfo;

    public TComboEpisode getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(TComboEpisode detailInfo) {
        this.detailInfo = detailInfo;
    }

    /* //赛程id
    private Long mid;
    //赛事id
    private Long cid;
    ////赛事名称
    private String cname;
    //赛季
    private String season;
    //比赛开始时间
    private String startTime;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer matchStatus;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //图文直播状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer textLiveStatus;
    //是否对阵 0:非对阵 1:对阵
    private Integer vs;
    //轮次
    private String round;
    //比赛阶段
    private String stage;
    //分组
    private String group;
    //比赛大项
    private Long gameFType;
    //比赛大项名称
    private String gameFName;
    //比赛小项
    private Long gameSType;
    //比赛小项名称
    private String gameSName;
    //是否有直播 0:无 1:有
    private Integer isLive = 0;
    //是否有图文直播 0:无 1:有
    private Integer isTextLive = 0;
    //是否有录播 0:无 1:有
    private Integer isRecorded = 0;
    //是否有集锦 0:无 1:有
    private Integer isHighlights = 0;
    //比赛所属时段
    private Integer timeSection;
    //杯赛还是联赛
    private MatchSystem matchSystem;
    //赛事logo
    private String logo;
    //比赛时段和比赛时间
    private String moment;
    //参赛者
    private List<TCompetitor> competitors = Lists.newArrayList();
    //播放链接
    private String playLink;
    //图文直播链接
    private String tLiveLink;
    //图文直播链接-H5地址
    private String tLiveLink4H5;
    //图片集
    private Map<String, String> images;
    //期数
    private String periods;
    //描述
    private String desc;
    //时长
    private long duration;
    *//*    //类型
        private Integer type;*//*
    //新英付费标识
    private int xinyingPay;
    //赛事阶段名称: 英超 第20轮, NBA 季后赛等
    private String showName;
    //评论id
    private String commentId;
    //是否章鱼猜球 0:无 1:有
    private Integer isOctopus;
    //是否被删除 0:无 1:有
    private Integer deleted;
    //是否是重点赛程 0:不是 1:是
    private Integer key;
    //本节目是否付费
    private Integer isEpisodePay;
    //晋级之路的order,前端通过此字段排列
    private Integer theRoadOrder;
    //所属国家
    private CountryCode countryCode = CountryCode.CN;*/
}
