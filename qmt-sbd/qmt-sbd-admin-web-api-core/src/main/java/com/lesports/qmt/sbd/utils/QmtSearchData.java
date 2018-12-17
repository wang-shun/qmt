package com.lesports.qmt.sbd.utils;

import com.google.common.collect.Lists;
import com.lesports.qmt.model.support.QmtModel;

import java.util.List;

/**
 * Created by lufei1 on 2016/12/3.
 */
public class QmtSearchData<T extends QmtModel<Long>> {
    private List<T> rows = Lists.newArrayList();
    private long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
