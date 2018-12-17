package com.lesports.qmt.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.*;
import com.lesports.qmt.resource.constants.DataType;
import com.lesports.qmt.resource.converter.CvoConverter;
import com.lesports.qmt.resource.converter.support.CvoConverterBuilders;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.core.ComboResourceCreater;
import com.lesports.qmt.resource.cvo.*;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbc.client.QmtSbcResourceApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: ellios
 * Time: 16-12-28 : 下午12:15
 */
public class QmtComboResourceCreater implements ComboResourceCreater {

    private static final Logger LOG = LoggerFactory.getLogger(QmtComboResourceCreater.class);

    @Override
     public Object createComboResources(Long resourceId, PageParam page, CallerParam caller,int dataType) {
        Preconditions.checkArgument(LeNumberUtils.toLong(resourceId) > 0, "资源位id不合法，需要大于0");
        TResource resourceDto = QmtSbcResourceApis.getTResourceById(resourceId, caller,dataType);
        if (resourceDto == null) {
            LOG.warn("fail to createComboResource. resourceDto is null. resourceId : {}, caller : {}", resourceId, caller);
            return null;
        }
        if(resourceDto.getUpdateType() == ResourceUpdateType.AUTO){
            dataType = 2;
        }
        if(!resourceDto.isGroup()){
            return createComboResource(resourceDto,page,caller,dataType);
        }
        List<ComboResource> comboResourceList = new ArrayList<>();
        List<Long> childIds = resourceDto.getChildIds();
        if(childIds != null && !childIds.isEmpty()){
            for(Long childId : childIds){
                TResource childResourceDto = QmtSbcResourceApis.getTResourceById(childId, caller,dataType);
                comboResourceList.add(createComboResource(childResourceDto,page,caller,dataType));
            }
        }
        return comboResourceList;
    }

    @Override
    public Object createComboResources(Long resourceId, PageParam page, CallerParam caller) {
        return createComboResources(resourceId,page,caller,DataType.VIEW);
    }

    @Override
    public ComboResource createComboResource(Long resourceId, PageParam page, CallerParam caller) {
        int dataType = DataType.VIEW;
        Preconditions.checkArgument(LeNumberUtils.toLong(resourceId) > 0, "资源位id不合法，需要大于0");
        TResource resourceDto = QmtSbcResourceApis.getTResourceById(resourceId, caller,dataType);
        if (resourceDto == null) {
            LOG.warn("fail to createComboResource. resourceDto is null. resourceId : {}, caller : {}", resourceId, caller);
            return null;
        }

        if(resourceDto.getUpdateType() == ResourceUpdateType.AUTO){
            dataType = DataType.PREVIEW;
        }
        return createComboResource(resourceDto,page,caller,dataType);
    }

    private ComboResource createComboResource(TResource resourceDto, PageParam page, CallerParam caller,int dataType){
        ComboResource comboResource = createComboResource5Dto(resourceDto);
        prepareComboData(comboResource, resourceDto, page, caller,dataType);
        return comboResource;
    }


    private ComboResource createComboResource5Dto(TResource resource) {
        if (resource == null) {
            return null;
        }
        ComboResource comboResource = new ComboResource();
        comboResource.setResourceName(resource.getName());
        comboResource.setResourceDesc(resource.getDesc());
        comboResource.setUpdateType(resource.getUpdateType());
        comboResource.setType(resource.getType());
        comboResource.setLinks(resource.getLinks());
        comboResource.setResourceShortName(resource.getShortName());
        return comboResource;
    }

