package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/12/9.
 */
public class ActivityVo extends Activity implements QmtVo {

    private static final long serialVersionUID = -1388909755997901882L;

    public ActivityVo() {
    }

    public ActivityVo(Activity activity) {
        try {
            LeBeanUtils.copyProperties(this, activity);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Activity toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Activity activity = new Activity();
        try {
            LeBeanUtils.copyProperties(activity, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return activity;
    }

}
