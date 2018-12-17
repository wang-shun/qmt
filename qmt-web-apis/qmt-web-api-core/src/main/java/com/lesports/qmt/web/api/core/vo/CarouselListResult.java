package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.utils.pojo.LivePlayBill;

import java.io.Serializable;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/15
 */
public class CarouselListResult implements Serializable {
    private List<LivePlayBill.CurrentRow> entries;
    private Integer page;
    private Integer count;

    public List<LivePlayBill.CurrentRow> getEntries() {
        return entries;
    }

    public void setEntries(List<LivePlayBill.CurrentRow> entries) {
        this.entries = entries;
    }

    public void addEntriy(LivePlayBill.CurrentRow entry) {
        if (null == entries) {
            entries = Lists.newArrayList();
        }
        entries.add(entry);
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
