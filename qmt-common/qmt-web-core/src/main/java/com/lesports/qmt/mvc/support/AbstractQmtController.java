package com.lesports.qmt.mvc.support;

import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.mvc.QmtController;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:23
 */
abstract public class AbstractQmtController<VO extends QmtVo, ID extends Serializable> implements QmtController<VO, ID>{

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractQmtController.class);

    protected QmtPageParam getValidPageParam(QmtPageParam page) {
        page.setPage(getValidPage(page.getPage()));
        page.setCount(getValidCount(page.getCount()));
        return page;
    }

    protected int getValidPage(int page) {
        return page > 0 ? page : 1;
    }

    protected int getValidCount(int count) {
        return count > 0 ? (count < QmtConstants.MAX_PAGE_SIZE ? count : QmtConstants.MAX_PAGE_SIZE)
                : QmtConstants.DEFAULT_WEB_PAGE_SIZE;
    }

    /**
     * 转换前端所需字段格式，如日期等
     * @param vo
     */
    protected <T extends QmtVo> T pretty(T vo) {
        return vo != null ? (T) vo.pretty() : vo;
    }

    /**
     * 转换前端所需字段格式，如日期等
     * @param qmtPage
     */
    protected <T extends QmtVo> QmtPage<T> pretty(QmtPage<T> qmtPage) {
        if (CollectionUtils.isNotEmpty(qmtPage.getContent())) {
            qmtPage.getContent().forEach(T::pretty);
        }
        return qmtPage;
    }

    protected <T extends QmtVo> List<T> pretty(List<T> vos) {
        if (CollectionUtils.isNotEmpty(vos)) {
            vos.forEach(T::pretty);
        }
        return vos;
    }
}
