package com.lesports.qmt.sbd.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by gengchengliang on 2015/7/8.
 */
public class ImageUtil {

	private static final String JPG = ".jpg";

	private static final String PNG = ".png";

	private static final String GIF = ".gif";

	public static final String OLY_PNG_SUFFIX = "/11.png";

	/**
	 * 根据imgPath拼接后缀
	 *
	 * @param imgPathWeb webUrl:无后缀
	 * @param imgPath    取此字段的后缀
	 * @return
	 */
	public static String addLogoSuffix (String imgPathWeb, String imgPath) {
		if (StringUtils.isBlank(imgPathWeb)) {
			return null;
		}
		//如果imgPathWeb是带有后缀的说明是原图,则直接返回原图不进行拼接
		if (imgPathWeb.endsWith(PNG) || imgPathWeb.endsWith(JPG)) {
			return imgPathWeb;
		}
		StringBuffer sb = new StringBuffer(imgPathWeb);
		if(StringUtils.isNotBlank(imgPath)){
			return getSuffix(sb, imgPath).toString();
		} else {
			return sb.toString();
		}
	}

	/**
	 * 添加后缀
	 * @param sb
	 * @param imgPath
	 * @return
	 */
	private static StringBuffer getSuffix (StringBuffer sb, String imgPath) {
		if (imgPath.endsWith(JPG))
			sb.append(JPG);
		if (imgPath.endsWith(PNG))
			sb.append(PNG);
		return sb;
	}

}
