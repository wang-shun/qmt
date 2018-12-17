package com.lesports.qmt.op.web.api.core.vo.baidu;



import javax.xml.bind.annotation.XmlType;

/**
 * Created by ruiyuansheng on 2015/12/16.
 */
@XmlType(propOrder = { "loc", "lastmod"})
public class SiteMap {

    //必填，识别sitemap的位置
    private String loc;
    //必填，识别相对sitemap文件的修改时间
    private String lastmod;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }
}
