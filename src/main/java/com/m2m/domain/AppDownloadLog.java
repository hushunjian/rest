package com.m2m.domain;

import java.util.Date;

public class AppDownloadLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_download_log.id
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_download_log.uid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_download_log.fromUid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    private Long fromuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_download_log.create_time
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_download_log.id
     *
     * @return the value of app_download_log.id
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_download_log.id
     *
     * @param id the value for app_download_log.id
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_download_log.uid
     *
     * @return the value of app_download_log.uid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_download_log.uid
     *
     * @param uid the value for app_download_log.uid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_download_log.fromUid
     *
     * @return the value of app_download_log.fromUid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public Long getFromuid() {
        return fromuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_download_log.fromUid
     *
     * @param fromuid the value for app_download_log.fromUid
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public void setFromuid(Long fromuid) {
        this.fromuid = fromuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_download_log.create_time
     *
     * @return the value of app_download_log.create_time
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_download_log.create_time
     *
     * @param createTime the value for app_download_log.create_time
     *
     * @mbggenerated Wed Jan 17 14:16:55 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}