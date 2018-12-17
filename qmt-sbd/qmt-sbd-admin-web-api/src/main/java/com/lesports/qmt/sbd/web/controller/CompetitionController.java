package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.adapter.CompetitionAdapter;
import com.lesports.qmt.sbd.param.CompetitionParam;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
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
 * Created by lufei1 on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/competitions")
@WebLogAnnotation(ID_TYPE = IdType.COMPETITION)
public class CompetitionController {
    private static final Logger LOG = LoggerFactory.getLogger(CompetitionController.class);

    @Resource
    private CompetitionWebService competitionWebService;
    @Resource
    private CompetitionAdapter competitionAdapter;

    @RequestMapping(method = RequestMethod.GET)
    public QmtPage<CompetitionVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam, QmtCallerParam callerParam) {
        try {
            return competitionWebService.list(params, pageParam, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CompetitionVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return competitionWebService.findOne(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public List<CompetitionVo> getByIds(@RequestParam String ids, QmtCallerParam callerParam) {
        try {
            return competitionWebService.getCompetitionsByIds(ids, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.competitionList', 'ADD')")
    public boolean save(@Validated @ModelAttribute CompetitionParam param,
                        BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save competition, competitionParam:{}", param);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            CompetitionVo competitionVo = competitionAdapter.toVo(param);
            return competitionWebService.save(competitionVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
