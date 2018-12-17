package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/7.
 */
public class TagVo extends Tag implements QmtVo {

    public TagVo() {
    }

    public TagVo(Tag tag) {
        try {
            LeBeanUtils.copyProperties(this, tag);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Tag toTag() {
        //直接用类型转换得到的对象会报序列化错误
        Tag tag = new Tag();
        try {
            LeBeanUtils.copyProperties(tag, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return tag;
    }
}
