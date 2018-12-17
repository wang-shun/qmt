package com.lesports.qmt.config.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 搜索关键词
 * Created by ruiyuansheng on 2015/10/16.
 */
@Document(collection = "suggests")
public class Suggest extends QmtModel<Long> {

    private static final long serialVersionUID = -2204823089873723483L;

    //名称
    private String suggest;
    //顺序
    private Integer order;

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
