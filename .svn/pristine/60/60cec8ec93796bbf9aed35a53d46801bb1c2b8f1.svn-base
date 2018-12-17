package com.lesports.qmt.userinfo.web.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.userinfo.model.Workbench;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2017/2/15.
 */
public class WorkbenchVo extends Workbench implements QmtVo {
    private static final long serialVersionUID = 7425869548671927894L;

    public WorkbenchVo(Workbench workbench) {
        try {
            LeBeanUtils.copyProperties(this, workbench);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }
}
