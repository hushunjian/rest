package com.m2m.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.m2m.Constant;
import com.m2m.copier.ContentCopier;
import com.m2m.copier.TagCopier;
import com.m2m.copier.TopicCopier;
import com.m2m.copier.UserCopier;
import com.m2m.dao.ContentDao;
import com.m2m.dao.TopicDao;
import com.m2m.dao.TopicTagDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.AdBanner;
import com.m2m.domain.Billboard;
import com.m2m.domain.BillboardDetails;
import com.m2m.domain.BillboardList;
import com.m2m.domain.BillboardRelation;
import com.m2m.domain.Content;
import com.m2m.domain.EmotionPack;
import com.m2m.domain.EmotionPackDetail;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicCategory;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.TopicTag;
import com.m2m.domain.TopicTagDetail;
import com.m2m.domain.UserFamous;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserFriend;
import com.m2m.domain.UserIndustry;
import com.m2m.domain.UserIndustryWrong;
import com.m2m.domain.UserInvitationHis;
import com.m2m.domain.UserProfile;
import com.m2m.domain.UserTag;
import com.m2m.entity.BillBoardListDTO;
import com.m2m.entity.ExtAcImage;
import com.m2m.entity.ExtAdBanner;
import com.m2m.entity.ExtAttention;
import com.m2m.entity.ExtHotContent;
import com.m2m.entity.ExtOutContent;
import com.m2m.entity.ExtOutData;
import com.m2m.entity.ExtOutImageData;
import com.m2m.entity.ExtTagInfo;
import com.m2m.entity.ExtTagOutInfo;
import com.m2m.entity.ExtTagTmp;
import com.m2m.entity.ExtTagTopicInfo;
import com.m2m.entity.ExtTopicCount;
import com.m2m.entity.ExtTopicReviewCount;
import com.m2m.entity.ExtUserProfile;
import com.m2m.enums.USER_OPRATE_TYPE;
import com.m2m.mapper.BillboardMapper;
import com.m2m.mapper.EmotionPackMapper;
import com.m2m.mapper.ExtBillBoardMapper;
import com.m2m.mapper.ExtContentMapper;
import com.m2m.mapper.ExtIndustryMapper;
import com.m2m.mapper.ExtTagMapper;
import com.m2m.mapper.ExtTopicMapper;
import com.m2m.mapper.ExtUserMapper;
import com.m2m.mapper.UserTagMapper;
import com.m2m.request.PricedKingdomListRequest;
import com.m2m.request.ShowListDetailRequest;
import com.m2m.request.ShowListRequest;
import com.m2m.response.AcKingdomListResponse;
import com.m2m.response.AttentionResponse;
import com.m2m.response.BangDanData;
import com.m2m.response.BaseResponse;
import com.m2m.response.EmojiPackDetailResponse;
import com.m2m.response.EmojiPackageQueryResponse;
import com.m2m.response.HotResponse;
import com.m2m.response.IndustryContentListResponse;
import com.m2m.response.MyPublishByTypeResponse;
import com.m2m.response.NewestResponse;
import com.m2m.response.PricedKingdomInfo;
import com.m2m.response.PricedKingdomListResponse;
import com.m2m.response.RecTagGroupResponse;
import com.m2m.response.ShowListDetailResponse;
import com.m2m.response.ShowListResponse;
import com.m2m.response.TagDetailResponse;
import com.m2m.response.TagLikeResponse;
import com.m2m.response.UserGroupResponse;
import com.m2m.util.CommonUtil;
import com.m2m.util.DateUtil;
import com.m2m.util.ProbabilityUtil;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;
import com.m2m.web.Specification;

@Service
public class ContentService {

	private static final Logger log = LoggerFactory.getLogger(ContentService.class);
	
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private TopicTagDao topicTagDao;
	@Autowired
	private ContentDao contentDao;
	
	@Autowired
	private ExtContentMapper extContentMapper;
	@Autowired
	private UserTagMapper userTagMapper;
	@Autowired
	private ExtUserMapper extUserMapper;
	@Autowired
	private ExtTopicMapper extTopicMapper;
	@Autowired
	private ExtBillBoardMapper extBillBoardMapper;
	@Autowired
	private BillboardMapper billboardMapper;
	@Autowired
	private ExtIndustryMapper extIndustryMapper;
	@Autowired
	private ExtTagMapper extTagMapper;
	@Autowired
	private EmotionPackMapper emotionPackMapper;
	
	
	public AttentionResponse attention(long uid, long updateTime) {
		AttentionResponse attentionResponse = new AttentionResponse();
		String beforeThreeDays = DateUtil.date2string(DateUtil.addDay(new Date(), -3), "yyyy-MM-dd 00:00:00"); 
		List<ExtAttention> list = extContentMapper.getAttentionAndLikeTag(uid, updateTime, 10,beforeThreeDays);
		if(list==null || list.size()==0){
			return attentionResponse;
		}
		int exchangeRate = appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE")==null?100:appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE");
		List<Long> contentIds = new ArrayList<Long>();
		List<Long> tagIds = new ArrayList<Long>();
		for (ExtAttention extAttention : list) {
			if (extAttention.getType() == 3) {
				contentIds.add(extAttention.getId());
			} else {
				tagIds.add(extAttention.getId());
			}
		}
		String likeTagTitle = "";
		String likeTagImage = "";
		if(tagIds.size()>0){
			likeTagTitle = appConfigService.getAppConfigByKey("LIKE_TAG_TITLE");
			likeTagImage = Constant.QINIU_DOMAIN + "/" + appConfigService.getAppConfigByKey("LIKE_TAG_IMAGE");
		}
		double minRmb = Double.parseDouble((String) appConfigService.getAppConfigByKey("KINGDOM_SHOW_RMB_BRAND_MIN"));
		List<Long> uidList = new ArrayList<Long>();
		List<Long> topicIdList = new ArrayList<Long>();
		Map<String, Content> contentMap = new HashMap<String, Content>();
		List<Content> listContent = new ArrayList<Content>();
		if(contentIds.size()>0){
			listContent = contentDao.getContentListByIds(contentIds);
		}
		for (Content idx : listContent) {
			contentMap.put(String.valueOf(idx.getId()), idx);
			if (!uidList.contains(idx.getUid())) {
				uidList.add(idx.getUid());
			}
			if (!topicIdList.contains(idx.getForwardCid())) {
				topicIdList.add(idx.getForwardCid());
			}
		}
		// 查询标签外露内容
		Map<String, List<ExtTagOutInfo>> tagOutDataMap = new HashMap<String, List<ExtTagOutInfo>>();
		List<ExtTagOutInfo> listExtTagOutInfo = new ArrayList<ExtTagOutInfo>();
		if (tagIds.size() > 0) {
			listExtTagOutInfo = extContentMapper.getTagOutList(tagIds);
		}
		if (null != listExtTagOutInfo && listExtTagOutInfo.size() > 0) {
			Long tagId = null;
			Long fragmentUid = null;
			List<ExtTagOutInfo> toList = null;
			for (ExtTagOutInfo m : listExtTagOutInfo) {
				tagId = m.getTagId();
				toList = tagOutDataMap.get(tagId.toString());
				if (null == toList) {
					toList = new ArrayList<ExtTagOutInfo>();
					tagOutDataMap.put(tagId.toString(), toList);
				}
				toList.add(m);
				fragmentUid = m.getUid();
				if (null != fragmentUid && fragmentUid.longValue() > 0) {
					if (!uidList.contains(fragmentUid)) {
						uidList.add(fragmentUid);
					}
				}
			}
		}

		// 一次性查询所有标签信息
		Map<String, TopicTag> tagMap = new HashMap<String, TopicTag>();
		if (tagIds.size() > 0) {
		List<TopicTag> tagList = topicTagDao.getTopicTagListByIds(tagIds);
		if (null != tagList && tagList.size() > 0) {
			for (TopicTag up : tagList) {
				tagMap.put(String.valueOf(up.getId()), up);
			}
		}
		}
		// 一次性查询所有点赞信息
		Map<String, String> likeMap = new HashMap<String, String>();
		List<Long> likeList = new ArrayList<Long>();
		if (contentIds.size() > 0) {
			likeList = extContentMapper.getLikeContentIdList(uid, contentIds);
		}
		if (null != likeList && likeList.size() > 0) {
			for (Long up : likeList) {
				likeMap.put(String.valueOf(up), "1");
			}
		}

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = new HashMap<String, List<TopicFragmentWithBLOBs>>();
		String v = appConfigService.getAppConfigByKey("KINGDOM_OUT_MINUTE");
		int limitMinute = 3;
		if (!StringUtils.isEmpty(v)) {
			limitMinute = Integer.valueOf(v).intValue();
		}

		List<TopicFragmentWithBLOBs> topicOutList = extContentMapper.getOutFragments(topicIdList, limitMinute);
		if (null != topicOutList && topicOutList.size() > 0) {
			Long topicId = null;
			Long atUid = null;
			Long fragmentUid = null;
			List<TopicFragmentWithBLOBs> toList = null;
			for (TopicFragmentWithBLOBs m : topicOutList) {
				topicId = m.getTopicId();
				toList = topicOutDataMap.get(topicId.toString());
				if (null == toList) {
					toList = new ArrayList<TopicFragmentWithBLOBs>();
					topicOutDataMap.put(topicId.toString(), toList);
				}
				toList.add(m);
				atUid = m.getAtUid();
				if (null != atUid && atUid.longValue() > 0) {
					if (!uidList.contains(atUid)) {
						uidList.add(atUid);
					}
				}
				fragmentUid = m.getUid();
				if (null != fragmentUid && fragmentUid.longValue() > 0) {
					if (!uidList.contains(fragmentUid)) {
						uidList.add(fragmentUid);
					}
				}
			}
		}

		// 一次性查询所有用户信息
		Map<String, UserProfile> profileMap = new HashMap<String, UserProfile>();
		List<UserProfile> profileList = userDao.getUserProfilesByUids(uidList);
		if (null != profileList && profileList.size() > 0) {
			for (UserProfile up : profileList) {
				profileMap.put(String.valueOf(up.getUid()), up);
			}
		}

		// 一次性查询关注信息
		Map<String, String> followMap = new HashMap<String, String>();
		List<UserFollow> userFollowList = userDao.getAllFollows(uid, uidList);
		if (null != userFollowList && userFollowList.size() > 0) {
			for (UserFollow uf : userFollowList) {
				followMap.put(uf.getSourceUid() + "_" + uf.getTargetUid(), "1");
			}
		}
		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = new HashMap<String, Topic>();
		List<Topic> topicList = topicDao.getTopicListByIds(topicIdList);
		if (null != topicList && topicList.size() > 0) {
			Long topicId = null;
			for (Topic topic : topicList) {
				topicId = topic.getId();
				topicMap.put(topicId.toString(), topic);
			}
		}
		// 一次性查询所有王国的成员数
		Map<String, Long> topicMemberCountMap = topicDao.getTopicMembersCount(topicIdList);
		if (null == topicMemberCountMap) {
			topicMemberCountMap = new HashMap<String, Long>();
		}
		// 一次性查询王国订阅信息
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		List<LiveFavorite> liveFavouriteList = topicDao.getLiveFavoritesByUidAndTopicIds(uid, topicIdList);
		if (null != liveFavouriteList && liveFavouriteList.size() > 0) {
			for (LiveFavorite lf : liveFavouriteList) {
				liveFavouriteMap.put(lf.getTopicId().toString(), "1");
			}
		}
		// 一次性查询所有王国的国王更新数，以及评论数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		List<ExtTopicReviewCount> tcList = extContentMapper.getTopicUpdateCount(topicIdList);
		if (null != tcList && tcList.size() > 0) {
			for (ExtTopicReviewCount m : tcList) {
				topicCountMap.put(String.valueOf(m.getTopicId()), m.getTopicCount());
				reviewCountMap.put(String.valueOf(m.getTopicId()), m.getReviewCount());
			}
		}

		UserProfile userProfile = null;
		List<TopicFragmentWithBLOBs> topicOutDataList = null;
		List<ExtTagOutInfo> tagOutDataList = null;
		TopicFragmentWithBLOBs topicOutData = null;
		UserProfile atUserProfile = null;
		UserProfile lastUserProfile = null;

