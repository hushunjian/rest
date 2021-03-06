package com.m2m.mapper;

import com.m2m.domain.UserTag;
import com.m2m.domain.UserTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserTagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(UserTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(UserTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from user_tag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into user_tag (id, uid, tag_id, ",
        "type, score, is_top, ",
        "top_time, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{tagId,jdbcType=BIGINT}, ",
        "#{type,jdbcType=INTEGER}, #{score,jdbcType=INTEGER}, #{isTop,jdbcType=INTEGER}, ",
        "#{topTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(UserTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<UserTag> selectByExample(UserTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, uid, tag_id, type, score, is_top, top_time, create_time",
        "from user_tag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserTag selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserTag record, @Param("example") UserTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") UserTag record, @Param("example") UserTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(UserTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update user_tag",
        "set uid = #{uid,jdbcType=BIGINT},",
          "tag_id = #{tagId,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "score = #{score,jdbcType=INTEGER},",
          "is_top = #{isTop,jdbcType=INTEGER},",
          "top_time = #{topTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserTag record);
}