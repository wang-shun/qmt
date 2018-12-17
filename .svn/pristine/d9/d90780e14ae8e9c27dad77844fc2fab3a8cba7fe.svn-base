package com.lesports.qmt.op.web.api.core.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by zhonglin on 2016/7/18.
 */
public class CmsDataUtil {

    private static RestTemplate template =new RestTemplate();
    private static final Logger LOG = LoggerFactory.getLogger(CmsDataUtil.class);
    //360头条模板id
    public static final String OLY_360_TOP_ID = "6412";
    //360焦点图模板id
    public static final String OLY_360_FOCUS_ID = "6409";
    //360视频模板id
    public static final String OLY_360_VIDEO_ID = "6415";
    //360X新闻模板id
    public static final String OLY_360_NEWS_ID = "6418";
    //360夺牌模板id
    public static final String OLY_360_METAL_ID = "6421";
    //360图集模板id
    public static final String OLY_360_PIC_ID = "6424";
    //360哇塞模板id
    public static final String OLY_360_WASAI_ID = "6427";
    //360弹窗模板id
    public static final String OLY_360_POP_ID = "6430";

    public static List<Map<String,Object>> getCmsData(String  cmsId) {
        List<Map<String,Object>> contents = Lists.newArrayList();
        JSONObject fileContent = template.getForObject(Constants.CMS_DATA_URL + cmsId + ".json", JSONObject.class);
        if(fileContent!=null && fileContent.get("blockContent")!=null){
            contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        }
        return contents;

    }
}
