package com.m2m.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.m2m.domain.BlockTopicExample;
import com.m2m.domain.GiftHistory;
import com.m2m.domain.GiftHistoryExample;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.LiveFavoriteExample;
import com.m2m.domain.LotteryContentExample;
import com.m2m.domain.LotteryInfoExample;
import com.m2m.domain.LotteryWinExample;
import com.m2m.domain.TagTrainSampleExample;
import com.m2m.domain.TagTrainSampleWithBLOBs;
import com.m2m.domain.TeaseInfo;
import com.m2m.domain.TeaseInfoExample;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicAggregationExample;
import com.m2m.domain.TopicCategory;
import com.m2m.domain.TopicCategoryExample;
import com.m2m.domain.TopicData;
import com.m2m.domain.TopicDataExample;
import com.m2m.domain.TopicExample;
import com.m2m.domain.TopicFragmentExample;
import com.m2m.domain.TopicFragmentLikeHis;
import com.m2m.domain.TopicFragmentLikeHisExample;
import com.m2m.domain.TopicFragmentTemplate;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.TopicGiven;
import com.m2m.domain.TopicGivenExample;
import com.m2m.domain.TopicImage;
import com.m2m.domain.TopicImageExample;
import com.m2m.domain.TopicNews;
import com.m2m.domain.TopicNewsExample;
import com.m2m.domain.TopicReadHisExample;
import com.m2m.domain.TopicUserConfig;
import com.m2m.domain.TopicUserConfigExample;
import com.m2m.domain.TopicUserForbid;
import com.m2m.domain.TopicUserForbidExample;
import com.m2m.entity.ExtIdCount;
import com.m2m.mapper.BlockTopicMapper;
import com.m2m.mapper.ExtTopicMapper;
import com.m2m.mapper.GiftHistoryMapper;
import com.m2m.mapper.LiveFavoriteMapper;
import com.m2m.mapper.LotteryContentMapper;
import com.m2m.mapper.LotteryInfoMapper;
import com.m2m.mapper.LotteryWinMapper;
import com.m2m.mapper.TagTrainSampleMapper;
import com.m2m.mapper.TeaseInfoMapper;
import com.m2m.mapper.TopicAggregationMapper;
import com.m2m.mapper.TopicCategoryMapper;
import com.m2m.mapper.TopicDataMapper;
import com.m2m.mapper.TopicFragmentLikeHisMapper;
import com.m2m.mapper.TopicFragmentMapper;
import com.m2m.mapper.TopicFragmentTemplateMapper;
import com.m2m.mapper.TopicGivenMapper;
import com.m2m.mapper.TopicImageMapper;
import com.m2m.mapper.TopicMapper;
import com.m2m.mapper.TopicNewsMapper;
import com.m2m.mapper.TopicReadHisMapper;
import com.m2m.mapper.TopicUserConfigMapper;
import com.m2m.mapper.TopicUserForbidMapper;
import com.m2m.response.GetLiveByCidResponse.AcImageElement;
import com.m2m.web.Specification;


@Repository
public class TopicDao {

	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private ExtTopicMapper extTopicMapper;
	@Autowired
	private LiveFavoriteMapper liveFavoriteMapper;
	@Autowired
	private TopicAggregationMapper topicAggregationMapper;
	@Autowired
	private TopicUserForbidMapper topicUserForbidMapper;
	@Autowired
	private TopicFragmentMapper topicFragmentMapper;
	@Autowired
	private TopicUserConfigMapper topicUserConfigMapper;
	@Autowired
	private TagTrainSampleMapper tagTrainSampleMapper;
	@Autowired
	private TopicFragmentTemplateMapper topicFragmentTemplateMapper;
	@Autowired
	private TopicReadHisMapper topicReadHisMapper;
	@Autowired
	private TopicNewsMapper topicNewsMapper;
	@Autowired
	private TopicDataMapper topicDataMapper;
	@Autowired
	private GiftHistoryMapper giftHistoryMapper;
	@Autowired
	private TopicImageMapper topicImageMapper;
	@Autowired
	private TopicFragmentLikeHisMapper topicFragmentLikeHisMapper;
	@Autowired
	private TopicCategoryMapper topicCategoryMapper;
	@Autowired
	private TopicGivenMapper topicGivenMapper;
	@Autowired
	private BlockTopicMapper blockTopicMapper;
	@Autowired
	private LotteryInfoMapper lotteryInfoMapper;
	@Autowired
	private TeaseInfoMapper teaseInfoMapper;
	@Autowired
	private LotteryContentMapper lotteryContentMapper;
	@Autowired
    private LotteryWinMapper lotteryWinMapper;
	
