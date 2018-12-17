package com.lesports.qmt.log;

import com.lesports.id.api.IdType;

import java.lang.annotation.*;

/**
 * Created by zhangdeqiang on 2016/12/20.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLogAnnotation {
    IdType ID_TYPE();
    String[] ID_ATTRIBUTE() default {"id"};
    String MEMO() default "";
}
