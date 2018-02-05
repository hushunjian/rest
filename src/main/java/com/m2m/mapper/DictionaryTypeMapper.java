package com.m2m.mapper;

import com.m2m.domain.DictionaryType;
import com.m2m.domain.DictionaryTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DictionaryTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int countByExample(DictionaryTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int deleteByExample(DictionaryTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    @Delete({
        "delete from dictionary_type",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    @Insert({
        "insert into dictionary_type (id, name)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR})"
    })
    int insert(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int insertSelective(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    List<DictionaryType> selectByExample(DictionaryTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    @Select({
        "select",
        "id, name",
        "from dictionary_type",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    DictionaryType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int updateByExampleSelective(@Param("record") DictionaryType record, @Param("example") DictionaryTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int updateByExample(@Param("record") DictionaryType record, @Param("example") DictionaryTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    int updateByPrimaryKeySelective(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dictionary_type
     *
     * @mbggenerated Sat Jan 20 14:05:14 CST 2018
     */
    @Update({
        "update dictionary_type",
        "set name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DictionaryType record);
}