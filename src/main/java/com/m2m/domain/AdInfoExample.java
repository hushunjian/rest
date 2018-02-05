package com.m2m.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public AdInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAdTitleIsNull() {
            addCriterion("ad_title is null");
            return (Criteria) this;
        }

        public Criteria andAdTitleIsNotNull() {
            addCriterion("ad_title is not null");
            return (Criteria) this;
        }

        public Criteria andAdTitleEqualTo(String value) {
            addCriterion("ad_title =", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleNotEqualTo(String value) {
            addCriterion("ad_title <>", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleGreaterThan(String value) {
            addCriterion("ad_title >", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleGreaterThanOrEqualTo(String value) {
            addCriterion("ad_title >=", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleLessThan(String value) {
            addCriterion("ad_title <", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleLessThanOrEqualTo(String value) {
            addCriterion("ad_title <=", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleLike(String value) {
            addCriterion("ad_title like", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleNotLike(String value) {
            addCriterion("ad_title not like", value, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleIn(List<String> values) {
            addCriterion("ad_title in", values, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleNotIn(List<String> values) {
            addCriterion("ad_title not in", values, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleBetween(String value1, String value2) {
            addCriterion("ad_title between", value1, value2, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdTitleNotBetween(String value1, String value2) {
            addCriterion("ad_title not between", value1, value2, "adTitle");
            return (Criteria) this;
        }

        public Criteria andAdCoverIsNull() {
            addCriterion("ad_cover is null");
            return (Criteria) this;
        }

        public Criteria andAdCoverIsNotNull() {
            addCriterion("ad_cover is not null");
            return (Criteria) this;
        }

        public Criteria andAdCoverEqualTo(String value) {
            addCriterion("ad_cover =", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverNotEqualTo(String value) {
            addCriterion("ad_cover <>", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverGreaterThan(String value) {
            addCriterion("ad_cover >", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverGreaterThanOrEqualTo(String value) {
            addCriterion("ad_cover >=", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverLessThan(String value) {
            addCriterion("ad_cover <", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverLessThanOrEqualTo(String value) {
            addCriterion("ad_cover <=", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverLike(String value) {
            addCriterion("ad_cover like", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverNotLike(String value) {
            addCriterion("ad_cover not like", value, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverIn(List<String> values) {
            addCriterion("ad_cover in", values, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverNotIn(List<String> values) {
            addCriterion("ad_cover not in", values, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverBetween(String value1, String value2) {
            addCriterion("ad_cover between", value1, value2, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverNotBetween(String value1, String value2) {
            addCriterion("ad_cover not between", value1, value2, "adCover");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthIsNull() {
            addCriterion("ad_cover_width is null");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthIsNotNull() {
            addCriterion("ad_cover_width is not null");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthEqualTo(Integer value) {
            addCriterion("ad_cover_width =", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthNotEqualTo(Integer value) {
            addCriterion("ad_cover_width <>", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthGreaterThan(Integer value) {
            addCriterion("ad_cover_width >", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_cover_width >=", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthLessThan(Integer value) {
            addCriterion("ad_cover_width <", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthLessThanOrEqualTo(Integer value) {
            addCriterion("ad_cover_width <=", value, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthIn(List<Integer> values) {
            addCriterion("ad_cover_width in", values, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthNotIn(List<Integer> values) {
            addCriterion("ad_cover_width not in", values, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthBetween(Integer value1, Integer value2) {
            addCriterion("ad_cover_width between", value1, value2, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_cover_width not between", value1, value2, "adCoverWidth");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightIsNull() {
            addCriterion("ad_cover_height is null");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightIsNotNull() {
            addCriterion("ad_cover_height is not null");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightEqualTo(Integer value) {
            addCriterion("ad_cover_height =", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightNotEqualTo(Integer value) {
            addCriterion("ad_cover_height <>", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightGreaterThan(Integer value) {
            addCriterion("ad_cover_height >", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_cover_height >=", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightLessThan(Integer value) {
            addCriterion("ad_cover_height <", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightLessThanOrEqualTo(Integer value) {
            addCriterion("ad_cover_height <=", value, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightIn(List<Integer> values) {
            addCriterion("ad_cover_height in", values, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightNotIn(List<Integer> values) {
            addCriterion("ad_cover_height not in", values, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightBetween(Integer value1, Integer value2) {
            addCriterion("ad_cover_height between", value1, value2, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andAdCoverHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_cover_height not between", value1, value2, "adCoverHeight");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIsNull() {
            addCriterion("effective_time is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIsNotNull() {
            addCriterion("effective_time is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeEqualTo(Date value) {
            addCriterion("effective_time =", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotEqualTo(Date value) {
            addCriterion("effective_time <>", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeGreaterThan(Date value) {
            addCriterion("effective_time >", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("effective_time >=", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeLessThan(Date value) {
            addCriterion("effective_time <", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("effective_time <=", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIn(List<Date> values) {
            addCriterion("effective_time in", values, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotIn(List<Date> values) {
            addCriterion("effective_time not in", values, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeBetween(Date value1, Date value2) {
            addCriterion("effective_time between", value1, value2, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("effective_time not between", value1, value2, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityIsNull() {
            addCriterion("display_probability is null");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityIsNotNull() {
            addCriterion("display_probability is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityEqualTo(Integer value) {
            addCriterion("display_probability =", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityNotEqualTo(Integer value) {
            addCriterion("display_probability <>", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityGreaterThan(Integer value) {
            addCriterion("display_probability >", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityGreaterThanOrEqualTo(Integer value) {
            addCriterion("display_probability >=", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityLessThan(Integer value) {
            addCriterion("display_probability <", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityLessThanOrEqualTo(Integer value) {
            addCriterion("display_probability <=", value, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityIn(List<Integer> values) {
            addCriterion("display_probability in", values, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityNotIn(List<Integer> values) {
            addCriterion("display_probability not in", values, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityBetween(Integer value1, Integer value2) {
            addCriterion("display_probability between", value1, value2, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andDisplayProbabilityNotBetween(Integer value1, Integer value2) {
            addCriterion("display_probability not between", value1, value2, "displayProbability");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNull() {
            addCriterion("topic_id is null");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNotNull() {
            addCriterion("topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andTopicIdEqualTo(Long value) {
            addCriterion("topic_id =", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotEqualTo(Long value) {
            addCriterion("topic_id <>", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThan(Long value) {
            addCriterion("topic_id >", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThanOrEqualTo(Long value) {
            addCriterion("topic_id >=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThan(Long value) {
            addCriterion("topic_id <", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThanOrEqualTo(Long value) {
            addCriterion("topic_id <=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdIn(List<Long> values) {
            addCriterion("topic_id in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotIn(List<Long> values) {
            addCriterion("topic_id not in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdBetween(Long value1, Long value2) {
            addCriterion("topic_id between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotBetween(Long value1, Long value2) {
            addCriterion("topic_id not between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andAdUrlIsNull() {
            addCriterion("ad_url is null");
            return (Criteria) this;
        }

        public Criteria andAdUrlIsNotNull() {
            addCriterion("ad_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdUrlEqualTo(String value) {
            addCriterion("ad_url =", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlNotEqualTo(String value) {
            addCriterion("ad_url <>", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlGreaterThan(String value) {
            addCriterion("ad_url >", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlGreaterThanOrEqualTo(String value) {
            addCriterion("ad_url >=", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlLessThan(String value) {
            addCriterion("ad_url <", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlLessThanOrEqualTo(String value) {
            addCriterion("ad_url <=", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlLike(String value) {
            addCriterion("ad_url like", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlNotLike(String value) {
            addCriterion("ad_url not like", value, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlIn(List<String> values) {
            addCriterion("ad_url in", values, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlNotIn(List<String> values) {
            addCriterion("ad_url not in", values, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlBetween(String value1, String value2) {
            addCriterion("ad_url between", value1, value2, "adUrl");
            return (Criteria) this;
        }

        public Criteria andAdUrlNotBetween(String value1, String value2) {
            addCriterion("ad_url not between", value1, value2, "adUrl");
            return (Criteria) this;
        }

        public Criteria andBannerIdIsNull() {
            addCriterion("banner_id is null");
            return (Criteria) this;
        }

        public Criteria andBannerIdIsNotNull() {
            addCriterion("banner_id is not null");
            return (Criteria) this;
        }

        public Criteria andBannerIdEqualTo(Long value) {
            addCriterion("banner_id =", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdNotEqualTo(Long value) {
            addCriterion("banner_id <>", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdGreaterThan(Long value) {
            addCriterion("banner_id >", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("banner_id >=", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdLessThan(Long value) {
            addCriterion("banner_id <", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdLessThanOrEqualTo(Long value) {
            addCriterion("banner_id <=", value, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdIn(List<Long> values) {
            addCriterion("banner_id in", values, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdNotIn(List<Long> values) {
            addCriterion("banner_id not in", values, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdBetween(Long value1, Long value2) {
            addCriterion("banner_id between", value1, value2, "bannerId");
            return (Criteria) this;
        }

        public Criteria andBannerIdNotBetween(Long value1, Long value2) {
            addCriterion("banner_id not between", value1, value2, "bannerId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ad_info
     *
     * @mbggenerated do_not_delete_during_merge Thu Jan 11 17:51:54 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}