package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/15
 */
public class SearchSuggestResult implements Serializable {
    List<SearchSuggestItem> suggests;
    Integer page;
    Integer count;

    public List<SearchSuggestItem> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<SearchSuggestItem> suggests) {
        this.suggests = suggests;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
