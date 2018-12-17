package com.lesports.qmt.sbc.web.controller;

import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.converter.TopicVoConverter;
import com.lesports.qmt.sbc.param.SaveTopicParam;
import com.lesports.qmt.sbc.service.TopicItemPackageWebService;
import com.lesports.qmt.sbc.service.TopicWebService;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.qmt.sbc.vo.TopicVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/11/2.
 */
@RestController
@RequestMapping(value="/topics")
@WebLogAnnotation(ID_TYPE = IdType.TOPIC)
public class TopicController extends AbstractQmtController<TopicVo, Long> {

    @Resource
    private TopicWebService topicWebService;
    @Resource
    private TopicVoConverter topicVoConverter;
    @Resource
    private TopicItemPackageWebService packageWebService;

    /**
     * 保存专题
     * @param saveTopicParam
     * @param bindingResult
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveTopicParam saveTopicParam,
                     BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            TopicVo topicVo = topicVoConverter.toVo(saveTopicParam);
            Long result = topicWebService.saveWithId(topicVo);
            LOG.info("save topic, param:{}, result:{}", saveTopicParam, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取专题详情
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Override
    public TopicVo getOneById(@PathVariable Long id) {
        try {
            return pretty(topicWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除专题
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = topicWebService.delete(id);
            LOG.info("delete topic, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上/下线专题操作
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id,
                                      @RequestParam(value = "status", required = true) String status) {
        try {
            boolean result = topicWebService.updatePublishStatus(id, PublishStatus.valueOf(status));
            LOG.info("set topic status, id:{}, status:{}", id, status);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据条件查询专题列表
     * @param params
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<TopicVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list topic, params:{}, pageParam: {}", params, pageParam);
            return pretty(topicWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取专题包列表
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/packages", method= RequestMethod.GET)
    public List<TopicItemPackageVo> listPackage(@PathVariable long id) {
        try {
            LOG.info("list topic packages, id:{}", id);
            return pretty(packageWebService.list(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 专题包排序
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/{id}/packages/order", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'ADD')")
    public boolean resetPackagesOrder(@PathVariable long id, @RequestParam(required = true) String ids) {
        try {
            boolean result = packageWebService.resetPackagesOrder(id, LeStringUtils.commaString2LongList(ids));
            LOG.info("resetPackagesOrder, id:{}, ids:{}, result:{}", id, ids, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 导数据用，生成专题id
     * @return
     */
    @RequestMapping(value = "/nextId", method=RequestMethod.GET)
    @ResponseBody
    public Long nextId() {
        return LeIdApis.nextId(IdType.TOPIC);
    }
}
