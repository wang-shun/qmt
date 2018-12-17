package com.lesports.qmt.config.service;

import com.lesports.qmt.config.param.SaveCopyrightParam;
import com.lesports.qmt.config.vo.CopyrightVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtWebService;

/**
 * Created by zhangxudong@le.com on 2016/10/29.
 */
public interface CopyrightWebService extends QmtWebService<CopyrightVo, Long> {
    QmtPage<CopyrightVo> getCopyrightVoPage(int pageNumber, int pageSize, Boolean published);
    boolean publishCopyright(long id);
    Long saveCopyrightParam(SaveCopyrightParam saveCopyrightParam);
    Long checkCopyrightByName(String releasePackageName);
}