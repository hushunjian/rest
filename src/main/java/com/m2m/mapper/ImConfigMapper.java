package com.m2m.mapper;

import com.m2m.domain.ImConfig;
import com.m2m.domain.ImConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ImConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int countByExample(ImConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int deleteByExample(ImConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    @Delete({
        "delete from im_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    @Insert({
        "insert into im_config (id, uid, token, ",
        "create_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{token,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(ImConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int insertSelective(ImConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    List<ImConfig> selectByExample(ImConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    @Select({
        "select",
        "id, uid, token, create_time",
        "from im_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    ImConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int updateByExampleSelective(@Param("record") ImConfig record, @Param("example") ImConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int updateByExample(@Param("record") ImConfig record, @Param("example") ImConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    int updateByPrimaryKeySelective(ImConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_config
     *
     * @mbggenerated Thu Jan 18 15:01:28 CST 2018
     */
    @Update({
        "update im_config",
        "set uid = #{uid,jdbcType=BIGINT},",
          "token = #{token,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ImConfig record);
}