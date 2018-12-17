package com.lesports.qmt.web.datacenter;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.client.QmtSbcResourceApis;
import com.lesports.qmt.web.datacenter.dataprocess.DataProcess;
import com.lesports.qmt.web.datacenter.dataprocess.DataProcessFactory;
import com.lesports.qmt.web.datacenter.support.AbstractQmtSbcResourceContentCenter;
import com.lesports.qmt.web.datacenter.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wangjichuan  date:16-12-13 time:15:30
 */
public class QmtSbcResourceContentCenter extends AbstractQmtSbcResourceContentCenter {

    private static final Logger LOG = LoggerFactory.getLogger(QmtSbcResourceContentCenter.class);

    private QmtSbcResourceContentCenter(){

    }
    private static void prepareResourceData(ResourceVo resourceVo,TResource tResource){
        if(tResource != null){
            resourceVo.setResourceName(tResource.getName());
            resourceVo.setResourceDesc(tResource.getDesc());
            resourceVo.setUpdateType(tResource.getUpdateType());
            resourceVo.setType(tResource.getType());
            resourceVo.setLinks(revertResourceLinks(tResource.getLinks()));
        }
    }
    private static void prepareResourceContentData(ResourceVo resourceVo,TResource tResource,PageParam pageParam,CallerParam callerParam){
        List<ContentBaseVo> contents = new ArrayList<ContentBaseVo>();
        Long resourceId = tResource.getId();
        ResourceUpdateType updateType = tResource.getUpdateType();
        List<TResourceContent> tResourceContents = QmtSbcResourceApis.getTResourceContentsByResourceId(resourceId,pageParam,callerParam);
        resourceVo.setPage(pageParam.getPage());
        if(updateType == ResourceUpdateType.MANUAL){
            resourceVo.setCount(QmtSbcResourceApis.countResourceContentByResourceId(resourceId, callerParam));  //手动的数据个数就是手动维护的个数
        }
        if(CollectionUtils.isEmpty(tResourceContents)){
            return;
        }
        if (tResource.getType() == ResourceDataType.TV_CONTENT || tResource.getType() == ResourceDataType.TV_DESKTOP_THEME) {
            // TV类型资源位特殊处理
            DataProcess<?, TvContentVo> tvProcess = DataProcessFactory.getDataProcess(TvContentVo.class);
            for (TResourceContent tResourceContent : tResourceContents) {
                contents.add(tvProcess.constructManualData(tResourceContent, callerParam, DataProcess.ConstructType.NO_RPC));
            }
        } else {
            if(tResource.getType() == ResourceDataType.COMMON_COMPETITION){//通栏比赛卡片自动的数据要走异步，把它视为手动的维护数据的。
                updateType = ResourceUpdateType.MANUAL;
            }
            switch (updateType) {
                case MANUAL:
                    contents = gainMunualResourceContent(tResourceContents, callerParam);
                    break;
                case AUTO:
                    TResourceContent tResourceContent = tResourceContents.get(0);
                    resourceVo.setResourceContentLinks(revertResourceLinks(tResourceContent.getLinks()));
                    contents = gainAutoResourceContent(tResource.getType(), tResourceContents.get(0), pageParam, callerParam);
                    break;
            }
        }
        resourceVo.setContents(contents);
    }
    private static List<LinkVo> revertResourceLinks(List<TResourceLink> tResourceLinks){
        List<LinkVo> linkVos = null;
        if(CollectionUtils.isNotEmpty(tResourceLinks)){
            linkVos = new ArrayList<LinkVo>();
            for(TResourceLink tResourceLink : tResourceLinks){
                LinkVo linkVo = new LinkVo();
                linkVo.setName(tResourceLink.getName());
                linkVo.setUrl(tResourceLink.getUrl());
                linkVos.add(linkVo);
            }
        }
        return linkVos;
    }
    private static List<ContentBaseVo> gainAutoResourceContent(ResourceDataType resourceDataType,TResourceContent tResourceContent,PageParam pageParam,CallerParam callerParam){
        List<ContentBaseVo> contents = new ArrayList<ContentBaseVo>();
        DataProcess dataProcess = null;
        //不同类型的资源位取数据的规则不同
        if(resourceDataType == ResourceDataType.COMMON_COMPETITION){
            //根据分页 赛事ids 已结束场次  直播中场次  未开始场次 来搜索数据列表  当前只需要吐数据规则 走异步接口
            dataProcess = DataProcessFactory.getDataProcess(EpisodeVo.class);
        }else if(resourceDataType == ResourceDataType.COMMON_CONTENT_LIST
                    ||resourceDataType == ResourceDataType.COMPETITION_MODULE
                        || resourceDataType == ResourceDataType.PLAYER_MODULE){
            //根据分页 内容类型  时间排序方式  正文星级 标签  来搜索新闻
            dataProcess = DataProcessFactory.getDataProcess(NewsVo.class);
        }else if(resourceDataType == ResourceDataType.DATA_LIST){//积分榜
            dataProcess = DataProcessFactory.getDataProcess(RankVo.class);
        }
        contents = dataProcess.constructAutoData(tResourceContent, pageParam, callerParam);
        return contents;
    }
    private static List<ContentBaseVo> gainMunualResourceContent(List<TResourceContent> tResourceContents,CallerParam callerParam){
        List<ContentBaseVo> contents = new ArrayList<ContentBaseVo>();
        for(TResourceContent tResourceContent : tResourceContents){
            DataProcess dataProcess = null;
            Class clazz = null;
            DataProcess.ConstructType constructType = DataProcess.ConstructType.ALL;
            switch (tResourceContent.getType()){
                //如果内容是新闻类型的 以下这几个都是新闻类型的
                case NEWS:;
                case RICHTEXT:
                case IMAGE_ALBUM:
                case VIDEO_NEWS: clazz = NewsVo.class;break;
                //直播相关
                case EPISODE:clazz = EpisodeVo.class;break;
                case POST:clazz = PostVo.class;break;
                case H5:clazz = H5Vo.class;break;
            }
            dataProcess = DataProcessFactory.getDataProcess(clazz);
            contents.add(dataProcess.constructManualData(tResourceContent,callerParam,constructType));
        }
        return contents;
    }
    private static void checkArgument(Long resourceId){
        Preconditions.checkNotNull(resourceId,"版块id不能为空");
        Preconditions.checkArgument(resourceId > 0, "资源位id不合法，需要大于0");
    }
    public static ResourceVo getResourceData(Long resourceId,PageBean pageBean,CallerBean callerBean){
        checkArgument(resourceId);
        PageParam pageParam = pageBean.getPageParam();
        CallerParam callerParam = callerBean.getCallerParam();
        TResource tResource = QmtSbcResourceApis.getTResourceById(resourceId, callerParam);
        ResourceVo resourceVo = new ResourceVo();
        if(tResource != null){
            prepareResourceData(resourceVo, tResource);
        }
        return resourceVo;
    }

}
