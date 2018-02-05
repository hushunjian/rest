package com.m2m.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 设置value形式，带超时时间
     * @param key
     * @param value
     * @param timeout
     */
    public void setex(String key, String value, int timeout){
    	redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 删除缓存
     * @param key
     */
    public void del(String key){
    	redisTemplate.delete(key);
    }
    
    /**
     * 自增1
     * @param key
     * @return
     */
    public long incr(String key) {
    	return redisTemplate.opsForValue().increment(key, 1);
    }
    
    /**
     * Hash操作，设置
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key,String field,String value){
    	redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Hash操作，获取
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key,String field){
    	return (String)redisTemplate.opsForHash().get(key, field);
    }
    
    public List<String> lrange(String key,long start,long end){
    	return redisTemplate.opsForList().range(key, start, end);
    }
    
    public void lrem(String key, long count, String value) {
    	redisTemplate.opsForList().remove(key, count, value);
    }
    /**
     * Set操作，查询全部
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
    	return redisTemplate.opsForSet().members(key);
    }
    /**
     * 增加一个数
     * @param key
     */
    public void incrby(String key,long num){
    	redisTemplate.opsForValue().increment(key, num);
    }
}
