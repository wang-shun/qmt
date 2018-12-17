package com.lesports.qmt.tlive.model;

import com.google.common.collect.Sets;
import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.tlive.api.common.AnchorRole;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Set;

/**
 * 图文直播主表
 * Created by lufei1 on 2015/9/13.
 */
@Document(collection = "text_lives")
public class TextLive extends QmtModel<Long> {
    private static final long serialVersionUID = -3876613116988084764L;

    //直播员信息
    private Set<Anchor> anchors = Sets.newHashSet();
    //图文直播状态
    private LiveStatus status;
    //图文直播类型
    private TextLiveType type;
    //节目id
    private long eid;
    //比赛id
    private long mid;
    //父id 直播员直播属于主直播，play-by-play数据是子直播
    @Field("parent_id")
    private long parentId;
    //第三方消息最新id
    @Field("latest_partner_id")
    private String latestPartnerId;
    //是否被删除
    private Boolean deleted;


    /**
     * 主播信息
     */
    public static class Anchor implements Serializable {
        private static final long serialVersionUID = 2695634449226727118L;

        //直播员ID
        @Field("anchor_id")
        private Long anchorId;
        //直播员昵称
        private String name;
        //直播员头像
        private String logo;
        //直播员角色
        private AnchorRole role;

        public Long getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(Long anchorId) {
            this.anchorId = anchorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public AnchorRole getRole() {
            return role;
        }

        public void setRole(AnchorRole role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "Anchor{" +
                    "anchorId=" + anchorId +
                    ", name='" + name + '\'' +
                    ", logo='" + logo + '\'' +
                    ", role=" + role +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Anchor anchor = (Anchor) o;

            if (anchorId != null ? !anchorId.equals(anchor.anchorId) : anchor.anchorId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return anchorId != null ? anchorId.hashCode() : 0;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Anchor> getAnchors() {
        return anchors;
    }

    public void setAnchors(Set<Anchor> anchors) {
        this.anchors = anchors;
    }

    public LiveStatus getStatus() {
        return status;
    }

    public void setStatus(LiveStatus status) {
        this.status = status;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public TextLiveType getType() {
        return type;
    }

    public void setType(TextLiveType type) {
        this.type = type;
    }

    public String getLatestPartnerId() {
        return latestPartnerId;
    }

    public void setLatestPartnerId(String latestPartnerId) {
        this.latestPartnerId = latestPartnerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TextLive{");
        sb.append("id=").append(id);
        sb.append(", anchors=").append(anchors);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", eid=").append(eid);
        sb.append(", mid=").append(mid);
        sb.append(", parentId=").append(parentId);
        sb.append(", latestPartnerId='").append(latestPartnerId).append('\'');
        sb.append(", createAt='").append(createAt).append('\'');
        sb.append(", updateAt='").append(updateAt).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
