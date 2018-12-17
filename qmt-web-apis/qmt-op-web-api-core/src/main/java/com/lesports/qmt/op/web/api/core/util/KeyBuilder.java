package com.lesports.qmt.op.web.api.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generate a string key according to the param and indication as a cache key.
 * Please make the value you add in to the param is with predictable iteration order.
 * @author pangchuanxiao
 * @since 2015/6/30.
 */
public class KeyBuilder {
    private String indication;
    private LinkedHashMap<String, Object> params;

    private KeyBuilder(){

    }

    public static KeyBuilder create() {
        KeyBuilder builder = new KeyBuilder();
        builder.indication = StringUtils.EMPTY;
        builder.params = new LinkedHashMap<>();
        return builder;
    }

    public KeyBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public KeyBuilder setIndication(String in) {
        indication = in;
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("_");
        }
        stringBuilder.append(indication);
        return stringBuilder.toString();
    }
}
