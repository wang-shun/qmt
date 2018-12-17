package com.lesports.qmt.log.common;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现CAT上下文，以用来传递messageTreeId
 */
public class CatContext implements Cat.Context {

	private Map<String,String> properties = new HashMap<String, String>();
	
	@Override
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}

	@Override
	public String getProperty(String key) {
		return properties.get(key);
	}

}
