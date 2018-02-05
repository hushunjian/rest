package com.m2m.domain;

import java.util.Date;

public class BillboardList {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.list_key
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String listKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.target_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long targetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.since_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer sinceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column billboard_list.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.id
     *
     * @return the value of billboard_list.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.id
     *
     * @param id the value for billboard_list.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.list_key
     *
     * @return the value of billboard_list.list_key
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getListKey() {
        return listKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.list_key
     *
     * @param listKey the value for billboard_list.list_key
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setListKey(String listKey) {
        this.listKey = listKey == null ? null : listKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.target_id
     *
     * @return the value of billboard_list.target_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.target_id
     *
     * @param targetId the value for billboard_list.target_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.type
     *
     * @return the value of billboard_list.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.type
     *
     * @param type the value for billboard_list.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.since_id
     *
     * @return the value of billboard_list.since_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getSinceId() {
        return sinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.since_id
     *
     * @param sinceId the value for billboard_list.since_id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setSinceId(Integer sinceId) {
        this.sinceId = sinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column billboard_list.update_time
     *
     * @return the value of billboard_list.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column billboard_list.update_time
     *
     * @param updateTime the value for billboard_list.update_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}