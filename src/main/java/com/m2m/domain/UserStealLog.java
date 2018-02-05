package com.m2m.domain;

import java.util.Date;

public class UserStealLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long topicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.stealed_coins
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer stealedCoins;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_steal_log.is_big_red_pack
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer isBigRedPack;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.id
     *
     * @return the value of user_steal_log.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.id
     *
     * @param id the value for user_steal_log.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.uid
     *
     * @return the value of user_steal_log.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.uid
     *
     * @param uid the value for user_steal_log.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.topic_id
     *
     * @return the value of user_steal_log.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.topic_id
     *
     * @param topicId the value for user_steal_log.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.stealed_coins
     *
     * @return the value of user_steal_log.stealed_coins
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getStealedCoins() {
        return stealedCoins;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.stealed_coins
     *
     * @param stealedCoins the value for user_steal_log.stealed_coins
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setStealedCoins(Integer stealedCoins) {
        this.stealedCoins = stealedCoins;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.create_time
     *
     * @return the value of user_steal_log.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.create_time
     *
     * @param createTime the value for user_steal_log.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_steal_log.is_big_red_pack
     *
     * @return the value of user_steal_log.is_big_red_pack
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getIsBigRedPack() {
        return isBigRedPack;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_steal_log.is_big_red_pack
     *
     * @param isBigRedPack the value for user_steal_log.is_big_red_pack
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setIsBigRedPack(Integer isBigRedPack) {
        this.isBigRedPack = isBigRedPack;
    }
}