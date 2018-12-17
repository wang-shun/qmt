package com.lesports.qmt.cms.admin.web.controller;

import com.google.common.collect.Maps;
import com.lesports.qmt.cms.admin.web.service.ColumnPageWebService;
import com.lesports.qmt.cms.admin.web.service.ColumnWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnVo;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-6 : 上午10:20
 */
@RestController
@RequestMapping(value = "/columns")
public class ColumnController extends AbstractQmtController<ColumnVo, Long> {

    @Resource
    private ColumnWebService columnWebService;

    @Resource
    private ColumnPageWebService columnPageWebService;

    /**
     * 根据id获取栏目页详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public ColumnVo getOneById(@PathVariable Long id) {
        try {
            return pretty(columnWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除新闻
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Override
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = columnWebService.delete(id);
            LOG.info("delete columns, id:{}, result:{}", id, result);
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
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public QmtPage<ColumnVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list columns, params:{} pageParam : {}", params, pageParam);
            return pretty(columnWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 添加栏目页
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "addAndReplicate",method = RequestMethod.POST)
    @ResponseBody
    public Long addAndReplicate(ColumnVo vo,@RequestParam(name = "columnId")Long scrColumnId) {
        long result = -1L;
        try {
            LOG.info("addAndReplicate srcColumnId={},vo={}",scrColumnId, vo);
            Long columnId = columnWebService.saveWithId(vo);
            columnPageWebService.copyColumnPage(scrColumnId,columnId,vo.getName());
            result = columnId;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 添加栏目页 并把当前栏目中页面拷贝到新建的栏目页中
     *
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Long add(ColumnVo vo) {
        long result = -1;
        try {

            result = columnWebService.saveWithId(vo);
            LOG.info("add column with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新栏目页
     *
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public boolean update(ColumnVo vo) {
        boolean result = false;
        try {
            if (vo.getId() == null) {
                LOG.error("fail to update column, for vo has no id. vo : {}", vo);
                throw new LeWebApplicationException("illegal param", LeStatus.BAD_REQUEST);
            }
            result = columnWebService.save(vo);
            LOG.info("update column with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
