package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/30.
 */
public class TopicItemPackageVo extends TopicItemPackage implements QmtVo {
    private static final long serialVersionUID = 9066928727193356854L;

    public TopicItemPackageVo() {
    }

    public TopicItemPackageVo(TopicItemPackage topic) {
        try {
            LeBeanUtils.copyProperties(this, topic);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public TopicItemPackage toModel() {
        //直接用类型转换得到的对象会报序列化错误
        TopicItemPackage topic = new TopicItemPackage();
        try {
            LeBeanUtils.copyProperties(topic, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return topic;
    }
}
