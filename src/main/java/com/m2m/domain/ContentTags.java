package com.m2m.domain;

public class ContentTags {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column content_tags.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column content_tags.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String tag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column content_tags.hot
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private Integer hot;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column content_tags.id
     *
     * @return the value of content_tags.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column content_tags.id
     *
     * @param id the value for content_tags.id
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column content_tags.tag
     *
     * @return the value of content_tags.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column content_tags.tag
     *
     * @param tag the value for content_tags.tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column content_tags.hot
     *
     * @return the value of content_tags.hot
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Integer getHot() {
        return hot;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column content_tags.hot
     *
     * @param hot the value for content_tags.hot
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setHot(Integer hot) {
        this.hot = hot;
    }
}