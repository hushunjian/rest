package com.m2m.domain;

import java.util.Date;

public class VoteOption {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vote_option.id
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vote_option.voteId
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    private Long voteid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vote_option.optionName
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    private String optionname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vote_option.create_time
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vote_option.id
     *
     * @return the value of vote_option.id
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vote_option.id
     *
     * @param id the value for vote_option.id
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vote_option.voteId
     *
     * @return the value of vote_option.voteId
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public Long getVoteid() {
        return voteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vote_option.voteId
     *
     * @param voteid the value for vote_option.voteId
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public void setVoteid(Long voteid) {
        this.voteid = voteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vote_option.optionName
     *
     * @return the value of vote_option.optionName
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public String getOptionname() {
        return optionname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vote_option.optionName
     *
     * @param optionname the value for vote_option.optionName
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public void setOptionname(String optionname) {
        this.optionname = optionname == null ? null : optionname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vote_option.create_time
     *
     * @return the value of vote_option.create_time
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vote_option.create_time
     *
     * @param createTime the value for vote_option.create_time
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}