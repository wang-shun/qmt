package com.lesports.qmt.config.service.impl;

import com.lesports.qmt.config.client.QmtCopyrightInternalApis;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.config.param.SaveCopyrightParam;
import com.lesports.qmt.config.service.CopyrightWebService;
import com.lesports.qmt.config.vo.CopyrightVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/29.
 */
@Service("copyrightWebService")
public class CopyrightWebServiceImpl implements CopyrightWebService {

    private static final Logger LOG = LoggerFactory.getLogger(CopyrightWebServiceImpl.class);

    @Override
    public QmtPage<CopyrightVo> getCopyrightVoPage(int pageNumber, int pageSize, Boolean published) {
        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);
        long count = QmtCopyrightInternalApis.countCopyrightByQuery(new InternalQuery());
        if(null != published)
            query.addCriteria(new InternalCriteria("published").is(published));
//        long count = QmtCopyrightInternalApis.countCopyrightByQuery(new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE)));
        List<Copyright> data = QmtCopyrightInternalApis.getCopyrightByQuery(query);
        if(CollectionUtils.isEmpty(data)) return new QmtPage(new ArrayList<>(), new QmtPageParam(), count);
        return new QmtPage(CopyrightVo.toCopyrightVoList(data), new QmtPageParam(), count);
    }

    @Override
    public Long checkCopyrightByName(String releasePackageName) {
        releasePackageName = releasePackageName.replace(" ", "");
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
        InternalQuery query = new InternalQuery().with(pageable);
        query.addCriteria(new InternalCriteria("release_package_name").is(releasePackageName));
        List<Copyright> data = QmtCopyrightInternalApis.getCopyrightByQuery(query);
        if(CollectionUtils.isEmpty(data)) return -1l;
        return data.get(0).getId();
    }

    @Override
    public Long saveWithId(CopyrightVo copyrightVo) {
        if(null == copyrightVo) return -1l;
        return QmtCopyrightInternalApis.saveCopyright(copyrightVo);
    }

    @Override
    public Long saveCopyrightParam(SaveCopyrightParam saveCopyrightParam) {
        if(null == saveCopyrightParam) return -1l;
        return QmtCopyrightInternalApis.saveCopyright(saveCopyrightParam.toCopyright());
    }

    @Override
    public boolean publishCopyright(long id) {
        if(0 == id) return false;
        Copyright copyright = QmtCopyrightInternalApis.getCopyrightById(id);
        if(null == copyright) return false;
        if(true == copyright.isPublished())
            copyright.setPublished(false);
        else
            copyright.setPublished(true);
        return QmtCopyrightInternalApis.saveCopyright(copyright) > 0;
    }

    @Override
    public CopyrightVo findOne(Long id) {
        if(null == id || id <= 0) return null;
        return new CopyrightVo(QmtCopyrightInternalApis.getCopyrightById(id));
    }

    @Override
    public boolean delete(Long id) {
        if(null == id || id <= 0) return false;
        return QmtCopyrightInternalApis.deleteCopyright(id);
    }
}