	public List<TagTrainSampleWithBLOBs> getAllTagTrainSample(){
		TagTrainSampleExample example = new TagTrainSampleExample();
		return tagTrainSampleMapper.selectByExampleWithBLOBs(example);
	}
	
	public Topic getUserEmotionTopic(long uid){
		TopicExample example = new TopicExample();
		TopicExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andSubTypeEqualTo(Specification.TopicSubType.EMOTION.index);//情绪王国or生活记录
		List<Topic> list = topicMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
    public List<Topic> getTopicListByIds(List<Long> ids) {
    	if(ids.size()>0){
	        TopicExample example = new TopicExample();
	        TopicExample.Criteria criteria = example.createCriteria();
	        criteria.andIdIn(ids);
	        return topicMapper.selectByExample(example);
    	}else{
    		return new ArrayList<Topic>();
    	}
    }
    
    public List<Topic> getTopPricedKingdomList(int page, int pageSize, List<Long> blacklistUids){
    	TopicExample example = new TopicExample();
		TopicExample.Criteria criteria = example.createCriteria();
		if(null != blacklistUids && blacklistUids.size() > 0){
			criteria.andUidNotIn(blacklistUids);
		}
		int start = (page-1)*pageSize;
		example.setOrderByClause(" price desc limit " + start + "," + pageSize);
		return topicMapper.selectByExample(example);
    }
    
    public int getUserTopicCount(long uid){
    	TopicExample example = new TopicExample();
		TopicExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return topicMapper.countByExample(example);
    }

	// 获取所有直播红点
	public List<Topic> getMyLivesByUpdateTime2(long uid, long updateTime, List<Long> topics) {
		TopicExample example = new TopicExample();
		TopicExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andLongTimeLessThan(updateTime);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -3);
		criteria.andLongTimeGreaterThan(calendar.getTimeInMillis());
		criteria.andStatusNotEqualTo(Specification.LiveStatus.REMOVE.index);
		TopicExample.Criteria criteriaOr = example.createCriteria();
		if (topics != null && topics.size() > 0) {
			criteriaOr.andLongTimeLessThan(updateTime);
			criteriaOr.andLongTimeGreaterThan(calendar.getTimeInMillis());
			criteriaOr.andUidNotEqualTo(uid);
			criteriaOr.andIdIn(topics);
			example.or(criteriaOr);
		}
		// 最后更新时间降序排列（必须获取所有的）
		example.setOrderByClause("long_time desc");
		return topicMapper.selectByExample(example);
	}
	
	public Topic getTopicById(long topicId) {
		return topicMapper.selectByPrimaryKey(topicId);
	}
	
	public void updateTopic(Topic topic) {
		topicMapper.updateByPrimaryKeySelective(topic);
	}

	public List<LiveFavorite> getLiveFavoritesByUidAndTopicIds(long uid, List<Long> ids) {
		if (ids.size() > 0) {
			LiveFavoriteExample example = new LiveFavoriteExample();
			LiveFavoriteExample.Criteria criteria = example.createCriteria();
			criteria.andTopicIdIn(ids);
			criteria.andUidEqualTo(uid);
			return liveFavoriteMapper.selectByExample(example);
		} else {
			return new ArrayList<LiveFavorite>();
		}
	}
	
	public LiveFavorite getLiveFavoriteByTopicIdAndUid(long uid, long topicId){
		LiveFavoriteExample example = new LiveFavoriteExample();
		LiveFavoriteExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andTopicIdEqualTo(topicId);
		List<LiveFavorite> list = liveFavoriteMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<LiveFavorite> getLiveFavoriteListByTopicId(long topicId){
		LiveFavoriteExample example = new LiveFavoriteExample();
		LiveFavoriteExample.Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topicId);
		return liveFavoriteMapper.selectByExample(example);
	}
	
