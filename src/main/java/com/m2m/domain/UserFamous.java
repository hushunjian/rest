package com.m2m.domain;

import java.util.Date;

public class UserFamous {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_famous.id
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_famous.uid
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_famous.update_time
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_famous.id
     *
     * @return the value of user_famous.id
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_famous.id
     *
     * @param id the value for user_famous.id
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_famous.uid
     *
     * @return the value of user_famous.uid
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_famous.uid
     *
     * @param uid the value for user_famous.uid
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_famous.update_time
     *
     * @return the value of user_famous.update_time
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_famous.update_time
     *
     * @param updateTime the value for user_famous.update_time
     *
     * @mbggenerated Thu Jan 11 16:50:54 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}