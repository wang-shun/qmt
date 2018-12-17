package com.lesports.qmt.op.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.qmt.op.web.api.core.vo.NewsVo;
import com.lesports.qmt.op.web.api.core.vo.OlyNewsVo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TVideo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lufei1 on 2015/11/5.
 */
@Component("newsVoCreater")
public class NewsVoCreater {

    private static final String PC_VIDEO_URL = "http://sports.letv.com/video/%s.html";
    private static final String PC_NEWS_URL = "http://sports.letv.com/news/%s.html";

    public static final String VIDEO_NEWS_IMAGE_200_150 = "200*150";

    public NewsVo createNewsVo(TNews tNews) {
        NewsVo newsVo = new NewsVo();
        newsVo.setId(tNews.getId());
        newsVo.setCreateTime(tNews.getPublishAt());
        newsVo.setImageUrl(getImageMap(tNews.getVideoImages()));
        newsVo.setName(tNews.getName());
        newsVo.setType(NewsType.VIDEO.getValue());
        newsVo.setVideoUrl(String.format(PC_VIDEO_URL, tNews.getVid()));
        return newsVo;
    }

    public OlyNewsVo createOlyNewsVo(TNews tNews) {
        OlyNewsVo newsVo = new OlyNewsVo();
        newsVo.setId(tNews.getId());
        newsVo.setCreateTime(tNews.getPublishAt());
        if(tNews.getCoverImage()!=null){
//            newsVo.setImageUrl(tNews.getCoverImage().toLowerCase().replace(".jpg","/169_210_160.jpg"));
        }
        newsVo.setName(tNews.getName());
        newsVo.setType(tNews.getType().getValue());
        newsVo.setVideoUrl(String.format(PC_NEWS_URL, tNews.getId())+"?ch=sogou_olympic");
        newsVo.setDesc(tNews.getDesc());
        newsVo.setSource(tNews.getSource());
        return newsVo;
    }


    public NewsVo createNewsVo(TVideo tVideo) {
        NewsVo newsVo = new NewsVo();
        newsVo.setId(tVideo.getId());
        newsVo.setCreateTime(tVideo.getCreateAt());
        newsVo.setImageUrl(getImageMap(tVideo.getImages()));
        newsVo.setName(tVideo.getName());
        newsVo.setType(NewsType.VIDEO.getValue());
        newsVo.setVideoUrl(String.format(PC_VIDEO_URL, tVideo.getId()));
        return newsVo;
    }


    private Map<String, String> getImageMap(Map<String, String> imageMap) {
        Map<String, String> returnMap = Maps.newHashMap();
        if (imageMap != null) {
            if (imageMap.get(VIDEO_NEWS_IMAGE_200_150) != null) {
                returnMap.put(VIDEO_NEWS_IMAGE_200_150, imageMap.get(VIDEO_NEWS_IMAGE_200_150));
            } else {
                returnMap.put(VIDEO_NEWS_IMAGE_200_150, "");
            }
        }
        return returnMap;
    }

    private Map<String, String> getTagNewsImageMap(Map<String, String> imageMap) {
        Map<String, String> returnMap = Maps.newHashMap();
        if (imageMap != null) {
            if (imageMap.get(VIDEO_NEWS_IMAGE_200_150) != null) {
                returnMap.put(VIDEO_NEWS_IMAGE_200_150, imageMap.get(VIDEO_NEWS_IMAGE_200_150));
            } else {
                returnMap.put(VIDEO_NEWS_IMAGE_200_150, "");
            }
        }
        return returnMap;
    }
}
