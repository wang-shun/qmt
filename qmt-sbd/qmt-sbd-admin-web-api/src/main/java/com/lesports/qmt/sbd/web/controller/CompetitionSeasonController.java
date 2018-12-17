package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.sbd.adapter.CompetitionSeasonAdapter;
import com.lesports.qmt.sbd.param.CompetitionSeasonParam;
import com.lesports.qmt.sbd.service.CompetitionSeasonWebService;
import com.lesports.qmt.sbd.vo.CompetitionSeasonVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lufei1 on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/competitionSeasons")
@WebLogAnnotation(ID_TYPE = IdType.COMPETITION_SEASON)
public class CompetitionSeasonController {
    private static final Logger LOG = LoggerFactory.getLogger(CompetitionSeasonController.class);

    @Resource
    private CompetitionSeasonWebService competitionSeasonWebService;

    @Resource
    private CompetitionSeasonAdapter competitionSeasonAdapter;

    @RequestMapping(method = RequestMethod.GET)
    public List<CompetitionSeasonVo> list(@RequestParam long cid, QmtCallerParam callerParam) {
        try {
            return competitionSeasonWebService.findByCid(cid, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CompetitionSeasonVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return competitionSeasonWebService.findOne(id, callerParam.getCallerParam());
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
    public boolean save(@ModelAttribute CompetitionSeasonParam param, BindingResult bindingResult,
                        QmtCallerParam callerParam) {
        try {
            LOG.info("save competitionSeason, competitionSeasonParam:{}", param);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            CompetitionSeasonVo competitionSeasonVo = competitionSeasonAdapter.toVo(param);
            return competitionSeasonWebService.save(competitionSeasonVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
