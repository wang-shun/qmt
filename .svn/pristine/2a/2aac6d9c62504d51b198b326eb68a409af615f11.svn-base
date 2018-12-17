package com.lesports.qmt.userinfo.service.support;

import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.spring.security.authentication.LeCasAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

/**
 * Created by denghui on 2017/2/15.
 */
public abstract class AbstractUserinfoService <T extends QmtModel, ID extends Serializable> extends AbstractQmtService<T, ID> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractUserinfoService.class);

    protected String getCreator() {
        LeCasAuthenticationToken token = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getName();
    }
}
