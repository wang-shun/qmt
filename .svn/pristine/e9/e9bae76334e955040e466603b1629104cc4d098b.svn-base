package com.lesports.qmt.web.model;

import java.io.Serializable;

/**
 * 分享
 * Created by gengchengliang on 2015/7/24.
 */

public class Share implements Serializable {

    private static final long serialVersionUID = 5801457987377182508L;

    private long id;
    //分享的url
    private String url;
    //分享的图片
    private String pic;
    //分享的标题
    private String title;
    //分享的描述
    private String desc;
    //分享类型
    private Integer shareType;
    //分享的图片4:3
    private String pic43;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getPic43() {
        return pic43;
    }

    public void setPic43(String pic43) {
        this.pic43 = pic43;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Share{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", pic='").append(pic).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", shareType=").append(shareType);
        sb.append(", pic43='").append(pic43).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static enum ShareType{
        //比赛
        MATCH(0),
        //直播
        LIVE(1),
        //视频
        VIDEO(2),
        //专辑
        ALBUM(3),
        //新闻
        NEWS(4),
        //轮播台
        CAROUSEL(5),
        //专题
        TOPIC(6),
        //图文直播
        TEXTLIVE(7),
        //回放
        RECORD(8),
        //集锦
        HIGHLIGHTS(9),
        //相关
        RELATED(10),
        //富文本新闻
        RICHTEXT(11),
        //图集
        IMAGE_ALBUM(12),
        //帖子
        POST(13),
		//小专题列表页
		TOPIC_LIST(14),
		//突发专题列表页
		HTOPIC_LIST(15);

        private final int value;

        private ShareType(int value) {
            this.value = value;
        }

        /**
         * Get the integer value of this enum value, as defined in the Thrift IDL.
         */
        public int getValue() {
            return value;
        }

        /**
         * Find a the enum type by its integer value, as defined in the Thrift IDL.
         * @return null if the value is not found.
         */
        public static ShareType findByValue(int value) {
            switch (value) {
                case 0:
                    return MATCH;
                case 1:
                    return LIVE;
                case 2:
                    return VIDEO;
                case 3:
                    return ALBUM;
                case 4:
                    return NEWS;
                case 5:
                    return CAROUSEL;
                case 6:
                    return TOPIC;
                case 7:
                    return TEXTLIVE;
                case 8:
                    return RECORD;
                case 9:
                    return HIGHLIGHTS;
                case 10:
                    return RELATED;
                case 11:
                    return RICHTEXT;
                case 12:
                    return IMAGE_ALBUM;
                case 13:
                    return POST;
				case 14:
					return TOPIC_LIST;
				case 15:
					return HTOPIC_LIST;
                default:
                    return null;
            }
        }
    }

}


