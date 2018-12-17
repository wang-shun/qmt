package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.service.SuggestWebService;
import com.lesports.qmt.config.vo.SuggestVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by denghui on 2016/12/9.
 */
@RestController
@RequestMapping(value="/suggests")
@WebLogAnnotation(ID_TYPE = IdType.SUGGEST)
public class SuggestController extends AbstractQmtController<SuggestVo,Long> {

    @Resource
    private SuggestWebService suggestWebService;

    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.tvConf.searchWords', 'ADD')")
    public Long save(@ModelAttribute SuggestVo suggestVo) {
        try {
            if (StringUtils.isBlank(suggestVo.getSuggest())) {
                LOG.error("suggest name is required, param:{}", suggestVo);
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            Long result = suggestWebService.saveWithId(suggestVo);
            LOG.info("save suggest, param:{}, result:{}", suggestVo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    @Override
    public SuggestVo getOneById(@PathVariable Long id) {
        try {
            return pretty(suggestWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<SuggestVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list suggests, params:{}", params);
            return pretty(suggestWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.tvConf.searchWords', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = suggestWebService.delete(id);
            LOG.info("delete suggest, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/order", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.tvConf.searchWords', 'UPDATE')")
    public boolean resetOrder(@RequestParam(required = true) String ids) {
        try {
            boolean result = suggestWebService.resetOrder(LeStringUtils.commaString2LongList(ids));
            LOG.info("resetOrder, ids:{}, result:{}", ids, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
