package com.m2m.mapper;

import com.m2m.domain.VoteRecord;
import com.m2m.domain.VoteRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface VoteRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int countByExample(VoteRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int deleteByExample(VoteRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    @Delete({
        "delete from vote_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    @Insert({
        "insert into vote_record (id, uid, voteId, ",
        "optionId, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{voteid,jdbcType=BIGINT}, ",
        "#{optionid,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(VoteRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int insertSelective(VoteRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    List<VoteRecord> selectByExample(VoteRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    @Select({
        "select",
        "id, uid, voteId, optionId, create_time",
        "from vote_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    VoteRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") VoteRecord record, @Param("example") VoteRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int updateByExample(@Param("record") VoteRecord record, @Param("example") VoteRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    int updateByPrimaryKeySelective(VoteRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote_record
     *
     * @mbggenerated Wed Jan 17 14:32:35 CST 2018
     */
    @Update({
        "update vote_record",
        "set uid = #{uid,jdbcType=BIGINT},",
          "voteId = #{voteid,jdbcType=BIGINT},",
          "optionId = #{optionid,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(VoteRecord record);
}