package com.m2m.mapper;

import com.m2m.domain.TopicTagDetail;
import com.m2m.domain.TopicTagDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TopicTagDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(TopicTagDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(TopicTagDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from topic_tag_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into topic_tag_detail (id, topic_id, ",
        "uid, tag_id, tag, ",
        "create_time, status, ",
        "auto_tag)",
        "values (#{id,jdbcType=BIGINT}, #{topicId,jdbcType=BIGINT}, ",
        "#{uid,jdbcType=BIGINT}, #{tagId,jdbcType=BIGINT}, #{tag,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{status,jdbcType=INTEGER}, ",
        "#{autoTag,jdbcType=INTEGER})"
    })
    int insert(TopicTagDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(TopicTagDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<TopicTagDetail> selectByExample(TopicTagDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, topic_id, uid, tag_id, tag, create_time, status, auto_tag",
        "from topic_tag_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    TopicTagDetail selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") TopicTagDetail record, @Param("example") TopicTagDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") TopicTagDetail record, @Param("example") TopicTagDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(TopicTagDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_tag_detail
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update topic_tag_detail",
        "set topic_id = #{topicId,jdbcType=BIGINT},",
          "uid = #{uid,jdbcType=BIGINT},",
          "tag_id = #{tagId,jdbcType=BIGINT},",
          "tag = #{tag,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "auto_tag = #{autoTag,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TopicTagDetail record);
}