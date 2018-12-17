package com.lesports.qmt.msg.core;

import com.lesports.utils.LeProperties;

/**
 * Created by lufei1 on 2015/7/1.
 */
public class MsgProducerConstants {
    static {
        LeProperties.loadProperties("properties/application-swiftq.properties");
        LeProperties.loadProperties("swiftq.properties");
    }
    //${swiftq.connection.url}
    public static final String SWIFTQ_CONNECTION_URL = LeProperties.getString("swiftq.connection.url");
    public static final String SWIFTQ_EXCHANGE_NAME = LeProperties.getString("swiftq.exchange.name", "lesports.qmt.msg.cn.direct");
    public static final String SWIFTQ_ROUTING_KEY_NAME = LeProperties.getString("swiftq.routingkey.name", "lesports.routingkey.qmt.msg.cn");
    public static final Boolean MESSAGE_PRODUCER_ON = LeProperties.getBoolean("swiftq.producer.on", true);

    public static final String SWIFTQ_MESSAGE_CENTER_URL = LeProperties.getString("swiftq.message.center.url");
    public static final String SWIFTQ_MESSAGE_CENTER_EXCHANGE = LeProperties.getString("swiftq.message.center.exchange", "lesports.message.center");
    public static final String SWIFTQ_MESSAGE_LECOLUD_URL = LeProperties.getString("cloud.swiftq.connection.url");
    public static final String CDN_CLEAN_URL = LeProperties.getString("cdn.cache.clean.url");

    //消息中心分区域
    public static final String CN_SWIFTQ_CONNECTION_URL = LeProperties.getString("swiftq.connection.url.qmt.cn");
    public static final String HK_SWIFTQ_CONNECTION_URL = LeProperties.getString("swiftq.connection.url.qmt.hk");
    public static final String US_SWIFTQ_CONNECTION_URL = LeProperties.getString("swiftq.connection.url.qmt.us");

}