    private void prepareComboData(ComboResource comboResource,TResource resource, PageParam page, CallerParam caller,int dataType) {
        long resourceId = resource.getId();
        ResourceUpdateType updateType = resource.getUpdateType();
        int pageNum = page.getPage();
        if(updateType == ResourceUpdateType.AUTO){
            page.setPage(1);
        }
        List<TResourceContent> contents = QmtSbcResourceApis.
                getTResourceContentsByResourceId(resourceId, page, caller,dataType);
        page.setPage(pageNum);

        if (CollectionUtils.isEmpty(contents)) {
            LOG.info("resource content is empty. resource : {}, page : {},dataType :{} caller : {}", resource, page,dataType, caller);
            comboResource.setContents(Collections.EMPTY_LIST);
            return;
        }
        LOG.info("resourceId={},resourceContentList size={}",resourceId,contents.size());

        if (resource.getType() == ResourceDataType.TV_CONTENT || resource.getType() == ResourceDataType.TV_DESKTOP_THEME) {
            comboResource.setContents(getTvContentVoList(contents, caller));
            return;
        }

        if (resource.getType() == ResourceDataType.COMMON_COMPETITION) {
            //通栏比赛卡片自动的数据要走异步，把它视为手动的维护数据的。
            updateType = ResourceUpdateType.MANUAL;
        }

        if (updateType == ResourceUpdateType.MANUAL) {
            //获取手动的内容列表
            comboResource.setContents(getManualContentVoList(contents, caller));
            return;
        }
        //非手动，获取自动的内容列表
        TResourceContent content = contents.get(0);
        if(resource.getType() == ResourceDataType.COMPETITION_MODULE){
            TComboEpisode dto = QmtSbcEpisodeApis.getEpisodeByMid(LeNumberUtils.toLong(content.getItemId()), caller);
            comboResource.setEpisode(dto);
        }
        comboResource.setContentLinks(content.getLinks());
        comboResource.setContents(getAutoContentVoList(resource.getType(), content, page, caller));
    }

    /**
     * 获取自动的内容vo列表
     *
     * @param resourceDataType
     * @param content
     * @param page
     * @param caller
     * @return
     */
    private List<BaseCvo> getAutoContentVoList(ResourceDataType resourceDataType,
                                               TResourceContent content, PageParam page, CallerParam caller) {
        List<BaseCvo> contents = Lists.newArrayList();
        //不同类型的资源位取数据的规则不同
        if (resourceDataType == ResourceDataType.COMMON_COMPETITION) {
            //根据分页 赛事ids 已结束场次  直播中场次  未开始场次 来搜索数据列表  当前只需要吐数据规则 走异步接口
            return getAutoEpisodeCvoList(content, page, caller);
        } else if (resourceDataType == ResourceDataType.COMMON_CONTENT_LIST
                || resourceDataType == ResourceDataType.COMPETITION_MODULE
                || resourceDataType == ResourceDataType.PLAYER_MODULE) {
            //根据分页 内容类型  时间排序方式  正文星级 标签  来搜索新闻
            return getAutoNewsCvoList(content, page, caller);
        } else if (resourceDataType == ResourceDataType.DATA_LIST) {//积分榜
            //todo
            return Lists.newArrayList();
        }
        return null;
    }

    /**
     * 获取手动维护的内容vo列表
     *
     * @param contents
     * @param caller
     * @return
     */
    private List<BaseCvo> getManualContentVoList(List<TResourceContent> contents, CallerParam caller) {
        return contents.stream().map(new Function<TResourceContent, BaseCvo>() {
            @Override
            public BaseCvo apply(TResourceContent content) {
                CvoConverter converter = getCvoConverter4Content(content);
                if (converter == null) {
                    return null;
                }
                return converter.toCvo(content, caller);
            }
        }).filter(cvo -> cvo != null).collect(Collectors.toList());
    }

    private List<BaseCvo> getTvContentVoList(List<TResourceContent> contents, CallerParam caller) {
        return contents.stream().map(new Function<TResourceContent, BaseCvo>() {
            @Override
            public BaseCvo apply(TResourceContent content) {
                CvoConverter converter = CvoConverterBuilders.getConverter(TvContentCvo.class);
                if (converter == null) {
                    return null;
                }
                return converter.toCvo(content, caller);
            }
        }).filter(cvo -> cvo != null).collect(Collectors.toList());
    }