		for (ExtAttention extAttention : list) {
			AttentionResponse.ContentElement contentElement = new AttentionResponse.ContentElement();
			if (extAttention.getType() == 3) {
				Content content = contentMap.get(String.valueOf(extAttention.getId()));
				if (content == null) {
					continue;
				}
				contentElement = ContentCopier.INSTANCE.asAttentionResponseContentElement(content);
				contentElement.setUpdateTime(new Date(extAttention.getLongTime()));
				// 获取用户信息
				userProfile = profileMap.get(String.valueOf(content.getUid()));
				contentElement.setV_lv(userProfile.getvLv());
				contentElement.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
				if (!StringUtils.isEmpty(userProfile.getAvatarFrame())) {
					contentElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
				}
				contentElement.setNickName(userProfile.getNickName());
				contentElement.setLevel(userProfile.getLevel());
				String contentStr = StringUtils.isEmpty(content.getContent())?"":content.getContent();
				if (contentStr.length() > 100) {
					contentElement.setContent(contentStr.substring(0, 100));
				} else {
					contentElement.setContent(contentStr);
				}
				contentElement.setIsLike(likeMap.get(String.valueOf(content.getId())) == null ? 0 : 1);

				String cover = content.getConverImage();
				contentElement.setCoverImage(Constant.QINIU_DOMAIN + "/" + cover);
				// 判断人员是否关注
				if (null != followMap.get(uid + "_" + content.getUid())) {
					contentElement.setIsFollowed(1);
				} else {
					contentElement.setIsFollowed(0);
				}
				if (null != followMap.get(content.getUid() + "_" + uid)) {
					contentElement.setIsFollowMe(1);
				} else {
					contentElement.setIsFollowMe(0);
				}
				contentElement.setFavoriteCount(content.getFavoriteCount() + 1);
				if (null != liveFavouriteMap.get(content.getForwardCid().toString())) {
					contentElement.setFavorite(1);
				} else {
					contentElement.setFavorite(0);
				}
				// 王国的，需要实际的成员数
				if (null != topicMemberCountMap.get(content.getForwardCid().toString())) {
					contentElement.setFavoriteCount(
							topicMemberCountMap.get(content.getForwardCid().toString()).intValue() + 1);
				} else {
					contentElement.setFavoriteCount(1);
				}
				contentElement.setLiveStatus(0);
				if (null != reviewCountMap.get(content.getForwardCid().toString())) {
					contentElement.setReviewCount(reviewCountMap.get(content.getForwardCid().toString()).intValue());
				} else {
					contentElement.setReviewCount(0);
				}
				if (null != topicCountMap.get(content.getForwardCid().toString())) {
					contentElement.setTopicCount(topicCountMap.get(content.getForwardCid().toString()).intValue());
				} else {
					contentElement.setTopicCount(0);
				}
				Topic topic = topicMap.get(String.valueOf(content.getForwardCid()));
				if (null != topic) {
					contentElement.setLastUpdateTime(topic.getLongTime());
					int internalStatust = baseService.getInternalStatus(topic, uid);
					if (internalStatust == Specification.SnsCircle.OUT.index) {
						if (liveFavouriteMap.get(String.valueOf(topic.getId())) != null) {
							internalStatust = Specification.SnsCircle.IN.index;
						}
					}
					contentElement.setInternalStatus(internalStatust);
					contentElement.setContentType(topic.getType());
					if (topic.getType() == 1000) {
						// 聚合王国子王国数量
						contentElement.setAcCount(topicDao.countAggregationSubTopic(content.getForwardCid()));
					}
					contentElement.setPrice(topic.getPrice());
					contentElement.setPriceRMB(baseService.exchangeKingdomPrice(topic.getPrice(), exchangeRate));
					contentElement.setShowPriceBrand(0); //
					contentElement.setShowRMBBrand(contentElement.getPriceRMB() >= minRmb ? 1 : 0);// 显示吊牌
				}
				// 增加王国外露内容
				if (content.getType().intValue() == Specification.ArticleType.LIVE.index) {// 王国才有外露
					topicOutDataList = topicOutDataMap.get(content.getForwardCid().toString());
					if (null != topicOutDataList && topicOutDataList.size() > 0) {
						// 先判断是否UGC
						// 第一个如果是UGC则其他的不要了，如果不是，则后面如果有UGC则不要了
						topicOutData = topicOutDataList.get(0);
						lastUserProfile = profileMap.get(String.valueOf(topicOutData.getUid()));
						if (null != lastUserProfile) {// 这里放上最近发言的那个人的头像
							contentElement.setUid(lastUserProfile.getUid());
							contentElement.setNickName(lastUserProfile.getNickName());
							contentElement.setV_lv(lastUserProfile.getvLv());
							contentElement.setLevel(lastUserProfile.getLevel());
							contentElement.setAvatar(baseService.genAvatar(lastUserProfile.getAvatar()));
							if (!StringUtils.isEmpty(lastUserProfile.getAvatarFrame())) {
								contentElement
										.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + lastUserProfile.getAvatarFrame());
							} else {
								contentElement.setAvatarFrame(null);
							}
							if (null != followMap.get(uid + "_" + lastUserProfile.getUid())) {
								contentElement.setIsFollowed(1);
							} else {
								contentElement.setIsFollowed(0);
							}
							if (null != followMap.get(lastUserProfile.getUid() + "_" + uid)) {
								contentElement.setIsFollowMe(1);
							} else {
								contentElement.setIsFollowMe(0);
							}
						}

						int type = topicOutData.getType();
						int contentType = topicOutData.getContentType();
						if ((type == 0 || type == 52) && contentType == 23) {// 第一个是UGC
							ExtOutData outElement = new ExtOutData();
							outElement = TopicCopier.INSTANCE.asExtOutData(topicOutData);
							String fragmentImage = topicOutData.getFragmentImage();
							if (!StringUtils.isEmpty(fragmentImage)) {
								outElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + fragmentImage);
							}
							if (outElement.getAtUid() > 0) {
								atUserProfile = profileMap.get(String.valueOf(outElement.getAtUid()));
								if (null != atUserProfile) {
									outElement.setAtNickName(atUserProfile.getNickName());
								}
							}
							contentElement.getUgcData().add(outElement);
						} else {// 第一个不是UGC
							for (int i = 0; i < topicOutDataList.size(); i++) {
								topicOutData = topicOutDataList.get(i);
								type = topicOutData.getType();
								contentType = topicOutData.getContentType();
								ExtOutData outElement = new ExtOutData();
								outElement = TopicCopier.INSTANCE.asExtOutData(topicOutData);
								String fragmentImage = topicOutData.getFragmentImage();
								if (!StringUtils.isEmpty(fragmentImage)) {
									outElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + fragmentImage);
								}
								if (outElement.getAtUid() > 0) {
									atUserProfile = profileMap.get(String.valueOf(outElement.getAtUid()));
									if (null != atUserProfile) {
										outElement.setAtNickName(atUserProfile.getNickName());
									}
								}
								if ((type == 0 || type == 52) && contentType == 23) {// UGC不要了
									continue;
								} else if ((type == 0 || type == 55 || type == 52) && contentType == 0) {// 文本
									if (contentElement.getTextData().size() == 0) {
										contentElement.getTextData().add(outElement);
									}
								} else if (type == 13 || (type == 55 && contentType == 63)) {// 音频
									if (contentElement.getAudioData().size() == 0) {
										contentElement.getAudioData().add(outElement);
									}
								} else {// 图片区展示部分
									if (contentElement.getImageData().size() < 3) {
										contentElement.getImageData().add(outElement);
									}
								}
							}
						}
					}
				}
			} else if (extAttention.getType() == 16) {
				contentElement.setUpdateTime(new Date(extAttention.getLongTime()));
				TopicTag topicTag = tagMap.get(String.valueOf(extAttention.getId()));
				if (topicTag == null) {
					continue;
				}
				contentElement.setType(extAttention.getType());
				contentElement.setTagId(extAttention.getId());
				contentElement.setTagName(topicTag.getTag());
				contentElement.setNickName(likeTagTitle);
				contentElement.setAvatar(likeTagImage);
				tagOutDataList = tagOutDataMap.get(String.valueOf(extAttention.getId()));
				if (null != tagOutDataList && tagOutDataList.size() > 0) {
				List<ExtOutData> extOutDataList=  TagCopier.INSTANCE.asExtOutDataList(tagOutDataList);
				if (null != extOutDataList && extOutDataList.size() > 0) {
					for (int i = 0; i < extOutDataList.size(); i++) {
						ExtOutData outDataElement = extOutDataList.get(i);
						if (StringUtils.isNotEmpty(outDataElement.getFragmentImage())) {
							outDataElement.setFragmentImage(
									Constant.QINIU_DOMAIN + "/" + outDataElement.getFragmentImage());
						}
					}
					contentElement.setImageData(extOutDataList);
				}
				}
			}else{
				continue;
			}
			attentionResponse.getAttentionData().add(contentElement);
		}
		return attentionResponse;
	}

	public RecTagGroupResponse recTagGroup(long uid) {
		RecTagGroupResponse response = new RecTagGroupResponse();
		int tagCount = appConfigService.getIntegerAppConfigByKey("HOME_HOT_LABELS");
		tagCount = tagCount * 2;
		Map<String, ExtTagTmp> tagMap = new LinkedHashMap<String, ExtTagTmp>();
		int curPos = 0;
		if (ProbabilityUtil.isInProb(40)) { // 40%概率出现行为标签。
			// 通过用户行为产生的排名最高的前5个标签,且评分必须超过20
			List<ExtTagTmp> favoTags = extContentMapper.getUserFavoriteTags(uid, 2,null);
			int rndSize = RandomUtils.nextInt(1, 3);
			int s = 0;
			for (ExtTagTmp info : favoTags) { // 填充1~2个行为标签。
				if (tagMap.get(String.valueOf(info.getTagId())) == null) {
					if (curPos >= tagCount || s >= rndSize) {
						break;
					}
					tagMap.put(String.valueOf(info.getTagId()), info);
					curPos++;
					s++;
				}
			}
		}
		// 取系统标签
		List<ExtTagTmp> sysTagList = extContentMapper.getSysTagsInfo(uid,tagCount,null);
		int n = 0;
		while (curPos < tagCount && n < sysTagList.size()) { // 填充剩余空位。
			ExtTagTmp tagInfo = sysTagList.get(n);
			if (tagMap.get(String.valueOf(tagInfo.getTagId())) == null) {
				tagMap.put(String.valueOf(tagInfo.getTagId()), tagInfo);
				curPos++;
			}
			n++;
		}

		Iterator<Entry<String, ExtTagTmp>> iterator1 = tagMap.entrySet().iterator();
		List<Long> tagIds = new ArrayList<Long>();
		while (iterator1.hasNext()) {
			Entry<String, ExtTagTmp> entry = iterator1.next();
			ExtTagTmp info = entry.getValue();
			tagIds.add(info.getTagId());
		}
/*		// 一次性查询出所有标签王国信息
		List<ExtTagTopicInfo> listTagTopicInfo = new ArrayList<ExtTagTopicInfo>();
		if (tagIds.size() > 0) {
			listTagTopicInfo = extContentMapper.getTagTopicInfo(tagIds);
		}
		Map<String, ExtTagTopicInfo> tagTopicMap = new HashMap<String, ExtTagTopicInfo>();
		if (listTagTopicInfo != null) {
			for (ExtTagTopicInfo extTagTopicInfo : listTagTopicInfo) {
				tagTopicMap.put(String.valueOf(extTagTopicInfo.getTagId()), extTagTopicInfo);
			}
		}
*/
		// 查询标签外露内容
		Map<String, List<ExtTagOutInfo>> tagOutDataMap = new HashMap<String, List<ExtTagOutInfo>>();
		List<ExtTagOutInfo> listExtTagOutInfo = new ArrayList<ExtTagOutInfo>();
		if (tagIds.size() > 0) {
			listExtTagOutInfo = extContentMapper.getTagOutList(tagIds);
		}
		if (null != listExtTagOutInfo && listExtTagOutInfo.size() > 0) {
			Long tagId = null;
			List<ExtTagOutInfo> toList = null;
			for (ExtTagOutInfo m : listExtTagOutInfo) {
				tagId = m.getTagId();
				toList = tagOutDataMap.get(tagId.toString());
				if (null == toList) {
					toList = new ArrayList<ExtTagOutInfo>();
					tagOutDataMap.put(tagId.toString(), toList);
				}
				//最多只显示3条外露内容
				if(toList.size()<3){
					toList.add(m);
				}
			}
		}

		Iterator<Entry<String, ExtTagTmp>> iterator = tagMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ExtTagTmp> entry = iterator.next();
			ExtTagTmp info =  entry.getValue();
			long tagId = info.getTagId();
			ExtTagInfo element = new ExtTagInfo();
			element.setIsShowLikeButton(1);
			element.setKingdomCount(info.getKingdomCount());
			element.setPersonCount(info.getPersonCount());
			element.setTagName(info.getTagName());
			element.setTagId(tagId);
			List<ExtTagOutInfo> list = tagOutDataMap.get(String.valueOf(tagId));
			List<ExtOutImageData> extOutImageDataList = TagCopier.INSTANCE.asExtOutImageDataList(list);
			if (null != extOutImageDataList && extOutImageDataList.size() > 0) {
				for (int i = 0; i < extOutImageDataList.size(); i++) {
					ExtOutImageData extOutImageDataElement = extOutImageDataList.get(i);
					if (StringUtils.isNotEmpty(extOutImageDataElement.getFragmentImage())) {
						extOutImageDataElement.setFragmentImage(
								Constant.QINIU_DOMAIN + "/" + extOutImageDataElement.getFragmentImage());
					}
				}
				element.setImageData(extOutImageDataList);
			}
			response.getRecTags().add(element);
		}
		return response;
	}
	public TagLikeResponse tagLike(long uid, long data, int isLike, int needNew, String tagIds) {
		TagLikeResponse response = new TagLikeResponse();
		UserTag userTag = topicTagDao.getUserTagByUidAndTagid(uid, data);
		int isSave = 0; // 1保存 0更新
		if (userTag == null) {
			userTag = new UserTag();
			userTag.setUid(uid);
			userTag.setTagId(data);
			isSave = 1;
		}
		if (isLike == 0) { // 不喜欢
			userTag.setType(2);
			userTag.setScore(0);
			userTag.setIsTop(0);
			// 不喜欢补标签
			if (needNew == 1) {
				List<Long> tagIdList = new ArrayList<Long>();
				if (!StringUtils.isEmpty(tagIds)) {
					String[] tagIdArr = tagIds.split(",");
					for (String tagId : tagIdArr) {
						tagIdList.add(Long.parseLong(tagId));
					}
				}
				ExtTagTmp topicTag = null;
				if (ProbabilityUtil.isInProb(40)) { // 40%概率出现行为标签。
					// 通过用户行为产生的排名最高的前5个标签,且评分必须超过20
					List<ExtTagTmp> favoTags = extContentMapper.getUserFavoriteTags(uid, 2,tagIdList);
					for (ExtTagTmp tagInfo2 : favoTags) {
						if (tagIdList.contains(tagInfo2.getTagId())) {
							continue;
						} else {
							topicTag = tagInfo2;
							break;
						}
					}
				}
				if (topicTag == null) {
					// 取系统标签
					List<ExtTagTmp> sysTagList = extContentMapper.getSysTagsInfo(uid,10,tagIdList);
						for (ExtTagTmp topicTag2 : sysTagList) {
								if (tagIdList.contains(topicTag2.getTagId())) {
									continue;
								} else {
									topicTag = topicTag2;
									break;
								}
						}
					}
					if (topicTag != null) {
						List<Long> serchTagIdList = new ArrayList<Long>();
						serchTagIdList.add(topicTag.getTagId());
						// 一次性查询出所有标签王国信息
						List<ExtTagTopicInfo> listTagTopicInfo = new ArrayList<ExtTagTopicInfo>();
						listTagTopicInfo = extContentMapper.getTagTopicInfo(serchTagIdList);
						Map<String, ExtTagTopicInfo> tagTopicMap = new HashMap<String, ExtTagTopicInfo>();
						if (listTagTopicInfo != null) {
							for (ExtTagTopicInfo extTagTopicInfo : listTagTopicInfo) {
								tagTopicMap.put(String.valueOf(extTagTopicInfo.getTagId()), extTagTopicInfo);
							}
						}
						// 查询标签外露内容
						Map<String, List<ExtTagOutInfo>> tagOutDataMap = new HashMap<String, List<ExtTagOutInfo>>();
						List<ExtTagOutInfo> listExtTagOutInfo = new ArrayList<ExtTagOutInfo>();
						listExtTagOutInfo = extContentMapper.getTagOutList(serchTagIdList);
						if (null != listExtTagOutInfo && listExtTagOutInfo.size() > 0) {
							Long tagId = null;
							List<ExtTagOutInfo> toList = null;
							for (ExtTagOutInfo m : listExtTagOutInfo) {
								tagId = m.getTagId();
								toList = tagOutDataMap.get(tagId.toString());
								if (null == toList) {
									toList = new ArrayList<ExtTagOutInfo>();
									tagOutDataMap.put(tagId.toString(), toList);
								}
								toList.add(m);
							}
						}
						long tagId = topicTag.getTagId();
						ExtTagInfo element = new ExtTagInfo();
						element.setIsShowLikeButton(1);
						int tagPersons = 0;
						int kingdomCount = 0;
						ExtTagTopicInfo extTagTopicInfo = tagTopicMap.get(String.valueOf(tagId));
						if (extTagTopicInfo != null) {
							tagPersons = extTagTopicInfo.getTagPersons();
							kingdomCount = extTagTopicInfo.getKingdomCount();
						}
						element.setKingdomCount(kingdomCount);
						element.setPersonCount(tagPersons);
						element.setTagName(topicTag.getTagName());
						element.setTagId(tagId);
						List<ExtTagOutInfo> list = tagOutDataMap.get(String.valueOf(tagId));
						List<ExtOutImageData> extOutImageDataList = TagCopier.INSTANCE.asExtOutImageDataList(list);
						if (null != extOutImageDataList && extOutImageDataList.size() > 0) {
							for (int i = 0; i < extOutImageDataList.size(); i++) {
								ExtOutImageData extOutImageDataElement = extOutImageDataList.get(i);
								if (StringUtils.isNotEmpty(extOutImageDataElement.getFragmentImage())) {
									extOutImageDataElement.setFragmentImage(
											Constant.QINIU_DOMAIN + "/" + extOutImageDataElement.getFragmentImage());
								}
							}
							element.setImageData(extOutImageDataList);
						}
						response.setNewKingdomTag(element);
					}
			}
		}else if (isLike == 1) { // 喜欢
			userTag.setType(1);
			userTag.setIsTop(0);
		}else if (isLike == 2) { // 置顶
			userTag.setIsTop(1);
			userTag.setTopTime(new Date());
		}else if (isLike == 3) { // 移除
			userTag.setType(0);
			userTag.setIsTop(0);
		}else if (isLike == 4) { // 取消置顶
			userTag.setIsTop(0);
		}
		if (isSave == 1) {
			userTagMapper.insertSelective(userTag);
		} else {
			userTagMapper.updateByPrimaryKey(userTag);
		}
		return response;
	}
	
	private void builderInvitationElement(HotResponse dto, long currentUid){
		//查询是否有待领取的邀请
		UserInvitationHis his = userDao.getUserLastestInvitation(currentUid,0);
		if(null == his){//没有待领取的东东，所以不需要返回
			return;
		}
		UserProfile fromUserProfile = userDao.getUserProfileByUid(his.getFromUid());
		if(null == fromUserProfile){//用户不存在就无所谓了
			extUserMapper.updateUserInvitationHisReceive(his.getId());//赶紧更新掉，免得影响后面
			return;
		}
		
		HotResponse.InvitationElement invitationElement = new HotResponse.InvitationElement();
		invitationElement.setType(14);
		invitationElement.setAvatar(baseService.genAvatar(fromUserProfile.getAvatar()));
		if(!StringUtils.isEmpty(fromUserProfile.getAvatarFrame())){
			invitationElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + fromUserProfile.getAvatarFrame());
		}
		if(his.getCoins().intValue() > 0){
			invitationElement.setCoins(his.getCoins().intValue());
		}else{
			invitationElement.setCoins(0);
			//对于不显示按钮的，只会暂时一次
			extUserMapper.updateUserInvitationHisReceive(his.getId());
		}
		String name = CommonUtil.getShortName(fromUserProfile.getNickName(), 12);
		StringBuilder ctext = new StringBuilder();
		if(his.getType().intValue() == 1){//邀请的奖励
			ctext.append("邀请").append(name).append("加入米汤");
		}else{
			ctext.append("通过").append(name).append("邀请加入米汤");
		}
		if(his.getCoins().intValue() > 0){
			ctext.append("\n可获取").append(his.getCoins().intValue()).append("米汤币奖励");
			invitationElement.setBtnText("点击\n领取奖励");
		}
		invitationElement.setCText(ctext.toString());
		invitationElement.setHtEnd(2+name.length());
		invitationElement.setHtStart(2);
		invitationElement.setInvitationType(his.getType());
		invitationElement.setBtnAction(his.getType());
		invitationElement.setUid(his.getFromUid());
		invitationElement.setV_lv(fromUserProfile.getvLv());
		dto.getData().add(invitationElement);
	}
	
	public Response<ShowListResponse> showList(ShowListRequest request){
		List<Long> blacklistUids = extUserMapper.getBlacklist(request.getUid());
        if(null == blacklistUids){
        	blacklistUids = new ArrayList<Long>();
        }
    	
        ShowListResponse bangDanDto = new ShowListResponse();
        int searchType = 2;//找组织
        if(request.getListType() == 1){
            searchType = 1;//找谁
        }
        
        List<BillboardDetails> showList = contentDao.getShowListPageByType(request.getSinceId(), searchType);
        if(null != showList && showList.size() > 0){
            //为减少在for循环里查询sql，这里统一将一些数据一次性查出使用
            List<Long> bidList = new ArrayList<Long>();
            for(BillboardDetails bbd : showList){
                bidList.add(bbd.getBid());
            }
            List<Long> topicIdList = new ArrayList<Long>();
            List<Long> uidList = new ArrayList<Long>();
            List<Long> subBidList = new ArrayList<Long>();

            Map<String, List<BillboardRelation>> relationMap = new HashMap<String, List<BillboardRelation>>();
            //一次性查询所有榜单相关信息
            Map<String, Billboard> bMap = new HashMap<String, Billboard>();
            List<Billboard> bList = contentDao.loadBillboardByBids(bidList);
            if(null != bList && bList.size() > 0){
                List<BillboardRelation> relationList = null;
                for(Billboard bb : bList){
                    bMap.put(bb.getId().toString(), bb);

                    if(bb.getMode() == 0){
                        int pageSize = 0;//榜单集是所有
                        List<Long> noTargetIds = null;
                        if(bb.getType() == 1){
                            pageSize = 3;//王国
                            if(null != blacklistUids && blacklistUids.size() > 0){
                            	noTargetIds = extTopicMapper.getTopicIdsByUids(blacklistUids);
                            }
                        }else if(bb.getType() == 2){
                            pageSize = 5;//人
                            noTargetIds = blacklistUids;
                        }
                        
                        relationList = contentDao.getRelationListPage(bb.getId(), -1, pageSize, noTargetIds);
                        if(null != relationList && relationList.size() > 0){
                            relationMap.put(bb.getId().toString(), relationList);
                            for(BillboardRelation bbr : relationList){
                                if(bbr.getType() == 1){//王国
                                    if(!topicIdList.contains(bbr.getTargetId())){
                                        topicIdList.add(bbr.getTargetId());
                                    }
                                }else if(bbr.getType() == 2){//人
                                    if(!uidList.contains(bbr.getTargetId())){
                                        uidList.add(bbr.getTargetId());
                                    }
                                }else if(bbr.getType() == 3){//榜单
                                    if(!subBidList.contains(bbr.getTargetId())){
                                        subBidList.add(bbr.getTargetId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //王国相关
            Map<String, Topic> topicMap = new HashMap<>();//王国信息
            Map<String, String> liveFavouriteMap = new HashMap<String, String>();//王国订阅信息
            Map<String, Content> topicContentMap = new HashMap<String, Content>();//王国内容表信息
            Map<String, Long> reviewCountMap = new HashMap<String, Long>();//王国评论信息
            Map<String, Long> topicMemberCountMap = null;//王国成员数信息
            Map<String, String> topicTagMap = new HashMap<String, String>();//一次性查询王国的标签信息
            if(topicIdList.size() > 0){
                List<Topic> topicList = topicDao.getTopicListByIds(topicIdList);
                if(null != topicList && topicList.size() > 0){
                    Long uid = null;
                    for(Topic m : topicList){
                        topicMap.put(m.getId().toString(), m);
                        uid = m.getUid();
                        if(!uidList.contains(uid)){
                            uidList.add(uid);
                        }
                    }
                }
                List<LiveFavorite> liveFavoriteList = topicDao.getLiveFavoritesByUidAndTopicIds(request.getUid(), topicIdList);
                if(null != liveFavoriteList && liveFavoriteList.size() > 0){
                    for(LiveFavorite lf : liveFavoriteList){
                        liveFavouriteMap.put(lf.getUid().toString(), "1");
                    }
                }
                List<Content> topicContentList = contentDao.getContentListByTopicIds(topicIdList);
                if(null != topicContentList && topicContentList.size() > 0){
                    for(Content c : topicContentList){
                        topicContentMap.put(c.getForwardCid().toString(), c);
                    }
                }
                List<ExtTopicCount> tcList = extTopicMapper.getTopicCountByTids(topicIdList);
                if(null != tcList && tcList.size() > 0){
                    for(ExtTopicCount m : tcList){
                        reviewCountMap.put(String.valueOf(m.getTopicId()), m.getReviewCount());
                    }
                }
                topicMemberCountMap = topicDao.getTopicMembersCount(topicIdList);
                List<TopicTagDetail> topicTagList = topicTagDao.getTopicTagDetailListByTopicIds(topicIdList);
                if(null != topicTagList && topicTagList.size() > 0){
                    long tid = 0;
                    String tags = null;
                    Long topicId = null;
                    for(TopicTagDetail ttd : topicTagList){
                        topicId = ttd.getTopicId();
                        if(topicId.longValue() != tid){
                            //先插入上一次
                            if(tid > 0 && !StringUtils.isEmpty(tags)){
                                topicTagMap.put(String.valueOf(tid), tags);
                            }
                            //再初始化新的
                            tid = topicId.longValue();
                            tags = null;
                        }
                        if(tags != null){
                            tags = tags + ";" + ttd.getTag();
                        }else{
                            tags = ttd.getTag();
                        }
                    }
                    if(tid > 0 && !StringUtils.isEmpty(tags)){
                        topicTagMap.put(String.valueOf(tid), tags);
                    }
                }
            }
            if(null == topicMemberCountMap){
                topicMemberCountMap = new HashMap<String, Long>();
            }
            //人相关
            Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();//用户信息
            Map<String, String> followMap = new HashMap<String, String>();//关注信息
            if(uidList.size() > 0){
                List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
                if(null != userList && userList.size() > 0){
                    for(UserProfile u : userList){
                        userMap.put(u.getUid().toString(), u);
                    }
                }
                List<UserFollow> userFollowList = userDao.getAllFollows(request.getUid(), uidList);
                if(null != userFollowList && userFollowList.size() > 0){
                    for(UserFollow uf : userFollowList){
                        followMap.put(uf.getSourceUid()+"_"+uf.getTargetUid(), "1");
                    }
                }
            }
            //子榜单相关
            Map<String, Billboard> subBillboardMap = new HashMap<>();
            if(subBidList.size() > 0){
                List<Billboard> subList = contentDao.loadBillboardByBids(subBidList);
                if(null != subList && subList.size() > 0){
                    for(Billboard bb : subList){
                        subBillboardMap.put(bb.getId().toString(), bb);
                    }
                }
            }
            //一次性查出所有分类信息
            Map<String, TopicCategory> kingdomCategoryMap = new HashMap<>();
            List<TopicCategory> kcList = topicDao.getAllTopicCategory();
            if(null != kcList && kcList.size() > 0){
            	for(TopicCategory m : kcList){
            		kingdomCategoryMap.put(m.getId().toString(), m);
            	}
            }
            
            int exchangeRate = appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE")==null?100:appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE");
            double minPrice =Double.parseDouble((String) appConfigService.getAppConfigByKey("KINGDOM_SHOW_PRICE_BRAND_MIN"));

            Billboard billBoard = null;
            BangDanData bangDanData = null;
            BangDanData.BangDanInnerData bangDanInnerData = null;
            List<BillboardRelation> relationList = null;
            Topic topic = null;
            UserProfile userProfile = null;
            Content topicContent = null;
            Billboard subBillBoard = null;
            TopicCategory kingdomCategory = null;
            for(BillboardDetails bbd : showList){
                billBoard = bMap.get(bbd.getBid().toString());
                if(null == billBoard){
                    continue;
                }
                bangDanData = new BangDanData();
                bangDanData.setSummary(billBoard.getSummary());
                bangDanData.setTitle(billBoard.getName());
                bangDanData.setListId(billBoard.getId());
                if(!StringUtils.isEmpty(billBoard.getImage())){
                    bangDanData.setCoverImage(Constant.QINIU_DOMAIN + "/" + billBoard.getImage());
                }
                bangDanData.setIsShowName(billBoard.getShowName());
                bangDanData.setCoverWidth(billBoard.getImgWidth());
                bangDanData.setCoverHeight(billBoard.getImgHeight());
                bangDanData.setBgColor(billBoard.getBgColor());
                // 是否是榜单集合类型
                bangDanData.setType((billBoard.getType()==3)?2:1);
                bangDanData.setSinceId(bbd.getSort().longValue());
                bangDanData.setSubType(billBoard.getType());

                //获取榜单里的具体内容（王国3个，人5个，如果是榜单集则显示所有榜单）
                int pageSize = 0;//榜单集是所有
                if(billBoard.getType() == 1){
                    pageSize = 3;//王国
                }else if(billBoard.getType() == 2){
                    pageSize = 5;//人
                }
                if(billBoard.getMode() > 0){//自动榜单
                    this.buildAutoBillBoardSimple(bangDanData, billBoard.getId(), billBoard.getMode(), request.getUid(), billBoard.getType(), -1, pageSize, blacklistUids, minPrice);
                }else{//人工榜单
                    relationList = relationMap.get(billBoard.getId().toString());
                    if(null != relationList && relationList.size() > 0){
                        for(BillboardRelation billBoardRelation : relationList){
                            bangDanInnerData = new BangDanData.BangDanInnerData();
                            long targetId = billBoardRelation.getTargetId();
                            bangDanInnerData.setSubType(billBoardRelation.getType());
                            if(billBoardRelation.getType()==1){// 王国数据
                                bangDanInnerData.setSubListId(billBoard.getId());
                                topic = topicMap.get(String.valueOf(targetId));
                                if(null == topic){
                                    log.info("王国[id="+targetId+"]不存在");
                                    continue;
                                }
                                long uid = topic.getUid().longValue();
                                bangDanInnerData.setUid(uid);
                                userProfile = userMap.get(String.valueOf(uid));
                                if(null == userProfile){
                                    log.info("用户[uid="+uid+"]不存在");
                                    continue;
                                }
                                bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()) );
                                bangDanInnerData.setNickName(userProfile.getNickName());
                                bangDanInnerData.setV_lv(userProfile.getvLv());
                                bangDanInnerData.setLevel(userProfile.getLevel());
                                if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                                	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                                }
                                if(null != followMap.get(request.getUid()+"_"+uid)){
                                    bangDanInnerData.setIsFollowed(1);
                                }else{
                                    bangDanInnerData.setIsFollowed(0);
                                }
                                if(null != followMap.get(uid+"_"+request.getUid())){
                                    bangDanInnerData.setIsFollowMe(1);
                                }else{
                                    bangDanInnerData.setIsFollowMe(0);
                                }
                                bangDanInnerData.setContentType(topic.getType());
                                if(null != liveFavouriteMap.get(String.valueOf(targetId))){
                                    bangDanInnerData.setFavorite(1);
                                }else{
                                    bangDanInnerData.setFavorite(0);
                                }
                                topicContent = topicContentMap.get(String.valueOf(targetId));
                                if(null == topicContent){
                                    continue;
                                }
                                bangDanInnerData.setPrice(topic.getPrice());
                                bangDanInnerData.setPriceRMB(baseService.exchangeKingdomPrice(bangDanInnerData.getPrice(), exchangeRate));
                                bangDanInnerData.setShowPriceBrand(bangDanInnerData.getPrice()>=minPrice?1:0);
                                bangDanInnerData.setShowRMBBrand(0);// 显示吊牌不显示RMB吊牌。

                                bangDanInnerData.setId(topicContent.getId());
                                bangDanInnerData.setCid(topicContent.getId());
                                bangDanInnerData.setTopicId(targetId);
                                bangDanInnerData.setForwardCid(targetId);
                                bangDanInnerData.setTitle(topic.getTitle());
                                bangDanInnerData.setCoverImage(Constant.QINIU_DOMAIN + "/" + topic.getLiveImage());
                                int internalStatust = baseService.getInternalStatus(topic, request.getUid());
                                if(internalStatust==Specification.SnsCircle.OUT.index){
                                	if( liveFavouriteMap.get(topic.getId().toString())!=null){
                                		internalStatust=Specification.SnsCircle.IN.index;
                                	}
                                }
                                bangDanInnerData.setInternalStatus(internalStatust);
                                if(null != topicMemberCountMap.get(String.valueOf(targetId))){
                                    bangDanInnerData.setFavoriteCount(topicMemberCountMap.get(String.valueOf(targetId)).intValue() + 1);
                                }else{
                                    bangDanInnerData.setFavoriteCount(1);
                                }
                                bangDanInnerData.setReadCount(topicContent.getReadCountDummy());
                                bangDanInnerData.setLikeCount(topicContent.getLikeCount());
                                if(null != reviewCountMap.get(String.valueOf(targetId))){
                                    bangDanInnerData.setReviewCount(reviewCountMap.get(String.valueOf(targetId)).intValue());
                                }else{
                                    bangDanInnerData.setReviewCount(0);
                                }
                                if(null != topicTagMap.get(String.valueOf(targetId))){
                                    bangDanInnerData.setTags(topicTagMap.get(String.valueOf(targetId)));
                                }else{
                                    bangDanInnerData.setTags("");
                                }
                                if(topic.getCategoryId() > 0){
                                	kingdomCategory = kingdomCategoryMap.get(topic.getCategoryId().toString());
                                	if(null != kingdomCategory){
                                		bangDanInnerData.setKcName(kingdomCategory.getName());
                                	}
                                }
                            }else if(billBoardRelation.getType()==2){// 人
                                bangDanInnerData.setSubListId(billBoard.getId());
                                bangDanInnerData.setUid(targetId);
                                userProfile = userMap.get(String.valueOf(targetId));
                                if(null == userProfile){
                                    log.info("用户[uid="+targetId+"]不存在");
                                    continue;
                                }
                                bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()) );
                                bangDanInnerData.setNickName(userProfile.getNickName());
                                bangDanInnerData.setV_lv(userProfile.getvLv());
                                bangDanInnerData.setLevel(userProfile.getLevel());
                                if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                                	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                                }
                                if(null != followMap.get(request.getUid()+"_"+targetId)){
                                    bangDanInnerData.setIsFollowed(1);
                                }else{
                                    bangDanInnerData.setIsFollowed(0);
                                }
                                if(null != followMap.get(targetId+"_"+request.getUid())){
                                    bangDanInnerData.setIsFollowMe(1);
                                }else{
                                    bangDanInnerData.setIsFollowMe(0);
                                }
                                bangDanInnerData.setIntroduced(userProfile.getIntroduced());
                            }else if(billBoardRelation.getType()==3){// 榜单集合
                                subBillBoard = subBillboardMap.get(String.valueOf(targetId));
                                if(null == subBillBoard){
                                    continue;
                                }
                                bangDanInnerData.setSubListId(subBillBoard.getId());
                                if(!StringUtils.isEmpty(subBillBoard.getImage())){
                                    bangDanInnerData.setCoverImage(Constant.QINIU_DOMAIN + "/" + subBillBoard.getImage());
                                }
                                bangDanInnerData.setTitle(subBillBoard.getName());
                            }
                            bangDanData.getSubList().add(bangDanInnerData);
                        }
                    }
                }
                bangDanDto.getListData().add(bangDanData);
            }
        }
        
        //米汤币榜单
        if(request.getSinceId() <= 0 && searchType == 2){//只有找组织，并且第一页才需要
        	List<Topic> topicList = topicDao.getTopPricedKingdomList(1, 3, blacklistUids);
        	if(null != topicList && topicList.size() > 0){
        		bangDanDto.setListPricedTopic(this.buildListPricedTopic(topicList, request.getUid()));
        	}
        }
        
        if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.3")){//2.2.3版本以前不支持标签
            if(bangDanDto.getListData().size() > 0){
                for(BangDanData bdd : bangDanDto.getListData()){
                    if(bdd.getSubList().size() > 0){
                        for(BangDanData.BangDanInnerData data : bdd.getSubList()){
                            data.setTags(null);
                        }
                    }
                }
            }
        }

        return Response.success(bangDanDto);
	}
	
	private List<PricedKingdomInfo> buildListPricedTopic(List<Topic> topicList, long currentUid){
		List<PricedKingdomInfo> listPricedTopic = Lists.newArrayList();
		
		int exchangeRate = appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE")==null?100:appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE");
		double minPrice =Double.parseDouble((String) appConfigService.getAppConfigByKey("KINGDOM_SHOW_PRICE_BRAND_MIN"));
		
		List<Long> uidList = new ArrayList<>();
		List<Long> topicIdList = new ArrayList<>();
		Map<String, Content> topicContentMap = new HashMap<>();

		if (null != topicList && topicList.size() > 0) {
			for (Topic m : topicList) {
				if (!uidList.contains(m.getUid())) {
					uidList.add(m.getUid());
				}
				if (!topicIdList.contains(m.getId())) {
					topicIdList.add(m.getId());
				}
			}
		}
		List<Content> topicContentList = contentDao.getContentListByTopicIds(topicIdList);
		if (null != topicContentList && topicContentList.size() > 0) {
			for (Content c : topicContentList) {
				topicContentMap.put(c.getForwardCid().toString(), c);
			}
		}
		Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();
		if (uidList.size() > 0) {
			List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
			if (null != userList && userList.size() > 0) {
				for (UserProfile u : userList) {
					userMap.put(u.getUid().toString(), u);
				}
			}
		}
		Content topicContent = null;
		UserProfile userProfile = null;
		PricedKingdomInfo data = null;
		for (Topic topic : topicList) {
			data = new PricedKingdomInfo();
			data.setUid(topic.getUid());
			userProfile = userMap.get(topic.getUid().toString());
			if (null == userProfile) {
				log.info("用户[uid=" + topic.getUid() + "]不存在");
				continue;
			}
			data.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
			data.setNickName(userProfile.getNickName());
			data.setV_lv(userProfile.getvLv());
			data.setLevel(userProfile.getLevel());
			if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
				data.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
			}
			data.setContentType(topic.getType());
			topicContent = topicContentMap.get(topic.getId().toString());
			if (null == topicContent) {
				continue;
			}
			data.setType(3);
			data.setPrice(topic.getPrice());
			data.setPriceRMB(baseService.exchangeKingdomPrice(topic.getPrice(), exchangeRate));
			data.setId(topicContent.getId());
			data.setCid(topicContent.getId());
			data.setTopicId(topic.getId());
			data.setForwardCid(topic.getId());
			data.setTitle(topic.getTitle());
			data.setCoverImage(Constant.QINIU_DOMAIN + "/" + topic.getLiveImage());
			data.setShowPriceBrand(data.getPrice()>=minPrice?1:0);
			listPricedTopic.add(data);
		}
		return listPricedTopic;
	}
	
	private void buildAutoBillBoardSimple(BangDanData bangDanData, long bid, int mode, long currentUid, int type, long sinceId, int pageSize, List<Long> blacklistUids, double minPrice){
        List<BillBoardListDTO> result = this.getAutoBillBoardList(mode, sinceId, pageSize,blacklistUids);

        if(null != result && result.size() > 0){
            List<Long> topicIdList = new ArrayList<Long>();
            List<Long> uidList = new ArrayList<Long>();
            if(type == 1){//王国
                for(BillBoardListDTO bbl : result){
                    if(!topicIdList.contains(bbl.getTargetId())){
                        topicIdList.add(bbl.getTargetId());
                    }
                }
            }else if(type == 2){//人
                for(BillBoardListDTO bbl : result){
                    if(!uidList.contains(bbl.getTargetId())){
                        uidList.add(bbl.getTargetId());
                    }
                }
            }

            Map<String, Topic> topicMap = new HashMap<>();
            Map<String, String> liveFavouriteMap = new HashMap<String, String>();
            Map<String, Content> topicContentMap = new HashMap<String, Content>();
            Map<String, Long> reviewCountMap = new HashMap<String, Long>();
            Map<String, Long> topicMemberCountMap = null;
            Map<String, String> topicTagMap = new HashMap<String, String>();
            if(topicIdList.size() > 0){
                List<Topic> topicList = topicDao.getTopicListByIds(topicIdList);
                if(null != topicList && topicList.size() > 0){
                    for(Topic m : topicList){
                        topicMap.put(m.getId().toString(), m);
                        if(!uidList.contains(m.getUid())){
                            uidList.add(m.getUid());
                        }
                    }
                }
                List<LiveFavorite> liveFavouriteList = topicDao.getLiveFavoritesByUidAndTopicIds(currentUid, topicIdList);
                if(null != liveFavouriteList && liveFavouriteList.size() > 0){
                    for(LiveFavorite lf : liveFavouriteList){
                        liveFavouriteMap.put(lf.getTopicId().toString(), "1");
                    }
                }
                List<Content> topicContentList = contentDao.getContentListByTopicIds(topicIdList);
                if(null != topicContentList && topicContentList.size() > 0){
                    for(Content c : topicContentList){
                        topicContentMap.put(c.getForwardCid().toString(), c);
                    }
                }
                List<ExtTopicCount> tcList = extTopicMapper.getTopicCountByTids(topicIdList);
                if(null != tcList && tcList.size() > 0){
                    for(ExtTopicCount m : tcList){
                        reviewCountMap.put(String.valueOf(m.getTopicId()), m.getReviewCount());
                    }
                }
                topicMemberCountMap = topicDao.getTopicMembersCount(topicIdList);
                if(null == topicMemberCountMap){
                    topicMemberCountMap = new HashMap<String, Long>();
                }
                List<TopicTagDetail> topicTagList = topicTagDao.getTopicTagDetailListByTopicIds(topicIdList);
                if(null != topicTagList && topicTagList.size() > 0){
                    long tid = 0;
                    String tags = null;
                    for(TopicTagDetail ttd : topicTagList){
                        if(ttd.getTagId().longValue() != tid){
                            //先插入上一次
                            if(tid > 0 && !StringUtils.isEmpty(tags)){
                                topicTagMap.put(String.valueOf(tid), tags);
                            }
                            //再初始化新的
                            tid = ttd.getTagId().longValue();
                            tags = null;
                        }
                        if(tags != null){
                            tags = tags + ";" + ttd.getTag();
                        }else{
                            tags = ttd.getTag();
                        }
                    }
                    if(tid > 0 && !StringUtils.isEmpty(tags)){
                        topicTagMap.put(String.valueOf(tid), tags);
                    }
                }
            }
            
            Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();
            //一次性查询关注信息
            Map<String, String> followMap = new HashMap<String, String>();
            if(uidList.size() > 0){
                List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
                if(null != userList && userList.size() > 0){
                    for(UserProfile u : userList){
                        userMap.put(u.getUid().toString(), u);
                    }
                }
                List<UserFollow> userFollowList = userDao.getAllFollows(currentUid, uidList);
                if(null != userFollowList && userFollowList.size() > 0){
                    for(UserFollow uf : userFollowList){
                        followMap.put(uf.getSourceUid()+"_"+uf.getTargetUid(), "1");
                    }
                }
            }
            //一次性查出所有分类信息
            Map<String, TopicCategory> kingdomCategoryMap = new HashMap<>();
            List<TopicCategory> kcList = topicDao.getAllTopicCategory();
            if(null != kcList && kcList.size() > 0){
            	for(TopicCategory m : kcList){
            		kingdomCategoryMap.put(m.getId().toString(), m);
            	}
            }

            int exchangeRate = appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE")==null?100:appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE");
            
            BangDanData.BangDanInnerData bangDanInnerData = null;
            Topic topic = null;
            UserProfile userProfile = null;
            Content topicContent = null;
            TopicCategory kingdomCategory = null;
            for(BillBoardListDTO bbl : result){
                bangDanInnerData = new BangDanData.BangDanInnerData();
                bangDanInnerData.setSubType(type);
                if(type==1){// 王国数据
                	if(bid > 0){
                		bangDanInnerData.setSubListId(bid);
                	}else{
                		bangDanInnerData.setSinceId(bbl.getSinceId());
                	}
                    topic = topicMap.get(String.valueOf(bbl.getTargetId()));
                    if(null == topic){
                        log.info("王国[id="+bbl.getTargetId()+"]不存在");
                        continue;
                    }
                    long uid = topic.getUid().longValue();
                    bangDanInnerData.setUid(uid);
                    userProfile = userMap.get(String.valueOf(uid));
                    if(null == userProfile){
                        log.info("用户[uid="+uid+"]不存在");
                        continue;
                    }
                    bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
                    bangDanInnerData.setNickName(userProfile.getNickName());
                    bangDanInnerData.setV_lv(userProfile.getvLv());
                    bangDanInnerData.setLevel(userProfile.getLevel());
                    if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                    	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                    }
                    if(null != followMap.get(currentUid+"_"+uid)){
                        bangDanInnerData.setIsFollowed(1);
                    }else{
                        bangDanInnerData.setIsFollowed(0);
                    }
                    if(null != followMap.get(uid+"_"+currentUid)){
                        bangDanInnerData.setIsFollowMe(1);
                    }else{
                        bangDanInnerData.setIsFollowMe(0);
                    }
                    bangDanInnerData.setContentType(topic.getType());
                    if(liveFavouriteMap.get(String.valueOf(bbl.getTargetId())) != null){
                        bangDanInnerData.setFavorite(1);
                    }else{
                        bangDanInnerData.setFavorite(0);
                    }
                    topicContent = topicContentMap.get(String.valueOf(bbl.getTargetId()));
                    if(null == topicContent){
                        continue;
                    }
                    bangDanInnerData.setId(topicContent.getId());
                    bangDanInnerData.setCid(topicContent.getId());
                    bangDanInnerData.setTopicId(bbl.getTargetId());
                    bangDanInnerData.setForwardCid(bbl.getTargetId());
                    bangDanInnerData.setTitle(topic.getTitle());
                    bangDanInnerData.setCoverImage(Constant.QINIU_DOMAIN + "/" + topic.getLiveImage());
                    int internalStatust = baseService.getInternalStatus(topic, currentUid);
                    if(internalStatust==Specification.SnsCircle.OUT.index){
                    	if( liveFavouriteMap.get(topic.getId().toString())!=null){
                    		internalStatust=Specification.SnsCircle.IN.index;
                    	}
                    }
                    bangDanInnerData.setInternalStatus(internalStatust);
                    if(null != topicMemberCountMap.get(String.valueOf(bbl.getTargetId()))){
                        bangDanInnerData.setFavoriteCount(topicMemberCountMap.get(String.valueOf(bbl.getTargetId())).intValue()+1);
                    }else{
                        bangDanInnerData.setFavoriteCount(1);
                    }
                    bangDanInnerData.setReadCount(topicContent.getReadCountDummy());
                    bangDanInnerData.setLikeCount(topicContent.getLikeCount());
                    if(null != reviewCountMap.get(String.valueOf(bbl.getTargetId()))){
                        bangDanInnerData.setReviewCount(reviewCountMap.get(String.valueOf(bbl.getTargetId())).intValue());
                    }else{
                        bangDanInnerData.setReviewCount(0);
                    }
                    if(null != topicTagMap.get(String.valueOf(bbl.getTargetId()))){
                        bangDanInnerData.setTags(topicTagMap.get(String.valueOf(bbl.getTargetId())));
                    }else{
                        bangDanInnerData.setTags("");
                    }
                    bangDanInnerData.setPrice(topic.getPrice());
                    bangDanInnerData.setPriceRMB(baseService.exchangeKingdomPrice(bangDanInnerData.getPrice(), exchangeRate));
                    bangDanInnerData.setShowPriceBrand(bangDanInnerData.getPrice()>=minPrice?1:0);
                    bangDanInnerData.setShowRMBBrand(0);// 显示吊牌不显示RMB吊牌。
                    if(topic.getCategoryId() > 0){
                    	kingdomCategory = kingdomCategoryMap.get(topic.getCategoryId().toString());
                    	if(null != kingdomCategory){
                    		bangDanInnerData.setKcName(kingdomCategory.getName());
                    	}
                    }
                }else if(type==2){// 人
                	if(bid > 0){
                		bangDanInnerData.setSubListId(bid);
                	}else{
                		bangDanInnerData.setSinceId(bbl.getSinceId());
                	}
                    bangDanInnerData.setUid(bbl.getTargetId());
                    userProfile = userMap.get(String.valueOf(bbl.getTargetId()));
                    if(null == userProfile){
                        log.info("用户[uid="+bbl.getTargetId()+"]不存在");
                        continue;
                    }
                    bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()) );
                    bangDanInnerData.setNickName(userProfile.getNickName());
                    bangDanInnerData.setV_lv(userProfile.getvLv());
                    bangDanInnerData.setLevel(userProfile.getLevel());
                    if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                    	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                    }
                    if(null != followMap.get(currentUid+"_"+bbl.getTargetId())){
                        bangDanInnerData.setIsFollowed(1);
                    }else{
                        bangDanInnerData.setIsFollowed(0);
                    }
                    if(null != followMap.get(bbl.getTargetId()+"_"+currentUid)){
                        bangDanInnerData.setIsFollowMe(1);
                    }else{
                        bangDanInnerData.setIsFollowMe(0);
                    }
                    bangDanInnerData.setIntroduced(userProfile.getIntroduced());
                }
                bangDanData.getSubList().add(bangDanInnerData);
            }
        }
    }
	
	private List<BillBoardListDTO> getAutoBillBoardList(int mode, long sinceId, int pageSize, List<Long> blacklistUids){
        List<BillBoardListDTO> result = null;
        String currentCacheKey = null;
        List<BillboardList> bbList = null;
        List<Long> topicIds = null;
        switch(mode){
            case 1://最活跃的米汤新鲜人
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getActiveUserBillboard(sinceId, pageSize, blacklistUids);
                break;
            case 2://最受追捧的米汤大咖
                currentCacheKey = redisService.get(Constant.BILLBOARD_KEY_POPULAR_PEOPLE);
                if(StringUtils.isEmpty(currentCacheKey)){
                    currentCacheKey = Constant.BILLBOARD_KEY_TARGET1;
                }
                bbList = contentDao.getBillBoardListPage(Constant.BILLBOARD_KEY_POPULAR_PEOPLE+currentCacheKey, (int)sinceId, pageSize, blacklistUids);
                result = ContentCopier.INSTANCE.asBillBoardListDTOList(bbList);
                break;
            case 3://最爱叨逼叨的话痨王国
                currentCacheKey = redisService.get(Constant.BILLBOARD_KEY_JAY_PEOPLE);
                if(StringUtils.isEmpty(currentCacheKey)){
                    currentCacheKey = Constant.BILLBOARD_KEY_TARGET1;
                }
                bbList = contentDao.getBillBoardListPage(Constant.BILLBOARD_KEY_JAY_PEOPLE+currentCacheKey, (int)sinceId, pageSize, blacklistUids);
                result = ContentCopier.INSTANCE.asBillBoardListDTOList(bbList);
                break;
            case 4://这里的互动最热闹
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getInteractionHottestKingdomBillboard(sinceId, pageSize, blacklistUids);
                break;
            case 5://最丰富多彩的王国
                currentCacheKey = redisService.get(Constant.BILLBOARD_KEY_COLOURFUL_KINGDOM);
                if(StringUtils.isEmpty(currentCacheKey)){
                    currentCacheKey = Constant.BILLBOARD_KEY_TARGET1;
                }
                topicIds = extTopicMapper.getTopicIdsByUids(blacklistUids);
                bbList = contentDao.getBillBoardListPage(Constant.BILLBOARD_KEY_COLOURFUL_KINGDOM+currentCacheKey, (int)sinceId, pageSize, topicIds);
                result = ContentCopier.INSTANCE.asBillBoardListDTOList(bbList);
                break;
            case 6://求安慰的孤独王国
                currentCacheKey = redisService.get(Constant.BILLBOARD_KEY_LONELY_KINGDOM);
                if(StringUtils.isEmpty(currentCacheKey)){
                    currentCacheKey = Constant.BILLBOARD_KEY_TARGET1;
                }
                topicIds = extTopicMapper.getTopicIdsByUids(blacklistUids);
                bbList = contentDao.getBillBoardListPage(Constant.BILLBOARD_KEY_LONELY_KINGDOM+currentCacheKey, (int)sinceId, pageSize, topicIds);
                result = ContentCopier.INSTANCE.asBillBoardListDTOList(bbList);
                break;
            case 7://最新更新的王国
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getLivesByUpdateTime(sinceId, pageSize, blacklistUids);
                break;
            case 8://新注册的帅哥
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getNewPeople(1, sinceId, pageSize, blacklistUids);
                break;
            case 9://新注册的美女
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getNewPeople(0, sinceId, pageSize, blacklistUids);
                break;
            case 10://新注册的用户（无所谓有没有王国）
                //实时统计
                if(sinceId < 0){
                    sinceId = Long.MAX_VALUE;
                }
                result = extBillBoardMapper.getNewRegisterUsers(sinceId, pageSize, blacklistUids);
                break;
            case 11://炙手可热的米汤红人
                //实时统计
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.fansBillboard(sinceId, pageSize, blacklistUids);
                break;
            case 12://王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.kingdomPriceList(sinceId, pageSize, blacklistUids);
                break;
            case 13://王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.kingdomIncrPriceList(sinceId, pageSize, blacklistUids);
                break;
            case 14://标签[运动的时候最性感]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("运动的时候最性感", sinceId, pageSize, blacklistUids);
                break;
            case 15://标签[运动的时候最性感]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("运动的时候最性感", sinceId, pageSize, blacklistUids);
                break;
            case 16://标签[非典型性话唠]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("非典型性话唠", sinceId, pageSize, blacklistUids);
                break;
            case 17://标签[非典型性话唠]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("非典型性话唠", sinceId, pageSize, blacklistUids);
                break;
            case 18://标签[声音与光影]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("声音与光影", sinceId, pageSize, blacklistUids);
                break;
            case 19://标签[声音与光影]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("声音与光影", sinceId, pageSize, blacklistUids);
                break;
            case 20://标签[建筑不止是房子]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("建筑不止是房子", sinceId, pageSize, blacklistUids);
                break;
            case 21://标签[建筑不止是房子]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("建筑不止是房子", sinceId, pageSize, blacklistUids);
                break;
            case 22://标签[寰球动漫游戏世界]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("寰球动漫游戏世界", sinceId, pageSize, blacklistUids);
                break;
            case 23://标签[寰球动漫游戏世界]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("寰球动漫游戏世界", sinceId, pageSize, blacklistUids);
                break;
            case 24://标签[玩物不丧志]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("玩物不丧志", sinceId, pageSize, blacklistUids);
                break;
            case 25://标签[玩物不丧志]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("玩物不丧志", sinceId, pageSize, blacklistUids);
                break;
            case 26://标签[铲屎官的日常]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("铲屎官的日常", sinceId, pageSize, blacklistUids);
                break;
            case 27://标签[铲屎官的日常]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("铲屎官的日常", sinceId, pageSize, blacklistUids);
                break;
            case 28://标签[旅行是我的态度]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("旅行是我的态度", sinceId, pageSize, blacklistUids);
                break;
            case 29://标签[旅行是我的态度]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("旅行是我的态度", sinceId, pageSize, blacklistUids);
                break;
            case 30://标签[深夜食堂]王国价值最高
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomPriceList("深夜食堂", sinceId, pageSize, blacklistUids);
                break;
            case 31://标签[深夜食堂]王国价值增长最快
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.tagKingdomIncrPriceList("深夜食堂", sinceId, pageSize, blacklistUids);
                break;
            case 32://个人米汤币排行榜
            	//实时统计
                if(sinceId < 0){
                    sinceId = 0l;
                }
                result = extBillBoardMapper.userCoinList(sinceId, pageSize, blacklistUids);
                break;
            case 33://对外分享次数用户榜单
            	if(sinceId < 0){
                    sinceId = 0l;
                }
            	result = extBillBoardMapper.shareUserList(sinceId, pageSize, blacklistUids);
            	break;
            case 34://外部阅读次数王国榜单
            	if(sinceId < 0){
                    sinceId = 0l;
                }
            	result = extBillBoardMapper.outReadKingdomList(sinceId, pageSize, blacklistUids);
            	break;
            case 35://正在抽奖的王国
            	if(sinceId < 0){
                    sinceId = 0l;
                }
            	result = extBillBoardMapper.kingdomLotteryList(sinceId, pageSize, blacklistUids);
            	break;
            case 37://优秀的新王国
            	if(sinceId < 0){
                    sinceId = 0l;
                }
            	Date now = new Date();
            	String today = DateUtil.date2string(now, "yyyy-MM-dd");
            	String preFiveDay = DateUtil.date2string(DateUtil.addDay(now, -4), "yyyy-MM-dd");
            	result = extBillBoardMapper.goodNewKingdomList(today, preFiveDay, sinceId, pageSize, blacklistUids);
            	break;
            default:
                break;
        }

        return result;
    }
	
	public Response<ShowListDetailResponse> showListDetail(ShowListDetailRequest request){
		ShowListDetailResponse billBoardDetailsDto = new ShowListDetailResponse();

		List<Long> blacklistUids = extUserMapper.getBlacklist(request.getUid());
        if(null == blacklistUids){
        	blacklistUids = new ArrayList<Long>();
        }
        
        Billboard billboard = billboardMapper.selectByPrimaryKey(request.getListId());
        if(null == billboard){
            return Response.failure(ResponseStatus.DATA_DOES_NOT_EXIST.status, ResponseStatus.DATA_DOES_NOT_EXIST.message);
        }

        double minPrice =Double.parseDouble((String) appConfigService.getAppConfigByKey("KINGDOM_SHOW_PRICE_BRAND_MIN"));
        int exchangeRate = appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE")==null?100:appConfigService.getIntegerAppConfigByKey("EXCHANGE_RATE");
        
        // 加载榜单基本信息
        billBoardDetailsDto.setSummary(billboard.getSummary());
        billBoardDetailsDto.setTitle(billboard.getName());
        billBoardDetailsDto.setListId(billboard.getId());
        if(!StringUtils.isEmpty(billboard.getImage())){
            billBoardDetailsDto.setCoverImage(Constant.QINIU_DOMAIN + "/" + billboard.getImage());
        }
        billBoardDetailsDto.setCoverWidth(billboard.getImgWidth());
        billBoardDetailsDto.setCoverHeight(billboard.getImgHeight());
        billBoardDetailsDto.setBgColor(billboard.getBgColor());
        billBoardDetailsDto.setType(billboard.getType()==3?2:1);
        billBoardDetailsDto.setSubType(billboard.getType());

        if(billboard.getMode() > 0){//自动榜单
            this.buildAutoBillBoardSimple(billBoardDetailsDto, 0, billboard.getMode(),  request.getUid(), billboard.getType(), request.getSinceId(), 20, blacklistUids, minPrice);
        }else{//人工榜单
            // 记载榜单旗下的列表数据
        	List<Long> noTargetIds = null;
        	if(billboard.getType() == 1){//王国
        		if(null != blacklistUids && blacklistUids.size() > 0){
                	noTargetIds = extTopicMapper.getTopicIdsByUids(blacklistUids);
                }
        	}else if(billboard.getType() == 2){//人
        		noTargetIds = blacklistUids;
        	}
        	List<BillboardRelation> data = contentDao.getRelationListPage(request.getListId(), (int)request.getSinceId(), 20, noTargetIds);
            if(null != data && data.size() > 0){
                //尽量不再循环里查sql，故将所需sql在循环外统一查询出来 -- modify by zcl
                List<Long> uidList = new ArrayList<Long>();//人
                List<Long> topicIdList = new ArrayList<Long>();//王国
                if(billboard.getType() == 1){//王国
                    for(BillboardRelation bbr : data){
                        if(!topicIdList.contains(bbr.getTargetId())){
                            topicIdList.add(bbr.getTargetId());
                        }
                    }
                }else if(billboard.getType() == 2){//人
                    for(BillboardRelation bbr : data){
                        if(!uidList.contains(bbr.getTargetId())){
                            uidList.add(bbr.getTargetId());
                        }
                    }
                }
                //王国相关
                Map<String, Topic> topicMap = new HashMap<>();//王国信息
                Map<String, String> liveFavouriteMap = new HashMap<String, String>();//王国订阅信息
                Map<String, Content> topicContentMap = new HashMap<String, Content>();//王国内容表信息
                Map<String, Long> reviewCountMap = new HashMap<String, Long>();//王国评论信息
                Map<String, Long> topicMemberCountMap = null;//王国成员信息
                Map<String, String> topicTagMap = new HashMap<String, String>();
                if(topicIdList.size() > 0){
                    List<Topic> topicList = topicDao.getTopicListByIds(topicIdList);
                    if(null != topicList && topicList.size() > 0){
                        for(Topic m : topicList){
                            topicMap.put(m.getId().toString(), m);
                            if(!uidList.contains(m.getUid())){
                                uidList.add(m.getUid());
                            }
                        }
                    }
                    List<LiveFavorite> liveFavouriteList = topicDao.getLiveFavoritesByUidAndTopicIds(request.getUid(), topicIdList);
                    if(null != liveFavouriteList && liveFavouriteList.size() > 0){
                        for(LiveFavorite lf : liveFavouriteList){
                            liveFavouriteMap.put(lf.getTopicId().toString(), "1");
                        }
                    }
                    List<Content> topicContentList = contentDao.getContentListByTopicIds(topicIdList);
                    if(null != topicContentList && topicContentList.size() > 0){
                        for(Content c : topicContentList){
                            topicContentMap.put(c.getForwardCid().toString(), c);
                        }
                    }
                    List<ExtTopicCount> tcList = extTopicMapper.getTopicCountByTids(topicIdList);
                    if(null != tcList && tcList.size() > 0){
                        for(ExtTopicCount m : tcList){
                            reviewCountMap.put(String.valueOf(m.getTopicId()), m.getReviewCount());
                        }
                    }
                    topicMemberCountMap = topicDao.getTopicMembersCount(topicIdList);
                    if(null == topicMemberCountMap){
                        topicMemberCountMap = new HashMap<String, Long>();
                    }
                    List<TopicTagDetail> topicTagList = topicTagDao.getTopicTagDetailListByTopicIds(topicIdList);
                    if(null != topicTagList && topicTagList.size() > 0){
                        long tid = 0;
                        String tags = null;
                        for(TopicTagDetail ttd : topicTagList){
                            if(ttd.getTagId().longValue() != tid){
                                //先插入上一次
                                if(tid > 0 && !StringUtils.isEmpty(tags)){
                                    topicTagMap.put(String.valueOf(tid), tags);
                                }
                                //再初始化新的
                                tid = ttd.getTagId().longValue();
                                tags = null;
                            }
                            if(tags != null){
                                tags = tags + ";" + ttd.getTag();
                            }else{
                                tags = ttd.getTag();
                            }
                        }
                        if(tid > 0 && !StringUtils.isEmpty(tags)){
                            topicTagMap.put(String.valueOf(tid), tags);
                        }
                    }
                }
                //人相关
                Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();//用户信息
                Map<String, String> followMap = new HashMap<String, String>();//关注信息
                if(uidList.size() > 0){
                    List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
                    if(null != userList && userList.size() > 0){
                        for(UserProfile u : userList){
                            userMap.put(u.getUid().toString(), u);
                        }
                    }
                    List<UserFollow> userFollowList = userDao.getAllFollows(request.getUid(), uidList);
                    if(null != userFollowList && userFollowList.size() > 0){
                        for(UserFollow uf : userFollowList){
                            followMap.put(uf.getSourceUid()+"_"+uf.getTargetUid(), "1");
                        }
                    }
                }
                //一次性查出所有分类信息
                Map<String, TopicCategory> kingdomCategoryMap = new HashMap<>();
                List<TopicCategory> kcList = topicDao.getAllTopicCategory();
                if(null != kcList && kcList.size() > 0){
                	for(TopicCategory m : kcList){
                		kingdomCategoryMap.put(m.getId().toString(), m);
                	}
                }

                Topic topic = null;
                UserProfile userProfile = null;
                Content topicContent = null;
                TopicCategory kingdomCategory = null;
                BangDanData.BangDanInnerData bangDanInnerData = null;
                for(BillboardRelation billBoardRelation : data){
                	bangDanInnerData = new BangDanData.BangDanInnerData();
                    long targetId = billBoardRelation.getTargetId();
                    int type = billBoardRelation.getType();
                    bangDanInnerData.setSubType(type);
                    bangDanInnerData.setSinceId(billBoardRelation.getSort().longValue());
                    if(type==1){// 王国
                        topic = topicMap.get(String.valueOf(targetId));
                        if(null == topic){
                            log.info("王国[id="+targetId+"]不存在");
                            continue;
                        }
                        long uid = topic.getUid().longValue();
                        bangDanInnerData.setUid(uid);
                        userProfile = userMap.get(String.valueOf(uid));
                        if(null == userProfile){
                            log.info("用户[uid="+uid+"]不存在");
                            continue;
                        }
                        bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()) );
                        bangDanInnerData.setNickName(userProfile.getNickName());
                        bangDanInnerData.setV_lv(userProfile.getvLv());
                        bangDanInnerData.setLevel(userProfile.getLevel());
                        if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                        	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                        }
                        if(null != followMap.get(request.getUid()+"_"+uid)){
                            bangDanInnerData.setIsFollowed(1);
                        }else{
                            bangDanInnerData.setIsFollowed(0);
                        }
                        if(null != followMap.get(uid+"_"+request.getUid())){
                            bangDanInnerData.setIsFollowMe(1);
                        }else{
                            bangDanInnerData.setIsFollowMe(0);
                        }
                        bangDanInnerData.setContentType(topic.getType());
                        if(null != liveFavouriteMap.get(String.valueOf(targetId))){
                            bangDanInnerData.setFavorite(1);
                        }else{
                            bangDanInnerData.setFavorite(0);
                        }
                        topicContent = topicContentMap.get(String.valueOf(targetId));
                        bangDanInnerData.setId(topicContent.getId());
                        bangDanInnerData.setCid(topicContent.getId());
                        bangDanInnerData.setTopicId(targetId);
                        bangDanInnerData.setForwardCid(targetId);
                        bangDanInnerData.setTitle(topic.getTitle());
                        bangDanInnerData.setCoverImage(Constant.QINIU_DOMAIN + "/" + topic.getLiveImage());
                        int internalStatust = baseService.getInternalStatus(topic, request.getUid());
                        if(internalStatust==Specification.SnsCircle.OUT.index){
                        	if( liveFavouriteMap.get(topic.getId().toString())!=null){
                        		internalStatust=Specification.SnsCircle.IN.index;
                        	}
                        }
                        bangDanInnerData.setInternalStatus(internalStatust);
                        if(null != topicMemberCountMap.get(String.valueOf(targetId))){
                            bangDanInnerData.setFavoriteCount(topicMemberCountMap.get(String.valueOf(targetId)).intValue()+1);
                        }else{
                            bangDanInnerData.setFavoriteCount(1);
                        }
                        bangDanInnerData.setReadCount(topicContent.getReadCountDummy());
                        bangDanInnerData.setLikeCount(topicContent.getLikeCount());
                        if(null != reviewCountMap.get(String.valueOf(targetId))){
                            bangDanInnerData.setReviewCount(reviewCountMap.get(String.valueOf(targetId)).intValue());
                        }else{
                            bangDanInnerData.setReviewCount(0);
                        }
                        if(null != topicTagMap.get(String.valueOf(targetId))){
                            bangDanInnerData.setTags(topicTagMap.get(String.valueOf(targetId)));
                        }else{
                            bangDanInnerData.setTags("");
                        }
                        bangDanInnerData.setPrice(topic.getPrice());
                        bangDanInnerData.setPriceRMB(baseService.exchangeKingdomPrice(bangDanInnerData.getPrice(), exchangeRate));
                        bangDanInnerData.setShowPriceBrand(bangDanInnerData.getPrice()>=minPrice?1:0);
                        bangDanInnerData.setShowRMBBrand(0);// 显示吊牌不显示RMB吊牌。
                        
                        int categoryId = topic.getCategoryId();
                        if(categoryId > 0){
                        	kingdomCategory = kingdomCategoryMap.get(String.valueOf(categoryId));
                        	if(null != kingdomCategory){
                        		bangDanInnerData.setKcName(kingdomCategory.getName());
                        	}
                        }
                    }else if(type==2){//人
                        bangDanInnerData.setUid(targetId);
                        userProfile = userMap.get(String.valueOf(targetId));
                        if(null == userProfile){
                        	log.info("用户[uid="+targetId+"]不存在");
                            continue;
                        }
                        bangDanInnerData.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
                        bangDanInnerData.setNickName(userProfile.getNickName());
                        bangDanInnerData.setV_lv(userProfile.getvLv());
                        bangDanInnerData.setLevel(userProfile.getLevel());
                        if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                        	bangDanInnerData.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                        }
                        if(null != followMap.get(request.getUid()+"_"+targetId)){
                            bangDanInnerData.setIsFollowed(1);
                        }else{
                            bangDanInnerData.setIsFollowed(0);
                        }
                        if(null != followMap.get(targetId+"_"+request.getUid())){
                            bangDanInnerData.setIsFollowMe(1);
                        }else{
                            bangDanInnerData.setIsFollowMe(0);
                        }
                        bangDanInnerData.setIntroduced(userProfile.getIntroduced());
                    }else if(type==3){// 榜单
                        Billboard bb = billboardMapper.selectByPrimaryKey(targetId);
                        if(!StringUtils.isEmpty(bb.getImage())){
                            bangDanInnerData.setCoverImage(Constant.QINIU_DOMAIN + "/" + bb.getImage());
                        }
                        bangDanInnerData.setId(bb.getId());
                        bangDanInnerData.setTitle(bb.getName());
                    }
                    billBoardDetailsDto.getSubList().add(bangDanInnerData);
                }
            }
        }

        if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.3")){//2.2.3版本以前不支持标签
            if(billBoardDetailsDto.getSubList().size() > 0){
                for(BangDanData.BangDanInnerData data : billBoardDetailsDto.getSubList()){
                    data.setTags(null);
                }
            }
        }

        return Response.success(billBoardDetailsDto);
	}
	
	public Response<PricedKingdomListResponse> getPricedKingdomList(PricedKingdomListRequest request){
		PricedKingdomListResponse result = new PricedKingdomListResponse();
		
		List<Long> blacklistUids = extUserMapper.getBlacklist(request.getUid());
		List<Topic> topicList = topicDao.getTopPricedKingdomList(request.getPage(), request.getPageSize(), blacklistUids);
		result.setListData(this.buildListPricedTopic(topicList, request.getUid()));
        return Response.success(result);
	}
	
	public Response<BaseResponse> industryOpt(long uid, long industryId, int action) {
		if (action == 1) {
			UserIndustry userIndustry = userDao.getUserIndustryById(industryId);
			if (userIndustry != null) {
				UserProfile userProfile = userDao.getUserProfileByUid(uid);
				if (userProfile != null) {
					userProfile.setIndustryId(industryId);
					userDao.updateUserProfile(userProfile);
					userDao.delUserIndustryWrongByUid(uid);
				}
			}
		} else if (action == 2) {
			UserIndustryWrong userIndustryWrong = new UserIndustryWrong();
			userIndustryWrong.setUid(uid);
			userIndustryWrong.setIndustryId(industryId);
			userDao.saveUserIndustryWrong(userIndustryWrong);
		}
		return Response.success();
	}


	public Response<UserGroupResponse> userGroup(long uid) {
		UserGroupResponse dto = new UserGroupResponse();
		List<Long> blacklistUids = extUserMapper.getBlacklist(uid);
		List<UserFamous> userFamousList = userDao.getUserFamousList(1, 30, blacklistUids);
		if (null != userFamousList && userFamousList.size() > 0) {
			List<Long> uidList = new ArrayList<Long>();
			for (UserFamous c : userFamousList) {
				if (!uidList.contains(c.getUid())) {
					uidList.add(c.getUid());
				}
			}
			// 一次性查出所有的用户信息
			Map<String, ExtUserProfile> userProfileMap = new HashMap<String, ExtUserProfile>();
			List<ExtUserProfile> profileList = extUserMapper.getExtUserProfileListByIds(uidList);
			if (null != profileList && profileList.size() > 0) {
				for (ExtUserProfile up : profileList) {
					userProfileMap.put(up.getUid().toString(), up);
				}
			}
			for (UserFamous uf : userFamousList) {
				UserGroupResponse.UserElement userElement = new UserGroupResponse.UserElement();
				ExtUserProfile userProfile = userProfileMap.get(uf.getUid().toString());
				if (null != userProfile) {
					userElement = UserCopier.INSTANCE.asUserGroupResponseUserElement(userProfile);
					userElement.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
					if (!StringUtils.isEmpty(userProfile.getAvatarFrame())) {
						userElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
					}

				}
				dto.getUserGroup().add(userElement);
			}
		}
		return Response.success(dto);
	}

	public HotResponse hot(int page, long uid) {
		HotResponse response = new HotResponse();
		// 其他栏目位置信息
		Map<String, String> hotPositionMap = new HashMap<String, String>();
		// 广告位位置信息
		Map<String, List<AdBanner>> adPositionMap = new HashMap<String, List<AdBanner>>();
		// 是否显示标签信息
		String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
		int isShowTags = 0;
		if (!StringUtils.isEmpty(isShowTagsStr)) {
			isShowTags = Integer.parseInt(isShowTagsStr);
		}
		int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
		Set<Integer> rightDigs = new HashSet<>();
		while (likeBtnRatio > rightDigs.size()) { // 随机序列。
			rightDigs.add(RandomUtils.nextInt(0, 101));
		}
		int maxButtonCount = 2; // 最多显示按钮数量
		if (page < 1) {
			page = 1;
		}
		if (page == 1) {
			int openPushPositions = 0;
			String openPushPositionsStr = appConfigService.getAppConfigByKey("OPEN_PUSH_POSITION");
			if (!StringUtils.isEmpty(openPushPositionsStr)) {
				openPushPositions = Integer.parseInt(openPushPositionsStr);
			}

			int bootFromFollowing = 0;
			int showAttentionListNumber = 10;
			String showAttentionListNumberStr = appConfigService.getAppConfigByKey("SHOW_ATTENTION_LIST_NUMBER");
			if (!StringUtils.isEmpty(showAttentionListNumberStr)) {
				showAttentionListNumber = Integer.parseInt(showAttentionListNumberStr);
			}
			int attentionListCount = extContentMapper.getAttentionAndLikeTagCount(uid);
			if (attentionListCount >= showAttentionListNumber) {
				bootFromFollowing = 1;
			}
			response.setBootFromFollowing(bootFromFollowing);
			response.setOpenPushPositions(openPushPositions);

			// 其他栏目位置信息
			String hotPosition = appConfigService.getAppConfigByKey("HOT_POSITION");
			JSONArray jsonArr = JSONArray.parseArray(hotPosition);
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject json = jsonArr.getJSONObject(i);
				int type = json.getIntValue("type");
				int positionMin = json.getIntValue("positionMin");
				int positionMax = json.getIntValue("positionMax");
				Random random = new Random();
				int s = random.nextInt(positionMax - positionMin + 1) + positionMin;
				if (hotPositionMap.get(String.valueOf(s)) == null) {
					hotPositionMap.put(String.valueOf(s), String.valueOf(type));
				} else {
					StringBuffer value = new StringBuffer(hotPositionMap.get(String.valueOf(s)).toString());
					value.append(",").append(String.valueOf(type));
					hotPositionMap.put(String.valueOf(s), value.toString());
				}
			}
			// 广告位位置信息
			List<AdBanner> listAdBanner = extContentMapper.getNormalBanners(uid);
			for (int i = 0; i < listAdBanner.size(); i++) {
				AdBanner adBanner = listAdBanner.get(i);
				String[] adPosition = adBanner.getBannerPosition().split("-");
				int positionMin = Integer.parseInt(adPosition[0]);
				int positionMax = Integer.parseInt(adPosition[1]);
				Random random = new Random();
				int s = random.nextInt(positionMax) % (positionMax - positionMin + 1) + positionMin;
				if (adPositionMap.get(String.valueOf(s)) == null) {
					List<AdBanner> adBHWList = new ArrayList<AdBanner>();
					adPositionMap.put(String.valueOf(s), adBHWList);
				} else {
					adPositionMap.get(String.valueOf(s)).add(adBanner);
				}
			}
		}
		List<Long> blacklistUids = extUserMapper.getBlacklist(uid);
		int pageSize = 20;
		List<Long> topicIdList = new ArrayList<Long>();
		List<Long> uidList = new ArrayList<Long>();
		List<Long> tagIdList = new ArrayList<Long>();
		List<ExtHotContent> hotContentList = extContentMapper.getHotContentList(uid, blacklistUids, page, pageSize);
		if (null != hotContentList && hotContentList.size() > 0) {
			for (ExtHotContent c : hotContentList) {
				if (c.getType() == 3 && !topicIdList.contains(c.getId())) {
					topicIdList.add(c.getId());
				}else if(c.getType()==16 && !tagIdList.contains(c.getId())){
					tagIdList.add(c.getId());
				}
			}
		}
		String likeTagTitle = "";
		String likeTagImage = "";
		if(tagIdList.size()>0){
			likeTagTitle = appConfigService.getAppConfigByKey("LIKE_TAG_TITLE");
			likeTagImage = Constant.QINIU_DOMAIN + "/" + appConfigService.getAppConfigByKey("LIKE_TAG_IMAGE");
		}
		List<Content> contentList = new ArrayList<Content>();
		if (topicIdList.size() > 0) {
			contentList = contentDao.getContentListByTopicIds(topicIdList);
		}
		Map<String, Content> contentMap = new HashMap<String, Content>();
		if (null != contentList && contentList.size() > 0) {
			for (Content c : contentList) {
				contentMap.put(String.valueOf(c.getForwardCid()), c);
				if (!uidList.contains(c.getUid())) {
					uidList.add(c.getUid());
				}
			}
		}
		
		Map<String,List<ExtTagOutInfo>> tagOutDataMap = baseService.getAllTagOut(tagIdList, uidList);
		
		Map<String, TopicTag> tagMap = baseService.getAllTopicTag(tagIdList);
		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
				topicIdList);

		// 一次性查询所有好友关系
		Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, uid);

		// 一次性查出所有的用户信息
		Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

		// 一次性查询关注信息
		Map<String, String> followMap = baseService.getAllUserFollowList(uidList, uid);

		// 一次性查询王国的最后一条更新记录
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

		// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
		baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
				topicMemberCountMap);

		// 一次性查询王国的标签信息
		Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

		// 一次性查出所有分类信息
		Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();

		for (int i = 0; i < pageSize; i++) {
			if (page == 1) {
				if (adPositionMap.get(String.valueOf(i)) != null) {
					List<AdBanner> list = adPositionMap.get(String.valueOf(i));
					for (int j = 0; j < list.size(); j++) {
						HotResponse.HeightWidthContentElement heightWidthContentElement = new HotResponse.HeightWidthContentElement();
						AdBanner adBanner = list.get(j);
						heightWidthContentElement.setCid(adBanner.getId());
						heightWidthContentElement.setH(adBanner.getAdBannerHeight());
						heightWidthContentElement.setW(adBanner.getAdBannerWidth());
						heightWidthContentElement.setType(12);
						response.getData().add(heightWidthContentElement);
					}
				}
				if (hotPositionMap.get(String.valueOf(i)) != null) {
					String[] types = hotPositionMap.get(String.valueOf(i)).toString().split(",");
					for (String typeStr : types) {
						int type = Integer.parseInt(typeStr);
						HotResponse.BaseContentElement baseElement = new HotResponse.BaseContentElement();
						switch (type) {
						case 11:// 语录
							baseElement.setType(11);
							response.getData().add(baseElement);
							break;
						case 13:// 分类入口
							baseElement.setType(13);
							response.getData().add(baseElement);
							break;
						case 14:// 邀请
							this.builderInvitationElement(response, uid);
							break;
						case 51:// 上市王国集合
							baseElement.setType(51);
							baseElement.setTitle("最新上市王国");
							int cc = extTopicMapper.countListingKingdoms();
							if (cc > 0) {// 大于0的才显示
								response.getData().add(baseElement);
							}
							break;
						case 52:// 用户集合
							baseElement.setType(52);
							baseElement.setTitle("最有料的国王");
							response.getData().add(baseElement);
							break;
						case 53:// 标签集合
							HotResponse.TagContentElement tagContentElement = new HotResponse.TagContentElement();
							tagContentElement.setType(53);
							int tagCount = appConfigService.getIntegerAppConfigByKey("HOME_HOT_LABELS");
							tagCount = tagCount * 2;
							tagContentElement.setSize(tagCount);
							tagContentElement.setTitle("推荐标签在这里");
							String tagTitle = appConfigService.getAppConfigByKey("RECOMMEND_TAG_TITLE");
							String tagImage = Constant.QINIU_DOMAIN + "/"
									+ appConfigService.getAppConfigByKey("RECOMMEND_TAG_IMAGE");
							tagContentElement.setNickName(tagTitle);
							tagContentElement.setAvatar(tagImage);
							response.getData().add(tagContentElement);
							break;
						case 54:// 行业
							UserProfile currentUserProfile = userDao.getUserProfileByUid(uid);
							if (currentUserProfile != null) {
								UserIndustry userIndustry = userDao
										.getUserIndustryById(currentUserProfile.getIndustryId());
								if (userIndustry == null) {
									userIndustry = extIndustryMapper.getGuessIndustryByUid(uid);
									if (userIndustry != null) {
										HotResponse.IndustryContentElement industryContentElement = new HotResponse.IndustryContentElement();
										industryContentElement.setType(54);
										industryContentElement.setIndustryId(userIndustry.getId());
										industryContentElement.setIndustry(userIndustry.getIndustryName());
										if (StringUtils.isNotEmpty(userIndustry.getCoverImg())) {
											industryContentElement.setCoverImage(
													Constant.QINIU_DOMAIN + "/" + userIndustry.getCoverImg());
										}
										response.getData().add(industryContentElement);
									}
								}
							}
							break;
						default:
							break;
						}
					}
				}
			}
			// 假如热点王国数量不够其他占位继续显示
			if (i >= hotContentList.size()) {
				continue;
			}
			ExtHotContent ehc = hotContentList.get(i);
			if(ehc.getType()==3){
				Content c = contentMap.get(String.valueOf(ehc.getId()));
				if(c!=null){
				ExtOutContent contentElement = baseService.handleContentForOut(c, uid, maxButtonCount, isShowTags, topicMap,
						rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap, lastFragmentMap,
						topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
						kingdomCategoryMap);
				response.getData().add(contentElement);
				}
			}else if(ehc.getType()==16){
				ExtOutContent contentElement = new ExtOutContent();
				contentElement.setUpdateTime(new Date(ehc.getLongTime()));
				TopicTag topicTag = tagMap.get(String.valueOf(ehc.getId()));
				if (topicTag == null) {
					continue;
				}
				contentElement.setType(ehc.getType());
				contentElement.setTagId(ehc.getId());
				contentElement.setTagName(topicTag.getTag());
				contentElement.setNickName(likeTagTitle);
				contentElement.setAvatar(likeTagImage);
				List<ExtTagOutInfo> tagOutDataList = tagOutDataMap.get(String.valueOf(ehc.getId()));
				if (null != tagOutDataList && tagOutDataList.size() > 0) {
				List<ExtOutData> extOutDataList=  TagCopier.INSTANCE.asExtOutDataList(tagOutDataList);
				if (null != extOutDataList && extOutDataList.size() > 0) {
					for (int j = 0; j < extOutDataList.size(); j++) {
						ExtOutData outDataElement = extOutDataList.get(j);
						if (StringUtils.isNotEmpty(outDataElement.getFragmentImage())) {
							outDataElement.setFragmentImage(
									Constant.QINIU_DOMAIN + "/" + outDataElement.getFragmentImage());
						}
					}
					contentElement.setImageData(extOutDataList);
				}
				}
			}
		}
		return response;
	}

	public Response<IndustryContentListResponse> industryContentList(long uid, int page, long industryId) {
		IndustryContentListResponse response = new IndustryContentListResponse();
		if (page < 1) {
			page = 1;
		}
		if (page == 1) {
			// 行业列表
			List<UserIndustry> listUserIndustry = extIndustryMapper.getIndustryListByUid(uid);
			List<IndustryContentListResponse.IndustryElement> listUserIndustryElement = ContentCopier.INSTANCE
					.asIndustryContentListIndustryElement(listUserIndustry);
			response.setIndustryData(listUserIndustryElement);
		}
		List<Long> blacklistUids = extUserMapper.getBlacklist(uid);
		List<Content> listContent = extContentMapper.getIndustryContentList(industryId, blacklistUids, page, 10);
		if (listContent == null || listContent.size() == 0) {
			return Response.success(response);
		}
		List<Long> uidList = new ArrayList<Long>();
		List<Long> topicIdList = new ArrayList<Long>();
		for (Content idx : listContent) {
			if (!uidList.contains(idx.getUid())) {
				uidList.add(idx.getUid());
			}
			if (!topicIdList.contains(idx.getForwardCid())) {
				topicIdList.add(idx.getForwardCid());
			}
		}

		// 是否显示标签信息
		String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
		int isShowTags = 0;
		if (!StringUtils.isEmpty(isShowTagsStr)) {
			isShowTags = Integer.parseInt(isShowTagsStr);
		}

		int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
		Set<Integer> rightDigs = new HashSet<>();
		while (likeBtnRatio > rightDigs.size()) { // 随机序列。
			rightDigs.add(RandomUtils.nextInt(0, 101));
		}
		int maxButtonCount = 2; // 最多显示按钮数量

		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
				topicIdList);

		// 一次性查询所有好友关系
		Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, uid);

		// 一次性查出所有的用户信息
		Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

		// 一次性查询关注信息
		Map<String, String> followMap = baseService.getAllUserFollowList(uidList, uid);

		// 一次性查询王国的最后一条更新记录
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

		// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
		baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
				topicMemberCountMap);

		// 一次性查询王国的标签信息
		Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

		// 一次性查出所有分类信息
		Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();

		for (Content content : listContent) {
			ExtOutContent contentElement = baseService.handleContentForOut(content, uid, maxButtonCount, isShowTags,
					topicMap, rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap, lastFragmentMap,
					topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
					kingdomCategoryMap);
			response.getContentData().add(contentElement);
		}
		return Response.success(response);
	}

	public Response<AcKingdomListResponse> acKingdomList(long uid, long ceTopicId, int resultType, int page) {
		AcKingdomListResponse response = new AcKingdomListResponse();

		List<Content> contentList = null;
		List<ExtAcImage> acImageList = null;

		response.setAcCount(topicDao.countAggregationSubTopic(ceTopicId));
		if (resultType == 0) {
			contentList = extContentMapper.getAcKingdomList(ceTopicId, page, 20);
			if(contentList==null || contentList.size()==0){
				return Response.success(response);
			}
			List<Long> uidList = new ArrayList<Long>();
			List<Long> topicIdList = new ArrayList<Long>();
			List<Long> forwardTopicIdList = new ArrayList<Long>();
			for (Content idx : contentList) {
				if (!uidList.contains(idx.getUid())) {
					uidList.add(idx.getUid());
				}
				if (idx.getType() == Specification.ArticleType.LIVE.index
						|| idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {// 王国
					if (!topicIdList.contains(idx.getForwardCid())) {
						topicIdList.add(idx.getForwardCid());
					}
					if (idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {
						if (!forwardTopicIdList.contains(idx.getForwardCid())) {
							forwardTopicIdList.add(idx.getForwardCid());
						}
					}
				}
			}

			// 是否显示标签信息
			String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
			int isShowTags = 0;
			if (!StringUtils.isEmpty(isShowTagsStr)) {
				isShowTags = Integer.parseInt(isShowTagsStr);
			}

			int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
			Set<Integer> rightDigs = new HashSet<>();
			while (likeBtnRatio > rightDigs.size()) { // 随机序列。
				rightDigs.add(RandomUtils.nextInt(0, 101));
			}
			int maxButtonCount = 2; // 最多显示按钮数量

			// 一次性查询所有王国信息
			Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

			// 一次性获取王国的外露内容
			Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
					topicIdList);

			// 一次性查询所有好友关系
			Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, uid);

			// 一次性查出所有的用户信息
			Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

			// 一次性查询关注信息
			Map<String, String> followMap = baseService.getAllUserFollowList(uidList, uid);

			// 一次性查询王国的最后一条更新记录
			Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

			// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
			Map<String, Long> topicCountMap = new HashMap<String, Long>();
			Map<String, Long> reviewCountMap = new HashMap<String, Long>();
			Map<String, String> liveFavouriteMap = new HashMap<String, String>();
			Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
			baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
					topicMemberCountMap);

			// 一次性查询王国的标签信息
			Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

			// 一次性查出所有分类信息
			Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();

			for (Content content : contentList) {
				ExtOutContent contentElement = baseService.handleContentForOut(content, uid, maxButtonCount, isShowTags,
						topicMap, rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap, lastFragmentMap,
						topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
						kingdomCategoryMap);
				response.getAcKingdomList().add(contentElement);
			}
		} else {
			acImageList = extContentMapper.getAcKingdomImageList(ceTopicId, page, 20);
			if(acImageList==null || acImageList.size()==0){
				return Response.success(response);
			}
			List<Long> uidList = new ArrayList<Long>();
			List<Long> idList = new ArrayList<Long>();
			for (ExtAcImage acImage : acImageList) {
				if (!uidList.contains(acImage.getUid())) {
					uidList.add(acImage.getUid());
				}
				if (!idList.contains(acImage.getId())) {
					idList.add(acImage.getId());
				}
			}
			List<Long> likeImageIdList = new ArrayList<Long>();
			if (idList.size() > 0) {
				likeImageIdList = extContentMapper.getAcKingdomImageLikeList(uid, idList);
			}
			// 一次性查出所有的用户信息
			Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);
			for (ExtAcImage acData : acImageList) {
				if (StringUtils.isNotEmpty(acData.getFragmentImage())) {
					acData.setFragmentImage(Constant.QINIU_DOMAIN + "/" + acData.getFragmentImage());
				}

				if (likeImageIdList.contains(acData.getId())) {
					acData.setIsLike(1);
				} else {
					acData.setIsLike(0);
				}
				ExtUserProfile userProfile = userProfileMap.get(acData.getUid());
				if (userProfile != null) {
					acData.setNickName(userProfile.getNickName());
					acData.setV_lv(userProfile.getvLv());
					acData.setLevel(userProfile.getLevel());
					acData.setAvatar(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatar());
				}
				response.getAcImageList().add(acData);
			}
		}
		return Response.success(response);
	}

	public Response<MyPublishByTypeResponse> myPublishByType(long uid, int type, long updateTime, long currentUid) {
		MyPublishByTypeResponse response = new MyPublishByTypeResponse();
		List<Content> contentList = null;
		if (type == 3) {// 我的王国（自己是国王的）
			contentList = extContentMapper.getMyOwnKingdom(uid, updateTime);
		} else if (type == 4) {// 我加入的王国（我是核心圈的，以及我加入的）
			contentList = extContentMapper.loadMyJoinKingdom(uid, updateTime);
		} else {
			contentList = new ArrayList<Content>();
			return Response.success(response);
		}
		if(contentList==null || contentList.size()==0){
			contentList = new ArrayList<Content>();
			return Response.success(response);
		}
		List<Long> uidList = new ArrayList<Long>();
		List<Long> topicIdList = new ArrayList<Long>();
		List<Long> forwardTopicIdList = new ArrayList<Long>();
		for (Content idx : contentList) {
			if (!uidList.contains(idx.getUid())) {
				uidList.add(idx.getUid());
			}
			if (idx.getType() == Specification.ArticleType.LIVE.index
					|| idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {// 王国
				if (!topicIdList.contains(idx.getForwardCid())) {
					topicIdList.add(idx.getForwardCid());
				}
				if (idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {
					if (!forwardTopicIdList.contains(idx.getForwardCid())) {
						forwardTopicIdList.add(idx.getForwardCid());
					}
				}
			}
		}

		// 是否显示标签信息
		String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
		int isShowTags = 0;
		if (!StringUtils.isEmpty(isShowTagsStr)) {
			isShowTags = Integer.parseInt(isShowTagsStr);
		}

		int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
		Set<Integer> rightDigs = new HashSet<>();
		while (likeBtnRatio > rightDigs.size()) { // 随机序列。
			rightDigs.add(RandomUtils.nextInt(0, 101));
		}
		int maxButtonCount = 2; // 最多显示按钮数量

		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
				topicIdList);

		// 一次性查询所有好友关系
		Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, currentUid);

		// 一次性查出所有的用户信息
		Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

		// 一次性查询关注信息
		Map<String, String> followMap = baseService.getAllUserFollowList(uidList, currentUid);

		// 一次性查询王国的最后一条更新记录
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

		// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
		baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
				topicMemberCountMap);

		// 一次性查询王国的标签信息
		Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

		// 一次性查出所有分类信息
		Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();

		for (Content content : contentList) {
			ExtOutContent contentElement = baseService.handleContentForOut(content, currentUid, maxButtonCount,
					isShowTags, topicMap, rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap,
					lastFragmentMap, topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
					kingdomCategoryMap);
			response.getMyPublishElements().add(contentElement);
		}
		return Response.success(response);
	}

	public Response<NewestResponse> newest(long sinceId, long uid) {
		NewestResponse response = new NewestResponse();

		List<Long> blacklistUids = extUserMapper.getBlacklist(uid);

		List<Content> contentList = extContentMapper.loadNewestContent(uid, sinceId, blacklistUids);

		if(contentList ==null|| contentList.size()==0){
			return Response.success(response);
		}
		List<Long> uidList = new ArrayList<Long>();
		List<Long> topicIdList = new ArrayList<Long>();
		List<Long> forwardTopicIdList = new ArrayList<Long>();
		for (Content idx : contentList) {
			if (!uidList.contains(idx.getUid())) {
				uidList.add(idx.getUid());
			}
			if (idx.getType() == Specification.ArticleType.LIVE.index
					|| idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {// 王国
				if (!topicIdList.contains(idx.getForwardCid())) {
					topicIdList.add(idx.getForwardCid());
				}
				if (idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {
					if (!forwardTopicIdList.contains(idx.getForwardCid())) {
						forwardTopicIdList.add(idx.getForwardCid());
					}
				}
			}
		}
		// 是否显示标签信息
		String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
		int isShowTags = 0;
		if (!StringUtils.isEmpty(isShowTagsStr)) {
			isShowTags = Integer.parseInt(isShowTagsStr);
		}
		int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
		Set<Integer> rightDigs = new HashSet<>();
		while (likeBtnRatio > rightDigs.size()) { // 随机序列。
			rightDigs.add(RandomUtils.nextInt(0, 101));
		}
		int maxButtonCount = 2; // 最多显示按钮数量

		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
				topicIdList);

		// 一次性查询所有好友关系
		Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, uid);

		// 一次性查出所有的用户信息
		Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

		// 一次性查询关注信息
		Map<String, String> followMap = baseService.getAllUserFollowList(uidList, uid);

		// 一次性查询王国的最后一条更新记录
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

		// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
		baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
				topicMemberCountMap);

		// 一次性查询王国的标签信息
		Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

		// 一次性查出所有分类信息
		Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();

		for (Content content : contentList) {
			ExtOutContent contentElement = baseService.handleContentForOut(content, uid, maxButtonCount, isShowTags,
					topicMap, rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap, lastFragmentMap,
					topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
					kingdomCategoryMap);
			response.getNewestData().add(contentElement);
		}
		return Response.success(response);
	}

	public Response<TagDetailResponse> tagDetail(long uid, long tagId, String tagName, int page) {
		if (page < 1) {
			page = 1;
		}
		TagDetailResponse response = new TagDetailResponse();
		List<Long> blacklistUids = extUserMapper.getBlacklist(uid);
		TopicTag topicTag = null;
		if (tagId == 0) {
			topicTag = topicTagDao.getTopicTagByTag(tagName);
		} else {
			topicTag = topicTagDao.getTopicTagById(tagId);
		}
		if (topicTag == null) {
			return Response.failure(500, "找不到该标签信息");
		}
		tagId = topicTag.getId();
		tagName = topicTag.getTag();
		// 广告位位置信息
		Map<String, List<Map<String, String>>> adPositionMap = new HashMap<String, List<Map<String, String>>>();
		if (page == 1) {
			List<Content> listTagTopic = extContentMapper.getContentByTagId(tagId, 5);
			for (Content tagContent : listTagTopic) {
				TagDetailResponse.CoverElement cover = new TagDetailResponse.CoverElement();
				cover.setType(3);
				cover.setTitle(tagContent.getContent() == null ? "" : tagContent.getContent());
				cover.setCid(tagContent.getId());
				cover.setTopicId(tagContent.getForwardCid());
				cover.setCoverImage(Constant.QINIU_DOMAIN + "/" + tagContent.getConverImage());
				response.getCoverList().add(cover);
				if (response.getCoverList().size() == 5) {
					break;
				}
			}
			if (response.getCoverList().size() < 3) {
				response.getCoverList().clear();
			}

			// 处理广告位
			List<ExtAdBanner> listAdBanner = extTagMapper.getAdBannerByTagId(tagId);
			for (int i = 0; i < listAdBanner.size(); i++) {
				ExtAdBanner adBanner = listAdBanner.get(i);
				int s = adBanner.getPosition();
				if (adPositionMap.get(String.valueOf(s)) == null) {
					List<Map<String, String>> adBHWList = new ArrayList<Map<String, String>>();
					Map<String, String> adBHWMap = new HashMap<String, String>();
					adBHWMap.put("ad_banner_id", String.valueOf(adBanner.getBannerId()));
					adBHWMap.put("ad_banner_height", String.valueOf(adBanner.getAdBannerHeight()));
					adBHWMap.put("ad_banner_width", String.valueOf(adBanner.getAdBannerWidth()));
					adBHWList.add(adBHWMap);
					adPositionMap.put(String.valueOf(s), adBHWList);
				} else {
					Map<String, String> adBHWMap = new HashMap<String, String>();
					adBHWMap.put("ad_banner_id", String.valueOf(adBanner.getBannerId()));
					adBHWMap.put("ad_banner_height", String.valueOf(adBanner.getAdBannerHeight()));
					adBHWMap.put("ad_banner_width", String.valueOf(adBanner.getAdBannerWidth()));
					adPositionMap.get(String.valueOf(s)).add(adBHWMap);
				}
			}
		}
		response.setTagId(tagId);
		response.setTagName(tagName);
		UserTag userTag = topicTagDao.getUserTagByUidAndTagid(uid, tagId);
		if (userTag == null) {
			response.setIsLike(0);
		} else {
			response.setIsLike(userTag.getType());
		}
		int pageSize = 20;
		List<Content> contents = extContentMapper.getTagTopicList(tagId, blacklistUids, page, pageSize);
		List<Long> uidList = new ArrayList<Long>();
		List<Long> topicIdList = new ArrayList<Long>();
		List<Long> forwardTopicIdList = new ArrayList<Long>();
		for (Content idx : contents) {
			if (!uidList.contains(idx.getUid())) {
				uidList.add(idx.getUid());
			}
			if (idx.getType() == Specification.ArticleType.LIVE.index
					|| idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {// 王国
				if (!topicIdList.contains(idx.getForwardCid())) {
					topicIdList.add(idx.getForwardCid());
				}
				if (idx.getType() == Specification.ArticleType.FORWARD_LIVE.index) {
					if (!forwardTopicIdList.contains(idx.getForwardCid())) {
						forwardTopicIdList.add(idx.getForwardCid());
					}
				}
			}
		}
		// 是否显示标签信息
		String isShowTagsStr = appConfigService.getAppConfigByKey("IS_SHOW_TAGS");
		int isShowTags = 0;
		if (!StringUtils.isEmpty(isShowTagsStr)) {
			isShowTags = Integer.parseInt(isShowTagsStr);
		}

		int likeBtnRatio = appConfigService.getIntegerAppConfigByKey("LIKE_BUTTON_APPEAR_RATIO");
		Set<Integer> rightDigs = new HashSet<>();
		while (likeBtnRatio > rightDigs.size()) { // 随机序列。
			rightDigs.add(RandomUtils.nextInt(0, 101));
		}
		int maxButtonCount = 2; // 最多显示按钮数量

		// 一次性查询所有王国信息
		Map<String, Topic> topicMap = baseService.getAllTopicList(topicIdList);

		// 一次性获取王国的外露内容
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = baseService.getAllTopicOutList(uidList,
				topicIdList);

		// 一次性查询所有好友关系
		Map<String, UserFriend> userFriendMap = baseService.getAllUserFriendList(uidList, uid);

		// 一次性查出所有的用户信息
		Map<String, ExtUserProfile> userProfileMap = baseService.getAllProfileList(uidList);

		// 一次性查询关注信息
		Map<String, String> followMap = baseService.getAllUserFollowList(uidList, uid);

		// 一次性查询王国的最后一条更新记录
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = baseService.getAllLastFragmentList(topicIdList);

		// 一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
		Map<String, Long> topicCountMap = new HashMap<String, Long>();
		Map<String, Long> reviewCountMap = new HashMap<String, Long>();
		Map<String, String> liveFavouriteMap = new HashMap<String, String>();
		Map<String, Long> topicMemberCountMap = new HashMap<String, Long>();
		baseService.getAllRelevantInfoList(topicIdList, uid, topicCountMap, reviewCountMap, liveFavouriteMap,
				topicMemberCountMap);

		// 一次性查询王国的标签信息
		Map<String, String> topicTagMap = baseService.getAllTopicTagList(topicIdList);

		// 一次性查出所有分类信息
		Map<String, TopicCategory> kingdomCategoryMap = baseService.getAllKcList();
		for (int j = 0; j < contents.size(); j++) {
			if (page == 1) {
				if (adPositionMap.get(String.valueOf(j)) != null) {
					List<Map<String, String>> list = adPositionMap.get(String.valueOf(j));
					for (int k = 0; k < list.size(); k++) {
						TagDetailResponse.AdElement adElement = new TagDetailResponse.AdElement();
						Map<String, String> map1 = (Map<String, String>) list.get(k);
						adElement.setCid(Long.parseLong((String) map1.get("ad_banner_id")));
						adElement.setH(Integer.valueOf((String) map1.get("ad_banner_height")));
						adElement.setW(Integer.valueOf((String) map1.get("ad_banner_width")));
						adElement.setType(12);
						response.getTagKingdomList().add(adElement);
					}
				}
			}
			Content content = contents.get(j);
			ExtOutContent contentElement = baseService.handleContentForOut(content, uid, maxButtonCount, isShowTags,
					topicMap, rightDigs, topicOutDataMap, userFriendMap, userProfileMap, followMap, lastFragmentMap,
					topicCountMap, reviewCountMap, liveFavouriteMap, topicMemberCountMap, topicTagMap,
					kingdomCategoryMap);
			response.getTagKingdomList().add(contentElement);
		}
		if (page == 1) {// 第一页记录下点击标签的操作记录
			userDao.addUserOprationLog(uid, USER_OPRATE_TYPE.HIT_TAG, tagName);
		}
		return Response.success(response);
	}

	public Response<EmojiPackDetailResponse> emojiPackageDetail(int packageId) {
		EmotionPack pack = emotionPackMapper.selectByPrimaryKey(packageId);
		if (null == pack) {
			return Response.failure(500, "表情包不存在");
		}

		EmojiPackDetailResponse response = new EmojiPackDetailResponse();
		response = ContentCopier.INSTANCE.asEmojiPackDetailResponse4EmotionPack(pack);
		response.setPackageCover(Constant.QINIU_DOMAIN + "/" + pack.getCover());
		List<EmotionPackDetail> detailList = contentDao.getEmotionPackDetailListByPid(packageId);
		EmojiPackDetailResponse.PackageDetailData data = null;
		for (EmotionPackDetail detail : detailList) {
			data = new EmojiPackDetailResponse.PackageDetailData();
			data = ContentCopier.INSTANCE.asPackageDetailData4EmotionPackDetail(detail);
			data.setImage(Constant.QINIU_DOMAIN + "/" + detail.getImage());
			data.setThumb(Constant.QINIU_DOMAIN + "/" + detail.getThumb());
			data.setContent(detail.getExtra());
			response.getEmojiData().add(data);
		}
		return Response.success(response);
	}

	public Response<EmojiPackageQueryResponse> emojiPackageQuery() {
		EmojiPackageQueryResponse response = new EmojiPackageQueryResponse();
		List<EmotionPack> packList = contentDao.getEmotionPackList();
		EmojiPackageQueryResponse.PackageData pdata = null;
		for (EmotionPack pack : packList) {
			pdata = new EmojiPackageQueryResponse.PackageData();
			pdata = ContentCopier.INSTANCE.asPackageData4EmotionPack(pack);
			pdata.setCover(Constant.QINIU_DOMAIN + "/" + pack.getCover());
			response.getPackageData().add(pdata);
		}
		return Response.success(response);
	}
}
