/*************************
 * program_album.thrift
 * Lesports Quan Mei Ti
 *************************************/
namespace java com.lesports.qmt.sbc.api.dto

include "api_common.thrift"

include "episode.thrift"
include "video.thrift"

//自制
struct TProgramAlbum {
    1: i64 id,
    //名称
    2: optional string name,
    //自制专辑下最新一期节目的信息
    3: optional episode.TComboEpisode latestEpisode,
    //自制专辑封面logo
    4: optional map<string,string> logo,
    //自制专辑下最新一期视频的信息
    5: optional video.TVideo latestVideo,
    //是否推荐
    6: optional bool recommend,
    //推荐排序
    7: optional i32 recommendOrder,
}
