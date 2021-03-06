package com.m2m.mapper;

import com.m2m.domain.TagTrainSample;
import com.m2m.domain.TagTrainSampleExample;
import com.m2m.domain.TagTrainSampleWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TagTrainSampleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from tag_train_sample",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into tag_train_sample (id, tag, ",
        "creation_date, alias_tag, ",
        "summary, keywords)",
        "values (#{id,jdbcType=INTEGER}, #{tag,jdbcType=VARCHAR}, ",
        "#{creationDate,jdbcType=TIMESTAMP}, #{aliasTag,jdbcType=VARCHAR}, ",
        "#{summary,jdbcType=LONGVARCHAR}, #{keywords,jdbcType=LONGVARCHAR})"
    })
    int insert(TagTrainSampleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(TagTrainSampleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<TagTrainSampleWithBLOBs> selectByExampleWithBLOBs(TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<TagTrainSample> selectByExample(TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, tag, creation_date, alias_tag, summary, keywords",
        "from tag_train_sample",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TagTrainSampleWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") TagTrainSampleWithBLOBs record, @Param("example") TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") TagTrainSampleWithBLOBs record, @Param("example") TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") TagTrainSample record, @Param("example") TagTrainSampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(TagTrainSampleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update tag_train_sample",
        "set tag = #{tag,jdbcType=VARCHAR},",
          "creation_date = #{creationDate,jdbcType=TIMESTAMP},",
          "alias_tag = #{aliasTag,jdbcType=VARCHAR},",
          "summary = #{summary,jdbcType=LONGVARCHAR},",
          "keywords = #{keywords,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(TagTrainSampleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tag_train_sample
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update tag_train_sample",
        "set tag = #{tag,jdbcType=VARCHAR},",
          "creation_date = #{creationDate,jdbcType=TIMESTAMP},",
          "alias_tag = #{aliasTag,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TagTrainSample record);
}