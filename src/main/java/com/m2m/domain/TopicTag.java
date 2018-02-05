package com.m2m.domain;

import java.util.Date;

public class TopicTag {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String tag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.is_rec
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer isRec;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.is_sys
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer isSys;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.status
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.user_hobby_ids
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String userHobbyIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.pid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.order_num
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer orderNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic_tag.cover_img
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String coverImg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.id
     *
     * @return the value of topic_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.id
     *
     * @param id the value for topic_tag.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.tag
     *
     * @return the value of topic_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.tag
     *
     * @param tag the value for topic_tag.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.is_rec
     *
     * @return the value of topic_tag.is_rec
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getIsRec() {
        return isRec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.is_rec
     *
     * @param isRec the value for topic_tag.is_rec
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setIsRec(Integer isRec) {
        this.isRec = isRec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.is_sys
     *
     * @return the value of topic_tag.is_sys
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getIsSys() {
        return isSys;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.is_sys
     *
     * @param isSys the value for topic_tag.is_sys
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setIsSys(Integer isSys) {
        this.isSys = isSys;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.status
     *
     * @return the value of topic_tag.status
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.status
     *
     * @param status the value for topic_tag.status
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.create_time
     *
     * @return the value of topic_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.create_time
     *
     * @param createTime the value for topic_tag.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.user_hobby_ids
     *
     * @return the value of topic_tag.user_hobby_ids
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getUserHobbyIds() {
        return userHobbyIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.user_hobby_ids
     *
     * @param userHobbyIds the value for topic_tag.user_hobby_ids
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setUserHobbyIds(String userHobbyIds) {
        this.userHobbyIds = userHobbyIds == null ? null : userHobbyIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.pid
     *
     * @return the value of topic_tag.pid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.pid
     *
     * @param pid the value for topic_tag.pid
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.order_num
     *
     * @return the value of topic_tag.order_num
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.order_num
     *
     * @param orderNum the value for topic_tag.order_num
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic_tag.cover_img
     *
     * @return the value of topic_tag.cover_img
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic_tag.cover_img
     *
     * @param coverImg the value for topic_tag.cover_img
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }
}