package com.m2m.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import com.m2m.dao.TopicDao;
import com.m2m.domain.TagTrainSampleWithBLOBs;

/**
 * 内容打标签
 * @author pc340
 *
 */
@Service
public class ContentTagService {

	private static final Logger log = LoggerFactory.getLogger(ContentTagService.class);
	
	private  Map<String, String> KEY_TAG_MAPPING = new HashMap<>();
	
	@Autowired
	private TopicDao topicDao;
	
	@PostConstruct
	private void init() {
		// 初始化标签样本
		log.info("indexTag started");
		List<TagTrainSampleWithBLOBs> pageList = topicDao.getAllTagTrainSample();
		if(null != pageList && pageList.size() > 0){
			for (TagTrainSampleWithBLOBs data : pageList) {
				// 精确匹配，建立关键词映射
				String tag = data.getTag().trim();
				String keys = data.getKeywords();
				if(!StringUtils.isEmpty(keys)){
					for(String k:keys.split(",")){
						k=k.trim();
						if(!StringUtils.isEmpty(k)){
							KEY_TAG_MAPPING.put(k,tag);
						}
					}
				}
			}
		}
		
		Configuration cfg=DefaultConfig.getInstance();
		Dictionary.initial(cfg);
		// 加载自定义词典。
		Dictionary.getSingleton().addWords(this.KEY_TAG_MAPPING.keySet());
		log.info("indexTag finished.");
	}
	
	/**
	 * 根据content内容匹配标签，并把匹配度高的count个标签返回
	 * @param content
	 * @param count
	 * @return
	 */
	public List<String> getContentTag(String content, int count){
		IKSegmenter seg = new IKSegmenter(new StringReader(content), true);

		Lexeme lex = null;
		Map<String,Integer> tagScore= new TreeMap<>();

		try {
			while ((lex = seg.next()) != null) {
				String key = lex.getLexemeText();
				String tag = this.KEY_TAG_MAPPING.get(key);
				if (tag!=null) {	// 如果在词典里出现就打分。
					Integer score= tagScore.get(key);
					if(score==null){
						score=0;
					}
					score++;
					tagScore.put(tag,score);
				}
			}
		} catch (IOException e) {
			log.error("匹配标签失败", e);
		}
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(tagScore.entrySet());
		//然后通过比较器来实现排序
		Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
			//升序排序
			public int compare(Map.Entry<String, Integer> o1,
							   Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		List<String> ret = new ArrayList<String>();
		int pos=0;
		for(Map.Entry<String,Integer> mapping:list){
			if(pos<count) {
				ret.add(mapping.getKey());
			}else{
				break;
			}
			pos++;
		}
		return ret;
	}
}
