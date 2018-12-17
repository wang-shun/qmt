package com.lesports.qmt.web.service;

import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.helper.ShareHelper;
import com.lesports.qmt.web.model.Share;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
* Created by gengchengliang on 2016/2/16.
*/
@Service("newsService")
public class NewsService {

    @Resource
    private ShareHelper shareHelper;

    @Resource
    private MetaFiller metaFiller;

    /**
     * 新闻详情页
     *
     * @param model
     * @param tNews
     * @param caller
     */
    public void fillModelWithTNews(Model model, TNews tNews, CallerParam caller, Locale locale) {
        model.addAttribute("tNews", tNews);
        //添加新闻mate信息
        metaFiller.fillNewsMeta(model, tNews,locale);
        if (caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE || caller.getCallerId() == LeConstants.LESPORTS_HK_MSITE_CALLER_CODE) {
            //图文+富文本+视频
            List<NewsType> types = Lists.newArrayList(NewsType.values());
            List<TNews> videoGraphicAndRichtext = QmtSbcApis.getNewsRelatedWithSomeNews(tNews.getId(), types, PageUtils.createPageParam(0, 10), caller);
            model.addAttribute("videoGraphicAndRichtext", videoGraphicAndRichtext);
        } else if (caller.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE || caller.getCallerId() == LeConstants.LESPORTS_HK_PC_CALLER_CODE) {
            //视频新闻
            List<NewsType> videoNewsTypes = Lists.newArrayList(NewsType.VIDEO);

            List<TNews> videoNews = QmtSbcApis.getNewsRelatedWithSomeNews(tNews.getId(), videoNewsTypes, PageUtils.createPageParam(0, 10), caller);
            model.addAttribute("videoNews", videoNews);
            //图文+富文本
            List<NewsType> richTypes = Lists.newArrayList(NewsType.RICHTEXT, NewsType.IMAGE_TEXT);

            List<TNews> graphicAndRichtext = QmtSbcApis.getNewsRelatedWithSomeNews(tNews.getId(), richTypes, PageUtils.createPageParam(0, 10), caller);
            model.addAttribute("graphicAndRichtext", graphicAndRichtext);
        }
        //新闻类型
        model.addAttribute("newsType", tNews.getType());
        //图集
        List<TNews> atlas = QmtSbcApis.getNewsRelatedWithSomeNews(tNews.getId(), Lists.newArrayList(NewsType.IMAGE_ALBUM), PageUtils.createPageParam(0, 10), caller);
        model.addAttribute("atlas", atlas);
        if (tNews.getType() == NewsType.IMAGE_ALBUM) {
            Map<String, TNews> nearbyTNews = QmtSbcApis.getTNewsNearbySomeNews(tNews.getId(), NewsType.IMAGE_ALBUM, caller);
            if (MapUtils.isNotEmpty(nearbyTNews)) {
                TNews prev = nearbyTNews.get("prev");
                model.addAttribute("prevAtlas", prev);
                TNews next = nearbyTNews.get("next");
                model.addAttribute("nextAtlas", next);
            }
        }
        //分享
        model.addAttribute("share", shareHelper.getShare3News(tNews, caller, Share.ShareType.NEWS, locale));
    }
}
