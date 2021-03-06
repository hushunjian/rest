package com.m2m.mapper;

import com.m2m.domain.Dictionary;
import com.m2m.domain.DictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DictionaryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int countByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int deleteByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    @Delete({
        "delete from dictionary",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    @Insert({
        "insert into dictionary (id, tid, value, ",
        "sort)",
        "values (#{id,jdbcType=BIGINT}, #{tid,jdbcType=BIGINT}, #{value,jdbcType=VARCHAR}, ",
        "#{sort,jdbcType=INTEGER})"
    })
    int insert(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int insertSelective(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    List<Dictionary> selectByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    @Select({
        "select",
        "id, tid, value, sort",
        "from dictionary",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    Dictionary selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    int updateByPrimaryKeySelective(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary
     *
     * @mbggenerated Sat Jan 20 13:58:53 CST 2018
     */
    @Update({
        "update dictionary",
        "set tid = #{tid,jdbcType=BIGINT},",
          "value = #{value,jdbcType=VARCHAR},",
          "sort = #{sort,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Dictionary record);
}