package com.lesports.qmt.transcode.web.param;

import com.google.common.collect.Lists;
import com.lesports.qmt.transcode.model.VideoTranscodeSubTask;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 11/4/16.
 */
public class SingleTaskDetailParam implements Serializable {
    VideoTranscodeTask videoTranscodeTask;
    List<VideoTranscodeSubTask> videoTranscodeSubTasks = Lists.newArrayList();

    public SingleTaskDetailParam() {
    }

    public SingleTaskDetailParam(VideoTranscodeTask videoTranscodeTask, List<VideoTranscodeSubTask> videoTranscodeSubTasks) {
        this.videoTranscodeTask = videoTranscodeTask;
        if (null != videoTranscodeSubTasks) this.videoTranscodeSubTasks = videoTranscodeSubTasks;
    }
}
