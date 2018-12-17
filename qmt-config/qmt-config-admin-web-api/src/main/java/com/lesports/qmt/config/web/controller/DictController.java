package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.converter.DictVoConverter;
import com.lesports.qmt.config.param.SaveDictParam;
import com.lesports.qmt.config.service.DictWebService;
import com.lesports.qmt.config.vo.DictEntryVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/28.
 */
@RestController
@RequestMapping(value="/dict")
@WebLogAnnotation(ID_TYPE = IdType.DICT_ENTRY)
public class DictController extends AbstractQmtController<DictEntryVo, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(DictController.class);

    @Resource
    private DictWebService dictWebService;
    @Resource
    private DictVoConverter dictVoConverter;

    /**
     * 保存字典
     * @param param
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.confManage.editDict', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveDictParam param, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }
            DictEntryVo dictEntryVo = dictVoConverter.toVo(param);
            Long result = dictWebService.saveWithId(dictEntryVo, CallerUtils.getDefaultCaller());
            LOG.info("save dict, param:{}, result:{}", dictEntryVo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public DictEntryVo getOneById(@PathVariable Long id, QmtCallerParam callerParam) {
        try {
            return pretty(dictWebService.findOne(id, callerParam.getCallerParam()));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 批量获取字典
     * @param ids 用逗号分隔的字典ids
     * @return
     */
    @RequestMapping(value="/ids", method= RequestMethod.GET)
    public List<DictEntryVo> getDictsByIds(@RequestParam(value = "ids", required = true) String ids, QmtCallerParam callerParam) {
        try {
            return pretty(dictWebService.getDictsByIds(ids, callerParam.getCallerParam()));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.confManage.editDict', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = dictWebService.delete(id);
            LOG.info("delete dict, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取子级字典
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/children", method= RequestMethod.GET)
    public List<DictEntryVo> getChildrenDictsByParentId(@PathVariable long id,
                                                        Long gameFType,
                                                        QmtCallerParam callerParam) {
        try {
            return pretty(dictWebService.getChildrenDictsByParentId(id, gameFType, callerParam.getCallerParam()));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 批量获取子级字典
     * @param ids
     * @return
     */
    @RequestMapping(value="/children", method= RequestMethod.GET)
    public Map<Long, List<DictEntryVo>> getChildrenDictsByParentIds(@RequestParam(value = "ids", required = true)String ids,
                                                                    QmtCallerParam callerParam) {
        try {
            return pretty(dictWebService.getChildrenDictsByParentIds(ids, callerParam.getCallerParam()));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取字典列表
     * @param params
     * @param pageParam
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public QmtPage<DictEntryVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam, QmtCallerParam callerParam) {
        try {
            LOG.info("list dict, params:{}, pageParam: {}", params, pageParam);
            return pretty(dictWebService.list(params, getValidPageParam(pageParam), callerParam.getCallerParam()));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DictEntryVo getOneById(Long aLong) {
        return null;
    }

    private Map<Long, List<DictEntryVo>> pretty(Map<Long, List<DictEntryVo>> map) {
        if (MapUtils.isNotEmpty(map)) {
         //   map.values().forEach(this::pretty);
        }
        return map;
    }
}
