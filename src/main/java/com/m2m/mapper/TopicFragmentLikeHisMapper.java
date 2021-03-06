package com.m2m.mapper;

import com.m2m.domain.TopicFragmentLikeHis;
import com.m2m.domain.TopicFragmentLikeHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TopicFragmentLikeHisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(TopicFragmentLikeHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(TopicFragmentLikeHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from topic_fragment_like_his",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into topic_fragment_like_his (id, fid, image_id, ",
        "uid, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{fid,jdbcType=BIGINT}, #{imageId,jdbcType=BIGINT}, ",
        "#{uid,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(TopicFragmentLikeHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(TopicFragmentLikeHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<TopicFragmentLikeHis> selectByExample(TopicFragmentLikeHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, fid, image_id, uid, create_time",
        "from topic_fragment_like_his",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    TopicFragmentLikeHis selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") TopicFragmentLikeHis record, @Param("example") TopicFragmentLikeHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") TopicFragmentLikeHis record, @Param("example") TopicFragmentLikeHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(TopicFragmentLikeHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic_fragment_like_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update topic_fragment_like_his",
        "set fid = #{fid,jdbcType=BIGINT},",
          "image_id = #{imageId,jdbcType=BIGINT},",
          "uid = #{uid,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TopicFragmentLikeHis record);
}