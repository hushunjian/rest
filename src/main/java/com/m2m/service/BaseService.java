package com.m2m.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.m2m.Constant;
import com.m2m.copier.ContentCopier;
import com.m2m.copier.TopicCopier;
import com.m2m.dao.TopicDao;
import com.m2m.dao.TopicTagDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.Content;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicCategory;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.TopicTag;
import com.m2m.domain.TopicTagDetail;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserFriend;
import com.m2m.entity.ExtKingdomRelevantInfo;
import com.m2m.entity.ExtOutContent;
import com.m2m.entity.ExtOutData;
import com.m2m.entity.ExtTagOutInfo;
import com.m2m.entity.ExtUserProfile;
import com.m2m.mapper.ExtContentMapper;
import com.m2m.mapper.ExtTopicFragmentMapper;
import com.m2m.mapper.ExtUserMapper;
import com.m2m.web.Specification;

@Service
public class BaseService {
	
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TopicTagDao topicTagDao;
	@Autowired
	private ExtContentMapper extContentMapper;
	@Autowired
	private ExtUserMapper extUserMapper;
	@Autowired
	private ExtTopicFragmentMapper extTopicFragmentMapper;
	
	
	private static final String POWER_KEY = "power:key";
	/**
	 * 判断用户核心圈身份
	 * @param topic
	 * @param uid
	 * @return
	 */
	public int getInternalStatus(Topic topic, long uid) {
		int internalStatus = 0;
		String coreCircle = topic.getCoreCircle();
		if (null != coreCircle) {
			JSONArray array = JSON.parseArray(coreCircle);
			for (int i = 0; i < array.size(); i++) {
				if (array.getLong(i).longValue() == uid) {
					internalStatus = Specification.SnsCircle.CORE.index;
					break;
				}
			}
		}
		return internalStatus;
	}

