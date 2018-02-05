package com.m2m.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.m2m.domain.TopicBadTag;
import com.m2m.domain.TopicBadTagExample;
import com.m2m.domain.TopicTag;
import com.m2m.domain.TopicTagDetail;
import com.m2m.domain.TopicTagDetailExample;
import com.m2m.domain.TopicTagExample;
import com.m2m.domain.UserTag;
import com.m2m.domain.UserTagExample;
import com.m2m.mapper.TopicBadTagMapper;
import com.m2m.mapper.ExtTagMapper;
import com.m2m.mapper.TopicTagDetailMapper;
import com.m2m.mapper.TopicTagMapper;
import com.m2m.mapper.UserTagMapper;

@Repository
public class TopicTagDao {

	@Autowired
	private TopicTagMapper topicTagMapper;
	@Autowired
	private TopicTagDetailMapper topicTagDetailMapper;
	@Autowired
	private TopicBadTagMapper topicBadTagMapper;
	@Autowired
	private UserTagMapper userTagMapper;
	@Autowired
	private ExtTagMapper extTagMapper;

	public List<TopicTag> getTopicTagListByIds(List<Long> ids) {
		if (ids.size() > 0) {
			TopicTagExample example = new TopicTagExample();
			TopicTagExample.Criteria criteria = example.createCriteria();
			criteria.andIdIn(ids);
			return topicTagMapper.selectByExample(example);
		} else {
			return new ArrayList<TopicTag>();
		}
	}
    public TopicTag getTopicTagByTag(String tag){
    	TopicTagExample example = new TopicTagExample();
    	TopicTagExample.Criteria criteria = example.createCriteria();
    	criteria.andTagEqualTo(tag);
    	criteria.andStatusEqualTo(0);
    	List<TopicTag> list = topicTagMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    public TopicTag getTopicTagById(long id){
    	TopicTagExample example = new TopicTagExample();
    	TopicTagExample.Criteria criteria = example.createCriteria();
    	criteria.andIdEqualTo(id);
    	criteria.andStatusEqualTo(0);
    	List<TopicTag> list = topicTagMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    public UserTag getUserTagByUidAndTagid(long uid,long tagId){
    	UserTagExample example = new UserTagExample();
    	UserTagExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andTagIdEqualTo(tagId);
    	example.setOrderByClause(" id asc limit 1 ");
    	List<UserTag> list = userTagMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public boolean isExistsTopicAutoTag(long topicId){
    	TopicTagDetailExample example = new TopicTagDetailExample();
    	TopicTagDetailExample.Criteria criteria = example.createCriteria();
    	criteria.andTopicIdEqualTo(topicId);
    	criteria.andAutoTagEqualTo(1);
    	criteria.andStatusEqualTo(0);
    	List<TopicTagDetail> list = topicTagDetailMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return true;
    	}
    	return false;
    }
    
    public boolean isExistsTopicTag(long topicId, String tag){
    	TopicTagDetailExample example = new TopicTagDetailExample();
    	TopicTagDetailExample.Criteria criteria = example.createCriteria();
    	criteria.andTopicIdEqualTo(topicId);
    	criteria.andStatusEqualTo(0);
    	criteria.andTagEqualTo(tag);
    	List<TopicTagDetail> list = topicTagDetailMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return true;
    	}
    	return false;
    }
    
    public List<TopicTagDetail> getTopicTagDetailsByTopicId(long topicId){
        TopicTagDetailExample example = new TopicTagDetailExample();
        TopicTagDetailExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause(" create_time asc ");
        return topicTagDetailMapper.selectByExample(example);
    }
    
    public List<TopicTagDetail> getTopicTagDetailListByTopicIds(List<Long> idList){
       	if(null == idList || idList.size() == 0){
    		return new ArrayList<TopicTagDetail>();
    	}
        TopicTagDetailExample example = new TopicTagDetailExample();
        TopicTagDetailExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdIn(idList);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause(" topic_id asc,id asc ");
        return topicTagDetailMapper.selectByExample(example);
    }
    
    public boolean isExistsTopicBadTag(long topicId, String tag){
    	TopicBadTagExample example = new TopicBadTagExample();
    	TopicBadTagExample.Criteria criteria = example.createCriteria();
    	criteria.andTopicIdEqualTo(topicId);
    	criteria.andTagEqualTo(tag);
    	List<TopicBadTag> list = topicBadTagMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return true;
    	}
    	return false;
    }
    
	public void removeUserDislikeUserTags(long uid,List<Long> idList){
		extTagMapper.removeUserDislikeUserTags(uid, idList);
    }

	public List<TopicTag> getTopicTagsByTags(List<String> tags) {
		return extTagMapper.getTopicTagsByTags(tags);
	}
	public List<String> getExistTopicTagByTags(List<String> tags) {
		return extTagMapper.getExistTopicTagByTags(tags);
	}
	public void saveNewTopicTag(List<TopicTag> needAddTopicTags) {
		extTagMapper.saveNewTopicTag(needAddTopicTags);
	}
	public void saveTopicTagDetail(List<TopicTagDetail> topicTagDetails) {
		extTagMapper.saveTopicTagDetail(topicTagDetails);
	}
}
