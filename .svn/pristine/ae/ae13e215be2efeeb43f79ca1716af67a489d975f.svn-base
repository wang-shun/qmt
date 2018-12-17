package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.model.ColumnPage;

import java.io.Serializable;

/**
 * User: ellios
 * Time: 17-1-13 : 下午1:12
 */
public class SimpleColumnPage implements Serializable {
    private static final long serialVersionUID = 3061730611276319290L;
    private Long id;
    private String name;
    private PageType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleColumnPage)) return false;

        SimpleColumnPage that = (SimpleColumnPage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleColumnPage{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public static SimpleColumnPage create(ColumnPage page){
        if(page == null){
            return null;
        }
        SimpleColumnPage simplePage = new SimpleColumnPage();
        simplePage.setId(page.getId());
        simplePage.setName(page.getName());
        simplePage.setType(page.getType());
        return simplePage;
    }
}
