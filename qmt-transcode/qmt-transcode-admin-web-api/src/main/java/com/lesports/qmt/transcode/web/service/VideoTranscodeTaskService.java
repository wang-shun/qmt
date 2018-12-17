package com.lesports.qmt.transcode.web.service;

import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.transcode.model.VideoTranscodeSubTask;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.web.param.*;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
public interface VideoTranscodeTaskService {
    VideoTranscodeTask createVideoTranscodeTask(VideoUploadInitParam videoUploadInitParam, Video video);

    VideoTranscodeTask reUpload(VideoUploadInitParam videoUploadInitParam);

    VideoTranscodeTask submitVideoTranscodeTask(VideoTranscodeParam param);

    VideoTranscodeTask updateVideoId(VideoTranscodeTask task, long videoId);

    VideoTranscodeTask updateFileUrl(VideoFileUrlParam param);

    SingleTaskDetailParam getSingleTaskDetailParamByVideoTranscodeTaskId(long id);

    VideoTranscodeSubTask addFormat(VideoTranscodeTask id, AddVideoFormatParam param);

    Page<VideoTranscodeTask> getVideoTranscodeTaskPage(String uid, int status, int pageNumber, int pageSize);

    Map getVideoTranscodeTask(long leecoVid);

    VideoUploadInitVo uploadSubTitleInit(long leecoVid, String fileName);

}
