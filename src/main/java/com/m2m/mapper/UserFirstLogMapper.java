package com.m2m.mapper;

import com.m2m.domain.UserFirstLog;
import com.m2m.domain.UserFirstLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserFirstLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(UserFirstLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(UserFirstLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from user_first_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into user_first_log (id, uid, action_type, ",
        "create_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{actionType,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserFirstLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(UserFirstLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<UserFirstLog> selectByExample(UserFirstLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, uid, action_type, create_time",
        "from user_first_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserFirstLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserFirstLog record, @Param("example") UserFirstLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") UserFirstLog record, @Param("example") UserFirstLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(UserFirstLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_first_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update user_first_log",
        "set uid = #{uid,jdbcType=BIGINT},",
          "action_type = #{actionType,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserFirstLog record);
}