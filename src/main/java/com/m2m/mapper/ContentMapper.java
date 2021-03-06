package com.m2m.mapper;

import com.m2m.domain.Content;
import com.m2m.domain.ContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ContentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from content",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into content (id, uid, forward_cid, ",
        "title, feeling, ",
        "type, conver_image, ",
        "forward_title, forward_url, ",
        "content_type, thumbnail, ",
        "hot_value, person_count, ",
        "review_count, like_count, ",
        "favorite_count, read_count, ",
        "read_count_dummy, is_top, ",
        "authorization, rights, ",
        "author, status, ",
        "create_time, update_time, ",
        "update_id, ugc_status, ",
        "content)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{forwardCid,jdbcType=BIGINT}, ",
        "#{title,jdbcType=VARCHAR}, #{feeling,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{converImage,jdbcType=VARCHAR}, ",
        "#{forwardTitle,jdbcType=VARCHAR}, #{forwardUrl,jdbcType=VARCHAR}, ",
        "#{contentType,jdbcType=INTEGER}, #{thumbnail,jdbcType=VARCHAR}, ",
        "#{hotValue,jdbcType=INTEGER}, #{personCount,jdbcType=INTEGER}, ",
        "#{reviewCount,jdbcType=INTEGER}, #{likeCount,jdbcType=INTEGER}, ",
        "#{favoriteCount,jdbcType=INTEGER}, #{readCount,jdbcType=INTEGER}, ",
        "#{readCountDummy,jdbcType=INTEGER}, #{isTop,jdbcType=INTEGER}, ",
        "#{authorization,jdbcType=INTEGER}, #{rights,jdbcType=INTEGER}, ",
        "#{author,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateId,jdbcType=BIGINT}, #{ugcStatus,jdbcType=INTEGER}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    int insert(Content record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(Content record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<Content> selectByExampleWithBLOBs(ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<Content> selectByExample(ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, uid, forward_cid, title, feeling, type, conver_image, forward_title, forward_url, ",
        "content_type, thumbnail, hot_value, person_count, review_count, like_count, ",
        "favorite_count, read_count, read_count_dummy, is_top, authorization, rights, ",
        "author, status, create_time, update_time, update_id, ugc_status, content",
        "from content",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("ResultMapWithBLOBs")
    Content selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") Content record, @Param("example") ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") Content record, @Param("example") ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") Content record, @Param("example") ContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(Content record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update content",
        "set uid = #{uid,jdbcType=BIGINT},",
          "forward_cid = #{forwardCid,jdbcType=BIGINT},",
          "title = #{title,jdbcType=VARCHAR},",
          "feeling = #{feeling,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "conver_image = #{converImage,jdbcType=VARCHAR},",
          "forward_title = #{forwardTitle,jdbcType=VARCHAR},",
          "forward_url = #{forwardUrl,jdbcType=VARCHAR},",
          "content_type = #{contentType,jdbcType=INTEGER},",
          "thumbnail = #{thumbnail,jdbcType=VARCHAR},",
          "hot_value = #{hotValue,jdbcType=INTEGER},",
          "person_count = #{personCount,jdbcType=INTEGER},",
          "review_count = #{reviewCount,jdbcType=INTEGER},",
          "like_count = #{likeCount,jdbcType=INTEGER},",
          "favorite_count = #{favoriteCount,jdbcType=INTEGER},",
          "read_count = #{readCount,jdbcType=INTEGER},",
          "read_count_dummy = #{readCountDummy,jdbcType=INTEGER},",
          "is_top = #{isTop,jdbcType=INTEGER},",
          "authorization = #{authorization,jdbcType=INTEGER},",
          "rights = #{rights,jdbcType=INTEGER},",
          "author = #{author,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_id = #{updateId,jdbcType=BIGINT},",
          "ugc_status = #{ugcStatus,jdbcType=INTEGER},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Content record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table content
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update content",
        "set uid = #{uid,jdbcType=BIGINT},",
          "forward_cid = #{forwardCid,jdbcType=BIGINT},",
          "title = #{title,jdbcType=VARCHAR},",
          "feeling = #{feeling,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "conver_image = #{converImage,jdbcType=VARCHAR},",
          "forward_title = #{forwardTitle,jdbcType=VARCHAR},",
          "forward_url = #{forwardUrl,jdbcType=VARCHAR},",
          "content_type = #{contentType,jdbcType=INTEGER},",
          "thumbnail = #{thumbnail,jdbcType=VARCHAR},",
          "hot_value = #{hotValue,jdbcType=INTEGER},",
          "person_count = #{personCount,jdbcType=INTEGER},",
          "review_count = #{reviewCount,jdbcType=INTEGER},",
          "like_count = #{likeCount,jdbcType=INTEGER},",
          "favorite_count = #{favoriteCount,jdbcType=INTEGER},",
          "read_count = #{readCount,jdbcType=INTEGER},",
          "read_count_dummy = #{readCountDummy,jdbcType=INTEGER},",
          "is_top = #{isTop,jdbcType=INTEGER},",
          "authorization = #{authorization,jdbcType=INTEGER},",
          "rights = #{rights,jdbcType=INTEGER},",
          "author = #{author,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_id = #{updateId,jdbcType=BIGINT},",
          "ugc_status = #{ugcStatus,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Content record);
}