package com.m2m.domain;

import java.util.Date;

public class TopicAggregation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long topicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.sub_topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long subTopicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.is_top
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer isTop;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.is_publish
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer isPublish;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_aggregation.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.id
     *
     * @return the value of topic_aggregation.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.id
     *
     * @param id the value for topic_aggregation.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.topic_id
     *
     * @return the value of topic_aggregation.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.topic_id
     *
     * @param topicId the value for topic_aggregation.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.sub_topic_id
     *
     * @return the value of topic_aggregation.sub_topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getSubTopicId() {
        return subTopicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.sub_topic_id
     *
     * @param subTopicId the value for topic_aggregation.sub_topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setSubTopicId(Long subTopicId) {
        this.subTopicId = subTopicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.is_top
     *
     * @return the value of topic_aggregation.is_top
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getIsTop() {
        return isTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.is_top
     *
     * @param isTop the value for topic_aggregation.is_top
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.is_publish
     *
     * @return the value of topic_aggregation.is_publish
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getIsPublish() {
        return isPublish;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.is_publish
     *
     * @param isPublish the value for topic_aggregation.is_publish
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.create_time
     *
     * @return the value of topic_aggregation.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.create_time
     *
     * @param createTime the value for topic_aggregation.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_aggregation.update_time
     *
     * @return the value of topic_aggregation.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_aggregation.update_time
     *
     * @param updateTime the value for topic_aggregation.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}