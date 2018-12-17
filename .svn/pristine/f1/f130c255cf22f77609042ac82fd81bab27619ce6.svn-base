package com.lesports.qmt.sbc.web.controller;

import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.converter.NewsVoConverter;
import com.lesports.qmt.sbc.param.SaveNewsParam;
import com.lesports.qmt.sbc.service.NewsWebService;
import com.lesports.qmt.sbc.vo.NewsVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by denghui on 2016/10/21.
 */
@RestController
@RequestMapping(value="/news")
@WebLogAnnotation(ID_TYPE = IdType.NEWS)
public class NewsController extends AbstractQmtController<NewsVo, Long> {

    @Resource
    private NewsWebService newsWebService;
    @Resource
    private NewsVoConverter newsVoConverter;


    /**
     * 保存新闻
     * @param saveNewsParam
     * @param bindingResult
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.news', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveNewsParam saveNewsParam,
                     BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            NewsVo newsVo = newsVoConverter.toVo(saveNewsParam);
            Long result = newsWebService.saveWithId(newsVo);
            LOG.info("save news, param:{}, result:{}", saveNewsParam, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取新闻详情
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Override
    public NewsVo getOneById(@PathVariable Long id) {
        try {
            return pretty(newsWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除新闻
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.content.news', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = newsWebService.delete(id);
            LOG.info("delete news, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上/下线新闻操作
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.news', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id,
                          @RequestParam(value = "status", required = true) String status) {
        try {
            boolean result = newsWebService.updatePublishStatus(id, PublishStatus.valueOf(status));
            LOG.info("set news status, id:{}, status:{}", id, status);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据条件查询新闻列表
     * @param params
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    @Deprecated
    public QmtPage<NewsVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list news, params:{}", params);
            return pretty(newsWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
