package com.lesports.qmt.sbc.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.converter.PeriodVoConverter;
import com.lesports.qmt.sbc.converter.ProgramAlbumVoConverter;
import com.lesports.qmt.sbc.param.SavePeriodParam;
import com.lesports.qmt.sbc.param.SaveProgramAlbumParam;
import com.lesports.qmt.sbc.service.ProgramAlbumWebService;
import com.lesports.qmt.sbc.vo.ProgramAlbumPeriodVo;
import com.lesports.qmt.sbc.vo.ProgramAlbumVo;
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
 * 自制专辑
 * Created by denghui on 2016/11/15.
 */
@RestController
@RequestMapping(value="/programAlbums")
@WebLogAnnotation(ID_TYPE = IdType.PROGRAM_ALBUM)
public class ProgramAlbumController extends AbstractQmtController<ProgramAlbumVo, Long> {

    @Resource
    private ProgramAlbumWebService programAlbumWebService;
    @Resource
    private PeriodVoConverter periodVoConverter;
    @Resource
    private ProgramAlbumVoConverter programAlbumVoConverter;

    /**
     * 保存自制专辑
     * @param param
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.customalbumsList', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveProgramAlbumParam param, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }
            ProgramAlbumVo vo = programAlbumVoConverter.toVo(param);
            Long result = programAlbumWebService.saveWithId(vo);
            LOG.info("save program album, param:{}, result:{}", vo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取自制专辑
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Override
    public ProgramAlbumVo getOneById(@PathVariable Long id) {
        try {
            return pretty(programAlbumWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据条件查询自制专辑列表
     * @param params
     * @param pageParam
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<ProgramAlbumVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list program album, params:{}, pageParam: {}", params, pageParam);
            return pretty(programAlbumWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取某个选集信息
     * @param aid
     * @param eid
     * @return
     */
    @RequestMapping(value = "/{aid}/periods/{eid}", method=RequestMethod.GET)
    public ProgramAlbumPeriodVo getPeriod(@PathVariable long aid, @PathVariable long eid) {
        try {
            ProgramAlbumPeriodVo periodVo = programAlbumWebService.getPeriodById(eid);
            return pretty(periodVo);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 保存选集信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/{aid}/periods", method=RequestMethod.POST)
    @ResponseBody
   @PreAuthorize("hasPermission('lesports.qmt.content.customalbumsList', 'ADD')")
    public Long savePeriod(@PathVariable long aid, @ModelAttribute SavePeriodParam param,
                            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }
            ProgramAlbumPeriodVo vo = periodVoConverter.toVo(param);
            Long result = programAlbumWebService.savePeriod(aid, vo);
            LOG.info("save program period, aid: {}, param:{}, result:{}",aid, param, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取某个自制专辑下的选集列表
     * @param id
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "/{id}/periods", method=RequestMethod.GET)
    public QmtPage<ProgramAlbumPeriodVo> listEpisodes(@PathVariable long id, QmtPageParam pageParam) {
        try {
            LOG.info("list periods in program album, aid:{}, pageParam: {}", id, pageParam);
            return pretty(programAlbumWebService.listPeriods(id, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @RequestMapping(value = "/{id}/videos", method=RequestMethod.GET)
    public QmtPage<ProgramAlbumPeriodVo.SimpleVideoVo> listVideos(@PathVariable long id, QmtPageParam pageParam) {
        try {
            LOG.info("list videos in program album, aid:{}, pageParam: {}", id, pageParam);
            return pretty(programAlbumWebService.listVideos(id, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 推荐自制专辑排序
     * @param ids
     * @return
     */
    @RequestMapping(value = "/recommendOrder", method=RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.content.customalbumsList', 'UPDATE')")
    public Boolean resetRecommendOrder(@RequestParam(required = true) String ids) {
        try {
            boolean result = programAlbumWebService.resetRecommendOrder(LeStringUtils.commaString2LongList(ids));
            LOG.info("recommendOrder, ids:{}, result: {}", ids, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
