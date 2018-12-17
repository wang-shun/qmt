package com.lesports.qmt.transcode.web.service;

import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.web.param.AddVideoFormatParam;
import com.lesports.qmt.transcode.web.param.LiveChannelVo;
import com.lesports.qmt.transcode.web.param.LiveTaskParam;
import com.lesports.qmt.transcode.web.param.VideoTranscodeParam;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
public interface LiveToVideoTaskService {
    /**
     * 初始化创建直播打点任务
     * @param param
     * @return
     */
    LiveToVideoTask createLiveToVideoTask(LiveTaskParam param, VideoTranscodeParam transcodeParam, Video video);

    /**
     * 初始化 创建直播打点子任务
     * @param parent
     * @return
     */
    List<LiveToVideoSubTask> createLiveToVideoSubTasks(LiveToVideoTask parent);

    /**
     * 根据直播打点任务和子任务 创建录制信息
     * @param liveToVideoTask
     * @param subTasks
     * @return
     */
    boolean createLiveRecording(LiveToVideoTask liveToVideoTask, List<LiveToVideoSubTask> subTasks);

    /**
     * 更新子任务信息
     * @param param
     * @return
     */
    LiveToVideoSubTask updateSubTaskStatus(LiveToVideoSubTask param);

    LiveToVideoTask updateTaskStatus(long taskId, int status);

    boolean createEncapsulationTask(LiveToVideoSubTask subTask);

    Page<LiveToVideoTask> getLiveToVideoTaskPage(String user, int pageNumber, int pageSize);

    boolean addliveToVideoTask(LiveToVideoTask task, AddVideoFormatParam videoSourceParam);

    /**
     * 获取直播频道列表
     * @return
     */
    List<ChannelIdNameEnameDTO> getLiveChannels();

    /**
     * 获取直播频道详情 带码率
     * @return
     */
    LiveChannelVo getChannelDetail(Long channel);

    LiveToVideoTask updateVideoId(LiveToVideoTask task, long videoId);

}
