package com.m2m.domain;

import java.util.Date;

public class TopicBadTag {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_bad_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_bad_tag.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long topicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_bad_tag.reporter_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long reporterUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_bad_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_bad_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String tag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_bad_tag.id
     *
     * @return the value of topic_bad_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_bad_tag.id
     *
     * @param id the value for topic_bad_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_bad_tag.topic_id
     *
     * @return the value of topic_bad_tag.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_bad_tag.topic_id
     *
     * @param topicId the value for topic_bad_tag.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_bad_tag.reporter_uid
     *
     * @return the value of topic_bad_tag.reporter_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getReporterUid() {
        return reporterUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_bad_tag.reporter_uid
     *
     * @param reporterUid the value for topic_bad_tag.reporter_uid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setReporterUid(Long reporterUid) {
        this.reporterUid = reporterUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_bad_tag.create_time
     *
     * @return the value of topic_bad_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_bad_tag.create_time
     *
     * @param createTime the value for topic_bad_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_bad_tag.tag
     *
     * @return the value of topic_bad_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_bad_tag.tag
     *
     * @param tag the value for topic_bad_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}