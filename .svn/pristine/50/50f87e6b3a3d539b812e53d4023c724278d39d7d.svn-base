package com.lesports.qmt.mvc;

import com.lesports.utils.LeDateUtils;

import java.io.Serializable;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:29
 */
public interface QmtVo extends Serializable {

    default String getCreateAt() { return null; }

    default void setCreateAt(String createAt) {}

    default String getUpdateAt() { return null; }

    default void setUpdateAt(String updateAt) {}

    /**
     * 转换前端所需字段格式，如日期等
     * @return
     */
    default QmtVo pretty() {
        this.setCreateAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getCreateAt()));
        this.setUpdateAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getUpdateAt()));
        return this;
    }
}
