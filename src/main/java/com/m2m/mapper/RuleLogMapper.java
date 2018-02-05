package com.m2m.mapper;

import com.m2m.domain.RuleLog;
import com.m2m.domain.RuleLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RuleLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(RuleLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(RuleLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from rule_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into rule_log (id, uid, rule_code, ",
        "rule_name, coin, ",
        "create_time, ext)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{ruleCode,jdbcType=INTEGER}, ",
        "#{ruleName,jdbcType=VARCHAR}, #{coin,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{ext,jdbcType=BIGINT})"
    })
    int insert(RuleLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(RuleLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<RuleLog> selectByExample(RuleLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, uid, rule_code, rule_name, coin, create_time, ext",
        "from rule_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    RuleLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") RuleLog record, @Param("example") RuleLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") RuleLog record, @Param("example") RuleLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(RuleLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule_log
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update rule_log",
        "set uid = #{uid,jdbcType=BIGINT},",
          "rule_code = #{ruleCode,jdbcType=INTEGER},",
          "rule_name = #{ruleName,jdbcType=VARCHAR},",
          "coin = #{coin,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "ext = #{ext,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RuleLog record);
}