package com.lesports.qmt.op.web.api.core.util;

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

    private static final String DATA_KEY = "data";
    private static final String ERROR_CODE_KEY = "errorCode";
    private static final String ERROR_MSG_KEY = "errorMsg";
    private static final String TS_KEY = "ts";
    private static final String IS_FINAL_KEY = "isFinal";

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

    public static Map<String, Object> createEmptyPageResponse(int page){
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, 0);
        results.put(LeConstants.ENTRIES_KEY, Collections.EMPTY_LIST);
        return results;
    }

    public static Map<String, Object> createBaiduResponse(Object entries,boolean isFinal){

        Map<String, Object> results = Maps.newHashMap();
        if(isFinal){
            results.put(IS_FINAL_KEY,isFinal);
        }
        results.put(DATA_KEY, entries);
        results.put(ERROR_CODE_KEY, 0);
        results.put(ERROR_MSG_KEY, "");
        results.put(TS_KEY,System.currentTimeMillis());
        return results;
    }



    public static Map<String, Object> createBaiduErrorPageResponse(int errCode,String errorMsg){
        Map<String, Object> results = Maps.newHashMap();
        results.put(ERROR_CODE_KEY, errCode);
        results.put(ERROR_MSG_KEY, errorMsg);
        results.put(TS_KEY, System.currentTimeMillis());
        return results;
    }

}
