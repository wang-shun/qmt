package com.lesports.qmt.op.web.api.core.vo.mbaidu;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by lufei1 on 2015/11/26.
 */
@XmlRootElement(name = "DOCUMENT")
public class ShortVideos {
    private List<ShortVideoItem> item;

    public List<ShortVideoItem> getItem() {
        return item;
    }

    public void setItem(List<ShortVideoItem> item) {
        this.item = item;
    }
}
