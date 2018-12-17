package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.client.QmtSbcResourceInternalApis;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.qmt.sbc.service.ResourceWebService;
import com.lesports.qmt.sbc.vo.ResourceVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:51
 */
@Service("resourceWebService")
public class ResourceWebServiceImpl implements ResourceWebService {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceWebServiceImpl.class);
    @Override
    public Long saveResource(ResourceVo resourceVo) {
        return QmtSbcResourceInternalApis.saveResource(resourceVo.toModel());
    }

    @Override
    public Long saveWithId(ResourceVo entity) {
        return null;
    }

    @Override
    public ResourceVo findOne(Long resourceId) {
        return new ResourceVo(QmtSbcResourceInternalApis.getResourceById(resourceId));
    }

    @Override
    public List<ResourceVo> getByIds(List<Long> ids) {
        List<QmtResource> qmtResources = QmtSbcResourceInternalApis.getResourceListByIds(ids);
        Collections.sort(qmtResources, (o1, o2) -> ids.indexOf(o1.getId()) - ids.indexOf(o2.getId()));
        return Lists.transform(qmtResources, new Function<QmtResource, ResourceVo>() {
            @Nullable
            @Override
            public ResourceVo apply(@Nullable QmtResource input) {
                if (input == null) return null;
                return new ResourceVo(input);
            }
        });
    }

    @Override
    public boolean delete(Long resourceId) {
        return QmtSbcResourceInternalApis.deleteResource(resourceId);
    }
    public long countResourceContentByResourceId(Long resourceId){
        InternalQuery query = new InternalQuery();
        query.getCriterias().add(new InternalCriteria("resourceId").is(resourceId));
        query.getCriterias().add(new InternalCriteria("deleted").ne(true));
        return QmtSbcResourceInternalApis.countByQuery(query,ResourceContent.class);
    }
    @Override
    public Page<ResourceContent> findResourceContentByResourceId(Long resourceId,QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.getCriterias().add(new InternalCriteria("resourceId").is(resourceId));
        query.getCriterias().add(new InternalCriteria("deleted").ne(true));
        long count = QmtSbcResourceInternalApis.countByQuery(query,ResourceContent.class);
        Pageable pageable = pageParam.toPageable(new Sort(Sort.Direction.ASC, "order"));//按照序号正排
        if(count <= 0){
            return new PageImpl<ResourceContent>(new ArrayList<ResourceContent>(), pageable, count);
        }
        query.with(pageable);
        List<ResourceContent> resourceContentList = QmtSbcResourceInternalApis.getResourceContentByQuery(query);
        return new PageImpl<ResourceContent>(resourceContentList, pageable, count);
    }

    @Override
    public Page<ResourceVo> search(Map<String, String> params, QmtPageParam pageParam) {
        params.put("page", pageParam.getPage()+"");
        params.put("count", pageParam.getCount()+"");
        String updateType = params.get("updateType");//更新类型
        String type = params.get("type");//资源位类型
        String idOrResourceName = params.get("idOrResourceName");//资源位Id或者资源位名称
        int searchType = LeNumberUtils.toInt(params.get("searchType"), 0);
        Long pId = LeNumberUtils.toLong(params.get("pId"),-1);
        String groupStr = params.get("group");
        boolean group = BooleanUtils.toBoolean(groupStr);
        InternalQuery query = new InternalQuery();
        //是否是资源位组
        //如果group 是true 或者false 才有下面的搜索条件
        if(StringUtils.isNotBlank(groupStr) && ("true".equals(groupStr) || "false".equals(groupStr))){
            query.getCriterias().add(new InternalCriteria("group").is(group));
        }
        if(!group){
            if(StringUtils.isNotBlank(updateType)){
                query.getCriterias().add(new InternalCriteria("updateType").is(updateType));
            }
            if(StringUtils.isNotBlank(type)){
                query.getCriterias().add(new InternalCriteria("type").is(type));
            }
            if(pId != -1){
                query.getCriterias().add(new InternalCriteria("p_ids").in(Arrays.asList(pId)));
            }
        }
        if(idOrResourceName != null){
            if(searchType == 0 ){
                //说明是全部
                if(NumberUtils.isNumber(idOrResourceName+"")){
                    query.getCriterias().add(new InternalCriteria().orOperator(new InternalCriteria("_id").is(Long.parseLong(idOrResourceName+"")),
                            new InternalCriteria("name").regex(idOrResourceName.toString())));
                }else {
                    query.getCriterias().add(new InternalCriteria("name").regex(idOrResourceName.toString()));
                }
                //按照id来模糊查找
            }else if(searchType == 1){
                if(NumberUtils.isNumber(idOrResourceName+"")){
                    query.getCriterias().add(new InternalCriteria("_id").is(Long.parseLong(idOrResourceName)));
                }else {
                    query.getCriterias().add(new InternalCriteria("_id").is(-1));//如果不是数字，则搜索一个id为-1的内容，肯定是搜不到的
                }
            }else if(searchType == 2){//按照资源位名称模糊查找
                query.getCriterias().add(new InternalCriteria("name").regex(idOrResourceName.toString()));
            }
        }
        long count = QmtSbcResourceInternalApis.getResourceCountByQuery(query);
        Pageable pageable = new PageRequest(pageParam.getPage()-1, pageParam.getCount(),new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        if(count <= 0){
            return new PageImpl<ResourceVo>(Collections.EMPTY_LIST, pageable, 0);
        }
        query.with(pageable);
        List<QmtResource> searchList = QmtSbcResourceInternalApis.getResourceByQuery(query);

        List<ResourceVo> content = Lists.newArrayList();
        Map<Long,ResourceVo> resourceVoMap = new HashMap<Long,ResourceVo>();
        if (CollectionUtils.isNotEmpty(searchList)){
            for (QmtResource qmtResource : searchList) {
                ResourceVo resourceVo = new ResourceVo();
                LeBeanUtils.copyNotEmptyPropertiesQuietly(resourceVo,qmtResource);
                resourceVoMap.put(resourceVo.getId(),resourceVo);
                content.add(resourceVo);
            }
        }
        if(pId != -1){
            QmtResource qmtResource = QmtSbcResourceInternalApis.getResourceById(pId);
            Set<Long> childIds = qmtResource.getChildIds();
            content.clear();
            for(Long child : childIds){
                content.add(resourceVoMap.get(child));
            }
        }
        return new PageImpl<ResourceVo>(content, pageable, count);
    }

    @Override
    public boolean saveResourceContent(ResourceContent resourceContent,boolean isTop) {

        if(isTop){
            //如果是置顶的话，该资源位内容的序号为1，其他资源位内容的需要都需要增加1
            List<ResourceContent> resourceContents = new ArrayList<>();

            if(resourceContent.getId() != null){
                //修改资源位内容为置顶  取比当前序号小的资源位内容
                resourceContents = findOrderReleteContent(resourceContent.getResourceId(),resourceContent.getOrder(),1);

            }else {
                //新建资源位内容置顶  把序号大于0的资源位内容的序号都+1
                resourceContents = findOrderReleteContent(resourceContent.getResourceId(),0,0);
            }
            resourceContents.stream().forEach(r -> QmtSbcResourceInternalApis.changeResourceContentOrder(r.getId(),1));
            //把当前资源位内容序号设置为1
            resourceContent.setOrder(1);
        }else {
            if(resourceContent.getId() == null){
                long count = countResourceContentByResourceId(resourceContent.getResourceId());
                resourceContent.setOrder(Integer.parseInt(count+"")+1);
            }
        }
        long resourceContentId = QmtSbcResourceInternalApis.saveResourceContent(resourceContent);
        if(resourceContentId >0){
            return true;
        }
        return false;
    }

    @Override
    public ResourceContent getResourceContentById(Long resourceId) {
        return QmtSbcResourceInternalApis.getResourceContentById(resourceId);
    }

    @Override
    public boolean deleteResourceContent(Long resourceContentId) {
        return QmtSbcResourceInternalApis.deleteResourceContent(resourceContentId);
    }

    /**
     *对资源位内容上移下移
     * @param resourceContentId
     * @param moveType 移动类型，1是上移，2是下移
     * @return
     */
    @Override
    public boolean moveResourceContent(Long resourceContentId, Integer moveType) {
        boolean result = true;
        if(resourceContentId == null || resourceContentId == 0 || moveType == null || moveType == 0){
            return result;
        }
        //找到一个比当前小的id的时候，置换id
        ResourceContent resourceContent = QmtSbcResourceInternalApis.getResourceContentById(resourceContentId);
        InternalQuery query = new InternalQuery();
        if(resourceContent == null){
            return result;
        }
        int increment = -1;
        Sort.Direction direction = Sort.Direction.ASC;
        if(moveType == 2){
            increment = - increment;
            query.getCriterias().add(new InternalCriteria("order").gt(resourceContent.getOrder()));
        }else {
            query.getCriterias().add(new InternalCriteria("order").lt(resourceContent.getOrder()));
            direction = Sort.Direction.DESC;
        }
        query.getCriterias().add(new InternalCriteria("deleted").is(false));
        query.getCriterias().add(new InternalCriteria("resourceId").is(resourceContent.getResourceId()));
        Pageable pageable = new PageRequest(0,1,new Sort(direction, "order"));
        query.with(pageable);
        List<ResourceContent> resourceContentList = QmtSbcResourceInternalApis.getResourceContentByQuery(query);

        if(CollectionUtils.isNotEmpty(resourceContentList)){
            result = QmtSbcResourceInternalApis.changeResourceContentOrder(resourceContentId,increment)
                    & QmtSbcResourceInternalApis.changeResourceContentOrder(resourceContentList.get(0).getId(),-increment) ;
        }
        return true;
    }


    /**
     *对资源位组中资源位顺序进行上移下移
     * @param gId  资源位组id
     * @param rId 资源位id
     * @param moveType 移动类型，1是上移，2是下移
     * @return
     */
    @Override
    public boolean moveResource(Long gId,Long rId, Integer moveType) {
        if(gId == null || gId == 0 || rId == null || rId == 0){
            return false;
        }
        QmtResource qmtResource = QmtSbcResourceInternalApis.getResourceById(gId);
        if(qmtResource != null && qmtResource.getGroup() == true){
            Set<Long> childIds = qmtResource.getChildIds();
            if(CollectionUtils.isNotEmpty(childIds) && childIds.contains(rId)){
                List<Long> childIdList = new ArrayList<>(childIds);
                if((rId.equals(childIdList.get(0)) && moveType == 1)
                        || (rId.equals(childIdList.get(childIdList.size()-1)) && moveType == 2)){
                    return false;
                }
                int childIndex = 0;
                for(int i=1;i<childIdList.size();i++){
                    if(childIdList.get(i).equals(rId)){
                        childIndex = i;
                        break;
                    }
                }
                if(moveType == 1){//向上移动
                    Long temp = childIdList.get(childIndex-1);
                    childIdList.set(childIndex-1,childIdList.get(childIndex));
                    childIdList.set(childIndex,temp);
                }else {//向下移动
                    Long temp = childIdList.get(childIndex+1);
                    childIdList.set(childIndex+1,childIdList.get(childIndex));
                    childIdList.set(childIndex,temp);
                }
                qmtResource.setChildIds(new LinkedHashSet<Long>(childIdList));
            }

        }
        return QmtSbcResourceInternalApis.saveResource(qmtResource) > 0;
    }

    /**
     * 找到该资源位 order相关的资源位内容，相关包括大于，小于
     * @param resourceId
     * @param order
     * @param type  0是大于  1或者其他是小于
     * @return
     */
    private List<ResourceContent> findOrderReleteContent(long resourceId,int order,int type){
        InternalQuery query = new InternalQuery();
        if(type == 0){
            query.getCriterias().add(new InternalCriteria("order").gt(order));
        }else {
            query.getCriterias().add(new InternalCriteria("order").lt(order));
        }
        query.getCriterias().add(new InternalCriteria("deleted").is(false));
        query.getCriterias().add(new InternalCriteria("resourceId").is(resourceId));
        return QmtSbcResourceInternalApis.getResourceContentByQuery(query);
    }

    /**
     * 用过资源位id找到响应的资源位内容
     * @param resourceId
     * @return
     */
    private List<ResourceContent> findResourceContentByResourceId(long resourceId){
        InternalQuery query = new InternalQuery();
        query.getCriterias().add(new InternalCriteria("deleted").is(false));
        query.getCriterias().add(new InternalCriteria("resourceId").is(resourceId));
        return QmtSbcResourceInternalApis.getResourceContentByQuery(query);
    }

    /**
     * 资源位组和资源位建立关系
     * @param pid   资源位组id
     * @param resourceIds  资源位id
     * @param type  关系类型  1是增加关联  2是解除关联
     * @return
     * @throws Exception
     */
    @Override
    public boolean addResourceRelete(Long pid, Set<Long> resourceIds,int type) throws Exception{
        boolean result = false;
       try {
           QmtResource resourceGroup = QmtSbcResourceInternalApis.getResourceById(pid);
           if(type == 2){
               resourceGroup.deleteChildIds(resourceIds);
           }else {
               resourceGroup.addChildIds(resourceIds);
           }
           QmtSbcResourceInternalApis.saveResource(resourceGroup);
           if(type == 2){
               for(Long resourceId:resourceIds){
                   QmtResource resource = QmtSbcResourceInternalApis.getResourceById(resourceId);
                   resource.deletePIds(pid);
                   QmtSbcResourceInternalApis.saveResource(resource);
               }
           }else {
               for(Long resourceId:resourceIds){
                   QmtResource resource = QmtSbcResourceInternalApis.getResourceById(resourceId);
                   resource.addPIds(pid);
                   QmtSbcResourceInternalApis.saveResource(resource);
               }
           }
           result = true;
       }catch (Exception e){
           throw new Exception("add ResourceRelete fail",e);
       }
        return result;
    }

    @Override
    public List<QmtResourceOnline> findResourceOnlineResourceId(Long resourceId){
        InternalQuery query = new InternalQuery();
        query.getCriterias().add(new InternalCriteria("fromId").is(resourceId));
        query.setSort(new Sort(Sort.Direction.DESC, "version"));
        return QmtSbcResourceInternalApis.findResourceOnlineByQuery(query);
    }


    @Override
    public boolean publishResource(long id) {
        try {
            QmtResource qmtResource = QmtSbcResourceInternalApis.getResourceById(id);
            if(qmtResource == null || qmtResource.getUpdateType() == ResourceUpdateType.AUTO){
                LOG.warn("resourceId={},qmtResource is null or updateType is auto",id);
                return false;
            }
            QmtResourceOnline qmtResourceOnline = new QmtResourceOnline();
            LeBeanUtils.copyNotEmptyPropertiesQuietly(qmtResourceOnline,qmtResource);
            List<QmtResourceOnline> qmtResourceOnlines = findResourceOnlineResourceId(id);
            int version = qmtResourceOnlines.size()+1;
            qmtResourceOnline.setVersion(version);
            qmtResourceOnline.setPublishAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
            qmtResourceOnline.setFromId(qmtResource.getId());
            qmtResourceOnline.setId(null);
            long resourceOnlineId = QmtSbcResourceInternalApis.saveResourceOnline(qmtResourceOnline);

            List<ResourceContent> resourceContents = findResourceContentByResourceId(id);
            for(ResourceContent resourceContent : resourceContents){
                ResourceContentOnline resourceContentOnline = new ResourceContentOnline();
                LeBeanUtils.copyNotEmptyPropertiesQuietly(resourceContentOnline,resourceContent);
                resourceContentOnline.setFromId(resourceContent.getId());
                resourceContentOnline.setResourceId(resourceOnlineId);
                resourceContentOnline.setVersion(version);
                resourceContentOnline.setPublishAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
                resourceContentOnline.setId(null);
                QmtSbcResourceInternalApis.saveResourceContentOnline(resourceContentOnline);
            }
        }catch (Exception e){
            LOG.error("publish resource fail id ={}",id,e);
            return false;
        }
        return true;
    }
}
