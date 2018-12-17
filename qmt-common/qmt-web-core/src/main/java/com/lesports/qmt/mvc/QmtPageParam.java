package com.lesports.qmt.mvc;

import com.lesports.qmt.QmtConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by denghui on 2016/11/2.
 */
public class QmtPageParam implements Serializable {

    private static final long serialVersionUID = 6140180594202518270L;

    private int page = 1;
    private int count = QmtConstants.DEFAULT_WEB_PAGE_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QmtPageParam{");
        sb.append("page=").append(page);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    public Pageable toPageable() {
        return toPageable(null);
    }

    public Pageable toPageable(Sort sort) {
        return new PageRequest(this.getPage()-1, this.getCount(), sort);
    }
}
