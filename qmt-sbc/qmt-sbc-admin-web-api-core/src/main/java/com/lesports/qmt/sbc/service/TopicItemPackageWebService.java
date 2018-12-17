package com.lesports.qmt.sbc.service;

import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.model.TopicItem;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.qmt.sbc.vo.TopicItemsVo;

import java.util.List;

/**
 * Created by denghui on 2016/11/30.
 */
public interface TopicItemPackageWebService extends QmtWebService<TopicItemPackageVo, Long> {

    /**
     * 获取专题下的内容包列表
     * @param topicId
     * @return
     */
    List<TopicItemPackageVo> list(long topicId);

    /**
     * 重置包顺序
     * @param topicId
     * @param ids
     * @return
     */
    boolean resetPackagesOrder(long topicId, List<Long> ids);

    /**
     * 获取某专题包的内容列表
     * @param packageId
     * @return
     */
    TopicItemsVo listItems(long packageId);

}
