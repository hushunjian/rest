package com.m2m.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.m2m.dao.TopicDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.QuotationInfo;
import com.m2m.domain.RobotInfo;
import com.m2m.domain.Topic;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserProfile;
import com.m2m.mapper.ExtRobotInfoMapper;
import com.m2m.mapper.LiveFavoriteMapper;
import com.m2m.mapper.TopicMapper;
import com.m2m.mapper.UserFollowMapper;
import com.m2m.request.SpeakRequest;
import com.m2m.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KingdomRobot {

    private static final ScheduledExecutorService ES = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private LiveService liveService;
    
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ExtRobotInfoMapper extRobotInfoMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
	private LiveFavoriteMapper liveFavoriteMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;
    
    @Data
    public static class ReplyTimes implements Serializable{
		private static final long serialVersionUID = -5724439814942875361L;

		private final int min;

        private final int max;

        public ReplyTimes(int min,int max){
            this.min = min;
            this.max = max;
        }
    }

    @Data
    public static class ExecutePolicy implements Serializable{
		private static final long serialVersionUID = -6758655838094037061L;

		private Date createTime;

        private long topicId;
        
        private long kingUid;

        private int lastHour;

        private int min;

        private int max;
    }

    private List<Date> splitTask(ExecutePolicy policy, ReplyTimes replyCount) {
        try {
        	Random random = new Random();
        	
            List<Date> ret = Lists.newArrayList();
            Calendar finalTime = Calendar.getInstance();
            finalTime.setTime(policy.getCreateTime());
            finalTime.add(Calendar.HOUR_OF_DAY,policy.getLastHour());
            log.info("final time : " + DateUtil.date2string(finalTime.getTime(), "yyyy-MM-dd HH:mm:ss"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(policy.getCreateTime());
            Calendar exeCal = Calendar.getInstance();
            while(calendar.before(finalTime)){
                int minute = random.nextInt(policy.getMax())+policy.getMin();
                int replyTimes = random.nextInt(replyCount.getMax())+replyCount.getMin();
                exeCal.setTime(calendar.getTime());
                for(int i = 0;i<replyTimes;i++){
                	int m = random.nextInt(minute)+1;
                	exeCal.add(Calendar.MINUTE, m);
                    log.info("every task execute time : " + DateUtil.date2string(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
                    ret.add(exeCal.getTime());
                    minute = minute - m;
                    if(minute<=0){
                    	break;
                    }
                }
                calendar.add(Calendar.MINUTE, policy.getMax()+policy.getMin());
            }
            
            if(ret.size() > 0){
            	//需要剔除0点到7天这段时间内的时间点
            	Calendar cal = Calendar.getInstance();
            	Date date = null;
            	for(int i=0;i<ret.size();i++){
            		date = ret.get(i);
            		cal.setTime(date);
            		int h = cal.get(Calendar.HOUR_OF_DAY);
            		if(h<7){
            			ret.remove(i);
            			i--;
            		}
            	}
            }
            
            return ret;
        } catch (Exception e) {
        	log.error("拆分任务失败", e);
        }
        return null;
    }

    public void start(ExecutePolicy policy){
    	Random random = new Random();
        // 第一次发言的逻辑
        int sleep = random.nextInt(75)+45;
        ES.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("王国["+policy.getTopicId()+"]第一次机器人回复。。。。@操作。。。。");
                RobotInfo robot = extRobotInfoMapper.getRandomRobotInfo();
                QuotationInfo quotationInfo = extRobotInfoMapper.getRandomQuotationInfoByType(1);//必须语录
                if(null == robot || null == quotationInfo){
                	log.info("当前库中没有机器人或语录，故不再自动回复");
                	return;
                }
                
                Topic topic = topicMapper.selectByPrimaryKey(policy.getTopicId());
                if(null == topic){
                	log.info("王国["+policy.getTopicId()+"]不存在，无所谓了");
                	return;
                }
                
                //有40%概率加入这个王国
                int joinRandom = random.nextInt(100);
                if(joinRandom < 40){
                	LiveFavorite lf = topicDao.getLiveFavoriteByTopicIdAndUid(robot.getUid(), policy.getTopicId());
                	if(null == lf){
                		lf = new LiveFavorite();
                		lf.setUid(robot.getUid());
                		lf.setTopicId(policy.getTopicId());
                		lf.setCreateTime(new Date());
                		liveFavoriteMapper.insertSelective(lf);
                	}
                }
                //有40%概率关注这个用户
                int subscribedRandom = random.nextInt(100);
                if(subscribedRandom < 40){
                	UserFollow userFollow = userDao.getUserFollowBySourceUidAndTargetUid(robot.getUid(), topic.getUid());
                	if(null == userFollow){
                		userFollow = new UserFollow();
                		userFollow.setSourceUid(robot.getUid());
                		userFollow.setTargetUid(topic.getUid());
                		userFollow.setCreateTime(new Date());
                		userFollowMapper.insertSelective(userFollow);
                	}
                }
                
                SpeakRequest speakDto = new SpeakRequest();
                speakDto.setTopicId(policy.getTopicId());
                speakDto.setUid(robot.getUid());
                speakDto.setType(10);
                speakDto.setContentType(10);
                speakDto.setQuotationInfoId(quotationInfo.getId());
                speakDto.setSource(-1);
                speakDto.setAtUid(policy.getKingUid());
                
                JSONObject obj = new JSONObject();
                obj.put("type", "text");
                obj.put("only", UUID.randomUUID().toString()+"-"+random.nextInt());
                obj.put("hAlign", "start");
                UserProfile user = userDao.getUserProfileByUid(policy.getKingUid());
                String atStr = "@" + user.getNickName();
                obj.put("text", atStr + " " + quotationInfo.getQuotation());
                obj.put("atStart", 0);
                obj.put("atEnd", atStr.length());
                obj.put("atUid", policy.getKingUid());
                
                speakDto.setFragment(obj.toJSONString());
                speakDto.setExtra(obj.toJSONString());
                
                liveService.speak(speakDto);
            }
        },sleep,TimeUnit.SECONDS);

        // 前24小时发言的逻辑
        task(policy,new ReplyTimes(1,1));

        // 后24小时发言
        // 调整策略
        // 重新new对象，防止线程间的对象共享造成的脏数据。
        ExecutePolicy policy2 = new ExecutePolicy();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(policy.getCreateTime());
        calendar.add(Calendar.HOUR_OF_DAY,policy.getLastHour());
        policy2.setCreateTime(calendar.getTime());
        policy2.setTopicId(policy.getTopicId());
        policy2.setLastHour(policy.getLastHour());
        policy2.setMin(300);
        policy2.setMax(60);
        task(policy2,new ReplyTimes(1,1));
    }

    private void task(ExecutePolicy policy, ReplyTimes replyTimes) {
        List<Date> ret = splitTask(policy, replyTimes);
        if(null == ret || ret.size() == 0){
        	log.info("任务分割后的时间结果集合必须大于零。");
        	return;
        }
        // 获取不重复的语录
        List<QuotationInfo> quotationInfos = extRobotInfoMapper.selectListQuotationInfo(ret.size());
        if(null == quotationInfos || quotationInfos.size() == 0){
        	log.info("无语料支持");
        	return;
        }
        int qcount = quotationInfos.size();

        for(int i = 0;i<ret.size();i++){
            long delay = ret.get(i).getTime() - System.currentTimeMillis();
            if(delay <= 0){
            	continue;
            }
            log.info("delay time is : " + delay);
            int finalI = i;
            
            ES.schedule(new Runnable() {
                @Override
                public void run() {
                    log.info("execute task to speak ..... ");
                    Random random = new Random();
                    QuotationInfo info = null;
                    if(finalI<qcount){
                    	info = quotationInfos.get(finalI);
                    }else{
                    	info = quotationInfos.get(random.nextInt(qcount));
                    }
                    RobotInfo robot = extRobotInfoMapper.getRandomRobotInfo();
                    if(null == robot){
                    	log.info("无机器人支持");
                    	return;
                    }
                    
                    Topic topic = topicMapper.selectByPrimaryKey(policy.getTopicId());
                    if(null == topic){
                    	log.info("王国["+policy.getTopicId()+"]不存在，无所谓了");
                    	return;
                    }
                    
                    //有40%概率加入这个王国
                    int joinRandom = random.nextInt(100);
                    if(joinRandom < 40){
                    	LiveFavorite lf = topicDao.getLiveFavoriteByTopicIdAndUid(robot.getUid(), policy.getTopicId());
                    	if(null == lf){
                    		lf = new LiveFavorite();
                    		lf.setUid(robot.getUid());
                    		lf.setTopicId(policy.getTopicId());
                    		lf.setCreateTime(new Date());
                    		liveFavoriteMapper.insertSelective(lf);
                    	}
                    }
                    //有40%概率关注这个用户
                    int subscribedRandom = random.nextInt(100);
                    if(subscribedRandom < 40){
                    	UserFollow userFollow = userDao.getUserFollowBySourceUidAndTargetUid(robot.getUid(), topic.getUid());
                    	if(null == userFollow){
                    		userFollow = new UserFollow();
                    		userFollow.setSourceUid(robot.getUid());
                    		userFollow.setTargetUid(topic.getUid());
                    		userFollow.setCreateTime(new Date());
                    		userFollowMapper.insertSelective(userFollow);
                    	}
                    }
                    
                    SpeakRequest speakDto = new SpeakRequest();
                    speakDto.setTopicId(policy.getTopicId());
                    speakDto.setUid(robot.getUid());
                    speakDto.setType(1);
                    speakDto.setContentType(0);
                    speakDto.setQuotationInfoId(info.getId());
                    speakDto.setSource(-1);
                    speakDto.setFragment(info.getQuotation());
                    liveService.speak(speakDto);
                }
            },delay, TimeUnit.MILLISECONDS);
        }
    }

    public static void main(String[] args) throws ParseException {

        Date createTime = DateUtil.string2date("2017-07-25 21:46:00", "yyyy-MM-dd HH:mm:ss");

        KingdomRobot.ExecutePolicy step1 = new KingdomRobot.ExecutePolicy();
        step1.setCreateTime(createTime);
        step1.setTopicId(1000);
        step1.setLastHour(24);
        step1.setMin(180);
        step1.setMax(60);


        KingdomRobot.ExecutePolicy step2 = new KingdomRobot.ExecutePolicy();
        step2.setCreateTime(createTime);
        step2.setTopicId(1000);
        step2.setLastHour(24);
        step2.setMin(300);
        step2.setMax(60);

        List<Date> list = new KingdomRobot().splitTask(step2, new ReplyTimes(1,1));
        for(Date d : list){
        	System.out.println(DateUtil.date2string(d, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}