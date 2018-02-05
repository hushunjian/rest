package com.m2m.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.m2m.domain.Billboard;
import com.m2m.domain.BillboardDetails;
import com.m2m.domain.BillboardDetailsExample;
import com.m2m.domain.BillboardExample;
import com.m2m.domain.BillboardList;
import com.m2m.domain.BillboardListExample;
import com.m2m.domain.BillboardRelation;
import com.m2m.domain.BillboardRelationExample;
import com.m2m.domain.Content;
import com.m2m.domain.ContentExample;
import com.m2m.domain.ContentLikesDetailsExample;
import com.m2m.domain.EmotionPack;
import com.m2m.domain.EmotionPackDetail;
import com.m2m.domain.EmotionPackDetailExample;
import com.m2m.domain.EmotionPackExample;
import com.m2m.domain.GiftInfo;
import com.m2m.domain.GiftInfoExample;
import com.m2m.mapper.BillboardDetailsMapper;
import com.m2m.mapper.BillboardListMapper;
import com.m2m.mapper.BillboardMapper;
import com.m2m.mapper.BillboardRelationMapper;
import com.m2m.mapper.ContentLikesDetailsMapper;
import com.m2m.mapper.ContentMapper;
import com.m2m.mapper.EmotionPackDetailMapper;
import com.m2m.mapper.EmotionPackMapper;
import com.m2m.mapper.GiftInfoMapper;
import com.m2m.web.Specification;

@Repository
public class ContentDao {

	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private BillboardDetailsMapper billboardDetailsMapper;
	@Autowired
	private BillboardMapper billboardMapper;
	@Autowired
	private BillboardRelationMapper billboardRelationMapper;
	@Autowired
	private BillboardListMapper billboardListMapper;
	@Autowired
	private ContentLikesDetailsMapper contentLikesDetailsMapper;
	@Autowired
	private EmotionPackDetailMapper emotionPackDetailMapper;
	@Autowired
	private EmotionPackMapper emotionPackMapper;
	@Autowired
	private GiftInfoMapper giftInfoMapper;
	
	
	public List<Content> getContentListByIds(List<Long> idList) {
		ContentExample example = new ContentExample();
		ContentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		return contentMapper.selectByExample(example);
	}
	public List<Content> getContentListByTopicIds(List<Long> topicIdList){
		if(null == topicIdList || topicIdList.size() == 0){
			return null;
		}
		ContentExample example = new ContentExample();
		ContentExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(3);//王国
		criteria.andForwardCidIn(topicIdList);
		return contentMapper.selectByExample(example);
	}
	
	public void updateContentUpdateInfo4Kingdom(long topicId, Date updateTime, long updateId){
		ContentExample example = new ContentExample();
		ContentExample.Criteria criteria = example.createCriteria();
		criteria.andForwardCidEqualTo(topicId);
		criteria.andTypeEqualTo(3);//王国
		
		Content updateEntity = new Content();
		updateEntity.setUpdateId(updateId);
		updateEntity.setUpdateTime(updateTime);
		
		contentMapper.updateByExampleSelective(updateEntity, example);
	}

    public Content getContentByTopicId(long topicId){
        ContentExample example = new ContentExample();
        ContentExample.Criteria criteria =example.createCriteria();
        criteria.andForwardCidEqualTo(topicId);
        criteria.andTypeEqualTo(Specification.ArticleType.LIVE.index);
        List<Content> list = contentMapper.selectByExample(example);
        return (list!=null&&list.size()>0)?list.get(0):null;
    }
    
    public List<BillboardDetails> getShowListPageByType(int sinceId, int type){
    	BillboardDetailsExample example = new BillboardDetailsExample();
    	BillboardDetailsExample.Criteria criteria = example.createCriteria();
    	criteria.andTypeEqualTo(type);
    	criteria.andSortGreaterThan(sinceId);
    	criteria.andStatusEqualTo(1);//只要上架的
    	example.setOrderByClause(" sort asc limit 10");
    	return billboardDetailsMapper.selectByExample(example);
    }
    
    public List<Billboard> loadBillboardByBids(List<Long> ids) {
        BillboardExample example = new BillboardExample();
        BillboardExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return billboardMapper.selectByExample(example);
    }
    
    public List<BillboardRelation> getRelationListPage(long bid, int sinceId, int pageSize, List<Long> noTargetIds){
    	BillboardRelationExample example = new BillboardRelationExample();
        BillboardRelationExample.Criteria criteria = example.createCriteria();
        criteria.andSourceIdEqualTo(bid);
        criteria.andSortGreaterThan(sinceId);
        if(null != noTargetIds && noTargetIds.size() > 0){
        	criteria.andTargetIdNotIn(noTargetIds);
        }
        if(pageSize > 0){
        	example.setOrderByClause(" sort asc limit " + pageSize);
        }else{
        	example.setOrderByClause(" sort asc ");
        }
        return billboardRelationMapper.selectByExample(example);
    }
    
    /**
     * 获取异步统计的自动榜单
     * @param key
     * @param sinceId
     * @param pageSize
     * @param noTargetIds
     * @return
     */
    public List<BillboardList> getBillBoardListPage(String key, int sinceId, int pageSize, List<Long> noTargetIds){
    	BillboardListExample example = new BillboardListExample();
    	BillboardListExample.Criteria criteria = example.createCriteria();
    	criteria.andListKeyEqualTo(key);
    	if(null != noTargetIds && noTargetIds.size() > 0){
    		criteria.andTargetIdNotIn(noTargetIds);
    	}
    	criteria.andSinceIdGreaterThan(sinceId);
    	if(pageSize > 0){
    		example.setOrderByClause(" since_id asc limit " + pageSize);
    	}else{
    		example.setOrderByClause(" since_id asc ");
    	}
    	return billboardListMapper.selectByExample(example);
    }

	public int isLike(Long contentId, long uid) {
		ContentLikesDetailsExample example = new ContentLikesDetailsExample();
        ContentLikesDetailsExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(contentId);
        criteria.andUidEqualTo(uid);
        int count = contentLikesDetailsMapper.countByExample(example);
        return  count > 0 ? 1 : 0;
	}

	public List<EmotionPackDetail> getEmotionPackDetailListByPid(int packageId) {
		EmotionPackDetailExample example = new EmotionPackDetailExample();
		example.createCriteria().andPackIdEqualTo(packageId);
		example.setOrderByClause("order_num asc");
		List<EmotionPackDetail> detailList = emotionPackDetailMapper.selectByExample(example);
		return detailList == null ? new ArrayList<EmotionPackDetail>() : detailList;
	}

	public List<EmotionPack> getEmotionPackList() {
		EmotionPackExample example = new EmotionPackExample();
		example.createCriteria().andIsValidEqualTo(1);
		example.setOrderByClause("order_num desc");
		List<EmotionPack> packList = emotionPackMapper.selectByExample(example);
		return packList == null ? new ArrayList<EmotionPack>() : packList;
	}

	public List<GiftInfo> getGiftInfoList() {
		GiftInfoExample example = new GiftInfoExample();
		GiftInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);
		example.setOrderByClause(" sort_number asc ");
		return giftInfoMapper.selectByExample(example);
	}
}
