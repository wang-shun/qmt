package com.lesports.qmt.mvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denghui on 2016/10/31.
 */
public class QmtPage<T extends QmtVo> implements Serializable {
    private static final long serialVersionUID = -1762592130299527110L;

    //内容列表
    private List<T> content;
    //第几页
    private int page;
    //每页大小
    private int size;
    //总记录数
    private long total;
    //总页数
    private int totalPages;

    public QmtPage() {
    }

    /**
     * 创建分页对象
     * @param content 内容列表
     * @param pageParam 分页参数，页数从1开始
     * @param total 总记录数
     */
    public QmtPage(List<T> content, QmtPageParam pageParam, long total) {
        Page<T> tPage = new PageImpl<>(content, new PageRequest(pageParam.getPage() - 1,pageParam.getCount()), total);
        this.content = tPage.getContent();
        this.page = tPage.getNumber() + 1;
        this.size = content.size();
        this.total = tPage.getTotalElements();
        this.totalPages = tPage.getTotalPages();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QmtPage{");
        sb.append("content=").append(content);
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", totalPages=").append(totalPages);
        sb.append('}');
        return sb.toString();
    }

}
