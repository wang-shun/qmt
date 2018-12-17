package com.lesports.qmt.cms.admin.web.param;

import java.io.Serializable;

/**
 * User: ellios
 * Time: 16-12-12 : 下午2:37
 */
public class ColumnPageCopyParam implements Serializable{

    private static final long serialVersionUID = 359271467527192199L;

    //copy from page id
    private Long fromPageId;
    //target page name
    private String name;

    private Long columnId;

    public Long getFromPageId() {
        return fromPageId;
    }

    public void setFromPageId(Long fromPageId) {
        this.fromPageId = fromPageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ColumnPageCopyParam{");
        sb.append("fromPageId=").append(fromPageId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", columnId=").append(columnId);
        sb.append('}');
        return sb.toString();
    }
}
