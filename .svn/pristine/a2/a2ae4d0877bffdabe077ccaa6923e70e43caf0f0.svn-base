package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.adapter.MatchAdapter;
import com.lesports.qmt.sbd.param.MatchParam;
import com.lesports.qmt.sbd.service.MatchWebService;
import com.lesports.qmt.sbd.vo.MatchVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/matches")
@WebLogAnnotation(ID_TYPE = IdType.MATCH)
public class MatchController {
    private static final Logger LOG = LoggerFactory.getLogger(MatchController.class);

    @Resource
    private MatchWebService matchWebService;
    @Resource
    private MatchAdapter matchAdapter;

    @RequestMapping(method = RequestMethod.GET)
    public QmtPage<MatchVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam, QmtCallerParam callerParam) {
        try {
            return matchWebService.list(params, pageParam, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MatchVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return matchWebService.findOne(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public List<MatchVo> getByIds(@RequestParam String ids, QmtCallerParam callerParam) {
        try {
            return matchWebService.getMatchesByIds(ids, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.schedule', 'ADD')")
    public boolean save(@ModelAttribute MatchParam param, BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save match ,matchParam:{}", param);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            MatchVo matchVo = matchAdapter.toVo(param);
            return matchWebService.save(matchVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新上线/下线状态
     *
     * @param id
     * @param online
     * @param callerParam
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.schedule', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id, @RequestParam(value = "online", required = true) boolean online,
                                      QmtCallerParam callerParam) {
        try {
            return matchWebService.updateOnlineStatus(id, online, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
