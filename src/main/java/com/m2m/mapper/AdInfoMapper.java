package com.m2m.mapper;

import com.m2m.domain.AdInfo;
import com.m2m.domain.AdInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(AdInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(AdInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from ad_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into ad_info (id, ad_title, ",
        "ad_cover, ad_cover_width, ",
        "ad_cover_height, effective_time, ",
        "create_time, display_probability, ",
        "type, topic_id, ad_url, ",
        "banner_id, status)",
        "values (#{id,jdbcType=BIGINT}, #{adTitle,jdbcType=VARCHAR}, ",
        "#{adCover,jdbcType=VARCHAR}, #{adCoverWidth,jdbcType=INTEGER}, ",
        "#{adCoverHeight,jdbcType=INTEGER}, #{effectiveTime,jdbcType=TIMESTAMP}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{displayProbability,jdbcType=INTEGER}, ",
        "#{type,jdbcType=INTEGER}, #{topicId,jdbcType=BIGINT}, #{adUrl,jdbcType=VARCHAR}, ",
        "#{bannerId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER})"
    })
    int insert(AdInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(AdInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<AdInfo> selectByExample(AdInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, ad_title, ad_cover, ad_cover_width, ad_cover_height, effective_time, create_time, ",
        "display_probability, type, topic_id, ad_url, banner_id, status",
        "from ad_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    AdInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") AdInfo record, @Param("example") AdInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") AdInfo record, @Param("example") AdInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(AdInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update ad_info",
        "set ad_title = #{adTitle,jdbcType=VARCHAR},",
          "ad_cover = #{adCover,jdbcType=VARCHAR},",
          "ad_cover_width = #{adCoverWidth,jdbcType=INTEGER},",
          "ad_cover_height = #{adCoverHeight,jdbcType=INTEGER},",
          "effective_time = #{effectiveTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "display_probability = #{displayProbability,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "topic_id = #{topicId,jdbcType=BIGINT},",
          "ad_url = #{adUrl,jdbcType=VARCHAR},",
          "banner_id = #{bannerId,jdbcType=BIGINT},",
          "status = #{status,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AdInfo record);
}