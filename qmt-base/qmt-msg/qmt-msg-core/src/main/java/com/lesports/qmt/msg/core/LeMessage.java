package com.lesports.qmt.msg.core;

import com.alibaba.fastjson.JSON;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;

import java.io.Serializable;
import java.util.List;

/**
 * User: ellios
 * Time: 15-6-28 : 下午9:57
 */
public class LeMessage implements Serializable {
    private static final long serialVersionUID = 1169997624476213210L;

    private String messageId;
    private Long entityId;
    private IdType idType;
    private ActionType actionType;
    private List<BusinessType> businessTypes; //业务处理类型
    private MessageContent content;
    private String entityName;
    private List<AreaType> areaTypes; //数据同步的地区
    private String data; //消息内容-json格式

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<BusinessType> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<BusinessType> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public List<AreaType> getAreaTypes() {
        return areaTypes;
    }

    public void setAreaTypes(List<AreaType> areaTypes) {
        this.areaTypes = areaTypes;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * do not remove. JSON.parseObject will use default constructor to parse object.
     */
    public LeMessage() {
    }

    public LeMessage(ActionType actionType, List<BusinessType> businessTypes, MessageContent content) {
        this.actionType = actionType;
        this.businessTypes = businessTypes;
        this.content = content;
    }

    public LeMessage(Long entityId, ActionType actionType, List<BusinessType> businessTypes, MessageContent content) {
        this.entityId = entityId;
        this.idType = LeIdApis.checkIdTye(entityId);//IdType.checkIdType(entityId);
        this.actionType = actionType;
        this.businessTypes = businessTypes;
        this.content = content;
    }

    public LeMessage(Long entityId, ActionType actionType, List<BusinessType> businessTypes, MessageContent content, IdType idType) {
        this.entityId = entityId;
        this.idType = idType;
        this.actionType = actionType;
        this.businessTypes = businessTypes;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "entityId=" + entityId +
                ", idType=" + idType +
                ", actionType=" + actionType +
                ", businessTypes=" + JSON.toJSONString(businessTypes) +
                ", content='" + content + '\'' +
                '}';
    }

}
