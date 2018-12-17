package com.lesports.qmt.tlive.cache;

import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.repository.LeCrudRepository;

import java.util.Map;

/**
 * Created by lufei1 on 2015/9/15.
 */
public interface TTextLiveCache extends LeCrudRepository<TTextLive, Long> {

    /**
     * 增加在线人数
     *
     * @param eid
     * @return
     */
    public int addOnlineCount(long eid);


    /**
     * 获取在线人数
     *
     * @param eid
     * @return
     */
    public int getOnlineCount(long eid);

    /**
     * 修改在线人数
     *
     * @param eid
     * @param onlineCount
     * @return
     */
    public boolean setOnlineCount(long eid, int onlineCount);


    /**
     * 顶踩直播员
     *
     * @param textLiveId
     * @param anchorId
     * @param act
     * @return
     */
    public int addUpDownAnchor(long textLiveId, long anchorId, UpDownAct act);


    /**
     * 人工干预直播员顶踩数
     *
     * @param textLiveId
     * @param anchorId
     * @param upDownActMap
     * @return
     */
    public boolean setUpDownAnchor(long textLiveId, long anchorId, Map<UpDownAct, Integer> upDownActMap);


    /**
     * 获取顶踩直播员结果
     *
     * @param textLiveId
     * @param anchorId
     * @return
     */
    public int getAnchorUpDown(long textLiveId, long anchorId, UpDownAct act);
}
