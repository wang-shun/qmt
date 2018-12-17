package com.lesports.qmt.web.api.core.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by ruiyuansheng on 2017/2/8.
 */
public class ImageUtils {

    public static String joinImageLogo(String logo, String ratio) {
        if (StringUtils.isNotBlank(logo)) {
            StringBuilder sb = new StringBuilder();
            sb.append(logo.substring(0, logo.lastIndexOf(".")));
            sb.append("_").append(ratio);
            sb.append(logo.substring(logo.lastIndexOf("."), logo.length()));
            return sb.toString();
        }
        return "";
    }
}
