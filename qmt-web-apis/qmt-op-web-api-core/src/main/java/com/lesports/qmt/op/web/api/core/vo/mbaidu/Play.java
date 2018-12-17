package com.lesports.qmt.op.web.api.core.vo.mbaidu;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lufei1 on 2015/11/20.
 */
@XmlType(propOrder = {"img", "text", "url", "time"})
public class Play {
    private String img;
    private String text;
    private String url;
    private String time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
