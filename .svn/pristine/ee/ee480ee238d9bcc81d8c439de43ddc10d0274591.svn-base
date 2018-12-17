/**************************************
 * transcode_common.thrift
 * Lesports Param
 *************************************/
namespace java com.lesports.qmt.transcode.model
//录制类型
enum RecordingType{
    FLV = 0,
    TS = 1
}

//任务类型
enum LiveToVideoTaskType{
    ENCAPSULATION = 2 ,
    RECORDING = 1 ;
}
/**
* http://wiki.letv.cn/pages/viewpage.action?pageId=60097273
**/
enum LiveToVideoTaskStatus{
    RECORDING = 20,
    RECORD_SUCCESS = 21,
    RECORD_FAIL = 29,
    TRANSCODING = 38,
    TRANSCODING_FAIL = 39,
    TASK_FINISH = 40
}

//视频转码任务状态
enum VideoTranscodeTaskStatus{
    UPLOAD_SUCCESS = 1,
    UPLOAD_FAIL = 2,
    TRANSCODING = 3,
    TRANSCODING_FAIL = 4,
    TASK_FINISH = 5,
    NEW = 6,
    TRANSCODE_CONFIG_COMMIT = 7,
    GET_DOWNLOAD_URL_FAIL = 8,
    TRANSCODING_SUCCESS = 9
}
