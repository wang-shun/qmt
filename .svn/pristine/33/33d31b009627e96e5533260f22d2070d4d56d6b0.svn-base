package com.lesports.qmt.cms.admin.web.controller;

import com.lesports.qmt.cms.admin.web.param.ColumnPageCopyParam;
import com.lesports.qmt.cms.admin.web.service.ColumnPageWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnPageVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 页面相关功能controller
 * User: ellios
 * Time: 16-12-1 : 下午6:49
 */
@RestController
@RequestMapping(value = "/pages")
public class ColumnPageController extends AbstractQmtController<ColumnPageVo, Long> {

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
    public ColumnPageVo getOneById(@PathVariable Long id) {
        try {
            return pretty(columnPageWebService.findOne(id));
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
            boolean result = columnPageWebService.delete(id);
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
     * 根据条件查询新闻列表
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public QmtPage<ColumnPageVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list pages, params:{} pageParam : {}", params, pageParam);
            return pretty(columnPageWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "publish",method = RequestMethod.POST)
    public boolean publish(Long columnPageId){
        boolean result = true;
        try {
            if (columnPageId == null || columnPageId <=0) {
                LOG.error("fail to publish column, id={}",columnPageId);
                throw new LeWebApplicationException("illegal param, columnPageId must be a positive number", LeStatus.BAD_REQUEST);
            }
            LOG.info("publish column page  id={}",columnPageId);
            columnPageWebService.publish(columnPageId);
        }catch (Exception e){
            LOG.error("publish column page error id={}",columnPageId,e);
            result = false;
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
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
    public Long add(ColumnPageVo vo) {
        long result = -1;
        try {

            result = columnPageWebService.saveWithId(vo);
            LOG.info("add column pages with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 复制栏目页
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    @ResponseBody
    public Long copy(ColumnPageCopyParam param) {
        long id = -1;
        try {

            id = columnPageWebService.copyColumnPage(param);
            LOG.info("copy column pages, param : {}", id);
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
    public boolean update(ColumnPageVo vo) {
        throw new UnsupportedOperationException("not support update");
    }

}
