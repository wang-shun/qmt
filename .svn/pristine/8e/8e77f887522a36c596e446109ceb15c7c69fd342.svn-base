package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.TopList;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lufei1 on 2016/10/31.
 */
public class TopListVo extends TopList implements QmtVo {

    private String typeName;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TopListVo() {
    }

    public TopListVo(TopList topList) {
        try {
            LeBeanUtils.copyProperties(this, topList);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public TopList toModel() {
        //直接用类型转换得到的对象会报序列化错误
        TopList topList = new TopList();
        try {
            LeBeanUtils.copyProperties(topList, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return topList;
    }
}
