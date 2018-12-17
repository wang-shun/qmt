package com.lesports.qmt.msg.core;

import com.lesports.id.api.IdType;
import com.lesports.utils.LeDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/15
 */
public class LeMessageBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(LeMessageBuilder.class);

    private static final Random RANDOM = new Random();
    private static InetAddress IP;
    private static String IP_STRING = "";
    private Long entityId;
    private String entityName;
    private List<BusinessType> businessTypes;
    private ActionType actionType;
    private MessageContent content;
    private IdType idType;
    private String data;

    static {
        try {
            NetworkInterface eth0 = NetworkInterface.getByName("eth0");
            if (null != eth0 && null != eth0.getInetAddresses()) {
                Enumeration e2 = eth0.getInetAddresses();
                while (e2.hasMoreElements()) {
                    IP = (InetAddress) e2.nextElement();
                }
            }
            if (null != IP && null != IP.getHostAddress()) {
                IP_STRING = IP.getHostAddress().replace(".", "");
            }
        } catch (Exception e) {
            LOG.warn("fail to get localhost address, error : {}", e.getMessage(), e);
        }
    }

    public IdType getIdType() {
        return idType;
    }

    public LeMessageBuilder setIdType(IdType idType) {
        this.idType = idType;
        return this;
    }

    public MessageContent getContent() {
        return content;
    }

    public LeMessageBuilder setContent(MessageContent content) {
        this.content = content;
        return this;
    }

    public String getData() {
        return data;
    }

    public LeMessageBuilder setData(String data) {
        this.data = data;
        return this;
    }

    public static LeMessageBuilder create() {
        return new LeMessageBuilder();
    }

    public Long getEntityId() {
        return entityId;
    }

    public LeMessageBuilder setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public LeMessageBuilder setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public List<BusinessType> getBusinessTypes() {
        return businessTypes;
    }

    public LeMessageBuilder setBusinessTypes(ActionType actionType, List<BusinessType> businessTypes) {
        this.businessTypes = businessTypes;
        this.actionType = actionType;
        return this;
    }

    public LeMessage build() {
        LeMessage leMessage = null;
        if (null == getIdType() && null == getEntityId()) {
            leMessage = new LeMessage(getActionType(), getBusinessTypes(), getContent());
        } else if (null == getIdType()) {
            leMessage = new LeMessage(getEntityId(), getActionType(), getBusinessTypes(), getContent());
        } else {
            leMessage = new LeMessage(getEntityId(), getActionType(), getBusinessTypes(), getContent(), getIdType());
        }
        leMessage.setMessageId(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()) + IP_STRING + RANDOM.nextInt(1000));
        leMessage.setData(this.getData());
        return leMessage;
    }
}
