package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.converter.ActivityVoConverter;
import com.lesports.qmt.config.param.SaveActivityParam;
import com.lesports.qmt.config.service.ActivityWebService;
import com.lesports.qmt.config.vo.ActivityVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by denghui on 2016/12/9.
 */
@RestController
@RequestMapping(value="/activities")
@WebLogAnnotation(ID_TYPE = IdType.ACTIVITY)
public class ActivityController extends AbstractQmtController<ActivityVo, Long> {

    @Resource
    private ActivityVoConverter activityVoConverter;
    @Resource
    private ActivityWebService activityWebService;

    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.mobileConf.promoteEdit', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveActivityParam param,
                     BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            ActivityVo activityVo = activityVoConverter.toVo(param);
            Long result = activityWebService.saveWithId(activityVo);
            LOG.info("save activity, param:{}, result:{}", param, result);
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
    public ActivityVo getOneById(@PathVariable Long id) {
        try {
            return pretty(activityWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.mobileConf.promoteEdit', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id,
                                      @RequestParam(required = true) Boolean online) {
        try {
            boolean result = activityWebService.updateOnlineStatus(id, online);
            LOG.info("updateOnlineStatus, id:{}, online:{}", id, online);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<ActivityVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list activities, params:{}", params);
            return pretty(activityWebService.list(params, getValidPageParam(pageParam)));
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
    @PreAuthorize("hasPermission('lesports.qmt.mobileConf.promoteEdit', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = activityWebService.delete(id);
            LOG.info("delete activity, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
