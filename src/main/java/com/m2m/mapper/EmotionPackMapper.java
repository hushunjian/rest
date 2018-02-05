package com.m2m.mapper;

import com.m2m.domain.EmotionPack;
import com.m2m.domain.EmotionPackExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EmotionPackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int countByExample(EmotionPackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int deleteByExample(EmotionPackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    @Delete({
        "delete from emotion_pack",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    @Insert({
        "insert into emotion_pack (id, name, ",
        "cover, emoji_type, ",
        "version, p_version, ",
        "extra, order_num, ",
        "is_valid)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{cover,jdbcType=VARCHAR}, #{emojiType,jdbcType=INTEGER}, ",
        "#{version,jdbcType=INTEGER}, #{pVersion,jdbcType=INTEGER}, ",
        "#{extra,jdbcType=VARCHAR}, #{orderNum,jdbcType=INTEGER}, ",
        "#{isValid,jdbcType=INTEGER})"
    })
    int insert(EmotionPack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int insertSelective(EmotionPack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    List<EmotionPack> selectByExample(EmotionPackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    @Select({
        "select",
        "id, name, cover, emoji_type, version, p_version, extra, order_num, is_valid",
        "from emotion_pack",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    EmotionPack selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int updateByExampleSelective(@Param("record") EmotionPack record, @Param("example") EmotionPackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int updateByExample(@Param("record") EmotionPack record, @Param("example") EmotionPackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    int updateByPrimaryKeySelective(EmotionPack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emotion_pack
     *
     * @mbggenerated Thu Jan 18 14:47:17 CST 2018
     */
    @Update({
        "update emotion_pack",
        "set name = #{name,jdbcType=VARCHAR},",
          "cover = #{cover,jdbcType=VARCHAR},",
          "emoji_type = #{emojiType,jdbcType=INTEGER},",
          "version = #{version,jdbcType=INTEGER},",
          "p_version = #{pVersion,jdbcType=INTEGER},",
          "extra = #{extra,jdbcType=VARCHAR},",
          "order_num = #{orderNum,jdbcType=INTEGER},",
          "is_valid = #{isValid,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(EmotionPack record);
}