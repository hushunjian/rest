package com.m2m.mapper;

import com.m2m.domain.LotteryWin;
import com.m2m.domain.LotteryWinExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LotteryWinMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int countByExample(LotteryWinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int deleteByExample(LotteryWinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    @Delete({
        "delete from lottery_win",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    @Insert({
        "insert into lottery_win (id, lottery_id, ",
        "uid, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{lotteryId,jdbcType=BIGINT}, ",
        "#{uid,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(LotteryWin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int insertSelective(LotteryWin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    List<LotteryWin> selectByExample(LotteryWinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    @Select({
        "select",
        "id, lottery_id, uid, create_time",
        "from lottery_win",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    LotteryWin selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int updateByExampleSelective(@Param("record") LotteryWin record, @Param("example") LotteryWinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int updateByExample(@Param("record") LotteryWin record, @Param("example") LotteryWinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    int updateByPrimaryKeySelective(LotteryWin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_win
     *
     * @mbggenerated Mon Jan 22 14:33:23 CST 2018
     */
    @Update({
        "update lottery_win",
        "set lottery_id = #{lotteryId,jdbcType=BIGINT},",
          "uid = #{uid,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(LotteryWin record);
}