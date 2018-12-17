package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.client.QmtSbcTopicInternalApis;
import com.lesports.qmt.sbc.converter.TopicItemPackageVoConverter;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.model.TopicItem;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.qmt.sbc.service.TopicItemPackageWebService;
import com.lesports.qmt.sbc.service.support.AbstractSbcWebService;
import com.lesports.qmt.sbc.utils.QmtSbcUtils;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.qmt.sbc.vo.TopicItemsVo;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by denghui on 2016/11/30.
 */
@Service("topicItemPackageWebService")
public class TopicItemPackageWebServiceImpl extends AbstractSbcWebService implements TopicItemPackageWebService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicItemPackageWebServiceImpl.class);
    private static final Function<TopicItemPackage,TopicItemPackageVo> VO_FUNCTION = new Function<TopicItemPackage, TopicItemPackageVo>() {
        @Nullable
        @Override
        public TopicItemPackageVo apply(@Nullable TopicItemPackage input) {
            return input == null ? null : new TopicItemPackageVo(input);
        }
    };

    @Resource
    private TopicItemPackageVoConverter packageVoConverter;

    @Override
    public List<TopicItemPackageVo> list(long topicId) {
        List<TopicItemPackage> packages = QmtSbcTopicInternalApis.getTopicItemPackagesByTopicId(topicId);
        return Lists.transform(packages, VO_FUNCTION);
    }

    @Override
    public boolean resetPackagesOrder(long topicId, List<Long> ids) {
        List<TopicItemPackage> itemPackages = QmtSbcTopicInternalApis.getTopicItemPackagesByTopicId(topicId);
        if (CollectionUtils.isEmpty(itemPackages)) {
            return true;
        }

        // check indexes
        int order = 1;
        for (Long id : ids) {
            TopicItemPackage itemPackage = new TopicItemPackage();
            itemPackage.setId(id);
            int i = itemPackages.indexOf(itemPackage);
            if (i < 0) {
                LOG.warn("package id not exists: {}", id);
                return false;
            }
            itemPackage = itemPackages.get(i);
            itemPackage.setOrder(order++);
        }

        boolean result = true;
        for (TopicItemPackage itemPackage : itemPackages) {
            result &= QmtSbcTopicInternalApis.saveTopicItemPackage(itemPackage) > 0;
            if (!result) {
                return result;
            }
        }
        return result;
    }

    @Override
    public TopicItemsVo listItems(long packageId) {
        TopicItemsVo vo = new TopicItemsVo();
        TopicItemPackage itemPackage = QmtSbcTopicInternalApis.getTopicItemPackageById(packageId);
        if (itemPackage == null) {
            LOG.warn("topic package not exists: id:{}", packageId);
            return vo;
        }

        vo.setPackageName(itemPackage.getName());
        //专题名称
        Topic topic = QmtSbcTopicInternalApis.getTopicById(itemPackage.getTopicId());
        vo.setTopicId(itemPackage.getTopicId());
        vo.setTopicName(topic.getName());
        //内容源名称
        for (TopicItem topicItem : itemPackage.getItems()) {
            String name = QmtSbcUtils.getNameByEntityId(topicItem.getItemId(), CallerUtils.getDefaultCaller());
            topicItem.setOriginalName(name);
        }
        vo.setItems(itemPackage.getItems());
        return vo;
    }

    @Override
    public Long saveWithId(TopicItemPackageVo vo) {
        if (vo.getId() == null) {
            return doInsert(vo);
        } else {
            return doUpdate(vo);
        }
    }

    private Long doInsert(TopicItemPackageVo vo) {
        vo.setCreator(getOperator());
        long id = QmtSbcTopicInternalApis.saveTopicItemPackage(vo.toModel());
        vo.setId(id);
        return id;
    }

    private Long doUpdate(TopicItemPackageVo vo) {
        TopicItemPackage existsPackage = QmtSbcTopicInternalApis.getTopicItemPackageById(vo.getId());
        existsPackage = packageVoConverter.copyEditableProperties(existsPackage, vo);
        existsPackage.setUpdater(getOperator());
        long id = QmtSbcTopicInternalApis.saveTopicItemPackage(existsPackage, true);
        return id;
    }

    @Override
    public TopicItemPackageVo findOne(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return QmtSbcTopicInternalApis.deleteTopicItemPackage(id);
    }

}
