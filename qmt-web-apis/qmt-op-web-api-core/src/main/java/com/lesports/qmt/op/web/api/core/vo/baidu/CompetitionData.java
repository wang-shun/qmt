package com.lesports.qmt.op.web.api.core.vo.baidu;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by lufei1 on 2015/8/20.
 */
public class CompetitionData {
    private List<Serialinfo> serialinfo;

    @XmlElementWrapper(name="display")
    public List<Serialinfo> getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(List<Serialinfo> serialinfo) {
        this.serialinfo = serialinfo;
    }
}