    private CvoConverter getCvoConverter4Content(TResourceContent content) {
        switch (content.getType()) {
            //如果内容是新闻类型的 以下这几个都是新闻类型的
            case NEWS:
            case RICHTEXT:
            case IMAGE_ALBUM:
            case VIDEO_NEWS:
                return CvoConverterBuilders.getConverter(NewsCvo.class);
            case EPISODE:
                return CvoConverterBuilders.getConverter(EpisodeCvo.class);
            case POST:
                return CvoConverterBuilders.getConverter(PostCvo.class);
            case H5:
                return CvoConverterBuilders.getConverter(H5Cvo.class);
            case VIDEO:
                return CvoConverterBuilders.getConverter(VideoCvo.class);
            case SUBJECT:
                return CvoConverterBuilders.getConverter(SubjectCvo.class);
            case TOPIC:
                return CvoConverterBuilders.getConverter(TopicCvo.class);
            case PROGRAM_ALBUM:
                return CvoConverterBuilders.getConverter(ProgramAlbumCvo.class);
            default:
                return null;
        }
    }

    /**
     * 获取自动的新闻内容vo列表
     *
     * @param content
     * @param page
     * @param caller
     * @return
     */
    private List<BaseCvo> getAutoEpisodeCvoList(TResourceContent content, PageParam page, CallerParam caller) {
        //todo
        return Lists.newArrayList();
    }

    /**
     * 获取自动的新闻内容vo列表
     *
     * @param content
     * @param page
     * @param caller
     * @return
     */
    private List<BaseCvo> getAutoNewsCvoList(TResourceContent content, PageParam page, CallerParam caller) {
        List<Long> newsIds = getNewsIdsByAutoCondition(content, page, caller);
        List<TNews> newsList = QmtSbcApis.getTNewsByIds(newsIds, caller);

        if (CollectionUtils.isEmpty(newsList)) {
            return Collections.EMPTY_LIST;
        }else {
            LOG.info("newsIds={},newsList size = {}",newsIds,newsList.size());
        }
        CvoConverter<NewsCvo, TNews> converter = CvoConverterBuilders.getConverter(NewsCvo.class);
        return newsList.stream().map(news -> converter.toCvo(content, news)).
                filter(cvo -> cvo != null).collect(Collectors.toList());
    }

    /**
     * 根据条件查询新闻id
     *
     * @param tResourceContent
     * @param page
     * @param caller
     * @return
     */
    private List<Long> getNewsIdsByAutoCondition(TResourceContent tResourceContent, PageParam page, CallerParam caller) {
        GetRelatedNewsParam getRelatedNewsParam = new GetRelatedNewsParam();
        //-1代表全部
        if(tResourceContent.getStarLevel() != -1){
            getRelatedNewsParam.setStar(tResourceContent.getStarLevel());
        }
        //设置查询的新闻类型
        List<NewsType> newsTypes = new ArrayList<>();
        List<ResourceItemType> resourceDataTypes = tResourceContent.getDataSearch();
        if (CollectionUtils.isNotEmpty(resourceDataTypes)) {
            newsTypes = resourceDataTypes.stream().map(itype -> mappingItemTypeToNewsType(itype)).
                    filter(type -> type != null).collect(Collectors.toList());
        }
        getRelatedNewsParam.setTypes(newsTypes);
        //查询的标签  有的是存放在itemId里面了 优先去ItemId里面的
        String itemId = tResourceContent.getItemId();
        if(StringUtils.isNotBlank(itemId)){
            //relate id 是比赛id
            getRelatedNewsParam.setRelatedIds(Arrays.asList(Long.parseLong(itemId)));
        }else {
            getRelatedNewsParam.setRelatedIds(tResourceContent.getTagIds());
        }
        if (tResourceContent.getTimeOrder() == 1) {//按照更新时间来排序
            page.setSort(new Sort(Lists.newArrayList(new Order("update_at", Direction.DESC))));
        }
        return QmtSbcApis.getNewsIdsByRelatedId(getRelatedNewsParam, page, caller);
    }

    private NewsType mappingItemTypeToNewsType(ResourceItemType itemType) {
        switch (itemType) {
            case NEWS:
                return NewsType.RICHTEXT;
            case RICHTEXT:
                return NewsType.RICHTEXT;
            case IMAGE_ALBUM:
                return NewsType.IMAGE_ALBUM;
            case VIDEO_NEWS:
                return NewsType.VIDEO;
            default:
                return null;
        }
    }

}
