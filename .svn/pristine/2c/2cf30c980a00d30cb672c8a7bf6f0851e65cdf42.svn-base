package com.lesports.qmt.tlive.repository.redis;

import java.util.List;

/**
 * Created by lufei1 on 2015/10/14.
 */
public interface TextLiveMessageRedisRepository {

    /**
     * 生成消息索引
     *
     * @param textLiveId
     * @param section
     * @return
     */
    public int generateMessageIndex(long textLiveId, long section);


    /**
     * 获取消息索引
     *
     * @param textLiveId
     * @param section
     * @return
     */
    public int getMessageIndex(long textLiveId, long section);


    /**
     * 从指定页中获取消息
     *
     * @param textLiveId
     * @param section
     * @param pageId
     * @return
     */
    public List<String> getIdByPage(long textLiveId, long section, int pageId);


    /**
     * 将消息Id加入桶中
     *
     * @param textLiveId
     * @param section
     * @param pageId
     * @return
     */
    public Long addIdToPage(long textLiveId, long section, int pageId, long messageId);


    /**
     * 将id_page关系保存至map
     *
     * @param textLiveId
     * @param section
     * @param messageId
     * @param pageId
     * @return
     */
    public Boolean saveIdPageMapping(long textLiveId, long section, long messageId, int pageId);

    /**
     * 根据id从map中获取page
     *
     * @param textLiveId
     * @param section
     * @param messageId
     * @return
     */
    public int getPageById(long textLiveId, long section, long messageId);
}
