package com.lesports.qmt.cms.model;

import com.google.common.collect.Maps;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * User: ellios
 * Time: 17-1-10 : 下午3:46
 */
@Document(collection = "mappers")
public class Mapper extends QmtModel<Long> {

    private static final long serialVersionUID = -6110212755273552088L;

    private PageType type;
    private Map<String, Object> res = Maps.newHashMap();
    private String version;

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public Map<String, Object> getRes() {
        return res;
    }

    public void setRes(Map<String, Object> res) {
        this.res = res;
    }

    public void addResource(String key , Object value){
        res.put(key, value);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
