package com.lesports.qmt.sbc.vo;

import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.tlive.model.TextLive;
import org.apache.commons.beanutils.LeBeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/5.
 */
public class EpisodeVo extends Episode implements QmtVo {

    private List<Stream> streams;

    private List<RelatedTagVo> relatedTags;

    public static class Stream implements Serializable {
        private String id;
        //直播流名称（解说语音）
        private String commentaryLanguage;
        //直播状态
        private LiveStatus status;
        //开始时间
        private String startTime;
        //结束时间
        private String endTime;
        //直播流的展示顺序
        private Integer order;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getCommentaryLanguage() {
            return commentaryLanguage;
        }

        public void setCommentaryLanguage(String commentaryLanguage) {
            this.commentaryLanguage = commentaryLanguage;
        }

        public LiveStatus getStatus() {
            return status;
        }

        public void setStatus(LiveStatus status) {
            this.status = status;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Stream{");
            sb.append("id='").append(id).append('\'');
            sb.append(", commentaryLanguage='").append(commentaryLanguage).append('\'');
            sb.append(", status=").append(status);
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", endTime='").append(endTime).append('\'');
            sb.append(", order=").append(order);
            sb.append('}');
            return sb.toString();
        }
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<RelatedTagVo> getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(List<RelatedTagVo> relatedTags) {
        this.relatedTags = relatedTags;
    }

    public EpisodeVo() {

    }

    public EpisodeVo(Episode episode) {
        try {
            LeBeanUtils.copyProperties(this, episode);
        } catch (IllegalAccessException | InvocationTargetException e) {

        }
    }

    public Episode toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Episode episode = new Episode();
        try {
            LeBeanUtils.copyProperties(episode, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return episode;
    }
}
