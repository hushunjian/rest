package com.m2m.mapper;

import com.m2m.domain.LiveFavorite;
import com.m2m.domain.LiveFavoriteExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LiveFavoriteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(LiveFavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(LiveFavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from live_favorite",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into live_favorite (id, topic_id, ",
        "uid, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{topicId,jdbcType=BIGINT}, ",
        "#{uid,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(LiveFavorite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(LiveFavorite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<LiveFavorite> selectByExample(LiveFavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, topic_id, uid, create_time",
        "from live_favorite",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    LiveFavorite selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") LiveFavorite record, @Param("example") LiveFavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") LiveFavorite record, @Param("example") LiveFavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(LiveFavorite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_favorite
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update live_favorite",
        "set topic_id = #{topicId,jdbcType=BIGINT},",
          "uid = #{uid,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(LiveFavorite record);
}