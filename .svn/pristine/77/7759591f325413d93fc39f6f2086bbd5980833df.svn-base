package com.lesports.qmt.tlive.service;

import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.model.TextLive;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/9/14.
 */
public interface TextLiveService extends QmtService<TextLive, Long> {

    /**
     * 根据id查询图文直播
     *
     * @param id
     * @return
     */
    TTextLive getTTextLiveById(long id);

    /**
     * 批量id查询图文直播
     *
     * @param ids
     * @return
     */
    List<TTextLive> getTTextLiveByIds(List<Long> ids);


    /**
     * 根据eid、type查询图文直播
     *
     * @param eid
     * @param textLiveType
     * @return
     */
    TextLive findTextLive(long eid, TextLiveType textLiveType);

    /**
     * 根据节目id获取图文直播
     *
     * @param eid
     * @return
     */
    List<TTextLive> getTTextLiveByEid(long eid);

    /**
     * 根据节目id获取主图文直播
     *
     * @param eid
     * @return
     */
    TTextLive getMainTTextLiveByEid(long eid);

    /**
     * 在线人数上报
     *
     * @param eid
     * @return
     */
    int reportOnlineCount(long eid);

    /**
     * 在线人数获取
     *
     * @param eid
     * @return
     */
    int getOnlineCount(long eid);

    /**
     * 人工干预在线人数
     *
     * @param eid
     * @param onlineCount
     * @return
     */
    boolean setOnlineCount(long eid, int onlineCount);


    /**
     * 人工干预直播员顶踩
     *
     * @param textLiveId
     * @param anchorId
     * @param upDownActMap
     * @return
     */
    boolean setUpDownAnchor(long textLiveId, long anchorId, Map<UpDownAct, Integer> upDownActMap);

    /**
     * 顶踩直播员
     *
     * @param textLiveId
     * @param anchorId
     * @param act
     * @return
     */
    TAnchor upDownAnchor(long textLiveId, long anchorId, UpDownAct act);


    /**
     * 获取直播员顶踩结果
     *
     * @param textLiveId
     * @param anchorId
     * @return
     */
    TAnchor getAnchorUpDown(long textLiveId, long anchorId);
}
