package com.m2m.mapper;

import com.m2m.domain.QuotationInfo;
import com.m2m.domain.QuotationInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface QuotationInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(QuotationInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(QuotationInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from quotation_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into quotation_info (id, quotation, ",
        "type, create_time)",
        "values (#{id,jdbcType=BIGINT}, #{quotation,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(QuotationInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(QuotationInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<QuotationInfo> selectByExample(QuotationInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, quotation, type, create_time",
        "from quotation_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    QuotationInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") QuotationInfo record, @Param("example") QuotationInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") QuotationInfo record, @Param("example") QuotationInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(QuotationInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quotation_info
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update quotation_info",
        "set quotation = #{quotation,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(QuotationInfo record);
}