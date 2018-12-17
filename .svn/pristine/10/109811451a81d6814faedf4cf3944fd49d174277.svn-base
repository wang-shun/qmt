package com.lesports.qmt.sbd.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangdeqiang on 2015/7/6.
 */
public class StringUtil extends StringUtils {


	public static final String MOU_HOU = ";";

	/**
	 * 对象为null，返回空字符串
	 *
	 * @param obj
	 * @return
	 */
	public static String valueOf(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	public static String formatStrForMybaitsLog(String sql) {

		if (isBlank(sql)) {

			return "";
		}

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(sql);
		return m.replaceAll("");

	}

    // 处理csv格式的特殊字符
    public static String convertCsvCell(Object o) {
        return "\"" + valueOf(o) + "\"" + ",";
    }

    public static String toCsvStr(Object obj) {
        return ((obj == null) ? "" : obj.toString()) + ",";
    }

	/**
	 * 生成随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * list to String
	 * list.add("aa");
	 * list.add("bb");
	 * list.add("cc");
	 * 'aa','bb','cc'
	 *
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder("'");
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append("','");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.append("'").toString();
	}

	public static void main(String[] args) {

		String sql = "insert into value   (  		dddd  )";

		System.out.println(formatStrForMybaitsLog(sql));
	}

	public static boolean isEmpty(String... str) {

		return str == null || str.length == 0;
	}


	public <T> List<T> strSplitList(String srouce, String split, Function<Object, T> fun) {

		List<T> list = Lists.newArrayList();

		if (isEmpty(srouce)) {

			return list;
		}

		String[] array = split(srouce, split);

		if (isEmpty(array)) {

			return list;
		}

		for (String str : array) {

			list.add(fun.apply(str));

		}

		return list;

	}

	abstract class DefSplitFunction<T> implements Function<String, T> {

		private T t;

		public DefSplitFunction(T t) {

			this.t = t;

		}

		public abstract T covert(T t, String... str);

		public T apply(String input) {

			return covert(t, split(input, MOU_HOU));
		}
	}

}
