package com.lesports.qmt.search.response.support;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/28.
 */
public class SearchPage implements Serializable {
    private static final long serialVersionUID = -1436674227078782026L;

    private List<Map<String, Object>> rows = Lists.newArrayList();
    private Long total = 0l;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchPage{");
        sb.append("rows=").append(rows);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }

    public static final SearchPage EMPTY_PAGE = new SearchPage();
}
