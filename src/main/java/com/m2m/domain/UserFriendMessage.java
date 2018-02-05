package com.m2m.domain;

import java.util.Date;

public class UserFriendMessage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.source_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long sourceUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend_message.target_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long targetUid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.id
     *
     * @return the value of user_friend_message.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.id
     *
     * @param id the value for user_friend_message.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.uid
     *
     * @return the value of user_friend_message.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.uid
     *
     * @param uid the value for user_friend_message.uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.content
     *
     * @return the value of user_friend_message.content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.content
     *
     * @param content the value for user_friend_message.content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.create_time
     *
     * @return the value of user_friend_message.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.create_time
     *
     * @param createTime the value for user_friend_message.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.source_uid
     *
     * @return the value of user_friend_message.source_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getSourceUid() {
        return sourceUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.source_uid
     *
     * @param sourceUid the value for user_friend_message.source_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setSourceUid(Long sourceUid) {
        this.sourceUid = sourceUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend_message.target_uid
     *
     * @return the value of user_friend_message.target_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTargetUid() {
        return targetUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend_message.target_uid
     *
     * @param targetUid the value for user_friend_message.target_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTargetUid(Long targetUid) {
        this.targetUid = targetUid;
    }
}