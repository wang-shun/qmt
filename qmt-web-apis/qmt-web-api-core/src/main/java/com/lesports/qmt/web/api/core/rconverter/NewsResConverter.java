package com.lesports.qmt.web.api.core.rconverter;

import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.NewsCvo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.web.api.core.creater.NewsVoCreater;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;

/**
 * Created by gengchengliang on 2016/12/29.
 */
@Component("newsResConverter")
public class NewsResConverter implements BaseResourceContentConverter<NewsVo, BaseCvo> {

	@Override
	public NewsVo getVo() {
		return new NewsVo();
	}

	@Override
	public NewsVo fillVo(BaseCvo baseCvo, Object... args) {
		NewsVo newsVo = getVo();
		newsVo.setName(baseCvo.getName());
		newsVo.setId(LeNumberUtils.toLong(baseCvo.getId()));
		newsVo.setCommentId(baseCvo.getCommentId());
		newsVo.setDesc(baseCvo.getDesc());
		adapterNews(newsVo, (NewsCvo) baseCvo);
		return newsVo;
	}

	@Override
	public void adapterNews(NewsVo newsVo, NewsCvo newsCvo) {
		newsVo.setCreateTime(newsCvo.getPubtime());
		newsVo.setRecommend(newsCvo.getStarLevel() >= 2 ? 1 : 0);//推荐2星及以上为推荐吧
		newsVo.setNewsType(newsCvo.getType().ordinal());
		newsVo.setDuration(newsCvo.getDuration() == 0 ? null : newsCvo.getDuration() + "");
		newsVo.setTags(newsCvo.getTags());
		TNews tNews = new TNews();
		tNews.setType(newsCvo.getNewsType());
		tNews.setCoverImage(newsCvo.getVideoImages());
        tNews.setVideoImages(newsCvo.getVideoImages());
		tNews.setImages(newsCvo.getImages());
		newsVo.setImageUrl(NewsVoCreater.getImageMap(tNews));
		if (newsCvo.getNewsType() == NewsType.VIDEO) {
			newsVo.setVid(newsCvo.getVid());
		}
		if (newsCvo.getNewsType() == NewsType.IMAGE_ALBUM) {
			newsVo.setImagesCount(newsCvo.getImages().size());
		}
		newsVo.setIsPay(newsCvo.getIsPay());
	}
}
