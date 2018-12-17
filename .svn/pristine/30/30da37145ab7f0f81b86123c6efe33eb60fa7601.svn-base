package com.lesports.qmt.web.api.core.rconverter;

import com.google.common.collect.Maps;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.NewsCvo;
import com.lesports.qmt.resource.cvo.SubjectCvo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TNewsImage;
import com.lesports.qmt.web.api.core.creater.NewsVoCreater;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.RecommendVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by gengchengliang on 2016/12/29.
 */
@Component("recommendResConverter")
public class RecommendResConverter implements BaseResourceContentConverter<RecommendVo, BaseCvo> {

	@Override
	public RecommendVo getVo() {
		return new RecommendVo();
	}

	@Override
	public RecommendVo fillVo(BaseCvo contentBaseVo, Object... args) {
		RecommendVo recommend = getVo();
		String id = contentBaseVo.getId();
		if (contentBaseVo.getType() == ResourceItemType.POST) {
			if (StringUtils.isBlank(id)) {
				return null;
			}
			id = id.substring(0, 7);//因为iPhone本地缓存需要一个id,而帖子的id太长,则截取帖子的前7位作为ID
		}
		recommend.setId(LeNumberUtils.toLong(id));
		recommend.setName(contentBaseVo.getName());
		recommend.setCreateTime(contentBaseVo.getCreateTime());
		recommend.setH5(contentBaseVo.getUrl());
		recommend.setPostId(contentBaseVo.getId());
		adapterVoType(recommend, contentBaseVo.getType(), args);
		addImageUrl(recommend, contentBaseVo, args[0].toString());
		if (contentBaseVo.getType() == ResourceItemType.SUBJECT) {
			adapterTopic(recommend, (SubjectCvo) contentBaseVo);
		} else if (contentBaseVo.getType() == ResourceItemType.NEWS || contentBaseVo.getType() == ResourceItemType.RICHTEXT ||
				contentBaseVo.getType() == ResourceItemType.IMAGE_ALBUM || contentBaseVo.getType() == ResourceItemType.VIDEO_NEWS) {
			adapterNews(recommend, (NewsCvo) contentBaseVo);
		}
		return recommend;
	}

	private void addImageUrl(RecommendVo recommendVo, BaseCvo contentBaseVo, String domain) {
		Map<String, String> imageMap = Maps.newHashMap();
		if (MapUtils.isEmpty(contentBaseVo.getImageUrl())) {
			return;
		}
		if (domain.equals("FOCUS")) {
			imageMap.put("750*350", contentBaseVo.getImageUrl().get("169"));
		} else {
			imageMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, contentBaseVo.getImageUrl().get("169"));
			imageMap.put(Constants.VIDEO_NEWS_IMAGE_43, contentBaseVo.getImageUrl().get("43"));
		}
		recommendVo.setImageUrl(imageMap);
	}

	@Override
	public void adapterTopic(RecommendVo recommendVo, SubjectCvo topicVo) {
		//todo 增加视频信息
	}


	@Override
	public void adapterNews(RecommendVo recommend, NewsCvo newsVo) {

		if (newsVo.getType() == ResourceItemType.NEWS) {
			if (newsVo.getNewsType() == NewsType.RICHTEXT) {
				newsVo.setType(ResourceItemType.RICHTEXT);
			} else if (newsVo.getNewsType() == NewsType.IMAGE_ALBUM) {
				newsVo.setType(ResourceItemType.IMAGE_ALBUM);
			} else if (newsVo.getNewsType() == NewsType.VIDEO) {
				newsVo.setType(ResourceItemType.VIDEO_NEWS);
			}
		}
//		adapterNewsType(recommend, newsVo);
		recommend.setNewsType(newsVo.getNewsType().getValue());
		TNews tNews = new TNews();
		tNews.setType(NewsType.findByValue(recommend.getNewsType()));
		if (newsVo.getType() == ResourceItemType.RICHTEXT) {
			if (CollectionUtils.isNotEmpty(newsVo.getImages())) {
				TNewsImage tNewsImage = newsVo.getImages().get(0);
				Map<String, String> imageMap = Maps.newHashMap();
				imageMap.put("169", tNewsImage.getImageUrl());
				imageMap.put("43", tNewsImage.getImageUrl());
				tNews.setCoverImage(imageMap);
			}
		} else if (newsVo.getType() == ResourceItemType.VIDEO_NEWS) {
			tNews.setVideoImages(newsVo.getVideoImages());
		} else {
			tNews.setCoverImage(newsVo.getVideoImages());
		}
		tNews.setImages(newsVo.getImages());
		Map<String, String> imageUrl = recommend.getImageUrl();
		if (MapUtils.isEmpty(imageUrl)) {
			recommend.setImageUrl(NewsVoCreater.getImageMap(tNews));
		} else {
			imageUrl.putAll(NewsVoCreater.getImageMap(tNews));
			recommend.setImageUrl(imageUrl);
		}


		//如果是视频新闻并且ResourceItemType为新闻时,则需要纠正ContentType的类型
		if (newsVo.getNewsType() == NewsType.VIDEO) {
			recommend.setContentType(0);
		}
		recommend.setIsPay(newsVo.getIsPay());
		//如果是图集的话,需要知道图片的张数
		if (newsVo.getNewsType() == NewsType.IMAGE_ALBUM) {
			recommend.setImagesCount(newsVo.getImages().size());
		}
	}

	@Override
	public void adapterVoType(RecommendVo recommend, ResourceItemType rType, Object... args) {
		Integer recommendType = null;
		if (rType == ResourceItemType.VIDEO_NEWS) {
			recommendType = 0;
		} else if (rType == ResourceItemType.SUBJECT) {
			recommendType = 1;
		} else if (rType == ResourceItemType.NEWS || rType == ResourceItemType.RICHTEXT || rType == ResourceItemType.IMAGE_ALBUM) {
			recommendType = 2; //如果ResourceItemType为NEWS,则需要进一步判断新闻的类型是否为视频新闻,如果是,则recommendType则为0
		} else if (rType == ResourceItemType.H5) {
			if (args[0].toString().equals("FOCUS")) {
				recommendType = 3;
			} else {
				recommendType = 6;
			}
		} else if (rType == ResourceItemType.POST) {
			recommendType = 4;
		} else if (rType == ResourceItemType.EPISODE) {
			if (args[0].toString().equals("FOCUS")) {
				recommendType = 6;
			} else {
				recommendType = 5;
			}
		}
		if (recommendType != null) {
			recommend.setContentType(recommendType);
		}
	}

	@Override
	public void adapterNewsType(RecommendVo recommend, BaseCvo contentBaseVo) {
		if (contentBaseVo.getType() == ResourceItemType.RICHTEXT) {
			recommend.setNewsType(0);
		} else if (contentBaseVo.getType() == ResourceItemType.IMAGE_ALBUM) {
			recommend.setNewsType(3);
		} else if (contentBaseVo.getType() == ResourceItemType.VIDEO_NEWS) {
			recommend.setNewsType(1);
		}
	}
}
