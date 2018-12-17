package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by denghui on 2016/11/2.
 */
public class QmtSbcTopicInternalApis extends QmtSbcInternalApis {
    
    public static long saveTopic(Topic topic) {
        return saveTopic(topic, false);
    }

    public static long saveTopic(Topic topic, boolean allowEmpty) {
        return saveEntity(topic, Topic.class, allowEmpty);
    }

    public static Topic getTopicById(Long id) {
        return getEntityById(id, Topic.class);
    }

    public static List<Topic> getTopicsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Topic.class);
    }

    public static long countTopicByQuery(InternalQuery query) {
        return countByQuery(query, Topic.class);
    }

    public static List<Long> getTopicIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Topic.class);
    }

    public static boolean deleteTopic(Long id) {
        return deleteEntity(id, Topic.class);
    }

    public static long saveTopicItemPackage(TopicItemPackage topicItemPackage) {
        return saveTopicItemPackage(topicItemPackage, false);
    }

    public static long saveTopicItemPackage(TopicItemPackage topicItemPackage, boolean allowEmpty) {
        return saveEntity(topicItemPackage, TopicItemPackage.class, allowEmpty);
    }

    public static boolean deleteTopicItemPackage(Long id) {
        return deleteEntity(id, TopicItemPackage.class);
    }

    public static TopicItemPackage getTopicItemPackageById(Long id) {
        return getEntityById(id, TopicItemPackage.class);
    }

    public static List<TopicItemPackage> getTopicItemPackagesByIds(List<Long> ids) {
        return getEntitiesByIds(ids, TopicItemPackage.class);
    }

    public static List<Long> getTopicItemPackageIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, TopicItemPackage.class);
    }

    public static List<TopicItemPackage> getTopicItemPackagesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, TopicItemPackage.class);
    }

    public static List<TopicItemPackage> getTopicItemPackagesByTopicId(Long topicId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("topic_id").is(topicId));
        query.with(new Sort(Sort.Direction.ASC, "order"));
        return getTopicItemPackagesByQuery(query);
    }

    public static List<Topic> getTopicsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Topic.class);
    }
}
