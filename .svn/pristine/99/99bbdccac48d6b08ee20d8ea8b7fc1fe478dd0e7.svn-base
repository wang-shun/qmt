package com.lesports.qmt.sbc.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.converter.TopicItemPackageVoConverter;
import com.lesports.qmt.sbc.param.SaveTopicItemPackageParam;
import com.lesports.qmt.sbc.param.SaveTopicItemParam;
import com.lesports.qmt.sbc.service.TopicItemPackageWebService;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.qmt.sbc.vo.TopicItemsVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by denghui on 2016/11/30.
 */
@RestController
@RequestMapping(value="/topicItemPackages")
@WebLogAnnotation(ID_TYPE = IdType.TOPIC_ITEM_PACKAGE)
public class TopicItemPackageController extends AbstractQmtController<TopicItemPackageVo, Long> {

    @Resource
    private TopicItemPackageWebService packageWebService;
    @Resource
    private TopicItemPackageVoConverter packageVoConverter;

    /**
     * 新增编辑专题包
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveTopicItemPackageParam param, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }
            TopicItemPackageVo vo = packageVoConverter.toVo(param);
            Long result = packageWebService.saveWithId(vo);
            LOG.info("save topic package, param:{}, result:{}", vo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TopicItemPackageVo getOneById(Long aLong) {
        return null;
    }

    @Override
    public QmtPage<TopicItemPackageVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        return null;
    }

    /**
     * 删除专题包
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.content.topic', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = packageWebService.delete(id);
            LOG.info("delete topic package, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取某专题包的内容列表
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/items", method=RequestMethod.GET)
    public TopicItemsVo getItems(@PathVariable Long id) {
        try {
            return packageWebService.listItems(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 导数据用，生成专题包id
     * @return
     */
    @RequestMapping(value = "/nextId", method=RequestMethod.GET)
    @ResponseBody
    public Long nextId() {
        return LeIdApis.nextId(IdType.TOPIC_ITEM_PACKAGE);
    }
}
