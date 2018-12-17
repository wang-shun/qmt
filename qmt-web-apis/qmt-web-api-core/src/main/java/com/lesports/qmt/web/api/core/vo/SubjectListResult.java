package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/15
 */
public class SubjectListResult implements Serializable {
    private List<TopicItem> subjects;
    private Integer page;
    private Integer count;

    public List<TopicItem> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TopicItem> subjects) {
        this.subjects = subjects;
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
