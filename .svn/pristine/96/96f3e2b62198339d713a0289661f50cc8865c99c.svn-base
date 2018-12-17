package com.lesports.qmt.web.api.core.util;

import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 15-7-15 : 下午1:09
 */
public class ResponseUtils {

    public static Map<String, Object> createPageResponse(int page, List entries){
        if(CollectionUtils.isEmpty(entries)){
            return createEmptyPageResponse(page);
        }
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, entries.size());
        results.put(LeConstants.ENTRIES_KEY, entries);
        return results;
    }

    public static Map<String, Object> createPageResponse(int page,String entriesKey, List entries){
        if(CollectionUtils.isEmpty(entries)){
            return createEmptyPageResponse(page);
        }
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, entries.size());
        results.put(entriesKey, entries);
        return results;
    }

    public static Map<String, Object> createPageResponse(int page,String entriesKey,int totalRound, int currentRound, List entries){
        if(CollectionUtils.isEmpty(entries)){
            return createEmptyPageResponse(page);
        }
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, entries.size());
        results.put("totalRound",totalRound);
        results.put("currentRound",currentRound);
		results.put(entriesKey, entries);
        return results;
    }

    public static Map<String, Object> createEmptyPageResponse(int page){
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, 0);
        results.put(LeConstants.TOTAL_KEY, 0);
        results.put(LeConstants.ENTRIES_KEY, Collections.EMPTY_LIST);
        return results;
    }

	public static Map<String, Object> createPageResponse(int page, Long total, List entries){
		if(CollectionUtils.isEmpty(entries)){
			return createEmptyPageResponse(page);
		}
		/*if (total <= 0) {
			return createPageResponse(page, entries);
		}*/
		Map<String, Object> results = Maps.newHashMap();
		results.put(LeConstants.PAGE_KEY, page);
		results.put(LeConstants.COUNT_KEY, entries.size());
        if (total != null) {
            results.put(LeConstants.TOTAL_KEY, total);
        }
		results.put(LeConstants.ENTRIES_KEY, entries);
		return results;
	}
}
