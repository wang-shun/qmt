//package com.lesports.qmt.sbc.thrift;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Maps;
//import com.lesports.AbstractIntegrationTest;
//import com.lesports.LeConstants;
//import com.lesports.api.common.ImageUrlExt;
//import com.lesports.qmt.config.api.dto.MenuResourceType;
//import com.lesports.qmt.config.api.dto.TMenu;
//import com.lesports.qmt.config.api.dto.TMenuItem;
//import com.lesports.qmt.config.client.QmtConfigApis;
//import com.lesports.qmt.sbc.api.common.NewsType;
//import com.lesports.qmt.sbc.api.dto.ResourceDataType;
//import com.lesports.qmt.sbc.api.dto.ResourceItemType;
//import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
//import com.lesports.qmt.sbc.model.News;
//import com.lesports.qmt.sbc.model.QmtResource;
//import com.lesports.qmt.sbc.model.Recommend;
//import com.lesports.qmt.sbc.model.ResourceContent;
//import com.lesports.qmt.sbc.repository.NewsRepository;
//import com.lesports.qmt.sbc.repository.RecommendRepository;
//import com.lesports.qmt.sbc.repository.ResourceContentRepository;
//import com.lesports.qmt.sbc.service.NewsService;
//import com.lesports.qmt.sbc.service.ResourceContentService;
//import com.lesports.qmt.sbc.service.ResourceService;
//import com.lesports.utils.CmsSubjectApis;
//import com.lesports.utils.pojo.SubjectData;
//import org.apache.commons.collections.CollectionUtils;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.mongodb.core.query.Query;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//import static org.springframework.data.mongodb.core.query.Query.query;
//
///**
// * Created by gengchengliang on 2017/2/23.
// */
//public class ImportRecommend2Resource extends AbstractIntegrationTest {
//
//	private static final Logger LOG = LoggerFactory.getLogger(ImportRecommend2Resource.class);
//
//	public static final Map<Long, Long> INDEX_2_RESOURCEID = Maps.newHashMap();
//
//	static {
//		INDEX_2_RESOURCEID.put(588040l, 100007008l);
//		INDEX_2_RESOURCEID.put(716040l, 100123008l);
//		INDEX_2_RESOURCEID.put(812040l, 100010008l);
//		INDEX_2_RESOURCEID.put(1878040l, 101094008l);
//		INDEX_2_RESOURCEID.put(695040l, 100009008l);
//		INDEX_2_RESOURCEID.put(727040l, 100031008l);
//		INDEX_2_RESOURCEID.put(791040l, 100028008l);
//		INDEX_2_RESOURCEID.put(1398040l, 100062001l);
//		INDEX_2_RESOURCEID.put(598040l, 100100008l);
//		INDEX_2_RESOURCEID.put(1676040l, 100069008l);
//		INDEX_2_RESOURCEID.put(694040l, 100004008l);
//		INDEX_2_RESOURCEID.put(2229040l, 101691008l);
//		INDEX_2_RESOURCEID.put(790040l, 100102008l);
//		INDEX_2_RESOURCEID.put(641040l, 100063008l);
//		INDEX_2_RESOURCEID.put(705040l, 100082008l);
//		INDEX_2_RESOURCEID.put(1975040l, 101722001l);
//		INDEX_2_RESOURCEID.put(704040l, 100081008l);
//		INDEX_2_RESOURCEID.put(736040l, 100027008l);
//		INDEX_2_RESOURCEID.put(800040l, 100300008l);
//		INDEX_2_RESOURCEID.put(619040l, 100312008l);
//		INDEX_2_RESOURCEID.put(811040l, 100022008l);
//		INDEX_2_RESOURCEID.put(618040l, 100056008l);
//		INDEX_2_RESOURCEID.put(2177040l, 101530008l);
//		INDEX_2_RESOURCEID.put(714040l, 100055008l);
//		INDEX_2_RESOURCEID.put(746040l, 100113008l);
//		INDEX_2_RESOURCEID.put(810040l, 100067008l);
//		INDEX_2_RESOURCEID.put(2091040l, 100012008l);
//		INDEX_2_RESOURCEID.put(596040l, 100083008l);
//		INDEX_2_RESOURCEID.put(692040l, 100016000l);
//		INDEX_2_RESOURCEID.put(2120040l, 101691008l);
//		INDEX_2_RESOURCEID.put(703040l, 100084008l);
//		INDEX_2_RESOURCEID.put(735040l, 100032008l);
//		INDEX_2_RESOURCEID.put(799040l, 100392008l);
//		INDEX_2_RESOURCEID.put(702040l, 100191008l);
//		INDEX_2_RESOURCEID.put(798040l, 100299008l);
//		INDEX_2_RESOURCEID.put(830040l, 100024008l);
//		INDEX_2_RESOURCEID.put(585040l, 104645000l);
//		INDEX_2_RESOURCEID.put(713040l, 100193008l);
//		INDEX_2_RESOURCEID.put(745040l, 100019008l);
//		INDEX_2_RESOURCEID.put(809040l, 100003008l);
//		INDEX_2_RESOURCEID.put(2047040l, 101672008l);
//		INDEX_2_RESOURCEID.put(584040l, 100014000l);
//		INDEX_2_RESOURCEID.put(744040l, 100132008l);
//		INDEX_2_RESOURCEID.put(808040l, 100048008l);
//		INDEX_2_RESOURCEID.put(595040l, 100085008l);
//		INDEX_2_RESOURCEID.put(691040l, 100017000l);
//		INDEX_2_RESOURCEID.put(594040l, 100080008l);
//		INDEX_2_RESOURCEID.put(690040l, 100018000l);
//		INDEX_2_RESOURCEID.put(701040l, 100079008l);
//		INDEX_2_RESOURCEID.put(797040l, 100135008l);
//		INDEX_2_RESOURCEID.put(829040l, 100159008l);
//		INDEX_2_RESOURCEID.put(604040l, 100194008l);
//		INDEX_2_RESOURCEID.put(700040l, 100045008l);
//		INDEX_2_RESOURCEID.put(796040l, 100017008l);
//		INDEX_2_RESOURCEID.put(615040l, 100044008l);
//		INDEX_2_RESOURCEID.put(711040l, 100035008l);
//		INDEX_2_RESOURCEID.put(743040l, 100118008l);
//		INDEX_2_RESOURCEID.put(807040l, 100120008l);
//		INDEX_2_RESOURCEID.put(614040l, 100013008l);
//		INDEX_2_RESOURCEID.put(2213040l, 101563008l);
//		INDEX_2_RESOURCEID.put(710040l, 100192008l);
//		INDEX_2_RESOURCEID.put(742040l, 100018008l);
//		INDEX_2_RESOURCEID.put(806040l, 100121008l);
//		INDEX_2_RESOURCEID.put(2044040l, 101700008l);
//		INDEX_2_RESOURCEID.put(785040l, 100006008l);
//		INDEX_2_RESOURCEID.put(688040l, 100003008l);
//		INDEX_2_RESOURCEID.put(720040l, 100137008l);
//		INDEX_2_RESOURCEID.put(1082040l, 100690008l);
//		INDEX_2_RESOURCEID.put(603040l, 100086008l);
//		INDEX_2_RESOURCEID.put(699040l, 100033008l);
//		INDEX_2_RESOURCEID.put(763040l, 100820001l);
//		INDEX_2_RESOURCEID.put(602040l, 100095008l);
//		INDEX_2_RESOURCEID.put(794040l, 100122008l);
//		INDEX_2_RESOURCEID.put(613040l, 100011008l);
//		INDEX_2_RESOURCEID.put(2214040l, 101823001l);
//		INDEX_2_RESOURCEID.put(709040l, 100104008l);
//		INDEX_2_RESOURCEID.put(805040l, 100026008l);
//		INDEX_2_RESOURCEID.put(612040l, 100010008l);
//		INDEX_2_RESOURCEID.put(2179040l, 102030001l);
//		INDEX_2_RESOURCEID.put(708040l, 100103008l);
//		INDEX_2_RESOURCEID.put(740040l, 100155008l);
//		INDEX_2_RESOURCEID.put(719040l, 100041008l);
//		INDEX_2_RESOURCEID.put(590040l, 100058008l);
//		INDEX_2_RESOURCEID.put(622040l, 100057008l);
//		INDEX_2_RESOURCEID.put(718040l, 100092008l);
//		INDEX_2_RESOURCEID.put(1220040l, 100731008l);
//		INDEX_2_RESOURCEID.put(1775040l, 101291008l);
//		INDEX_2_RESOURCEID.put(793040l, 100015008l);
//		INDEX_2_RESOURCEID.put(696040l, 100005008l);
//		INDEX_2_RESOURCEID.put(792040l, 100002008l);
//		INDEX_2_RESOURCEID.put(739040l, 100021008l);
//		INDEX_2_RESOURCEID.put(1997040l, 101518008l);
//		INDEX_2_RESOURCEID.put(706040l, 100098008l);
//		INDEX_2_RESOURCEID.put(738040l, 100126008l);
//		INDEX_2_RESOURCEID.put(589040l, 100008008l);
//		INDEX_2_RESOURCEID.put(621040l, 100023008l);
//		INDEX_2_RESOURCEID.put(717040l, 100087008l);
//		INDEX_2_RESOURCEID.put(781040l, 100019000l);
//		INDEX_2_RESOURCEID.put(813040l, 100154008l);
//		INDEX_2_RESOURCEID.put(877040l, 100020008l);
//	}
//
//	@Resource
//	private ResourceService resourceService;
//	@Resource
//	private RecommendRepository recommendRepository;
//	@Resource
//	private ResourceContentService resourceContentService;
//	@Resource
//	private ResourceContentRepository resourceContentRepository;
//	@Resource
//	private NewsRepository newsRepository;
//
//	@Test
//	public void importRecommends() {
////		Map<Long, Long> resource2Menu = Maps.newHashMap();
////		TMenu tMenu = QmtConfigApis.getTMenuById(300019018l);
////		List<TMenuItem> items = tMenu.getItems();
////		if (CollectionUtils.isNotEmpty(items)) {
////			for (TMenuItem item : items) {
////				List<TMenuItem> subItems = item.getSubItems();
////				if (CollectionUtils.isNotEmpty(subItems)) {
////					System.out.println("------------- start " +  item.getName() + "-----------------");
////					for (TMenuItem subItem : subItems) {
////						if (subItem.getResourceType() == MenuResourceType.COMMON) {
////							StringBuilder resourceNameSb = new StringBuilder();
////							resourceNameSb.append("APP").append(LeConstants.UNDERLINE).append(subItem.getName())
////									.append(LeConstants.UNDERLINE).append("推荐新闻").append(LeConstants.UNDERLINE)
////									.append(subItem.getIndex());
////							String resourceName = resourceNameSb.toString();
////							QmtResource resource = resourceService.findOneByName(resourceName);
////							if (resource == null) {
////								saveResource(resourceName);
////							}
////							Long tagId = INDEX_2_RESOURCEID.get(subItem.getIndex());
////							if (tagId == null) {
////								continue;
////							}
////							List<Recommend> recommends = findRecommends(tagId);
////							if (CollectionUtils.isNotEmpty(recommends) && resource != null) {
////								for (Recommend recommend : recommends) {
////									updateResourceContent(recommend, resource);
//////									saveResourceContent(recommend, resource);
////								}
////							}
//////							resource2Menu.put(subItem.getIndex(), resource.getId());
////						}
////					}
////					System.out.println("------------- end " + item.getName() + "-----------------");
////				}
////			}
////			System.out.println(JSON.toJSONString(resource2Menu));
////		}
//	}
//
//	private void updateResourceContent(Recommend recommend, QmtResource resource) {
//
//		if (recommend.getContentType().equals("TOPIC")){
//			ResourceContent resourceContent = getResourceContent(recommend, resource);
//			Map<String, ImageUrlExt> coverImage = Maps.newHashMap();
//			SubjectData.SubjectInfo subject = CmsSubjectApis.getSubject(recommend.getContentId());
//			ImageUrlExt imageUrlExt43 = new ImageUrlExt();
//			imageUrlExt43.setUrl(subject.getPic43());
//			ImageUrlExt imageUrlExt169 = new ImageUrlExt();
//			imageUrlExt169.setUrl(subject.getPic169());
//			coverImage.put("43", imageUrlExt43);
//			coverImage.put("169", imageUrlExt169);
//			resourceContent.setCoverImage(coverImage);
//			resourceContentRepository.save(resourceContent);
//		}
////		if (recommend.getContentType().equals("NEWS") || recommend.getContentType().equals("RICHTEXT") || recommend.getContentType().equals("IMAGE_ALBUM")) {
////			ResourceContent resourceContent = getResourceContent(recommend, resource);
////			Long contentId = recommend.getContentId();
////			Query q = query(where("deleted").is(false));
////			q.addCriteria(where("_id").is(contentId));
////			List<News> newsList = newsRepository.findByQuery(q);
////			if (CollectionUtils.isNotEmpty(newsList)) {
////				News news = newsList.get(0);
////				if (news.getType() == NewsType.IMAGE_TEXT) {
////					resourceContent.setContentType(ResourceItemType.TEXT_IMAGE);
////				} else if (news.getType() == NewsType.IMAGE_ALBUM) {
////					resourceContent.setContentType(ResourceItemType.IMAGE_ALBUM);
////				} else if (news.getType() == NewsType.VIDEO) {
////					resourceContent.setContentType(ResourceItemType.VIDEO_NEWS);
////				} else if (news.getType() == NewsType.RICHTEXT) {
////					resourceContent.setContentType(ResourceItemType.RICHTEXT);
////				}
////			}
////			resourceContentRepository.save(resourceContent);
////		}
//	}
//
//	private void saveResourceContent(Recommend recommend, QmtResource resource) {
//		boolean isExist = checkResourceContentExist(recommend, resource);
//		if (isExist) {
//			return;
//		}
//		ResourceContent resourceContent = new ResourceContent();
//		resourceContent.setResourceId(resource.getId());
//		if (recommend.getContentType().equals("POST")) {
//			resourceContent.setItemId(recommend.getContentSId());
//		} else {
//			resourceContent.setItemId(recommend.getContentId() + "");
//		}
//		resourceContent.setOrder(recommend.getOrder());
//		resourceContent.setName(recommend.getName());
//		resourceContent.setDeleted(false);
//		resourceContent.setContent(recommend.getName());
//		resourceContent.setH5Url(recommend.getContentUrl());
//		if (recommend.getContentType().equals("NEWS") || recommend.getContentType().equals("RICHTEXT") || recommend.getContentType().equals("IMAGE_ALBUM")) {
//			Query q = query(where("deleted").is(false));
//			q.addCriteria(where("_id").is(recommend.getContentId()));
//			List<News> newsList = newsRepository.findByQuery(q);
//			if (CollectionUtils.isNotEmpty(newsList)) {
//				News news = newsList.get(0);
//				if (news.getType() == NewsType.IMAGE_TEXT) {
//					resourceContent.setContentType(ResourceItemType.TEXT_IMAGE);
//				} else if (news.getType() == NewsType.IMAGE_ALBUM) {
//					resourceContent.setContentType(ResourceItemType.IMAGE_ALBUM);
//				} else if (news.getType() == NewsType.VIDEO) {
//					resourceContent.setContentType(ResourceItemType.VIDEO_NEWS);
//				} else if (news.getType() == NewsType.RICHTEXT) {
//					resourceContent.setContentType(ResourceItemType.RICHTEXT);
//				}
//			}
//		} else if (recommend.getContentType().equals("TOPIC")) {
//			resourceContent.setContentType(ResourceItemType.SUBJECT);
//		} else if (recommend.getContentType().equals("POST")) {
//			resourceContent.setContentType(ResourceItemType.POST);
//		} else if (recommend.getContentType().equals("MATCH")) {
//			resourceContent.setContentType(ResourceItemType.EPISODE);
//		} else if (recommend.getContentType().equals("H5")) {
//			resourceContent.setContentType(ResourceItemType.H5);
//		}
//		resourceContentService.save(resourceContent);
//	}
//
//	private ResourceContent getResourceContent(Recommend recommend, QmtResource resource) {
//		Query q = query(where("deleted").is(false));
//		q.addCriteria(where("resourceId").is(resource.getId()));
//		if (recommend.getContentType().equals("NEWS") || recommend.getContentType().equals("RICHTEXT") || recommend.getContentType().equals("IMAGE_ALBUM")
//				|| recommend.getContentType().equals("TOPIC") || recommend.getContentType().equals("MATCH")) {
//			q.addCriteria(where("item_id").is(recommend.getContentId() + ""));
//		} else if (recommend.getContentType().equals("POST")) {
//			q.addCriteria(where("item_id").is(recommend.getContentSId() + ""));
//		} else if (recommend.getContentType().equals("H5")) {
//			q.addCriteria(where("h5Url").is(recommend.getContentUrl()));
//		}
//		List<ResourceContent> resourceContentList = resourceContentRepository.findByQuery(q);
//		if (CollectionUtils.isNotEmpty(resourceContentList)) {
//			return resourceContentList.get(0);
//		}
//		return null;
//	}
//
//	private boolean checkResourceContentExist(Recommend recommend, QmtResource resource) {
//		Query q = query(where("deleted").is(false));
//		q.addCriteria(where("resourceId").is(resource.getId()));
//		if (recommend.getContentType().equals("NEWS") || recommend.getContentType().equals("RICHTEXT") || recommend.getContentType().equals("IMAGE_ALBUM")
//				|| recommend.getContentType().equals("TOPIC") || recommend.getContentType().equals("MATCH")) {
//			q.addCriteria(where("item_id").is(recommend.getContentId() + ""));
//		} else if (recommend.getContentType().equals("POST")) {
//			q.addCriteria(where("item_id").is(recommend.getContentSId() + ""));
//		} else if (recommend.getContentType().equals("H5")) {
//			q.addCriteria(where("h5Url").is(recommend.getContentUrl()));
//		}
//		List<ResourceContent> resourceContentList = resourceContentRepository.findByQuery(q);
//		if (CollectionUtils.isNotEmpty(resourceContentList)) {
//			return true;
//		}
//		return false;
//	}
//
//	private void saveResource(String resourceName) {
//		QmtResource resource = new QmtResource();
//		resource.setName(resourceName);
//		resource.setType(ResourceDataType.COMMON_CONTENT_LIST);
//		resource.setUpdateType(ResourceUpdateType.MANUAL);
//		resource.setDeleted(false);
//		resourceService.save(resource);
//	}
//
//	private List<Recommend> findRecommends(long tagId) {
//		Query q = query(where("deleted").is(false));
//		q.addCriteria(where("tag_id").is(tagId));
//		return recommendRepository.findByQuery(q);
//	}
//}
