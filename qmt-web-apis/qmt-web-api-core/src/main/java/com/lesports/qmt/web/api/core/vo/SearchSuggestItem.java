package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;

/**
 * lesports-projects
 * 直播赛事、专题、新闻
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
public class SearchSuggestItem implements Serializable{
    //赛事id
    private String suggest;

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }
}
