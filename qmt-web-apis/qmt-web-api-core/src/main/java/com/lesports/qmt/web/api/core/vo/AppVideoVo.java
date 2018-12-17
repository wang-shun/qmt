package com.lesports.qmt.web.api.core.vo;

/**
 * User: ellios
 * Time: 15-7-9 : 下午10:22
 */
public class AppVideoVo {

    private String vid;
    private String title;
    private String desc;
    private String createTime;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppVideoVo{");
        sb.append("vid='").append(vid).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
