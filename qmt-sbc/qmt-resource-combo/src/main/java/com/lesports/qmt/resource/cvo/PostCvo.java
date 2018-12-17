package com.lesports.qmt.resource.cvo;

/**
 * create by wangjichuan  date:16-12-21 time:13:04
 * 社区帖子的vo
 */
public class PostCvo extends BaseCvo {
    //发帖人id
    private String uid;
    private String uname;
    private String figurl;
    private Boolean isBigV;
    //回复数
    private int reply;
    //总的回复数  评论数+评论的回复数
    private int allReply;
    //访问量
    private int visit;
    //赞数
    private int up;
    // 真实的点赞数目
    private int realUp;
    private Long ctime;
    private Long lastTime;
    private String tag;
    private String tagname;
    private String tagtype;
    private String tagpicture;
    private Boolean good;
    private boolean richTxt;
    private boolean collapsed;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFigurl() {
        return figurl;
    }

    public void setFigurl(String figurl) {
        this.figurl = figurl;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getAllReply() {
        return allReply;
    }

    public void setAllReply(int allReply) {
        this.allReply = allReply;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getRealUp() {
        return realUp;
    }

    public void setRealUp(int realUp) {
        this.realUp = realUp;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTagtype() {
        return tagtype;
    }

    public void setTagtype(String tagtype) {
        this.tagtype = tagtype;
    }

    public String getTagpicture() {
        return tagpicture;
    }

    public void setTagpicture(String tagpicture) {
        this.tagpicture = tagpicture;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public boolean isRichTxt() {
        return richTxt;
    }

    public void setRichTxt(boolean richTxt) {
        this.richTxt = richTxt;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Boolean getIsBigV() {
        return isBigV;
    }

    public void setIsBigV(Boolean isBigV) {
        this.isBigV = isBigV;
    }
}
