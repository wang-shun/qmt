package com.lesports.qmt.op.web.api.core.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by lufei1 on 2015/8/20.
 */
public class ImageUtils {

    public static String joinImageLogo(String logo, String ratio) {
        if (StringUtils.isNotBlank(logo)) {
            StringBuilder sb = new StringBuilder();
            sb.append(logo.substring(0, logo.lastIndexOf(".")));
            sb.append("/").append(ratio);
            sb.append(logo.substring(logo.lastIndexOf("."), logo.length()));
            return sb.toString();
        }
        return "";
    }
}
