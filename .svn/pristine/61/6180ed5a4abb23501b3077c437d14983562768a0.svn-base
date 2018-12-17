package com.lesports.qmt.cms.admin.web.controller;

import com.lesports.qmt.cms.admin.web.service.LayoutWebService;
import com.lesports.qmt.cms.admin.web.vo.LayoutVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-6 : 上午10:30
 */
@RestController
@RequestMapping(value = "/layouts")
public class LayoutController extends AbstractQmtController<LayoutVo, Long> {

    @Resource
    private LayoutWebService layoutWebService;

    /**
     * 根据id获取栏目页详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public LayoutVo getOneById(@PathVariable Long id) {
        try {
            return pretty(layoutWebService.findOne(id));
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
            boolean result = layoutWebService.delete(id);
            LOG.info("delete layout, id:{}, result:{}", id, result);
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
    public QmtPage<LayoutVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list columns, params:{}, pageParam : {}", params, pageParam);
            return pretty(layoutWebService.list(params, getValidPageParam(pageParam)));
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
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Long add(LayoutVo vo) {
        long id = -1;
        try {
            id = layoutWebService.saveWithId(vo);
            LOG.info("add layout with vo : {}, result : {}", vo, id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return id;
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
    public boolean update(LayoutVo vo) {
        boolean result = false;
        try {
            if (vo.getId() == null) {
                LOG.error("fail to update layout, for vo has no id. vo : {}", vo);
                throw new LeWebApplicationException("illegal param", LeStatus.BAD_REQUEST);
            }
            result = layoutWebService.save(vo);
            LOG.info("update layout with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
