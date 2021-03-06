package com.m2m.domain;

import java.util.Date;

public class QuotationInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quotation_info.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quotation_info.quotation
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String quotation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quotation_info.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quotation_info.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quotation_info.id
     *
     * @return the value of quotation_info.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quotation_info.id
     *
     * @param id the value for quotation_info.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quotation_info.quotation
     *
     * @return the value of quotation_info.quotation
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getQuotation() {
        return quotation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quotation_info.quotation
     *
     * @param quotation the value for quotation_info.quotation
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setQuotation(String quotation) {
        this.quotation = quotation == null ? null : quotation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quotation_info.type
     *
     * @return the value of quotation_info.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quotation_info.type
     *
     * @param type the value for quotation_info.type
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quotation_info.create_time
     *
     * @return the value of quotation_info.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quotation_info.create_time
     *
     * @param createTime the value for quotation_info.create_time
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}