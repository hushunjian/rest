package com.m2m.domain;

import java.util.Date;

public class TopicImage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long topicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.fid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long fid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.image
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.video_url
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String videoUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.like_count
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer likeCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_image.extra
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String extra;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.id
     *
     * @return the value of topic_image.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.id
     *
     * @param id the value for topic_image.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.topic_id
     *
     * @return the value of topic_image.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.topic_id
     *
     * @param topicId the value for topic_image.topic_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.fid
     *
     * @return the value of topic_image.fid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getFid() {
        return fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.fid
     *
     * @param fid the value for topic_image.fid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setFid(Long fid) {
        this.fid = fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.image
     *
     * @return the value of topic_image.image
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.image
     *
     * @param image the value for topic_image.image
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.create_time
     *
     * @return the value of topic_image.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.create_time
     *
     * @param createTime the value for topic_image.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.type
     *
     * @return the value of topic_image.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.type
     *
     * @param type the value for topic_image.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.video_url
     *
     * @return the value of topic_image.video_url
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.video_url
     *
     * @param videoUrl the value for topic_image.video_url
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.like_count
     *
     * @return the value of topic_image.like_count
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.like_count
     *
     * @param likeCount the value for topic_image.like_count
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_image.extra
     *
     * @return the value of topic_image.extra
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getExtra() {
        return extra;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_image.extra
     *
     * @param extra the value for topic_image.extra
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}