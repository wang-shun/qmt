/**************************************
 * sms_service.thrift
 * Lesports Sms Service
 *************************************/


include "api_common.thrift"
include "sbc_common.thrift"

include "live.thrift"
include "resource.thrift"
include "news.thrift"
include "topic.thrift"
include "program_album.thrift"
include "episode.thrift"
include "param.thrift"
include "video.thrift"

namespace java com.lesports.qmt.sbc.api.service



/**
 * Lesports Quan Mei Ti Live Service Definition.
 * @author ellios
 */
service TSbcLiveService{
     #################### live ##################################

     live.TLive getTLiveById(1: i64 id, 2: api_common.CallerParam caller),
}


/**
 * Lesports Quan Mei Ti Resource Service Definition.
 * @author ellios
 */
service TSbcResourceService{
    ###################### resource ##################################
    /**
    * 通过资源位id获取资源位信息
    **/
    resource.TResource getTResourceById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过资源位id获取资源位信息
    **/
    resource.TResource getLastVersionTResourceById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过资源位id列表获取资源位信息
    **/
    list<resource.TResource> getTResourcesByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 通过资源位内容id获取资源位内容
    **/
    resource.TResourceContent getTResourceContentById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过资源位内容id列表获取资源位内容列表
    **/
    list<resource.TResourceContent> getTResourceContentsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 通过线上资源位内容id列表获取线上资源位内容列表
    **/
    list<resource.TResourceContent> getOnlineTResourceContentsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 通过资源位Id获取资源位内容列表
    **/
    list<i64> getResourceContentIdsByResourceId(1:i64 resourceId, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 通过线上资源位Id获取资源位内容列表
    **/
    list<i64> getOnlineResourceContentIdsByResourceId(1:i64 resourceId, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 通过资源位Id获取资源位内容个数 分页的时候用到
    **/
    i64 countResourceContentByResourceId(1:i64 resourceId,2: api_common.CallerParam caller),
}

/**
 * Lesports Quan Mei Ti Content Service Definition.
 * @author ellios
 */
service TSbcService {

    /*************************************videos*********************************/

    /**
    * 通过视频id获取视频信息
    **/
    video.TVideo getTVideoById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过视频id获取视频信息
    **/
    video.TVideo getTVideoByLeecoVid(1: i64 leecoVid, 2: api_common.CallerParam caller),

    /**
    * 通过视频id列表获取视频信息
    **/
    list<video.TVideo> getTVideoByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 根据相关信息获取视频列表
    * id 可以是大项id,小项id,赛事id,比赛id,球员id,球队id
    **/
    list<i64> getVideoIdsByRelatedId(1: param.GetRelatedVideosParam p, 2: api_common.PageParam page,
        3: api_common.CallerParam caller),

	/**
    * 根据相关信息获取视频数量
    * id 可以是大项id,小项id,赛事id,比赛id,球员id,球队id
    **/
    i64 countVideosByRelatedId(1: param.GetRelatedVideosParam p, 2: api_common.CallerParam caller),

	/**
	* 获取最新发布的视频id
	**/
	list<i64> getLatestVideoIds(1: api_common.PageParam page, 2: api_common.CallerParam caller),

	/**
	* 获取和某视频关联的其他视频id列表
	**/
	list<i64> getVideoIdsRelatedWithSomeVideo(1: i64 vid, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 获取专辑中某视频的索引位置
    **/
    video.TVideoIndexInfo getIndexOfVideoInAlbum(1: i64 vid, 2: i64 aid, 3: api_common.CallerParam caller),

   /**
    * 获取专辑下面的非正片视频
    **/
    list<i64> getNonPositiveVideosByAid(1: i64 aid, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 通过关联id获取视频列表
    * id 可以是大项id,小项id,赛事id,比赛id,球员id,球队id
    * type 类型: -1:全部 0:回放 1:集锦 2:战报 3:正片 20:其它
    * 正常分页
    **/
    video.TVideoInfo getTVideoInfoByRelatedIdAndType (1: param.GetRelatedVideosParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 获取专辑下面的所有录播视频
    **/
    list<video.TVideo> getRecordVideosInAlbum(1: i64 aid, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /**
    * 按照年月获取专辑下面的正片视频
    **/
    video.TFeatureInfo getFeatureVideosByYears(1: i64 aid, 2: string yearAndMonth,3: api_common.CallerParam caller),

    /**
    * 获取自制节目下视频列表
    **/
    list<i64> getAlbumVideosByAid(1: i64 aid, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

    /*************************************news*********************************/

    /**
    * 通过新闻id获取新闻信息
    **/
    news.TNews getTNewsById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过新闻id列表获取新闻信息
    **/
    list<news.TNews> getTNewsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 根据vid获取新闻
    **/
    news.TNews getTNewsByVid(1: i64 vid, 2: api_common.CallerParam caller),

    /**
    * 根据相关信息获取新闻列表
    * id 可以是大项id,小项id,赛事id,比赛id,球员id,球队id
    **/
    list<i64> getNewsIdsByRelatedId(1: param.GetRelatedNewsParam p, 2: api_common.PageParam page,
        3: api_common.CallerParam caller),

    /**
    * 根据相关信息获取新闻数量
    * id 可以是大项id,小项id,赛事id,比赛id,球员id,球队id
    **/
    i64 countNewsByRelatedId(1: param.GetRelatedNewsParam p, 2: api_common.CallerParam caller),

    /**
    * 获取和某新闻关联的其他新闻Id列表
    **/
    list<i64> getNewsIdsRelatedWithSomeNews(1: i64 newsId, 2: list<sbc_common.NewsType> types, 3: api_common.PageParam page,
        4: api_common.CallerParam caller),

    /**
    * 获取和某新闻关联的其他新闻Id列表
    **/
    map<string, i64> getNewsIdsNearbySomeNews(1: i64 newsId, 2: sbc_common.NewsType type, 3: api_common.CallerParam caller),

    /*************************************topic*********************************/

    /**
    * 通过专题id获取专题信息
    **/
    topic.TTopic getTTopicById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过专题id列表获取专题信息
    **/
    list<topic.TTopic> getTTopicsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 通过专题包id获取专题包信息
    **/
    topic.TTopicItemPackage getTTopicItemPackageById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 通过专题包id列表获取专题包信息
    **/
    list<topic.TTopicItemPackage> getTTopicItemPackagesByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 通过专题下的专题包id
    **/
    list<i64> getTopicItemPackageIdsByTopicId(1: i64 topicId, 2: api_common.CallerParam caller),

    /*************************************program album*********************************/

    /**
    * 根据专辑id获取专辑信息
    **/
	program_album.TProgramAlbum getTProgramAlbumById(1: i64 id, 2: api_common.CallerParam caller),

    /**
    * 根据专辑id批量获取专辑信息
    **/
	list<program_album.TProgramAlbum> getTProgramAlbumsByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

    /**
    * 获取所有的专辑列表
    **/
    list<i64> getTProgramAlbums(1: i64 tagId, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

}

/**
 * Lesports Quan Mei Ti Episode Service Definition.
 *
 */
service TSbcEpisodeService{
     #################################### episode ##########################################

     /**
     * 晋级之路
     **/
     list<i64> getTheRoadOfAdvance(1: i64 cid, 2: i64 csid, 3: api_common.CallerParam caller),

     /**
     * 从当前时间开始获取节目列表
     **/
     list<i64> getCurrentEpisodeIds(1: param.GetCurrentEpisodesParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

     /**
     * 按时间轴获取节目列表
     **/
     list<i64> getTimelineEpisodesByCids(1: param.GetCurrentEpisodesParam p, 2: api_common.PageParam page, 3: api_common.CallerParam caller),

     /**
     * 按天计算节目数量
     **/
     list<i64> countEpisodesByDay(1: param.CountEpisodesByDayParam p, 2: api_common.CallerParam caller),

     /**
     * 获取某天的节目列表
     **/
     list<i64> getSomeDayEpisodeIds(1: param.GetSomeDayEpisodesParam p, 2: api_common.PageParam page, 3:api_common.CallerParam caller),

     /**
      * 获取某天的节目列表
      **/
     list<i64> getSomeDayEpisodeIdsWithTimezone(1: param.GetSomeDayEpisodesParam p, 2: api_common.PageParam page, 3:api_common.CallerParam caller),


      /**
      * 获取有节目的日期
      **/
     list<string> getStartDatesBySomeDayEpisodeParam(1: param.CountEpisodesByDayParam p, 2:api_common.CallerParam caller),

     /**
     * 获取某个赛季，某个阶段的的节目信息,如(第几轮的比赛)
     **/
     list<i64> getEpisodeIdsOfSeasonByMetaEntryId (1: param.GetEpisodesOfSeasonByMetaEntryIdParam p,2: api_common.PageParam page
         3:api_common.CallerParam caller),

     /**
     * 获取和某节目相关的其他节目
     **/
     list<i64> getEpisodeIdsRelatedWithSomeEpisode(1: i64 episodeId, 2: i32 count, 3: api_common.CallerParam caller),

     /**
     * 获取某节目附近的其他节目,上一场和下一场
     **/
     map<string, i64> getEpisodeIdsNearbySomeEpisode(1: param.GetEpisodesNearbySomeEpisodeParam p, 2: api_common.CallerParam caller)

     /**
     * 根据节目id，获取Combo节目信息
     **/
     episode.TComboEpisode getTComboEpisodeById(1: i64 id, 2: api_common.CallerParam caller),

     /**
     * 根据节目id，批量获取Combo节目信息
     **/
     list<episode.TComboEpisode> getTComboEpisodesByIds(1: list<i64> ids, 2: api_common.CallerParam caller),

     /**
     * 获取专辑下的节目列表
     **/
     list<episode.TComboEpisode> getTComboEpisodesInAlbum(1: i64 albumId, 2: api_common.PageParam page, 3:api_common.CallerParam caller)

    /**
     * 获取(奥运)赛程
     **/
     list<episode.TComboEpisode> getEpisodes4OlyMatchs(1: param.GetMatchsEpisodesParam p, 2: api_common.PageParam page, 3:api_common.CallerParam caller),

    /**
    *  获取(奥运)赛程总数
    **/
    i64 countEpisodes4OlyMatchs(1: param.GetMatchsEpisodesParam p, 2: api_common.CallerParam caller),


     /**
      * 根据liveid获取节目
      */
     i64 getEpisodeIdByLiveId(1:string liveId, 2: api_common.CallerParam caller),

     /**
      * 获取图文直播id所关联的节目id
      */
     i64 getEpisodeIdByTextLiveId(1:i64 textLiveId, 2: api_common.CallerParam caller),

     /**
     *根据id获取往期的节目
     **/
     list<i64> getPassedEpisodeIdsInAlbum(1: i64 albumId,2: api_common.PageParam page,
     3: api_common.CallerParam caller),

     /**
      * 获取比赛id所关联的节目id
     */
     i64 getEpisodeIdByMid(1:i64 mId, 2: api_common.CallerParam caller),

     /**
     * 根据多个章鱼赛程ID获取节目列表
     * liveRequest: 直播请求类型: 只要直播,直播或者重点赛事,不过滤直播
     **/
     list<i64> getEpisodeIdsOfOctopus(1: param.GetZhangyuEpisodesParam p,2: api_common.PageParam page,3: api_common.CallerParam caller),

	/**
	 * 从当前时间开始,获取参赛者的比赛信息
	 * competitorId : 参赛者id
	 * csid : 赛季id, 可以为0, 为0取最新赛季,-1表示不过滤赛季
	 * statusRequest : 直播请求状态,未开始,比赛中,比赛后,未结束
	 * liveRequest: 直播请求类型: 只要直播,直播或者重点赛事,不过滤直播
	 * pageRequest: 分页请求
	 **/
	 list<i64> getCurrentEpisodeIdsByCompetitorId(1: i64 competitorId,2: param.GetEpisodesOfSeasonByMetaEntryIdParam p, 3: api_common.PageParam page,4: api_common.CallerParam caller),


	/**
	* 超级手机桌面
	**/
	list<i64> getLephoneDesktopChannelEpisodes(1: string date, 2: i64 gameType, 3: api_common.CallerParam caller),

	/**
	* app3.0获取一段时间的推荐比赛
	**/
	list<i64> getPeriodAppRecommendEpisodes(1: api_common.CallerParam caller),

	/**
     * 从当前时间开始获取会员节目列表
     **/
     list<i64> getCurrentMemberEpisodeIds(1: param.GetCurrentEpisodesParam p,2: api_common.PageParam page, 3: api_common.CallerParam caller),

	/**
     * 获取直播券对应的赛程列表
     **/
     list<i64> getTicketEpisodeIds(1: param.GetCurrentEpisodesParam p,2: api_common.PageParam page, 3: api_common.CallerParam caller),

	/**
     * 通栏比赛卡片
     **/
     list<i64> getEpisodeIds4CommonCompetitionResource(1: param.GetEpisodes4CommonCompetitionResourceParam p,2: api_common.PageParam page, 3: api_common.CallerParam caller),

}
