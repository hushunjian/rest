package com.m2m.domain;

public class ActivityWithBLOBs extends Activity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.activity_content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String activityContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.activity_result
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    private String activityResult;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.activity_content
     *
     * @return the value of activity.activity_content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getActivityContent() {
        return activityContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.activity_content
     *
     * @param activityContent the value for activity.activity_content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent == null ? null : activityContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.activity_result
     *
     * @return the value of activity.activity_result
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public String getActivityResult() {
        return activityResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.activity_result
     *
     * @param activityResult the value for activity.activity_result
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    public void setActivityResult(String activityResult) {
        this.activityResult = activityResult == null ? null : activityResult.trim();
    }
}