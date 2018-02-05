package com.m2m.mapper;

import com.m2m.domain.UserGag;
import com.m2m.domain.UserGagExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserGagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(UserGagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(UserGagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from user_gag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into user_gag (id, target_uid, ",
        "uid, type, cid, ",
        "gag_level)",
        "values (#{id,jdbcType=BIGINT}, #{targetUid,jdbcType=BIGINT}, ",
        "#{uid,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, #{cid,jdbcType=BIGINT}, ",
        "#{gagLevel,jdbcType=INTEGER})"
    })
    int insert(UserGag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(UserGag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<UserGag> selectByExample(UserGagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, target_uid, uid, type, cid, gag_level",
        "from user_gag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserGag selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserGag record, @Param("example") UserGagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") UserGag record, @Param("example") UserGagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(UserGag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_gag
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update user_gag",
        "set target_uid = #{targetUid,jdbcType=BIGINT},",
          "uid = #{uid,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "cid = #{cid,jdbcType=BIGINT},",
          "gag_level = #{gagLevel,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserGag record);
}