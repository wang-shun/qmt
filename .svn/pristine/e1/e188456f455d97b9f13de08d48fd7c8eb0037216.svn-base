package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Suggest;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/12/9.
 */
public class SuggestVo extends Suggest implements QmtVo {
    private static final long serialVersionUID = 7979237606574943575L;


    public SuggestVo() {
    }

    public SuggestVo(Suggest suggest) {
        try {
            LeBeanUtils.copyProperties(this, suggest);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Suggest toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Suggest suggest = new Suggest();
        try {
            LeBeanUtils.copyProperties(suggest, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return suggest;
    }

}