	public List<LiveFavorite> getLiveFavoriteListByTopicIdAndUidList(long topicId, List<Long> uidList){
		if(null == uidList || uidList.size() == 0){
			return null;
		}
		LiveFavoriteExample example = new LiveFavoriteExample();
		LiveFavoriteExample.Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topicId);
		criteria.andUidIn(uidList);
		return liveFavoriteMapper.selectByExample(example);
	}
    public List<Long> getTopicId(long uid) {
        List<Long> result = Lists.newArrayList();
        LiveFavoriteExample example = new LiveFavoriteExample();
        LiveFavoriteExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<LiveFavorite> liveFavoriteList = liveFavoriteMapper.selectByExample(example);
        for (LiveFavorite liveFavorite : liveFavoriteList) {
            result.add(liveFavorite.getTopicId());
        }
        return result;
    }
    public int countAggregationSubTopic(long topicId) {
    	TopicAggregationExample example = new TopicAggregationExample();
    	TopicAggregationExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        return topicAggregationMapper.countByExample(example);
    }
    
    public int countAggregationParentTopic(long topicId) {
    	TopicAggregationExample example = new TopicAggregationExample();
    	TopicAggregationExample.Criteria criteria = example.createCriteria();
        criteria.andSubTopicIdEqualTo(topicId);
        return topicAggregationMapper.countByExample(example);
    }
	
    public TopicUserForbid findTopicForbidStatus(long topicId) {
		TopicUserForbidExample example = new TopicUserForbidExample();
		TopicUserForbidExample.Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topicId);
		criteria.andForbidPatternEqualTo(3);
		List<TopicUserForbid> list = topicUserForbidMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
    
    public TopicUserForbid findTopicUserForbidByTopicIdAndUid(long topicId, long uid) {
		TopicUserForbidExample example = new TopicUserForbidExample();
		TopicUserForbidExample.Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topicId);
		criteria.andUidEqualTo(uid);
		criteria.andForbidPatternEqualTo(1);
		List<TopicUserForbid> list = topicUserForbidMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
    
    public int countTotalUserFragment(long topicId, long uid){
    	TopicFragmentExample example = new TopicFragmentExample();
        TopicFragmentExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andUidEqualTo(uid);
        return topicFragmentMapper.countByExample(example);
    }
    
    public int countTotalFragmentByTopicId(long topicId){
    	TopicFragmentExample example = new TopicFragmentExample();
        TopicFragmentExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        return topicFragmentMapper.countByExample(example);
    }
    
    public int countTopicFragmentBeforeFid(long topicId, long fid){
    	TopicFragmentExample example = new TopicFragmentExample();
        TopicFragmentExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andIdLessThanOrEqualTo(fid);
        return topicFragmentMapper.countByExample(example);
    }

    
    public TopicFragmentTemplate getTopicFragmentTemplate(){
        List<TopicFragmentTemplate> list = topicFragmentTemplateMapper.selectByExample(null);
        if(null != list && list.size() > 1){
        	Collections.shuffle(list);
        }
        return list != null && list.size() > 0 ? list.get(0):null;
    } 
	public boolean isNewInTopic(long uid, long topicId){
		TopicReadHisExample example = new TopicReadHisExample();
		TopicReadHisExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andTopicIdEqualTo(topicId);
		int count = topicReadHisMapper.countByExample(example);
		if(count == 0){
			return true;
		}
		return false;
	}
	public List<TopicNews> getTopicNewsList24h(Date  date){
		TopicNewsExample example = new TopicNewsExample();
		TopicNewsExample.Criteria criteria = example.createCriteria();
		criteria.andCreateTimeGreaterThan(date);
		example.setOrderByClause(" id desc limit 10");//只取10条就够了，，多了页面不好处理
		List<TopicNews> list = topicNewsMapper.selectByExample(example);
		return list;
	}
    
	public int saveTopicNews(TopicNews topicNews){
		return topicNewsMapper.insertSelective(topicNews);
	}
	public TopicData getTopicDataByTopicId(long topicId) {
		TopicDataExample example = new TopicDataExample();
		TopicDataExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
		List<TopicData>  list = topicDataMapper.selectByExample(example);
		return list.size()>0?list.get(0):null;
	}
	/**
	 * 获取增长比自己低的王国数
	 * @author chenxiang
	 * @date 2017-06-15
	 * @param 
	 */
    public int getLessPriceChangeTopicCount(int price) {
    	TopicDataExample example = new TopicDataExample();
    	TopicDataExample.Criteria criteria = example.createCriteria();
        criteria.andLastPriceIncrLessThan(price);
        return topicDataMapper.countByExample(example);
    }

	public void saveTopicData(TopicData topicData) {
		topicDataMapper.insertSelective(topicData);
	}
	/**
	 * 获取王国价值总数
	 * @author chenxiang
	 * @date 2017-06-15
	 * @param 
	 */
    public int getTopicDataCount() {
    	TopicDataExample example = new TopicDataExample();
        return topicDataMapper.countByExample(example);
    }
	/**
	 * 获取过去24小时送礼物列表
	 * @author chenxiang
	 * @date 2017-09-06
	 * @param date 24之前时间
	 */
	public List<GiftHistory> getGiftList24h(long topicId,Date date){
		GiftHistoryExample example = new GiftHistoryExample();
		GiftHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andCreateTimeGreaterThan(date);
		criteria.andTopicIdEqualTo(topicId);
        example.setOrderByClause(" gift_price / gift_count desc ");
		List<GiftHistory> list = giftHistoryMapper.selectByExample(example);
		return list;
	}
	
	
	public List<TopicFragmentLikeHis> getTopicFragmentLikeHisListByUidAndImageIds(long uid, List<Long> imageIds){
		TopicFragmentLikeHisExample example = new TopicFragmentLikeHisExample();
		TopicFragmentLikeHisExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andImageIdIn(imageIds);
		return topicFragmentLikeHisMapper.selectByExample(example);
	}
	public TopicFragmentLikeHis getTopicFragmentLikeHisByUidAndImageId(long uid, long imageId){
		TopicFragmentLikeHisExample example = new TopicFragmentLikeHisExample();
		TopicFragmentLikeHisExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andImageIdEqualTo(imageId);
		List<TopicFragmentLikeHis> list = topicFragmentLikeHisMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	public List<TopicCategory> getAllTopicCategory(){
		TopicCategoryExample example = new TopicCategoryExample();
		List<TopicCategory> list = topicCategoryMapper.selectByExample(example);
		return list;
	}
	
	public TopicCategory getTopicCategoryById(int id) {
		return topicCategoryMapper.selectByPrimaryKey(id);
	}
	
	public List<TopicGiven> getMyGivenKingdoms(long uid) {
		TopicGivenExample example = new TopicGivenExample();
		example.createCriteria().andUidEqualTo((int)uid);
		return topicGivenMapper.selectByExample(example);
	}

	private void removeBlockedKingodm(Long topicId) {
		BlockTopicExample example = new BlockTopicExample();
		example.createCriteria().andTopicIdEqualTo(topicId);
		blockTopicMapper.deleteByExample(example);
	}

	public void updateTopicUserConfig(TopicUserConfig topicUserConfig) {
		topicUserConfigMapper.updateByPrimaryKeySelective(topicUserConfig);
	}

	public void createTopicUserConfig(TopicUserConfig topicUserConfig) {
		topicUserConfigMapper.insertSelective(topicUserConfig);
	}
    
    public TopicUserConfig getTopicUserConfigByTopicIdAndUid(long topicId, long uid){
    	TopicUserConfigExample example = new TopicUserConfigExample();
    	TopicUserConfigExample.Criteria criteria = example.createCriteria();
    	criteria.andTopicIdEqualTo(topicId);
    	criteria.andUidEqualTo(uid);
    	List<TopicUserConfig> list = topicUserConfigMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
	public TopicImage getTopicImageByTopicId(Long topicId) {
		TopicImageExample example = new TopicImageExample();
		TopicImageExample.Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topicId);
		criteria.andFidEqualTo(0l);
		List<TopicImage> list =  topicImageMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public void saveTopicImage(TopicImage topicImage) {
		topicImageMapper.insertSelective(topicImage);
	}
	public List<TopicImage> getTopicImageListByFids(List<Long> fidList){
		if(null == fidList || fidList.size() == 0){
			return null;
		}
		TopicImageExample example = new TopicImageExample();
		TopicImageExample.Criteria criteria = example.createCriteria();
		criteria.andFidIn(fidList);
		return topicImageMapper.selectByExampleWithBLOBs(example);
	}
	public void updateTopicImage(TopicImage topicImage) {
		topicImageMapper.updateByPrimaryKeyWithBLOBs(topicImage);
	}
	public List<TopicImage> getTopicImageByTopicIdAndFidAndImageName(long topicId, long fid, String imageName, int type){
		TopicImageExample example = new TopicImageExample();
		TopicImageExample.Criteria criteria = example.createCriteria();
		criteria.andFidEqualTo(fid);
		criteria.andTopicIdEqualTo(topicId);
		if(type > 0){
			criteria.andTypeEqualTo(type);
		}
		if(StringUtils.isNotEmpty(imageName)){
			criteria.andImageEqualTo(imageName);
		}
		return  topicImageMapper.selectByExampleWithBLOBs(example);
	}
	public int countLotteryByTopicId(Long topicId) {
		LotteryInfoExample example = new LotteryInfoExample();
    	LotteryInfoExample.Criteria criteria = example.createCriteria();
    	criteria.andTopicIdEqualTo(topicId);
    	criteria.andStatusNotEqualTo(-1);
    	int count = lotteryInfoMapper.countByExample(example);
        return  count;
	}

	public void createTopicFragment(TopicFragmentWithBLOBs topicFragment) {
		topicFragment.setStatus(Specification.TopicFragmentStatus.ENABLED.index);
        extTopicMapper.saveTopicFragment(topicFragment);
        // 王国更新的时候去掉用户屏蔽的王国。
        this.removeBlockedKingodm(topicFragment.getTopicId());
	}
	
	public List<AcImageElement> getAggregationImage(long topicId) {
		return extTopicMapper.getAggregationImage(topicId);
	}
	
	public List<TeaseInfo> getAllTeaseInfo(){
		TeaseInfoExample example = new TeaseInfoExample();
		return teaseInfoMapper.selectByExample(example);
	}
	public int balanceTopicStealPriceHarvest(long topicId){
		List<Integer> list = extTopicMapper.getBalancePriceByTopicId(topicId);
		if(null != list && list.size() > 0){
			int balancePrice = list.get(0);
			if(balancePrice > 0){
				extTopicMapper.updateTopicDataHarvestPriceByTopicId(balancePrice, topicId);
			}
			return balancePrice;
		}
		return 0;
	}
	
	
	public Map<String, Long> getTopicMembersCount(List<Long> topicIdList) {
		Map<String, Long> result = new HashMap<String, Long>();
		if (null == topicIdList || topicIdList.size() == 0) {
			return result;
		}
		// 查询非核心圈成员
		List<ExtIdCount> list = extTopicMapper.getNoCoreCircleMembers(topicIdList);
		if (null != list && list.size() > 0) {
			for (ExtIdCount m : list) {
				result.put(String.valueOf(m.getId()), m.getCount());
			}
		}
		// 查询核心圈成员
		List<ExtIdCount> list2 = extTopicMapper.getCoreCircleMembers(topicIdList);
		if (null != list2 && list2.size() > 0) {
			Long count = null;
			Long coreCount = null;
			for (ExtIdCount m : list2) {
				coreCount = m.getCount();
				if (coreCount > 0) {
					count = result.get(String.valueOf(m.getId()));
					if (null == count) {
						count = coreCount;
					} else {
						count = count.longValue() + coreCount.longValue();
					}
					result.put(String.valueOf(m.getId()), count);
				}
			}
		}
		return result;
	}

	public int countLotteryContent(long lotteryId, long uid) {
		LotteryContentExample example = new LotteryContentExample();
    	LotteryContentExample.Criteria criteria = example.createCriteria();
    	criteria.andLotteryIdEqualTo(lotteryId);
    	criteria.andUidEqualTo(uid);
        return lotteryContentMapper.countByExample(example);
	}

	public int countLotteryJoinUser(long lotteryId) {
		LotteryContentExample example = new LotteryContentExample();
    	LotteryContentExample.Criteria criteria = example.createCriteria();
    	criteria.andLotteryIdEqualTo(lotteryId);
		return lotteryContentMapper.countByExample(example);
	}

	public int lotteryIsWin(long lotteryId, long uid) {
		LotteryWinExample example = new LotteryWinExample();
    	LotteryWinExample.Criteria criteria = example.createCriteria();
    	criteria.andLotteryIdEqualTo(lotteryId);
    	criteria.andUidEqualTo(uid);
        return lotteryWinMapper.countByExample(example);
	}

}
