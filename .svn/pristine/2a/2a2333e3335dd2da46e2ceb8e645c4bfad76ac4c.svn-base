package com.lesports.qmt.sbc.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.service.ResourceWebService;
import com.lesports.qmt.sbc.vo.ResourceContentVo;
import com.lesports.qmt.sbc.vo.ResourceVo;
import com.lesports.qmt.web.datacenter.QmtSbcResourceContentCenter;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * User: ellios
 * Time: 16-10-28 : 下午5:53
 */
@RestController
@RequestMapping(value = "/resources")
@WebLogAnnotation(ID_TYPE = IdType.RESOURCE)
public class ResourceController extends AbstractQmtController<ResourceVo, Long> {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);
    @Resource
    private ResourceWebService resourceWebService;
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResourceVo getOneById(@PathVariable Long id) {
        try {
            return resourceWebService.findOne(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/ids", method= RequestMethod.GET)
    public List<ResourceVo> getByIds(@RequestParam(required = true) String ids) {
        try {
            return resourceWebService.getByIds(LeStringUtils.commaString2LongList(ids));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean delete(Long aLong) {
        return false;
    }

    /**
     * 保存资源位与相应资源位组的关系
     * 资源位的pids会add pid
     * 资源位组会添加child
     * @param pid 资源位组id
     * @param resourceIds  资源位id，逗号分隔
     * @param type 联系类型  1是建立联系，2是解除关系
     * @return
     */
    @RequestMapping(value = "relete",method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'UPDATE')")
    public boolean addResourceRelation(Long pid,String resourceIds,@RequestParam(name = "type",required = false)String type) {
        LOG.info("[resource][addResourceRelation][pid={},resourceIds={},type={}]",pid,resourceIds,type);
        try {
            if(pid <0 || StringUtils.isBlank(resourceIds)){
                throw new LeWebApplicationException("param error pid or resourceIds is empty",LeStatus.PARAM_INVALID);
            }
            Set<Long> resourceIdSet = new HashSet<Long>();
            for(String resourceId : resourceIds.split(",")){
                resourceIdSet.add(Long.parseLong(resourceId));
            }
            int relateType = LeNumberUtils.toInt(type,1);
            return resourceWebService.addResourceRelete(pid,resourceIdSet,relateType);
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'ADD')")
    public Long save(ResourceVo resourceVo) {
        try {
            return resourceWebService.saveResource(resourceVo);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 发布手动的资源位
     * @param id
     * @return
     */
    @RequestMapping(value = "publish",method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'ADD')")
    public boolean publish(@RequestParam(name = "resourceId")Long id) {
        boolean result = false;
        try {
            result = resourceWebService.publishResource(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    @RequestMapping(value = "/resourceContent/{id}",method = RequestMethod.GET)
    public ResourceContent getResourceContentById(@PathVariable Long id) {
        try {
            return resourceWebService.getResourceContentById(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/saveResourceContent",method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'ADD')")
    public Boolean saveResourceContent(ResourceContentVo resourceContentVo,boolean isTop) {
        try {
            if(resourceContentVo == null || resourceContentVo.getResourceId() == null){
                throw new LeWebApplicationException("param error resourceId is empty",LeStatus.PARAM_INVALID);
            }
            isTop = Optional.ofNullable(isTop).orElse(false);
            return resourceWebService.saveResourceContent(resourceContentVo.toModel(),isTop);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/deleteResourceContent",method = RequestMethod.GET)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'DELETE')")
    public Boolean deleteRecourceContent(@RequestParam Long id) {
        try {
            if(id == null || id <=0){
                throw new LeWebApplicationException("param error resourceContentId is empty",LeStatus.PARAM_INVALID);
            }
            return resourceWebService.deleteResourceContent(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("[resourceContent][delete][id={}]",id, e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/resourceContentList",method = RequestMethod.GET)
    public Page<ResourceContent> resourceContentList(Long resourceId, QmtPageParam pageParam) {
        try {
            if(resourceId == null){
                throw new LeWebApplicationException("param error resourceId is empty",LeStatus.PARAM_INVALID);
            }
            return resourceWebService.findResourceContentByResourceId(resourceId,pageParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/resourceContentMove",method = RequestMethod.GET)
    public boolean resourceContentMove(@RequestParam(name = "moveType")Integer moveType,@RequestParam Long id){
        boolean result = false;
        try {
            result = resourceWebService.moveResourceContent(id,moveType);
        }catch (Exception e){
            LOG.error("[resourceContentMove][moveType={},id={},id2={}]",moveType,id,e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 资源位组中资源位顺序移动
     * @param moveType 移动类型，1是上移，2是下移
     * @param gId  资源位组id
     * @param rId 资源位id
     * @return
     */
    @RequestMapping(value = "/resourceMove",method = RequestMethod.GET)
    @PreAuthorize("hasPermission('lesports.qmt.resource', 'ADD')")
    public boolean resourceMove(@RequestParam(name = "moveType")Integer moveType,@RequestParam(name = "gid") Long gId,@RequestParam(name="rid") Long rId){
        boolean result = false;
        try {
            if(gId == null || rId == null){
                throw new LeWebApplicationException("param error, gId or rId is empty",LeStatus.PARAM_INVALID);
            }
            LOG.info("[resourceMove][gId={},rId={},moveType={}]", gId, rId, moveType);
            result = resourceWebService.moveResource(gId, rId, moveType);
        }catch (Exception e){
            LOG.error("[resourceContentMove][gId={},rId={},moveType={}]",gId,rId,moveType,e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/resourceData",method = RequestMethod.GET)
    public ComboResource resourceData(@RequestParam Long id,PageBean pageBean,CallerBean callerBean){
        try {
            return ComboResourceCreaters.getComboResource(id, pageBean.getPageParam(), callerBean.getCallerParam());
        }catch (Exception e){
            LOG.error("[resourceData][id={}]",id,e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/resourceData2",method = RequestMethod.GET)
    public Object resourceData2(@RequestParam Long id,PageBean pageBean,CallerBean callerBean,@RequestParam(required = false) String caller,
                                @RequestParam(required = false,defaultValue = "1") int dataType){
        if(StringUtils.isNotBlank(caller)){
            callerBean.setCallerId(Long.parseLong(caller));
        }
        try {
            Object object = ComboResourceCreaters.getComboResources(id,pageBean.getPageParam(),callerBean.getCallerParam(),dataType);
            if(object instanceof List){
                Map<String,Object> data = new HashMap<>();
                data.put("list",object);
                return data;
            }
            return object;
        }catch (Exception e){
            LOG.error("[resourceData][id={}]",id,e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Page<ResourceVo> search(@RequestParam Map<String, String> params, QmtPageParam pageParam) {
        try {
            return resourceWebService.search(params, pageParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
