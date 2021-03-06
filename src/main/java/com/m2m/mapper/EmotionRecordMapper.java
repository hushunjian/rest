package com.m2m.mapper;

import com.m2m.domain.EmotionRecord;
import com.m2m.domain.EmotionRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EmotionRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int countByExample(EmotionRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int deleteByExample(EmotionRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    @Delete({
        "delete from emotion_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    @Insert({
        "insert into emotion_record (id, uid, emotionId, ",
        "happyValue, freeValue, ",
        "create_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{emotionid,jdbcType=BIGINT}, ",
        "#{happyvalue,jdbcType=INTEGER}, #{freevalue,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(EmotionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int insertSelective(EmotionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    List<EmotionRecord> selectByExample(EmotionRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    @Select({
        "select",
        "id, uid, emotionId, happyValue, freeValue, create_time",
        "from emotion_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    EmotionRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") EmotionRecord record, @Param("example") EmotionRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int updateByExample(@Param("record") EmotionRecord record, @Param("example") EmotionRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    int updateByPrimaryKeySelective(EmotionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_record
     *
     * @mbggenerated Mon Jan 22 10:22:56 CST 2018
     */
    @Update({
        "update emotion_record",
        "set uid = #{uid,jdbcType=BIGINT},",
          "emotionId = #{emotionid,jdbcType=BIGINT},",
          "happyValue = #{happyvalue,jdbcType=INTEGER},",
          "freeValue = #{freevalue,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(EmotionRecord record);
}