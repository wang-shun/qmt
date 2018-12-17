package com.lesports.qmt.config.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 乐词信息
 * User: ellios
 * Time: 15-6-10 : 下午9:15
 */
public class LeCi implements Serializable {
    private static final long serialVersionUID = -8022223175054454073L;

    @Field("id")
    private Long leId;
    private String name;

    public Long getLeId() {
        return leId;
    }

    public void setLeId(Long leId) {
        this.leId = leId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LeCi{");
        sb.append("leId=").append(leId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeCi)) return false;

        LeCi leCi = (LeCi) o;

        if (leId != null ? !leId.equals(leCi.leId) : leCi.leId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return leId != null ? leId.hashCode() : 0;
    }
}