	/**
	 * 是否是国王
	 * @param uid
	 * @param topicUid
	 * @return
	 */
	public boolean isKing(long uid, long topicUid) {
		boolean result = false;
		if (uid == topicUid) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否在核心圈里面
	 * 
	 * @author zhangjiwei
	 * @date May 10, 2017
	 * @param uid
	 * @param coreCircle
	 * @return
	 */
	public boolean isInCore(long uid, String coreCircle) {
		boolean result = false;
		if (null != coreCircle && !"".equals(coreCircle)) {
			JSONArray array = JSON.parseArray(coreCircle);
			for (int i = 0; i < array.size(); i++) {
				if (array.getLong(i).longValue() == uid) {
					result = true;
					break;
				}
			}
		}
		return result;
	} 

	/**
	 * 米汤币兑换人名币
	 * 
	 * @param price
	 * @return
	 */
	public double exchangeKingdomPrice(int price) {
		String result = appConfigService.getAppConfigByKey("EXCHANGE_RATE");
		if (StringUtils.isEmpty(result)) {
			return 0;
		}
		BigDecimal exchangeRate = new BigDecimal(result);
		return new BigDecimal(price).divide(exchangeRate, 2, RoundingMode.HALF_UP).doubleValue();
	}
	/**
	 * 米汤币兑换人名币
	 * 
	 * @param price
	 * @return
	 */
	public double exchangeKingdomPrice(int price,int exchangeRate) {
		BigDecimal exchangeRateBigDecimal = new BigDecimal(exchangeRate);
		return new BigDecimal(price).divide(exchangeRateBigDecimal, 2, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * 头像处理,为空的就直接返回默认头像
	 * @param avatar
	 * @return
	 */
	public String genAvatar(String avatar) {
		if (StringUtils.isEmpty(avatar)) {
			avatar = Constant.DEFAULT_AVATAR;
		}
		return Constant.QINIU_DOMAIN + "/" + avatar;
	}
	
	/**
	 * 处理七牛图片地址
	 * @param qiNiuKey
	 * @return
	 */
	public String genQiNiuImageUrl(String qiNiuKey){
		if (StringUtils.isEmpty(qiNiuKey)) {
			return "";
		}else{
			return Constant.QINIU_DOMAIN + "/" + qiNiuKey;
		}
	}
	
	/**
	 * 判断是否是管理员
	 * @param uid
	 * @return
	 */
	public boolean isAdmin(long uid) {
        Set<String> admins = redisService.smembers(POWER_KEY);
        return admins.contains(uid+"");
    }
	
	/**
	 * 一次性查询所有王国信息
	 * @param topicIdList
	 * @return
	 */
	public Map<String, Topic> getAllTopicList(List<Long> topicIdList) {
		Map<String, Topic> topicMap = new HashMap<String, Topic>();
		if(topicIdList!=null && topicIdList.size()>0){
		List<Topic> topicList = topicDao.getTopicListByIds(topicIdList);
		if (null != topicList && topicList.size() > 0) {
			Long topicId = null;
			for (Topic topic : topicList) {
				topicId = topic.getId();
				topicMap.put(topicId.toString(), topic);
			}
		}
		}
		return topicMap;
	}
	
	/**
	 * 一次性获取王国的外露内容
	 * @param uidList
	 * @param topicIdList
	 * @return
	 */
	public Map<String, List<TopicFragmentWithBLOBs>> getAllTopicOutList(List<Long> uidList,List<Long> topicIdList) {
		Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap = new HashMap<String, List<TopicFragmentWithBLOBs>>();
		String v = appConfigService.getAppConfigByKey("KINGDOM_OUT_MINUTE");
		int limitMinute = 3;
		if (!StringUtils.isEmpty(v)) {
			limitMinute = Integer.valueOf(v).intValue();
		}
		if(topicIdList!=null && topicIdList.size()>0){
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
		}
		return topicOutDataMap;
	}
	
	/**
	 * 一次性查询所有好友关系
	 * @param uidList
	 * @param uid
	 * @return
	 */
	public Map<String, UserFriend> getAllUserFriendList(List<Long> uidList,long uid) {
		Map<String, UserFriend> userFriendMap = new HashMap<String, UserFriend>();
		if(uidList==null || uidList.size()==0){
			return userFriendMap;
		}
		List<UserFriend> userFriendList = userDao.getUserFriendBySourceUidListAndTargetUid(uidList, uid);
		if (null != userFriendList && userFriendList.size() > 0) {
			for (UserFriend up : userFriendList) {
				userFriendMap.put(up.getSourceUid().toString(), up);
				if (up.getFromUid()!=0 && !uidList.contains(up.getFromUid())) {
					uidList.add(up.getFromUid());
				}
			}
		}
		return userFriendMap;
	}
	
	/**
	 * 一次性查出所有的用户信息
	 * @param uidList
	 * @return
	 */
	public Map<String, ExtUserProfile> getAllProfileList(List<Long> uidList) {
		Map<String, ExtUserProfile> userProfileMap = new HashMap<String, ExtUserProfile>();
		if(uidList==null || uidList.size()==0){
			return userProfileMap;
		}
		List<ExtUserProfile> profileList = extUserMapper.getExtUserProfileListByIds(uidList);
		if (null != profileList && profileList.size() > 0) {
			for (ExtUserProfile up : profileList) {
				userProfileMap.put(up.getUid().toString(), up);
			}
		}
		return userProfileMap;
	}
	
	/**
	 * 一次性查询关注信息
	 * @param uidList
	 * @param uid
	 * @return
	 */
	public Map<String, String> getAllUserFollowList(List<Long> uidList,long uid) {
		Map<String, String> followMap = new HashMap<String, String>();
		if(uidList==null || uidList.size()==0){
			return followMap;
		}
		List<UserFollow> userFollowList = userDao.getAllFollows(uid, uidList);
		if (null != userFollowList && userFollowList.size() > 0) {
			for (UserFollow uf : userFollowList) {
				followMap.put(uf.getSourceUid() + "_" + uf.getTargetUid(), "1");
			}
		}
		return followMap;
	}
	/**
	 * 一次性查询王国的最后一条更新记录
	 * @param topicIdList
	 * @return
	 */
	public Map<String, TopicFragmentWithBLOBs> getAllLastFragmentList(List<Long> topicIdList) {
		Map<String, TopicFragmentWithBLOBs> lastFragmentMap = new HashMap<String, TopicFragmentWithBLOBs>();
		if (null != topicIdList && topicIdList.size() > 0) {
			List<TopicFragmentWithBLOBs> lastFragmentList = extTopicFragmentMapper.getLastFragmentByTopicIds(topicIdList);
			if (null != lastFragmentList && lastFragmentList.size() > 0) {
				for (TopicFragmentWithBLOBs lf : lastFragmentList) {
					lastFragmentMap.put(lf.getTopicId().toString(), lf);
				}
			}
		}
		return lastFragmentMap;
	}

	/**
	 *  一次性查出所有王国的更新数、评论数、王国订阅状态、王国成员数
	 * @param topicIdList
	 * @param uid
	 * @param topicCountMap
	 * @param reviewCountMap
	 * @param liveFavouriteMap
	 * @param topicMemberCountMap
	 */
	public void getAllRelevantInfoList(List<Long> topicIdList, long uid, Map<String, Long> topicCountMap,
			Map<String, Long> reviewCountMap, Map<String, String> liveFavouriteMap,
			Map<String, Long> topicMemberCountMap) {
		if (null != topicIdList && topicIdList.size() > 0) {
			List<ExtKingdomRelevantInfo> relevantInfoList = extContentMapper.getKingdomRelevantInfo(uid, topicIdList);
			if (null != relevantInfoList && relevantInfoList.size() > 0) {
				for (ExtKingdomRelevantInfo r : relevantInfoList) {
					Long topicId = r.getTopicId();
					long topicCount = r.getTopicCount();
					long reviewCount = r.getReviewCount();
					long favoriteCount = r.getFavoriteCount();
					long nonCoreCount = r.getNonCoreCount();
					long coreCount = r.getCoreCount();
					topicCountMap.put(topicId.toString(), Long.valueOf(topicCount));
					reviewCountMap.put(topicId.toString(), Long.valueOf(reviewCount));
					if (favoriteCount > 0) {
						liveFavouriteMap.put(topicId.toString(), "1");
					}
					topicMemberCountMap.put(topicId.toString(), Long.valueOf(nonCoreCount + coreCount));
				}
			}
		}
	}
	/**
	 * 一次性查询王国的标签信息
	 * @param topicIdList
	 * @return
	 */
	public Map<String, String>  getAllTopicTagList(List<Long> topicIdList){
		Map<String, String> topicTagMap = new HashMap<String, String>();
		List<TopicTagDetail> topicTagList = topicTagDao.getTopicTagDetailListByTopicIds(topicIdList);
		if (null != topicTagList && topicTagList.size() > 0) {
			long tid = 0;
			String tags = null;
			Long topicId = null;
			for (TopicTagDetail ttd : topicTagList) {
				topicId = ttd.getTopicId();
				if (topicId.longValue() != tid) {
					// 先插入上一次
					if (tid > 0 && !StringUtils.isEmpty(tags)) {
						topicTagMap.put(String.valueOf(tid), tags);
					}
					// 再初始化新的
					tid = topicId.longValue();
					tags = null;
				}
				if (tags != null) {
					tags = tags + ";" + ttd.getTag();
				} else {
					tags = ttd.getTag();
				}
			}
			if (tid > 0 && !StringUtils.isEmpty(tags)) {
				topicTagMap.put(String.valueOf(tid), tags);
			}
		}
		return topicTagMap;
	}
	/**
	 * 一次性查出所有分类信息
	 * @return
	 */
	public Map<String, TopicCategory>  getAllKcList(){
		Map<String, TopicCategory> kingdomCategoryMap = new HashMap<String, TopicCategory>();
		List<TopicCategory> kcList = topicDao.getAllTopicCategory();
		if (null != kcList && kcList.size() > 0) {
			for (TopicCategory m : kcList) {
				kingdomCategoryMap.put(String.valueOf(m.getId()), m);
			}
		}
		return kingdomCategoryMap;
	}
	/** 
	 * 一次性查询所有点赞信息
	 * @param contentIds
	 * @param uid
	 * @return
	 */
	public Map<String, String>  getAllLikeList(List<Long> contentIds,long uid){
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
		return likeMap;
	}
	/**
	 * 一次性查询所有标签信息
	 * @param contentIds
	 * @param uid
	 * @return
	 */
	public Map<String, TopicTag>  getAllTopicTag(List<Long> tagIds){
		Map<String, TopicTag> tagMap = new HashMap<String, TopicTag>();
		if (tagIds.size() > 0) {
		List<TopicTag> tagList = topicTagDao.getTopicTagListByIds(tagIds);
		if (null != tagList && tagList.size() > 0) {
			for (TopicTag up : tagList) {
				tagMap.put(String.valueOf(up.getId()), up);
			}
		}
		}
		return tagMap;
	}
	/**
	 * 一次性查询所有标签外露信息
	 * @param contentIds
	 * @param uid
	 * @return
	 */
	public Map<String, List<ExtTagOutInfo>>  getAllTagOut(List<Long> tagIds,List<Long> uidList){
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
		return tagOutDataMap;
	}
	
	
	/**
	 * 处理外露信息
	 * @param c
	 * @param uid
	 * @param maxButtonCount
	 * @param isShowTags
	 * @param topicMap
	 * @param rightDigs
	 * @param topicOutDataMap
	 * @param userFriendMap
	 * @param userProfileMap
	 * @param followMap
	 * @param lastFragmentMap
	 * @param topicCountMap
	 * @param reviewCountMap
	 * @param liveFavouriteMap
	 * @param topicMemberCountMap
	 * @param topicTagMap
	 * @param kingdomCategoryMap
	 * @return
	 */
	public ExtOutContent handleContentForOut(Content c, long uid,int maxButtonCount,int isShowTags,Map<String, Topic> topicMap,Set<Integer> rightDigs,
			Map<String, List<TopicFragmentWithBLOBs>> topicOutDataMap, Map<String, UserFriend> userFriendMap,
			Map<String, ExtUserProfile> userProfileMap, Map<String, String> followMap,
			Map<String, TopicFragmentWithBLOBs> lastFragmentMap, Map<String, Long> topicCountMap,
			Map<String, Long> reviewCountMap, Map<String, String> liveFavouriteMap,
			Map<String, Long> topicMemberCountMap, Map<String, String> topicTagMap,
			Map<String, TopicCategory> kingdomCategoryMap) {
		Topic topic = null;
		ExtUserProfile userProfile = null;
		TopicFragmentWithBLOBs lastFragment = null;
		List<TopicFragmentWithBLOBs> topicOutDataList = null;
		TopicFragmentWithBLOBs topicOutData = null;
		ExtUserProfile lastUserProfile = null;
		ExtUserProfile atUserProfile = null;
		String lastFragmentImage = null;
		ExtOutContent contentElement = new ExtOutContent();
		contentElement = ContentCopier.INSTANCE.asExtOutContent4Content(c);
		contentElement.setCid(c.getId());
		if (c.getConverImage() != null && c.getConverImage().length() > 0) {
			contentElement.setCoverImage(Constant.QINIU_DOMAIN + "/" + c.getConverImage());
		}
		if (c.getType() == Specification.ArticleType.LIVE.index) {
			contentElement.setContent(c.getTitle());
			contentElement.setTopicId(c.getForwardCid());
			topic = topicMap.get(c.getForwardCid().toString());
			if (null != topic) {
				// 私密王国
				if (topic.getRights() == Specification.KingdomRights.PRIVATE_KINGDOM.index) {
					contentElement.setRights(Specification.KingdomRights.PRIVATE_KINGDOM.index);
					// 当前用户是否可见
					if (isInCore(uid, topic.getCoreCircle())) {
						contentElement.setCanView(Specification.CanViewStatus.CAN_VIEW.index);
					} else {
						contentElement.setCanView(Specification.CanViewStatus.NOT_CAN_VIEW.index);
					}
				} else {
					contentElement.setRights(Specification.KingdomRights.PUBLIC_KINGDOM.index);
					contentElement.setCanView(Specification.CanViewStatus.CAN_VIEW.index);
				}
				contentElement.setContentType(topic.getType());
				int internalStatust = getInternalStatus(topic, uid);
				if (internalStatust == Specification.SnsCircle.OUT.index) {
					if (liveFavouriteMap.get(String.valueOf(topic.getId())) != null) {
						internalStatust = Specification.SnsCircle.IN.index;
					}
				}
				contentElement.setFinalUpdateTime(new Date(topic.getLongTime()));
				contentElement.setUpdateTime(new Date(topic.getLongTime()));
				contentElement.setLastUpdateTime(topic.getLongTime());
				contentElement.setInternalStatus(internalStatust);
				contentElement.setPrice(topic.getPrice());
				contentElement.setShowPriceBrand(0);
				contentElement.setOnlyFriend(topic.getOnlyFriend());
				if (userFriendMap.get(String.valueOf(topic.getUid())) != null) {
					contentElement.setIsFriend2King(1);
				}
				int kcid = topic.getCategoryId();
				if (kcid > 0) {
					TopicCategory kingdomCategory = kingdomCategoryMap.get(String.valueOf(kcid));
					if (null != kingdomCategory) {
						contentElement.setKcid(kingdomCategory.getId());
						contentElement.setKcName(kingdomCategory.getName());
						String kcImage = kingdomCategory.getCoverImg();
						if (!StringUtils.isEmpty(kcImage)) {
							contentElement.setKcImage(Constant.QINIU_DOMAIN + "/" + kcImage);
						}
						String kcIcon = kingdomCategory.getIcon();
						if (!StringUtils.isEmpty(kcIcon)) {
							contentElement.setKcIcon(Constant.QINIU_DOMAIN + "/" + kcIcon);
						}
					}
				}
				// 增加王国外露内容
				topicOutDataList = topicOutDataMap.get(c.getForwardCid().toString());
				if (null != topicOutDataList && topicOutDataList.size() > 0) {
					// 先判断是否UGC
					// 第一个如果是UGC则其他的不要了，如果不是，则后面如果有UGC则不要了
					topicOutData = topicOutDataList.get(0);
					lastUserProfile = userProfileMap.get(String.valueOf(topicOutData.getUid()));
					if (null != lastUserProfile) {// 这里放上最近发言的那个人的头像
						contentElement.setUid(lastUserProfile.getUid());
					}
					int type = topicOutData.getType();
					int contentType = topicOutData.getContentType();
					if ((type == 0 || type == 52) && contentType == 23) {// 第一个是UGC
						/*ExtOutData outElement = new ExtOutData();
						outElement = TopicCopier.INSTANCE.asExtOutData(topicOutData);
						String fragmentImage = topicOutData.getFragmentImage();
						if (!StringUtils.isEmpty(fragmentImage)) {
							outElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + fragmentImage);
						}
						outElement.setAtUid(topicOutData.getAtUid());
						if (outElement.getAtUid() > 0) {
							atUserProfile = userProfileMap.get(String.valueOf(outElement.getAtUid()));
							if (null != atUserProfile) {
								outElement.setAtNickName(atUserProfile.getNickName());
							}
						}
						contentElement.getUgcData().add(outElement);*/
					} else {// 第一个不是UGC
						for (int j = 0; j < topicOutDataList.size(); j++) {
							topicOutData = topicOutDataList.get(j);
							type = topicOutData.getType();
							contentType = topicOutData.getContentType();
							ExtOutData outElement = new ExtOutData();
							outElement = TopicCopier.INSTANCE.asExtOutData(topicOutData);
							String fragmentImage = topicOutData.getFragmentImage();
							if (!StringUtils.isEmpty(fragmentImage)) {
								outElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + fragmentImage);
							}
							if (outElement.getAtUid() > 0) {
								atUserProfile = userProfileMap.get(String.valueOf(outElement.getAtUid()));
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
			lastFragment = lastFragmentMap.get(c.getForwardCid().toString());
			if (null != lastFragment) {
				contentElement.setLastType(lastFragment.getType());
				contentElement.setLastContentType(lastFragment.getContentType());
				contentElement.setLastFragment(lastFragment.getFragment());
				lastFragmentImage = lastFragment.getFragmentImage();
				if (!StringUtils.isEmpty(lastFragmentImage)) {
					contentElement.setLastFragmentImage(Constant.QINIU_DOMAIN + "/" + lastFragmentImage);
				}
				contentElement.setLastStatus(lastFragment.getStatus());
				contentElement.setLastExtra(lastFragment.getExtra());
			}
			if (null == topicMemberCountMap.get(c.getForwardCid().toString())) {
				contentElement.setFavoriteCount(1);// 默认只有国王一个成员
			} else {
				contentElement.setFavoriteCount(topicMemberCountMap.get(c.getForwardCid().toString()).intValue() + 1);
			}
			if (null != reviewCountMap.get(c.getForwardCid().toString())) {
				contentElement.setReviewCount(reviewCountMap.get(c.getForwardCid().toString()).intValue());
			} else {
				contentElement.setReviewCount(0);
			}
			if(uid == topic.getUid()){
				contentElement.setIsOwner(1);
			}else{
				contentElement.setIsOwner(0);
			}
			if (null != liveFavouriteMap.get(c.getForwardCid().toString()) || uid == topic.getUid()) {
				contentElement.setFavorite(1);
			} else {
				contentElement.setFavorite(0);
			}
			if (null != topicTagMap.get(c.getForwardCid().toString()) && isShowTags == 1) {
				contentElement.setTags(topicTagMap.get(c.getForwardCid().toString()));
			} else {
				contentElement.setTags("");
			}

			// 王国喜欢不喜欢
			if (maxButtonCount > 0 && contentElement.getInternalStatus() == 0) {// 自己加入的，自己是核心圈的，自己是国王的不出现喜欢不喜欢
				contentElement.setIsShowLikeButton(rightDigs.contains(RandomUtils.nextInt(0, 101)) ? 1 : 0);
				if (contentElement.getIsShowLikeButton() == 1) {
					maxButtonCount--;
				}
			} else {
				contentElement.setIsShowLikeButton(0);
			}
			userProfile = userProfileMap.get(String.valueOf(contentElement.getUid()));
			if (userProfile != null) {
				contentElement.setV_lv(userProfile.getvLv());
				contentElement.setAvatar(genAvatar(userProfile.getAvatar()));
				if (!StringUtils.isEmpty(userProfile.getAvatarFrame())) {
					contentElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
				}
				contentElement.setNickName(userProfile.getNickName());
				contentElement.setLevel(userProfile.getLevel());
				// 判断人员是否关注
				if (null != followMap.get(uid + "_" + contentElement.getUid())) {
					contentElement.setIsFollowed(1);
				} else {
					contentElement.setIsFollowed(0);
				}
				if (null != followMap.get(contentElement.getUid() + "_" + uid)) {
					contentElement.setIsFollowMe(1);
				} else {
					contentElement.setIsFollowMe(0);
				}
				contentElement.setIndustryId(userProfile.getIndustryId());
				contentElement.setIndustry(userProfile.getIndustry());
				if (userFriendMap.get(String.valueOf(userProfile.getUid())) != null) {
					contentElement.setIsFriend(1);
					UserFriend uf = userFriendMap.get(String.valueOf(userProfile.getUid()));
					if (uf.getFromUid() != 0) {
						ExtUserProfile fromExtUserProfile = userProfileMap.get(String.valueOf(uf.getFromUid()));
						if (fromExtUserProfile != null) {
							contentElement.setReason("来自" + fromExtUserProfile.getNickName());
						}
					}
				}
			}
		}
		return contentElement;
	}
}
