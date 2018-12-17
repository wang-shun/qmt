package com.lesports.qmt.sbd.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by qiaohongxin on 2017/02/07
 */
@Document(collection = "data_import_config")
public class DataImportConfig extends QmtModel<Long> {
    private static final long serialVersionUID = 5288220348563444960L;
    //文件名
    @Field("file_name")
    private String fileName;
    //第三方来源
    @Field("partner_type")
    private PartnerType partnerType;
    //文件md5
    @Field("md5")
    private String md5;
    //状态
    private Boolean deleted;
    //是否当前最新文件
    private Boolean isCurrentNew;
    //尝试次数
    @Field("try_count")
    private Integer tryCount;


    @Override
    public String toString() {
        return "DataImportConfig{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", partnerType=" + partnerType +
                ", md5='" + md5 + '\'' +
                ", deleted=" + deleted +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PartnerType getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getIsCurrentNew() {
        return isCurrentNew;
    }

    public void setIsCurrentNew(Boolean isCurrentNew) {
        this.isCurrentNew = isCurrentNew;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }
}
